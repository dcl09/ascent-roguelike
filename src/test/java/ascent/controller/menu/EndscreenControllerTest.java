package ascent.controller.menu;

import ascent.Game;
import ascent.model.menu.Endscreen;
import ascent.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ascent.gui.ACTION.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class EndscreenControllerTest {
    Game game;
    Endscreen endscreen;
    EndscreenController endscreenController;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        endscreen = mock(Endscreen.class);
        endscreenController = new EndscreenController(endscreen);
    }

    @Test
    void upArrowKeyWorks() throws IOException {
        endscreenController.step(game, UP, 1000L);
        verify(endscreen).prevEntry();
    }

    @Test
    void downArrowKeyWorks() throws IOException {
        endscreenController.step(game, DOWN, 1000L);
        verify(endscreen).nextEntry();
    }

    @Test
    void selectKeyOnToMainMenuWorks() throws IOException {
        when(endscreen.getCurrEntry()).thenReturn("TO MAIN MENU");

        endscreenController.step(game, SELECT, 1000L);
        verify(game, times(2)).popState();
    }

    @Test
    void selectKeyOnRestartWorks() throws IOException {
        when(endscreen.getCurrEntry()).thenReturn("RESTART");

        endscreenController.step(game, SELECT, 1000L);
        verify(game, times(1)).popState();
        verify(game, times(1)).pushState(any(GameState.class));
    }
}
