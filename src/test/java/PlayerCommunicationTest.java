import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import playercommunication.exception.CommunicationException;
import playercommunication.model.Player;
import playercommunication.service.PlayerCommunication;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerCommunicationTest {

    @Mock
    private Player initiator;

    @Mock
    private Player receiver;

    private PlayerCommunication playerCommunication;
    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        playerCommunication = new PlayerCommunication(initiator, receiver, true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testSameProcessCommunication() throws IOException, ClassNotFoundException, CommunicationException {
        when(initiator.sendMessage(anyString())).thenReturn("Hello|1");
        when(initiator.receiveMessage(anyString())).thenReturn("Response|1");
        when(initiator.getName()).thenReturn("Initiator");
        when(receiver.receiveMessage(anyString())).thenReturn("Hello | Counter: 1");
        when(receiver.sendMessage(anyString())).thenReturn("Response|1");
        when(receiver.getName()).thenReturn("Receiver");
        playerCommunication.startCommunication();

        // Verify interactions
        verify(initiator, times(1)).sendMessage("Hello");
        verify(receiver, times(10)).receiveMessage(anyString());
        verify(receiver, times(10)).sendMessage(anyString());
        verify(initiator, times(10)).receiveMessage(anyString());
    }

    @Test
    public void testSameProcessCommunication_Exception() {
        when(initiator.sendMessage(anyString())).thenThrow(new RuntimeException("Test Exception"));
        CommunicationException exception = assertThrows(CommunicationException.class, () -> {
            playerCommunication.startCommunication();
        });

        assertEquals("Error during same-process communication", exception.getMessage());
    }
}
