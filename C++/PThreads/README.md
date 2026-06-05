# Main.cc

Main contains the primary execution of the program in which will take a file of data and threads will work and create an encryption for them.

The program takes 2 forms input from the user. One will be the file containing the numbers of rows of data there are and the data itself containing the ID, context, and number of iterations the data will go through encryption. The second form of input is from the user determining the allowed number of threads to be used. 

The program will also have 2 flags, one required and one optional, to be listed when running the program. The first flag is the mode in which the program will run.

## Modes

--all: All the threads used are released simultaneously

--rate: The main thread releases exactly one thread every millisecond using a sleep or timed wait

--thread: The main thread releases thread 1, and thread 1 released thread 2, thread 2 released thread 3, and so on.

The other flag which is optional is the --timeout flag. 
This is followed by a number in milliseconds to determine how long a thread can run before it exits due to exceeding the timeout timer.

## Important Variables and Program Descriptions

### Struct PThreadInfo
The code involves a struct that will be the container of the file data and the encryption data.

id - is the string ID of the thread

context - is the string value of the thread

counter - is the number of iterations for that the sha256 encryption method will do

tIndex - is the variable that will hold the size_t index of which thread completed encrypting the string value

char sha256[65] - is the variable that will hold the encryption string

### Global Variables

vector<PthreadInfo> threadInfo - a vector that will contain the structs of PthreadInfo to use when displaying the output.

totalRows - is the size_t value that is the total rows in the text file. This will also be the number of PthreadInfo structs that will be made.

k - is the size_t value that the user will provide to determine how many threads will be used from the total thread count.

n - is the size_t value that will be the total thread count which is also the number of processors that the computer has. (Achieved through get_nprocs() from sys/sysinfo.h library)

mode - is the CliMode data type to determine which flag is used to determine how the threads are released.

timeout_ms - is the uint32_t data type that will be the value from the user after using the optional --timeout flag to determine how long a thread should run before it times out and exits.

released_k - is the int value to determine which thread should be released for the next one to start.

### Main Function

threadPool - is a vector of pthread_t with the size of n that will be used when using the pthread_create function to create the threads.

threadIndices - is a vector of size_t that will resemble the numbered indices that each thread will have.

The first thing to do is to loop through the threadIndices with the correct numbers going from 1 to n since the index is 1-based, and loop through the threadPool creating the pthreads.

The program then uses the cin to get the totalRows from the text file and then resizes the threadInfo vector to match. The program then loops through the threadInfo vector to cin the id, context, and counter.

The program then prompts the user to see what value k is, determines the mode that the program is running in and then waits for the threads to finish their functions before continuing with the pthread_join function.

Once the threads finish, the program loops through the threadInfo vector listing the thread index that did the encryption, the string value, and the encryption string.

### Start_Routine Function

threadIndex - is a size_t variable that holds the thread index from the threadIndices vector.

The program then has a busy waiting while loop that sleeps the threads causing them to be in a "waiting" state until k is no longer 0.

Once k is filled, there is a check to determine if the threadIndex is greater than k indicating that that thread is not needed so exits the thread.

The program that has another while loop which sleeps ("blocks") the thread until it is explicitly released to run based on the mode that was given.

The program then takes in consideration of a start time indicating the thread is started to do work to be used later for the timeout check for the thread, and then starts a loop to loop through k-th row of the threadInfo for a thread to run.

The loop initialization being the row set to the threadIndex - 1

The loop condition being while the row variable is less than total rows

The loop incrementation is having the row equal to the row variable plus k.

Example to describe k-th row in the for loop:

There is 5 rows to encrypt but only 4 threads are allowed, which means:

Thread 1 completes row 1 and row 5
Thread 2 completes row 2
Thread 3 completes row 3
Thread 4 completes row 4

In this loop the use of the Timings_TimeoutExpired function is used to determine if the thread took too long and timed out which causes the thread to exit.

The loop is also where the encryption process starts. The program has a variable which gets the context and counter of the threadInfo struct at the specific row.

The program starts the create the "seed" variable that will be used in the ComputeIterativeSha256Hex function to do the encryption process. Having the context reinterpret_casted as a uint8_t* allows the context to be used in the sha256hex function to be encrypted.

Once the encryption process is done, the tIndex variable in threadInfo at that specific row get initialized to determine what thread index did what row. Once all threads are done, the blocking call in main (pthread_join) moves on and the final output is displayed.