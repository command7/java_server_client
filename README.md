# Server Client Communication

## Description

Create a server that can listen to incoming requests, process them and reply with appropriate responses and a client that connects to the server and communicates with it for information. There are 3 different programs with 3 different functionalities in this repository and each has its own server and client.

* **Server.class** and **Client.class** - Simple server client program where the client keeps sending text and after "." is sent, the server replies with all the text appended as a single string.
* **CaesarServer.class** and **CaesarClient.class** - Server can encrypt and decrypt text using Ceaser algorithm. Client sends text to server and specifies whether to be encrypted or decrypted and the server replies appropriately.
* **ChatServer.class** and **ChatClient.class** - This program acts like a chatting application. Once the server is running, any number of clients can connect to it and the server will forward incoming messages to all clients connected to it.

## How to run

### Basic Server Client

Server will listen at port 1234 by default. To run server

`java Server`

Client will automatically connect to port 1234. To run client

`java Client`

### Encryption / Decryption program

Server has to be started first and the. It will be listening on port 16789. The key (i.e) number of shifts should be given as an argument to the server. If key is not provided, server will operate on 3 which is the default key. To start server

`java CaesarServer 3`

The IP address of the server has to be provided as input to the client program. To start client

`java CaesarClient 127.0.0.1`

### Chatting application

Server has to be started first. By default it listens at port _16789_ To start server

`java ChatServer`

While initiating a client, the IP address of the server has to be provided. If not provided, it will assume that the server is running locally at _127.0.0.1_. To start client

`java ChatClient 127.0.0.1`
