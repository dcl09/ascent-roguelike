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
import org.mockito.InOrder;
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

    private PlayerViewer playerViewer;
    private MonsterViewer monsterViewer;
    private WallViewer wallViewer;
    private ChestViewer chestViewer;

    @BeforeEach
    void setup() {
        gui = Mockito.mock(GUI.class);
        model = Mockito.mock(GameModel.class);
        level = Mockito.mock(Level.class);

        playerViewer = Mockito.mock(PlayerViewer.class);
        monsterViewer = Mockito.mock(MonsterViewer.class);
        wallViewer = Mockito.mock(WallViewer.class);
        chestViewer = Mockito.mock(ChestViewer.class);

        gameView = new GameView(model, playerViewer, monsterViewer, wallViewer, chestViewer);

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
    void drawDelegatesToWallViewer() throws IOException {
        Wall wall = new Wall(new Position(1, 1));
        Mockito.when(level.getWalls()).thenReturn(List.of(wall));

        gameView.draw(gui);
        Mockito.verify(wallViewer).draw(wall, gui);
    }

    @Test
    void drawDelegatesToMonsterViewer() throws IOException {
        Monster monster = new Monster(new Position(5, 5));
        Mockito.when(level.getMonsters()).thenReturn(List.of(monster));

        gameView.draw(gui);
        Mockito.verify(monsterViewer).draw(monster, gui);
    }

    @Test
    void drawDelegatesToPlayerViewer() throws IOException {
        // Arrange
        Player player = new Player(new Position(10, 10));
        Mockito.when(model.getPlayer()).thenReturn(player);

        gameView.draw(gui);
        Mockito.verify(playerViewer).draw(player, gui);
    }

    // todo: implement tests for chest when it is properly implemented

    @Test
    void drawRespectsLayeringOrder() throws IOException {
        Mockito.when(level.getWalls()).thenReturn(List.of(new Wall(new Position(0,0))));
        Mockito.when(level.getMonsters()).thenReturn(List.of(new Monster(new Position(0,0))));
        Mockito.when(model.getPlayer()).thenReturn(new Player(new Position(0,0)));

        gameView.draw(gui);
        InOrder inOrder = Mockito.inOrder(gui, wallViewer, monsterViewer, playerViewer);
        inOrder.verify(gui).clear();
        inOrder.verify(wallViewer).draw(Mockito.any(), Mockito.eq(gui));
        inOrder.verify(monsterViewer).draw(Mockito.any(), Mockito.eq(gui));
        inOrder.verify(playerViewer).draw(Mockito.any(), Mockito.eq(gui));
        inOrder.verify(gui).refresh();
    }
}
