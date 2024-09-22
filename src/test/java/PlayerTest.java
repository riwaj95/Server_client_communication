import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import playercommunication.model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp(){
        player = new Player("TestPlayer");
    }

    @Test
    public void testGetName(){
        assertEquals("TestPlayer",player.getName());
    }

    @Test
    public void testGetMessageCounter(){
        assertEquals(0,player.getMessageCounter());
    }

    @Test
    public void testSendMessage() {
        String message = "Hello";
        String expectedMessage = "Hello|1";
        assertEquals(expectedMessage, player.sendMessage(message));
        assertEquals(1, player.getMessageCounter());
    }

    @Test
    public void testReceiveMessage() {
        String message = "Hello";
        String expectedMessage = "Hello | Counter: 1";
        assertEquals(expectedMessage, player.receiveMessage(message));
        assertEquals(1, player.getMessageCounter());
    }
}
