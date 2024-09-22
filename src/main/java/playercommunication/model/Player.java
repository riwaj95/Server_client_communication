package playercommunication.model;

public class Player {

    private String name;
    private int messageCounter;

    public Player(String name){
        this.name = name;
        this.messageCounter = 0;
    }
    public String getName() {
        return name;
    }

    public int getMessageCounter() {
        return messageCounter;
    }

    public void incrementMessageCounter() {
        messageCounter++;
    }

    public String sendMessage(String message) {
        incrementMessageCounter();
        return message + "|" + messageCounter;
    }

    public String receiveMessage(String message) {
        incrementMessageCounter();
        return message + " | Counter: " + messageCounter;
    }
}
