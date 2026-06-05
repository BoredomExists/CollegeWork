from socket import *
from sys import *
from select import *

import threading
from threading import Thread

# Client needs server's contact information
if len(argv) != 4:
    print("usage:", argv[0], "<server name> <server port> <username>")
    exit()

# Get server's whereabouts
serverName = argv[1]
serverPort = int(argv[2])
username = argv[3]

# Create a socket
sock = socket(AF_INET, SOCK_STREAM)

# Connect to the server
sock.connect((serverName, serverPort))
print(f"Connected to server at ('{serverName}', '{serverPort}')")

def send_message(sock):
    try:
        while True:
            line = input()
        
            if not line:
                print('*** Client closing connection ***')
                break
            
            message = f"{username}: {line}\n"
            sock.send(message.encode())
        
        sock.close()
    except:
        print('*** Client closing connection ***')

# Recieves the messages from the server from other clients
def recieve_message(sock):
    try:
        while True:
            line = sock.recv(1024)
            
            if not line:
                break
            
            print(line.decode(), end='')
            
        sock.close()
    except:
        exit()
        
Thread(target=send_message, args=(sock,)).start()
Thread(target=recieve_message, args=(sock,)).start()