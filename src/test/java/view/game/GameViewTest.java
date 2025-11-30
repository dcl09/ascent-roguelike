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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Test
    void drawRendersAllMonsters() throws IOException {
        List<Monster> monsters = Arrays.asList(
                new Monster(new Position(5, 5)),
                new Monster(new Position(6, 6))
        );
        Mockito.when(level.getMonsters()).thenReturn(monsters);

        gameView.draw(gui);

        Mockito.verify(gui).drawChar(5, 5, 'M', "RED");
        Mockito.verify(gui).drawChar(6, 6, 'M', "RED");
    }

    @Test
    void drawRendersPlayer() throws IOException {
        Player player = new Player(new Position(10, 10));
        Mockito.when(model.getPlayer()).thenReturn(player);

        gameView.draw(gui);

        Mockito.verify(gui).drawChar(10, 10, '@', "BLUE");
    }


    @Test
    void drawRespectsLayeringOrder() throws IOException {
        Position sharedPos = new Position(5, 5);

        Mockito.when(level.getWalls()).thenReturn(Collections.singletonList(new Wall(sharedPos)));
        Mockito.when(level.getMonsters()).thenReturn(Collections.singletonList(new Monster(sharedPos)));
        Mockito.when(model.getPlayer()).thenReturn(new Player(sharedPos));

        gameView.draw(gui);

        InOrder inOrder = Mockito.inOrder(gui);

        inOrder.verify(gui).clear();
        inOrder.verify(gui).drawChar(5, 5, '#', "WHITE");
        inOrder.verify(gui).drawChar(5, 5, 'M', "RED");
        inOrder.verify(gui).drawChar(5, 5, '@', "BLUE");
        inOrder.verify(gui).refresh();
    }

}
