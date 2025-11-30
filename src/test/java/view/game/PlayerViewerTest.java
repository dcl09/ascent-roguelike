package view.game;

import model.entities.Player;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerViewerTest {
    private PlayerViewer playerViewer;

    @BeforeEach
    void setup() {
        playerViewer = new PlayerViewer();
    }

    @Test
    void viewerInstantion() {
        assertNotNull(playerViewer);
    }
    
}
