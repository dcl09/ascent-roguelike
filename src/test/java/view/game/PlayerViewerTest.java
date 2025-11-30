package view.game;

import gui.GUI;
import model.entities.Player;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class PlayerViewerTest {
    private PlayerViewer playerViewer;
    private GUI gui;
    private Player player;

    @BeforeEach
    void setup() {
        playerViewer = new PlayerViewer();
        gui = Mockito.mock(GUI.class);
        player = new Player(new Position(5,8));
    }

}
