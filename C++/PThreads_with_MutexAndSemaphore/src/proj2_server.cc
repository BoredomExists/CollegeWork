// Copyright 2026 Christian Biermann

#include <proj2/lib/domain_socket.h>
#include <proj2/lib/file_reader.h>
#include <proj2/lib/sha_solver.h>
#include <proj2/lib/thread_log.h>
#include <pthread.h>
#include <semaphore.h>
#include <signal.h>
#include <sys/sysinfo.h>

#include <iostream>
#include <vector>
#include <queue>
#include <cstddef>
#include <cstdint>
#include <cstring>
#include <algorithm>

struct ClientRequest {
    std::string clientAddr;
    std::vector<std::string> file_paths;
    std::vector<std::uint32_t> rows_per_file;
    std::uint32_t neededReaders;
    std::uint32_t neededSolvers;
};

// Declare Functions
void Signal_Handler(int status);
void* Start_Routine(void* arg);
void ParseMessage(const std::string& message,
                  std::string* clientAddress,
                  std::vector<std::string>* file_paths,
                  std::vector<std::uint32_t>* rows_per_file);
bool CheckForDeadLock(const ClientRequest& request);

// Set by signal handler when the server should stop
volatile sig_atomic_t signalStatus = 0;

// Create variables for the mutex, semaphor, message queue, and requests queue
::pthread_mutex_t mutex;
::sem_t semaphore;
std::queue<std::string> msgQ;
std::queue<ClientRequest> requests;

// Create variables for the amount of readers, solvers
std::uint32_t nReaders;
std::uint32_t kSHASolvers;
std::uint32_t availableSolvers;
std::uint32_t availableReaders;

int main(int argc, char *argv[]) {
    if (argc < 4) {
        std::cout << "Usage: " << argv[0] << " <server_name> "
        << " <n-file_readers> <k-SHA_solvers>" << std::endl;
        return 0;
    }

    // Initialize readers, solvers, and server name
    std::string serverName = argv[1];
    nReaders = std::atoi(argv[2]);
    kSHASolvers = std::atoi(argv[3]);
    availableSolvers = kSHASolvers;
    availableReaders = nReaders;

    // Initialize the signal hander for shutdown
    ::signal(SIGINT, Signal_Handler);
    ::signal(SIGTERM, Signal_Handler);

    // Get number of threads and initialize mutex and semaphore
    int n = ::get_nprocs_conf();
    ::pthread_mutex_init(&mutex, nullptr);
    ::sem_init(&semaphore, 0, 0);

    // Create threads
    std::vector<pthread_t> threadPool(n);
    for (int i = 0; i < n; ++i) {
        ::pthread_create(&threadPool[i], nullptr, &Start_Routine, nullptr);
    }

    // Initialize file readers and solvers
    proj2::FileReaders::Init(nReaders);
    proj2::ShaSolvers::Init(kSHASolvers);

    // Create and bind server endpoint
    proj2::UnixDomainDatagramEndpoint server(serverName);
    server.Init();

    // Receive requests until a SIGTERM or SIGINT occurs
    for (;;) {
        if (signalStatus) break;

        std::string dump;
        std::string message = server.RecvFrom(&dump, 65000);
        ::pthread_mutex_lock(&mutex);  // Lock Mutex
        msgQ.push(message);  // Push Message into queue
        ::pthread_mutex_unlock(&mutex);  // Unlock Mutex
        ::sem_post(&semaphore);  // Call Up on semaphore (Starts a thread)
    }

    // Wake all blocked threads for clean exit
    for (std::size_t i = 0; i < threadPool.size(); ++i) {
        sem_post(&semaphore);
    }

    // Wait for all blocked threads to finish
    for (std::size_t i = 0; i < threadPool.size(); ++i) {
        ::pthread_join(threadPool[i], nullptr);
    }

    // Destroy mutex and semaphore
    ::pthread_mutex_destroy(&mutex);
    ::sem_destroy(&semaphore);

    return 0;
}

