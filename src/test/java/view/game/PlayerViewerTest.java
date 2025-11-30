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
    }

    @Test
    void drawPlayerAtValidPosition() {
        Player player = new Player(new Position(5, 8));

        playerViewer.draw(player, gui);
        Mockito.verify(gui).drawChar(5, 8, '@', "BLUE");
    }

    @Test
    void drawPlayerAtOrigin() {
        Player player = new Player(new Position(0, 0));

        playerViewer.draw(player, gui);
        Mockito.verify(gui).drawChar(0, 0, '@', "BLUE");
    }

    @Test
    void drawPlayerAtNegativePosition() {
        Player player = new Player(new Position(-1, -1));

        playerViewer.draw(player, gui);
        Mockito.verify(gui).drawChar(-1, -1, '@', "BLUE");
    }
}
