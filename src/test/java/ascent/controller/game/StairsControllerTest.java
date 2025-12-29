package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.entities.Stairs;
import ascent.model.game.Position;
import ascent.model.game.floor.FileLevelFactory;
import ascent.model.game.floor.Floor;
import ascent.state.EndscreenState;

import ascent.state.GameState;
import ascent.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.stubbing.answers.DoesNothing.doesNothing;

@ExtendWith(MockitoExtension.class)
public class StairsControllerTest {

    @Mock
    Floor floor;
    @Mock
    Player player;
    @Mock
    Stairs stairs;
    @Mock
    Game game;
    @Mock
    FileLevelFactory fileLevelFactory;

    StairsController controller;

    @BeforeEach
    void setUp() {
        controller = new StairsController(floor, fileLevelFactory);
        when(floor.getPlayer()).thenReturn(player);
        when(floor.getStairs()).thenReturn(stairs);
    }

    @Test
    void ifPlayerIsNotFacingStairsStepDoesNothing() throws IOException {
        when(player.facing()).thenReturn(new Position(1, 0));
        when(stairs.getPosition()).thenReturn(new Position(1, 1));

        controller.step(game, ACTION.INTERACT, 1000L);

        verify(game, never()).popState();
    }

    @Test
    void ifCurrentLevelIsNotFinalLevel() throws IOException {
        when(player.facing()).thenReturn(new Position(1, 1));
        when(stairs.getPosition()).thenReturn(new Position(1, 1));
        when(floor.getCurrLevel()).thenReturn(1);
        when(fileLevelFactory.createLevel(2, player)).thenReturn(floor);

        controller.step(game, ACTION.INTERACT, 1000L);

        verify(game).popState();
        verify(game).pushState(any(GameState.class));
        verify(game, never()).pushState(any(EndscreenState.class));
    }

    @Test
    void ifCurrentLevelIsFinalLevel() throws IOException {
        when(player.facing()).thenReturn(new Position(1, 1));
        when(stairs.getPosition()).thenReturn(new Position(1, 1));
        when(floor.getCurrLevel()).thenReturn(2);
        doThrow(new IOException()).when(fileLevelFactory).createLevel(3, player);

        controller.step(game, ACTION.INTERACT, 1000L);
        verify(game).popState();
        verify(game).pushState(any(EndscreenState.class));
        verify(game, never()).pushState(any(GameState.class));
    }
}
