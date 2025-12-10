package Ascent.model;

import Ascent.model.entities.Player;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameModelTest {

    @Test
    void constructorAssignsPlayerAndLevel() {
        Player player = new Player(new Position(10, 10));
        Floor level = Mockito.mock(Floor.class);

        GameModel model = new GameModel(player, level);

        assertEquals(player, model.getPlayer());
        assertEquals(level, model.getFloor());
    }
}