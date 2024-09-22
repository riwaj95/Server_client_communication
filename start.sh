#!/bin/bash

# Compile and package the project using Maven
mvn clean package

# Check if the build was successful
if [ $? -eq 0 ]; then
    echo "Build succeeded, starting the player communication..."

    # Run the program in same-process mode
    #java -cp target/PlayerCommunication-1.0-SNAPSHOT.jar playercommunication.Main &

    # Uncomment the following lines to run the program in separate process mode
    echo "Running in separate process mode..."
    java -cp target/PlayerCommunication-1.0-SNAPSHOT.jar playercommunication.Main separate server &
        SERVER_PID=$!

        # Give the server a 5 seconds to start up
        sleep 5

        # Start the client
        java -cp target/PlayerCommunication-1.0-SNAPSHOT.jar playercommunication.Main separate client

        # Wait for the server to finish
        wait $SERVER_PID
else
    echo "Build failed, please check the Maven output."
fi