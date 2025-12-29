package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Door;
import ascent.model.entities.Player;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoorControllerTest {

    @Mock
    Floor floor;
    @Mock
    Player player;
    @Mock
    Door door;
    @Mock
    Game game;

    DoorController controller;

    @BeforeEach
    void setUp() {
        controller = new DoorController(floor);
        when(floor.getPlayer()).thenReturn(player);
    }

    @Test
    void interactsWhenDoorNotNull() {
        when(player.facing()).thenReturn(new Position(1, 1));
        when(floor.getDoorAt(player.facing())).thenReturn(door);

        controller.step(game, ACTION.INTERACT, 1000L);

        verify(door).interact(any());
    }

    @Test
    void doesNothingWhenDoorIsNull() {
        when(player.facing()).thenReturn(new Position(1, 1));
        when(floor.getDoorAt(new Position(1, 1))).thenReturn(null);

        controller.step(game, ACTION.INTERACT, 1000L);

        verify(door, never()).interact(any());
    }
}
