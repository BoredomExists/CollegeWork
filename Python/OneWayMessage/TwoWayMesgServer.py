# Implementation of a one way message server in python

# Import socket related methods
from socket import *

# Import argv related methods
from sys import *


# Server needs the port number to listen on
if len(argv) != 2:
    print('usage:', argv[0], '<port>')
    exit()

# Get the port on which server should listen */
serverPort = int(argv[1])

# Create the server socket
serverSock = socket(AF_INET, SOCK_STREAM)

# Bind the socket to the given port
serverSock.bind(('', serverPort))

# Set the server for listening */
serverSock.listen()

# Wait to receive a connection request
print('Waiting for a client ...')
clientSock, clientAddr = serverSock.accept()
print('Connected to a client at', clientAddr)

# No other clients, close the server socket
serverSock.close()

# Make a file stream out of client socket | Currently uncessary code, left here in case
# clientSockFile = clientSock.makefile(mode='rw')

# Keep serving the client
while True:
    # Read a message from the client
    clientMessage = clientSock.recv(1024).decode()
    
    # Breaks the loop if the client did not send a messgae
    if not clientMessage:
        break
    
    # Prints the client's message
    print('Client: ', clientMessage)
    
    # Prompts the server to send a message to the client
    serverMessage = input('Server: ')
    
    # Breaks the loop if the server's message is null
    if not serverMessage:
        break
    
    # Sends the server's message to the client
    clientSock.send(serverMessage.encode())
    
# done
print('Closing Connection')
clientSock.close()