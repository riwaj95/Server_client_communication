# Solution and Explanation (JDK 17):

## Introduction

The Player Communication program facilitates communication between two players either within the same process or separate processes using network sockets. This program supports two communication modes: same-process communication and separate-process communication (client-server model).

## System Design

### Architecture

The program is designed with a modular architecture to ensure separation of concerns and ease of testing. The architecture consists of three main components:

1. Player: Represents a player model in the communication system.
2. PlayerCommunication: Manages the communication service logic between players.
3. Main: Entry point of the application that sets up and starts the communication based on the process.
4. CommunicationException: A custom exception for communication related logics.

## Class Structure

### Player.class

Attributes: name (String), messageCounter (int)

Methods:
getName(),
getMessageCounter(),
incrementMessageCounter(),
sendMessage(String),
receiveMessage(String)

### PlayerCommunication.class

Attributes: initiator (Player), receiver (Player), isSameProcess (boolean), socket (Socket), serverSocket (ServerSocket), inputStream (ObjectInputStream), outputStream (ObjectOutputStream)

Methods:
sameProcessCommunication(),
separateProcessCommunication(),
startCommunication()

### Main.class

Determines the mode of communication and initializes Player and PlayerCommunication instances accordingly.

## Design Patterns

### Factory Method:

The Main class determines the mode of communication and creates instances of PlayerCommunication with the appropriate configurations.

### Dependency Injection:

The PlayerCommunication class uses setter methods to inject mock dependencies for testing purposes, promoting flexibility and testability.

## SOLID Principles

### Single Responsibility Principle (SRP):

Each class has a single responsibility:

**Player**: Manages player-related data and functions.

**PlayerCommunication**: Manages communication service logic.

**Main**: Handles application startup and configurations.

### Open/Closed Principle (OCP):

The system is open for extension but closed for modification, allowing additional communication modes to be added without modifying existing code.

### Liskov Substitution Principle (LSP):

Subtypes should be substitutable for their base types without altering program correctness, ensuring extendability and mockability without breaking functionality.

### Interface Segregation Principle (ISP):

Although interfaces are not explicitly used, the principle is maintained conceptually by ensuring classes do not expose unnecessary methods.

### Dependency Inversion Principle (DIP):

High-level modules (PlayerCommunication) do not depend on low-level modules (Socket, ObjectInputStream), but rather abstractions, achieved through dependency injection.

## Deployment

### Same-Process mode :

In this mode, both players run in the same process.

### Seperate-Process mode : 

In this mode, one player acts as a server and the other as a client. The server must be started before the client.

#### Navigate to PlayerCommunication and run './start.sh' to run in both mode.
