package ascent.controller;

import ascent.Game;
import ascent.controller.game.PlayerController;
import ascent.gui.ACTION;
import ascent.gui.GUI;
import ascent.model.entities.Chest;
import ascent.model.entities.Door;
import ascent.model.entities.Player;
import ascent.model.entities.Wall;
import ascent.model.entities.monster.Monster;
import ascent.model.game.Position;
import ascent.model.game.floor.FileLevelFactory;
import ascent.model.game.floor.Floor;
import ascent.model.menu.Endscreen;
import ascent.model.menu.GameMenu;
import ascent.state.EndscreenState;
import ascent.state.GameMenuState;
import ascent.state.GameState;
import ascent.state.State;
import ascent.view.Viewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class PlayerControllerTest {
    private PlayerController controller;
    private Player player;
    private FileLevelFactory fileLevelFactory;
    private Floor floor;

    @BeforeEach
    void setUp() throws IOException {
        player = new Player(new Position(6,3));
        fileLevelFactory = new FileLevelFactory();
        floor = fileLevelFactory.createLevel(1, player);

        floor.setPlayer(player);

        floor.setWalls(List.of());
        floor.setMonsters(List.of());

        controller = new PlayerController(floor);
    }

    // start position is (6,3) -> from level 1
    @Test
    void heroMovesToEmptyPosition() {
        controller.movePlayer(new Position(5,4), 200);
        assertEquals(new Position(5, 4), player.getPosition());
    }

    @Test
    void heroDoesNotMoveThroughObstacles() {
        // wall blocks
        floor.setWalls(List.of(new Wall(new Position(5,3))));
        controller.movePlayer(new Position(5,3), 200);
        assertEquals(new Position(6, 3), player.getPosition());

        // chest blocks
        floor.setChests(List.of(new Chest(new Position(6,4), "YELLOW")));
        controller.movePlayer(new Position(6,4), 200);
        assertEquals(new Position(6, 3), player.getPosition());

        // door at default state (closed) blocks
        floor.setDoors(List.of(new Door(new Position(7,3))));
        controller.movePlayer(new Position(7,3), 200);
        assertEquals(new Position(6, 3), player.getPosition());
    }
}
