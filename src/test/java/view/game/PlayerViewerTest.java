package view.game;

import gui.GUI;
import model.entities.Player;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void drawPlayerAfterMovingOnce() {
        Player player = new Player(new Position(5, 5));

        player.moveUp();
        player.moveLeft();
        playerViewer.draw(player, gui);

        Mockito.verify(gui).drawChar(4, 4, '@', "BLUE");
    }

    @Test
    void drawPlayerAfterMultipleMoves() {
        Player player = new Player(new Position(10, 10));

        player.moveUp();
        player.moveUp();
        player.moveRight();
        player.moveDown();
        playerViewer.draw(player, gui);

        // 10,10 -> 10,9 -> 10,8 -> 11,8 -> 11,9
        Mockito.verify(gui).drawChar(11, 9, '@', "BLUE");
    }

    @Test
    void drawPlayerWithModifiedSymbol() {
        Player player = new Player(new Position(5, 5));
        player.setSymbol('S');

        playerViewer.draw(player, gui);

        Mockito.verify(gui).drawChar(5, 5, 'S', "BLUE");
    }

    @Test
    void drawPlayerWithModifiedColor() {
        Player player = new Player(new Position(5, 5));
        player.setColor("RED");

        playerViewer.draw(player, gui);

        Mockito.verify(gui).drawChar(5, 5, '@', "RED");
    }
}
