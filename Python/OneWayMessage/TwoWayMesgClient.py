# Implementation of the one way message client in python

# Import socket related methods
from socket import *

# Import argv related methods
from sys import *


# Client needs server's contact information
if len(argv) != 3:
    print("usage:", argv[0], "<server name> <server port>")
    exit()

# Get server's whereabouts
serverName = argv[1]
serverPort = int(argv[2])

# Create a socket
sock = socket(AF_INET, SOCK_STREAM)

# Connect to the server
sock.connect((serverName, serverPort))
print(f"Connected to server at ('{serverName}', '{serverPort}')")

# Make a file stream out of socket | Currently uncessary code, left here in case
# sockFile = sock.makefile(mode='rw')

while True:
    # Inputs client
    clientMessage = input('Client: ')
    
    # Breaks the loop if no inputted message
    if not clientMessage:
        break
    
    # Sends client's message to the server
    sock.send(clientMessage.encode())
    
    # Gets server reply
    serverMessage = sock.recv(1024).decode()
    
    # Breaks the loop if no server reply
    if not serverMessage:
        break
    
    # Prints the message from the server
    print('Server: ', serverMessage)

# done
print("Closing connection")
sock.close()
