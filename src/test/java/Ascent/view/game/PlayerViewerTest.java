package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Player;
import Ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerViewerTest {
    private PlayerViewer playerViewer;
    private GUI gui;

    @BeforeEach
    void setup() {
        playerViewer = new PlayerViewer();
        gui = Mockito.mock(GUI.class);
    }

    @Test
    void drawPlayerAtValidPosition() {
        Player player = new Player(new Position(5, 8));

        playerViewer.draw(player, gui);
        Mockito.verify(gui).drawChar(5, 8, '►', "YELLOW_BRIGHT");
    }

    @Test
    void drawPlayerAtOrigin() {
        Player player = new Player(new Position(0, 0));

        playerViewer.draw(player, gui);
        Mockito.verify(gui).drawChar(0, 0, '►', "YELLOW_BRIGHT");
    }

    @Test
    void drawPlayerAtNegativePosition() {
        Player player = new Player(new Position(-1, -1));

        playerViewer.draw(player, gui);
        Mockito.verify(gui).drawChar(-1, -1, '►', "YELLOW_BRIGHT");
    }

    @Test
    void drawPlayerAfterMovingOnce() {
        Player player = new Player(new Position(5, 5));

        player.moveUp();
        player.moveLeft();
        playerViewer.draw(player, gui);

        // moveUp/moveLeft não mudam o símbolo, apenas a posição. Símbolo inicial é '►'
        Mockito.verify(gui).drawChar(4, 4, '►', "YELLOW_BRIGHT");
    }

    @Test
    void drawPlayerAfterMultipleMoves() {
        Player player = new Player(new Position(10, 10));

        player.moveUp();
        player.moveUp();
        player.moveRight();
        player.moveDown();
        playerViewer.draw(player, gui);

        // moveUp/moveDown/moveRight não mudam o símbolo, apenas a posição. Símbolo
        // inicial é '►'
        Mockito.verify(gui).drawChar(11, 9, '►', "YELLOW_BRIGHT");
    }

    @Test
    void drawPlayerWithModifiedSymbol() {
        Player player = new Player(new Position(5, 5));
        player.setSymbol('S');

        playerViewer.draw(player, gui);

        Mockito.verify(gui).drawChar(5, 5, 'S', "YELLOW_BRIGHT");
    }

    @Test
    void drawPlayerWithModifiedColor() {
        Player player = new Player(new Position(5, 5));
        player.setColor("RED");

        playerViewer.draw(player, gui);

        // Após setColor, o símbolo inicial é '►' (direção RIGHT)
        Mockito.verify(gui).drawChar(5, 5, '►', "RED");
    }
}
