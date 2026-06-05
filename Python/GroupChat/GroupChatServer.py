# Learned how to use the select method
# https://stackoverflow.com/questions/34984443/using-select-method-for-client-server-chat-in-python

from socket import *
from sys import *
from select import *

import threading
from threading import Thread

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

# List of sockets for the server to look through
sockets = [serverSock]

# Dictionary of clients to keep track of
clients = {}

print('Waiting for a client ...')

# Sends the clients message to the other connected clients
def send_ClientMessage(message, clientSock):
    for c in clients:
        if c != clientSock:
            try:
                c.send(message)
            except:
                clientSock.close()
                sockets.remove(c)
                del clients[c]
                
# Recieves the clients messages to send to other connected clients
def receive_ClientMessage(clientSock, clientAddr):
    while True:
        try:
            message = clientSock.recv(1024)
            
            if not message:
                print('*** Client closed connection ***')
                sockets.remove(clientSock)
                del clients[clientSock]
                break

            send_ClientMessage(message, clientSock)
        except:
            print('*** Client closed connection ***')
            sockets.remove(clientSock)
            del clients[clientSock]
            break
        
    clientSock.close()
                
# Keeps the server checking for incoming clients
while True:
    readSockets, writeSockets, errorSockets = select(sockets, [], [])
    
    for newSocket in readSockets:
        if newSocket == serverSock:
            clientSock, clientAddr = serverSock.accept()
            sockets.append(clientSock)
            clients[clientSock] = clientAddr
            print('Connected to a client at', clientAddr)
            Thread(target=receive_ClientMessage, args=(clientSock, clientAddr)).start()
          
    # Breaks the loop if the server socket is the only one open  
    if len(sockets) == 1:
        break

# Closes all sockets in the sockets array
for sock in sockets:
    sock.close()
    
print("*** Server has disconnected ***")