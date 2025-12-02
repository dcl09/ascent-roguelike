package model;

import model.entities.Player;
import model.game.Position;
import model.game.level.Level;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    @Test
    void constructorAssignsPlayerAndLevel() {
        Player player = new Player(new Position(10, 10));
        Level level = Mockito.mock(Level.class);

        GameModel model = new GameModel(player, level);

        assertEquals(player, model.getPlayer());
        assertEquals(level, model.getLevel());
    }
}