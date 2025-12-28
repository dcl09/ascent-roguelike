package ascent.state;

import ascent.Game;
import ascent.controller.Controller;
import ascent.gui.ACTION;
import ascent.gui.GUI;
import ascent.model.game.floor.Floor;
import ascent.model.menu.Endscreen;
import ascent.model.menu.GameMenu;
import ascent.model.menu.StartMenu;
import ascent.view.Viewer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class StateTest {

    @Nested
    public class GenericStateTest {
        static Stream<State<?>> stateProvider() throws IOException {
            return Stream.of(
                    new EndscreenState(Mockito.mock(Endscreen.class)),
                    new GameMenuState(Mockito.mock(GameMenu.class)),
                    new StartMenuState(Mockito.mock(StartMenu.class)),
                    new GameState(Mockito.mock(Floor.class))
            );
        }

        @ParameterizedTest
        @MethodSource("stateProvider")
        void getViewerDoesNotReturnNull(State<?> state) throws IOException {
            assertNotEquals(null, state.getViewer());
        }

        @ParameterizedTest
        @MethodSource("stateProvider")
        void getControllerDoesNotReturnNull(State<?> state) {
            assertNotEquals(null, state.getController());
        }
    }

    @Nested
    public class StepTest {
        @Test
        void stepCallsControllerAndViewer() throws IOException {
            Game game = Mockito.mock(Game.class);
            GUI gui = Mockito.mock(GUI.class);
            Controller<Object> controller = Mockito.mock(Controller.class);
            Viewer<Object> viewer = Mockito.mock(Viewer.class);
            Object model = new Object();

            when (gui.processKey()).thenReturn(ACTION.UP);
            State<Object> state = new State<>(model) {
                @Override
                protected Controller<Object> getController() {
                    return controller;
                }

                @Override
                protected Viewer<Object> getViewer(){
                    return viewer;
                }
            };

            state.step(game, gui, 1L);
            verify(controller, times(1)).step(eq(game), eq(ACTION.UP), eq(1L));
            verify(viewer, times(1)).draw(eq(gui));
        }
    }
}
