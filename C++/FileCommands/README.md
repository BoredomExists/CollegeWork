## Methods

Main: The main function that determines the action that is going to happen to a file. There is also a usage message for each action type when executing the program with no extra arguments i.e. bin/mmap_util

int Create: The function that will create a file based on a file path and fill it with an amount of characters

Parameters: char *argv[]

First thing retrieving variables from the terminal such as the file path, the character fill, and the number of characters to add into the file. For the number of characters, two variables are made, one with data type off_t for the ftruncate function, and a size_t for the map. Once these variables are made, then the file is opened with the open file given the file path, and a bit wise OR access controls to determine the permissions of the open command. For create, we give the permissions read, write, create, and truncate, and since we are using the create, we have to give it a mode number for the create, in this case being 0666. Once we use the open function, we check to make sure it did not fail by checking its integer return value being a -1 if it failed.

We then use ftruncate to resize the file to insert the characters later and we also check for ftruncate's integer return value to see if it failed i.e. -1. We also check to make sure the size for the map is not zero. Then the mmap function is called to get a void * variable that will represent the mapping to the file. The map variable is also then checked if the mapping failed or not, and if so resize the file to its original size, in this case of create, it is set back to 0.

Converting the map to a char * map, it can be used then to insert the characters into the map and then "written" to the file. Once the characters are inserted into the map, msync is called to "flush" the changes into the file and then munmap is called to unmap the mapping to the file when the file is done being edited and then close the file descriptor with the close function. All these functions are checked for failure by checking their return values if their -1.

These checks are done for all file actions when to open, ftruncate, mmap, munmap, and close unless there is an error being checked before hand and some of these functions are called to resize or unmap a file.


int Insert: The function that will insert text within a file based on a file path, where to put the inserted character string, and the number of bytes to insert into the file.

Parameters: char *argv[]

The start of Insert begins the same way as Create where the variables are initialized. The variables for this function is getting the file path, the offset which is where to start inserting the characters, and the bytes incoming which is how many bytes of the character string that is going to be inserted. Like Create, the process of opening the file is the same, but after fstat is used to get the file length, so the file size can be adjusted for the inserted string. It is also checked to make sure that the fstat function passed. ANother check is to make sure that the offset that was givin is shorter than the length of the file. Once all those checks are done, ftruncate is then used to resize the file and the process of creating the map and getting the char map setup happens same as Create. Once this is done, a for loop that starts at the length of the file and moves backwards until it reaches the offset essentially a backwards loop. In this loop we are getting the old position of the file and creating a new position which is the old position + the number of incoming bytes. After we set the char map at the new position the old position, essentially making "space" for the insert.

Another for loop happens after this which is where the inserting process will occur. In this loop, it is start at the offest, and it goes up until it reaches the offset + the number of remaining bytes. In the loop, the standard input stream is grabbed and checked if the read byte from the input stream has reached EOF (End of File), if so, unmap, and resize the file back to its original length and close the file descriptor. Essentially if the read byte reaches EOF, the insert essentially fails and the file needs to be reverted. Otherwise if it succeeds, set the read byte in the char map at the for loop's i index.

Once all this is completed, the process of msync, munmap, and closing the file descriptor with all the checks in place.

int Append: The function that will add onto the file based on a file path and the number of bytes to add onto the file

Parameters: char *argv[]

Append starts very similar to Insert such as getting the variables from the terminal, in this case, the file path and the bytes incoming. Opens the file, get the file length with fstat and then it differs from here. Three variables are set, the original file size starting off as the length of the file, the current file size starting off as the length of the file, and the remaining bytes starting off as the bytes incoming. The first thing to check is if the file that is being appended is empty. If so, resize the file to be 1 and create the map function with the size being 1, create the char map, read only 1 byte from the standard input stream to append into the file. The process of putting the one character into the file is the same as create and insert where once the character is in the char map, call msync, munmap, and close. After this is done, decrement the remaining bytes by 1 and set the current file size to 1

Once either the first byte has been appended or the program is appending to a file with size > 0, a while loop is done while the remaining bytes is > 0, where the minimum amount of bytes between the current file size and the remaining bytes is used to create the new file size continously. Essentially, the process needs to continously increase the file size for every new byte added. Once this is done, do ftruncate to reshape the file, create the map and char map and then have a for loop starting at the current file size up to the new file size, where the standard input stream is read in, check for EOF, if no EOF, insert the read byte into the char map. Once this is finished, call msync and munmap since we are writing and creating a new map for each new appended character, and finally in the while loop decrement remaining bytes by the minimum bytes between the current file size and the remaining bytes, and set the current file size to the new file size. Outside the loop the last thing to do is close the file discriptor.