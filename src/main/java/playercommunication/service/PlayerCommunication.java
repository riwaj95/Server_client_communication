package playercommunication.service;

import playercommunication.exception.CommunicationException;
import playercommunication.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerCommunication {

    private Player initiator;
    private Player reciever;
    private boolean isSameProcess;

    public PlayerCommunication(Player initiator, Player reciever, boolean isSameProcess) {
        this.initiator = initiator;
        this.reciever = reciever;
        this.isSameProcess = isSameProcess;
    }

    private void sameProcessCommunication() throws CommunicationException {
        try{
            String initialMessage = "Hello";
            String initiatorMessage = initiator.sendMessage(initialMessage);
            System.out.println(initiator.getName() + " sends: " + initiatorMessage);

            for (int i = 0; i < 10; i++) {
                // Receiver receives the initiator's message
                String receiverResponse = reciever.receiveMessage(initiatorMessage);
                System.out.println(reciever.getName() + " receives: " + receiverResponse);

                // Receiver sends a reply back to the initiator
                String receiverMessage = reciever.sendMessage(receiverResponse);
                System.out.println(reciever.getName() + " sends: " + receiverMessage);

                // Initiator receives the receiver's message
                String initiatorResponse = initiator.receiveMessage(receiverMessage);
                System.out.println(initiator.getName() + " receives: " + initiatorResponse);

                // Initiator sends a new message to the receiver
                initiatorMessage = initiator.sendMessage(initiatorResponse);
                System.out.println(initiator.getName() + " sends: " + initiatorMessage);
        }
        }
        catch (Exception e){
            throw new CommunicationException("Error during same-process communication");
        }

    }

    private void separateProcessCommunication() throws IOException, ClassNotFoundException {
        if (initiator!=null && initiator.getName().equals("PlayerClient")) {
            // Client side logic
            try (Socket socket = new Socket("localhost", 8080);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                String initialMessage = "Hello";
                String initiatorMessage = initiator.sendMessage(initialMessage);
                out.writeObject(initiatorMessage);
                System.out.println(initiator.getName() + " sends: " + initiatorMessage);

                for (int i = 0; i < 10; i++) {
                    String response = (String) in.readObject();
                    System.out.println(initiator.getName() + " receives: " + initiator.receiveMessage(response));
                    String newMessage = initiator.sendMessage(response);
                    out.writeObject(newMessage);
                    System.out.println(initiator.getName() + " sends: " + newMessage);
                }
            }
        } else if (reciever!=null && reciever.getName().equals("PlayerServer")) {
            // Server side logic
            try (ServerSocket serverSocket = new ServerSocket(8080);
                 Socket clientSocket = serverSocket.accept();
                 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                for (int i = 0; i < 10; i++) {
                    String message = (String) in.readObject();
                    System.out.println(reciever.getName() + " receives: " + reciever.receiveMessage(message));
                    String response = reciever.sendMessage(message);
                    out.writeObject(response);
                    System.out.println(reciever.getName() + " sends: " + response);
                }
            }
        }
    }

    public void startCommunication() throws IOException, ClassNotFoundException,CommunicationException {
        if (isSameProcess) {
            sameProcessCommunication();
        } else {
            separateProcessCommunication();
        }
    }
}
