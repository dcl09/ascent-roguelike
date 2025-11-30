package view.game;

import gui.GUI;
import model.GameModel;
import model.entities.Monster;
import model.entities.Player;
import model.entities.Wall;
import model.game.Position;
import model.game.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import view.GameView;

import java.io.IOException;
import java.util.Collections;

public class GameViewTest {
    private GUI gui;
    private GameView gameView;
    private GameModel model;
    private Level level;

    @BeforeEach
    void setup() {
        gui = Mockito.mock(GUI.class);
        model = Mockito.mock(GameModel.class);
        level = Mockito.mock(Level.class);

        gameView = new GameView(model);

        Mockito.when(model.getLevel()).thenReturn(level);
        Mockito.when(level.getWalls()).thenReturn(Collections.emptyList());
        Mockito.when(level.getMonsters()).thenReturn(Collections.emptyList());
        Mockito.when(model.getPlayer()).thenReturn(new Player(new Position(0, 0)));
    }

    @Test
    void screenClearAndRefreshSequence() throws IOException {
        gameView.draw(gui);

        InOrder inOrder = Mockito.inOrder(gui);
        inOrder.verify(gui).clear();
        inOrder.verify(gui).refresh();
    }

    @Test
    void drawRendersAllWalls() throws IOException {
        List<Wall> walls = Arrays.asList(
                new Wall(new Position(1, 1)),
                new Wall(new Position(2, 2))
        );
        Mockito.when(level.getWalls()).thenReturn(walls);

        gameView.draw(gui);

        Mockito.verify(gui).drawChar(1, 1, '#', "WHITE");
        Mockito.verify(gui).drawChar(2, 2, '#', "WHITE");
    }
}
