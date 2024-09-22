package playercommunication;

import playercommunication.model.Player;
import playercommunication.service.PlayerCommunication;

public class Main {

    public static void main(String[] args) throws Exception {

        boolean isSameProcess = args.length == 0 || !args[0].equals("separate");

        if (isSameProcess) {
            // Same process communication
            Player initiator = new Player("initiator");
            Player receiver = new Player("receiver");

            PlayerCommunication communication = new PlayerCommunication(initiator, receiver, true);
            communication.startCommunication();
        } else {
            // Separate process communication
            if (args.length < 2) {
                System.out.println("Please specify 'client' or 'server' as the second argument for separate process communication.");
                return;
            }

            Player player;
            PlayerCommunication communication;

            if (args[1].equalsIgnoreCase("client")) {
                player = new Player("PlayerClient");
                communication = new PlayerCommunication(player, null, false);
            } else if (args[1].equalsIgnoreCase("server")) {
                player = new Player("PlayerServer");
                communication = new PlayerCommunication(null, player, false);
            } else {
                System.out.println("Invalid argument. Please specify 'client' or 'server' as the second argument.");
                return;
            }

            communication.startCommunication();
        }
    }
}
