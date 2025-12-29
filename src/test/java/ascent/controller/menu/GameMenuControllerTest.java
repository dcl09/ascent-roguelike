package ascent.controller.menu;

import ascent.Game;
import ascent.model.menu.GameMenu;
import ascent.state.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ascent.gui.ACTION.*;
import static org.mockito.Mockito.*;

class GameMenuControllerTest {

    Game game;
    GameMenu gameMenu;
    GameMenuController gameMenuController;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        gameMenu = mock(GameMenu.class);
        gameMenuController = new GameMenuController(gameMenu);
    }

    @Test
    void upArrowKeyWorks() throws IOException {
        gameMenuController.step(game, UP, 1000L);
        verify(gameMenu).prevEntry();
    }

    @Test
    void downArrowKeyWorks() throws IOException {
        gameMenuController.step(game, DOWN, 1000L);
        verify(gameMenu).nextEntry();
    }

    @Test
    void selectKeyOnMainMenuWorks() throws IOException {
        when(gameMenu.getCurrEntry()).thenReturn("MAIN MENU");

        gameMenuController.step(game, SELECT, 1000L);
        verify(game, times(2)).popState();
    }

    @Test
    void selectKeyOnResumeWorks() throws IOException {
        when(gameMenu.getCurrEntry()).thenReturn("RESUME");

        gameMenuController.step(game, SELECT, 1000L);
        verify(game).popState();
    }

    @Test
    void selectKeyOnExitWorks() throws IOException {
        when(gameMenu.getCurrEntry()).thenReturn("EXIT");

        gameMenuController.step(game, SELECT, 1000L);
        verify(game).popState();
    }

    @Test
    void selectKeyOnStartWorks() throws IOException {
        when(gameMenu.getCurrEntry()).thenReturn("START");

        gameMenuController.step(game, SELECT, 1000L);
        verify(game).pushState(any(GameState.class));
    }
}
