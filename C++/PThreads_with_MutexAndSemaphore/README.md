## Struct

Client Request is a struct object that will hold the information of each of the client's requests. This will hold the client's name, the directory path of each file, the number of rows per file, the amount of readers and solvers needed to process the request.

## Functions

Signal_Handler: A function that will change the variable signalStatus variable to determine if the server will exit.

Start_Routine: The function that the pthreads will use to process each request.

ParseMessage: Given the message from a request, it will parse the message giving back the client's name, the directory path of each file, and the number of rows in each file.

CheckForDeadlock: A boolean function to determine if the request will deadlock the system.

## Global Variables (Data Type  Variable Name)

sig_atomic_t signalStatus: A variable set by the signal handler to determine if the server should shutdown

pthread_mutex_t mutex: A variable representing the mutex being used to lock and unlock a mutex.

sem_t semaphore: A variable representing the semaphore being used to determine if there is a message in the message queue to process.

Queue(string) msgQ: A queue of strings that will contain the message request received from the client.

Queue(ClientRequest) requests: A queue of the client request structs that will contain the block requests that would deadlock the system.

uint32_t nReaders: A variable representing the number of readers the user gives the system

uint32_t kSHASolvers: A variable representing the number of SHASolvers the user gives the system.

uint32_t availableSolvers: A variable representing the number of solvers available for a request to use.

uint32_t availableReaders: A variable representing the number of readers available for a request to use.

## Main

Main is where majority of initializing variables and setting up the server will be. First there is a usage message in case the user is not sure how to run the program. It will also get the server name and initialize nReaders and kSHASolvers by getting the numbers from the terminal. The SIGINT and SIGTERM will also be set and then initialize the mutex and semaphore. A vector of pthread_t will be made to contain all the pthreads that will be used in the program. The number of threads to make is arbritrary but the program using get_nproc_conf to determine the thread count. The File Readers and SHA Solvers classes will be initialized and then a Server Endpoint will be made and initialized by using the server name.

The major part of Main is the forever loop that is made after. Here the server will continuously run waiting for a message from the client, lock the mutex, push the message into the message queue, unlock the mutex, and then essentially call Up on the semaphore using the sem_post function.

After this for loop ends, another for loop starts that goes through the thread pool, calling up on each of them so no thread is still processing a request when the program ends. Then another for loop exist looping through the thread pool again calling pthread_join on each of the function so main waits until all created threads have finished. Finally, the mutex and semaphore are destroyed and the program ends.

## Start Routine

This is the function that a thread will run to process a client's request and send a reply back. Everything happens in a forever loop indicating the threads will continue to wait and run when a message is available. Calling sem_wait or Down on the semaphore indicates a message has appeared. The mutex gets locked and the first thing that is checked is if there are any request pending as in there was not enough available readers or solvers to process the request. If the request queue is not empty, get the request from the queue and check if it can STILL deadlock the system, if it does not process that request instead of a new one and unlock the mutex.

If the requests queue is empty, process a new request. First check if the message queue is empty and if so unlock the mutex, and also check if the server had stopped. Pass that, the thread gets the message from the message queue and unlocks the mutex if the unlock from the message queue being empty did not go through, and parse the message. Once the message is parse, determine the max number of SHA solvers that are needed by checking the highest number of rows between the list of files and create a client request stuct object to process.

Once the struct object is made, lock the mutex since we are dealing with critical information, and check if the request will deadlock the system. If so, add it to the request queue to get processed later, if not, subtract available readers and solvers from needed readers and solvers. Unlock the mutex after both cases, but continue on if the request will deadlock, and create the solver and reader handles. Also create a variable to store the hash that will be processed to give back to the client. Finally, create a steam client back to the client and initialize it. Once initialized, send back the hash data to the client. Once that is done, then the thread gives back its resources used and locks the mutex to add back the needed readers and solvers. Unlocking and posting the semaphore is the last thing the thread does to indicate that a request is done and another thread needs to start processing a new request.

## Parse Message

Parse Message uses the message parameter that has data in it to parse it and fill the client's name, the file paths, and the rows that is in each file. This function will use pointer arithmetic getting each data type from a defined binary request protocol. Creating a const char * that is the data of the message, we will add an offset to that pointer and use memcpy to get the data and assign it to a variable (int_value which is a uint32_t). The offset will be added by a certain amount depending on the binary data type. If it is a uint32_t we are getting, the length of that memcpy needs to copy is 4. If it a string name, it will be whatever the uint32_t that just copied was.

For example, the binary protocol has a uint32_t before a char string. The uint32_t is the length of the char string, so storing that value in int_value, we can use int_value to get the char string. That is what is done to get the client's name and file_paths. This process happens to get the client's name, the file paths, and rows per file, filling in those variables. Once this is done, the thread finishes processing the request.

## Check For Deadlock

A boolean function that checks if the number of readers and solvers in a request is less or equal to the number of available readers and solvers. If so, the system is safe from deadlock, otherwise the system will deadlock. 

## Signal Hander

A function that sets the signal status to 1 indicating the server is shutting down.
