// Copyright 2026 Christian Biermann
#include <proj1/lib/cli_parser.h>
#include <proj1/lib/error.h>
#include <proj1/lib/sha256.h>
#include <proj1/lib/thread_log.h>
#include <proj1/lib/timings.h>
#include <pthread.h>
#include <sys/sysinfo.h>
#include <fstream>

#include <iostream>
#include <vector>
#include <cstddef>
#include <cstdint>

// Struct to contain file data for the thread
struct PThreadInfo {
    std::string id;        // The ID of the thread
    std::string context;   // The string context of the thread
    uint32_t counter;      // The number of iterations for the sha encryption
    size_t tIndex;         // The index of the thread that did the encryption
    char sha256[65];       // The text of the encryption
};

// Vector containing the structs of each thread with its info
static std::vector<PThreadInfo> threadInfo;

// The total number of rows that will be worked on.
// Filled through I/O input file
static size_t totalRows;

// The number of threads to run. Filled by user input
static size_t k;

// The total number of threads
static size_t n;

// The mode in which the program should run in
static CliMode mode;

// The time in milliseconds given to a thread to see
// how long it should run before timeout
static uint32_t timeout_ms;

// The number of threads that are allowed to run
static size_t released_k = 0;

// Function that the threads use
void* Start_Routine(void* arg);

int main(int argc, char *argv[]) {
    CliParse(argc, argv, &mode, &timeout_ms);

    // Gets the number of processes on computer
    n = ::get_nprocs();

    // Creates a vector of threads
    std::vector<::pthread_t> threadPool(n);

    // Creates a vector of the thread indices
    std::vector<size_t> threadIndices(n);

    // Fills the threadIndices with the correct numbers (1-Index)
    for (size_t i = 0; i < n; ++i) {
        threadIndices[i] = i + 1;
    }

    // Creates the threads in the threadPool
    for (size_t i = 0; i < threadPool.size(); ++i) {
        ::pthread_create(&threadPool[i], nullptr,
            &Start_Routine, &threadIndices[i]);
    }

    // Gets the totalRows from the input file
    std::cin >> totalRows;

    // Resizes the threadInfo vector to fill it later
    threadInfo.resize(totalRows);

    // Reads in the rest of the file input into a struct
    for (PThreadInfo& info : threadInfo) {
        std::cin >> info.id >> info.context >> info.counter;
    }

    // Start User Input and fills k
    std::cout << "Enter max threads (1 - " << n << "): " << std::endl;
    std::ifstream cinput("/dev/tty");
    if (cinput) {
        cinput >> k;
    }

    // Determine the mode to see how the threads are released
    switch (mode) {
    case CLI_MODE_ALL:
        released_k = k;  // Releases all the threads at once
        break;

    case CLI_MODE_RATE:
        // Releases threads one at a time with a small delay
        for (size_t i = 1; i <= k; ++i) {
            released_k = i;
            Timings_SleepMs(1);
        }
        break;

    case CLI_MODE_THREAD:
        // Have only one thread run at a time,
        // where the running thread will start the next thread when released
        released_k = 1;
        break;

    default:
        break;
    }

    // Blocking call to wait for all threads to finish
    for (::pthread_t thread : threadPool) {
        ::pthread_join(thread, nullptr);
    }

    // Output when all threads are completed and encryptions are done
    std::cout << "Thread \t\t Start \t\t Encryption" << std::endl;
    for (size_t i = 0; i < threadInfo.size(); ++i) {
        ThreadLog("%zu \t\t %s \t\t %s", threadInfo[i].tIndex,
            threadInfo[i].context.c_str(), threadInfo[i].sha256);
    }
    return 0;
}

void* Start_Routine(void* arg) {
    // The index of the thread
    size_t threadIndex = *reinterpret_cast<size_t *>(arg);

    // Busy waiting, waiting for k to be filled
    while (k == 0) {
        Timings_SleepMs(1);
    }

    // If the threadIndex is not apart of the
    // list of threads that are allowed to be used, exits
    if (threadIndex > k) {
        ThreadLog("[thread %zu] returned", threadIndex);
        return nullptr;
    }

    // Block until the thread is released
    while (threadIndex > released_k) {
        Timings_SleepMs(1);
    }

    // Allows the next thread to run when the current one starts
    if (mode == CLI_MODE_THREAD && threadIndex == released_k
        && threadIndex < k) {
        released_k = threadIndex + 1;
    }

    // Starts the thread and labels the starting time to check for timeout
    ThreadLog("[thread %zu] started", threadIndex);
    Timings_t start = Timings_NowMs();

    // Loop for the threads to process every k-th row
    for (size_t row = threadIndex - 1; row < totalRows; row += k) {
        // Check if a thread exceeded the timeout and if so exits.
        if (timeout_ms > 0 && Timings_TimeoutExpired(start, timeout_ms)) {
            ThreadLog("[thread %zu] timeout", threadIndex);
            ThreadLog("[thread %zu] returned", threadIndex);
            threadInfo[row].tIndex = threadIndex;
            snprintf(threadInfo[row].sha256, sizeof(threadInfo[row].sha256),
                "Encryption failed due to timeout");
            return nullptr;
        }

        // Get struct info
        const std::string context = threadInfo[row].context;
        uint32_t counter = threadInfo[row].counter;

        // Creates variable used for the seed parameter of
        // ComputeIterativeSha256Hex Function
        const std::uint8_t *seed;
        seed = reinterpret_cast<const std::uint8_t *>(context.data());

        // Computes the sha256hex
        ComputeIterativeSha256Hex(seed, context.length(),
        counter,  threadInfo[row].sha256);

        // Sets the thread index in the struct
        // to know which thread did the encryption
        threadInfo[row].tIndex = threadIndex;

        // Output labeling the thread completed a row
        ThreadLog("[thread %zu] completed row %zu", threadIndex, row + 1);
        ThreadLog("[thread %zu] returned", threadIndex);
    }
    return nullptr;
}