// Thread Function
void* Start_Routine(void*) {
    for (;;) {
        ::sem_wait(&semaphore);  // Waits until a message is available

        ClientRequest requestToProcess;  // Will be assigned to either be a new request or a blocked request
        bool requestValid = false;  // Will determine if the request can be process without worry of a deadlock

        ::pthread_mutex_lock(&mutex);  // Locks the mutex

        // Checks if there are any blocked requests
        // then checks if the process will still deadlock
        // if not, process that request instead of a new one
        if (!requests.empty()) {
            ClientRequest pendingRequest = requests.front();
            if (CheckForDeadLock(pendingRequest)) {
                availableReaders -= pendingRequest.neededReaders;
                availableSolvers -= pendingRequest.neededSolvers;
                requests.pop();
                requestToProcess = pendingRequest;
                requestValid = true;
                ::pthread_mutex_unlock(&mutex);
            }
        }

        // If there is not a request to process, create a new request
        if (!requestValid) {
            // If the queue is empty, check server status
            if (msgQ.empty()) {
                ::pthread_mutex_unlock(&mutex);

                if (signalStatus)
                    break;
                continue;
            }

            // Gets the message from the front of the queue and pops it
            // unlocks mutex after
            std::string msg = msgQ.front();
            msgQ.pop();
            ::pthread_mutex_unlock(&mutex);

            // Create variables to have data put into from ParseMessage
            std::string clientAddr;
            std::vector<std::string> file_paths;
            std::vector<std::uint32_t> rows_per_file;
            ParseMessage(msg, &clientAddr, &file_paths, &rows_per_file);

            // Ignores empty requests
            if (rows_per_file.empty()) {
                continue;
            }

            // Find the max number of rows in the list of files
            std::vector<std::uint32_t>::iterator maxValue =
                std::max_element(rows_per_file.begin(), rows_per_file.end());

            // Gets the specific number from iterator
            std::uint32_t maxHandlers = *maxValue;

            // Build a client request
            ClientRequest clientReq = {
                clientAddr,
                file_paths,
                rows_per_file,
                static_cast<std::uint32_t>(file_paths.size()),
                maxHandlers
            };

            // Lock the mutex and check if the request
            // will deadlock the system
            ::pthread_mutex_lock(&mutex);
            if (CheckForDeadLock(clientReq)) {

                // Subtract from the available readers and solvers
                // and unlock the mutex
                availableReaders -= clientReq.neededReaders;
                availableSolvers -= clientReq.neededSolvers;
                requestToProcess = clientReq;
                requestValid = true;
                ::pthread_mutex_unlock(&mutex);
            } else {
                // If it will deadlock, push it to a blocked queue
                requests.push(clientReq);
                ::pthread_mutex_unlock(&mutex);
                continue;
            }
        }

        // Checkout the SHA solvers and File Readers
        proj2::SolverHandle solverHandle =
        proj2::ShaSolvers::Checkout(requestToProcess.neededSolvers);

        proj2::ReaderHandle readerHandle =
        proj2::FileReaders::Checkout(requestToProcess.neededReaders,
                            &solverHandle);

        // Creates the hashOut vector and
        // processes the file paths, rows, and fills the hashOut
        std::vector<std::vector<proj2::ReaderHandle::HashType>>
            hashOut(requestToProcess.file_paths.size());
        readerHandle.Process(requestToProcess.file_paths,
                requestToProcess.rows_per_file, &hashOut);

        // Creates the response to send back to client
        std::string res;
        for (std::size_t i = 0; i < hashOut.size(); ++i) {
            for (std::size_t j = 0; j < hashOut[i].size(); ++j) {
            res.append(hashOut[i][j].data(), 64);
            }
        }

        // Initializes the stream client and sends the response
        proj2::UnixDomainStreamClient reply(requestToProcess.clientAddr);
        reply.Init();
        reply.Write(res.data(), res.size());

        // Check back in the solvers and readers (Gives back resources)
        proj2::FileReaders::Checkin(std::move(readerHandle));
        proj2::ShaSolvers::Checkin(std::move(solverHandle));

        // Lock the mutex and add back
        // the readers and solvers
        ::pthread_mutex_lock(&mutex);
        availableReaders += requestToProcess.neededReaders;
        availableSolvers += requestToProcess.neededSolvers;

        // Unlock the mutex and awake up another thread
        ::pthread_mutex_unlock(&mutex);
        ::sem_post(&semaphore);
    }
    return nullptr;
}

// Function to parse the message recieved from the client
// clientAddress, file_paths, rows_per_file are pointers
// because cpplint sucks and tells me to either const it or pointer it
void ParseMessage(const std::string& message,
                  std::string* clientAddress,
                  std::vector<std::string>* file_paths,
                  std::vector<std::uint32_t>* rows_per_file) {
    // Initializes n (increment pointer by) and gets the message data
    std::size_t n = 0;
    const char *c_ptr = message.data();
    std::uint32_t int_Value;

    // Gets the reply endpoint length
    std::memcpy(&int_Value, c_ptr + n, 4);
    n += 4;

    // Gets the reply endpoint string
    clientAddress->assign(c_ptr + n, int_Value);
    n += int_Value;

    // Gets the file count
    std::memcpy(&int_Value, c_ptr + n, 4);
    n += 4;

    // Clears the vectors from any previous message
    file_paths->clear();
    rows_per_file->clear();

    // Read each file path and row count
    for (std::size_t i = 0; i < int_Value; ++i) {
        std::uint32_t pathLength;

        // Gets the path length
        std::memcpy(&pathLength, c_ptr + n, 4);
        n += 4;

        // Gets the path string
        file_paths->emplace_back(c_ptr + n, pathLength);
        n += pathLength;

        // Gets the row count
        std::uint32_t rowCount;
        std::memcpy(&rowCount, c_ptr + n, 4);
        n += 4;

        // Adds the row count into the vector
        rows_per_file->push_back(rowCount);
    }
}

bool CheckForDeadLock(const ClientRequest& request) {
    return request.neededReaders <= availableReaders &&
           request.neededSolvers <= availableSolvers;
}

// Signal Handler function telling the main loop to exit
void Signal_Handler(int) {
    signalStatus = 1;
}
