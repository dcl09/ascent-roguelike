package model;

import model.entities.Player;
import model.game.Position;
import model.game.level.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @Test
    void constructor_ShouldCreateLevelAndStorePlayer() {
        Player player = new Player(new Position(5, 5));

        GameModel model = new GameModel(player);

        assertSame(player, model.getPlayer());

        Level level = model.getLevel();
        assertNotNull(level);

        assertEquals(player, level.getPlayer());
    }

    @Test
    void levelDimensions_ShouldMatchBaseplateBuilderDefaults() {
        GameModel model = new GameModel(new Player(new Position(0, 0)));
        Level level = model.getLevel();

        assertEquals(40, level.getWidth());
        assertEquals(20, level.getHeight());
    }
}
