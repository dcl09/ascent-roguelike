package ascent;

import ascent.gui.GUI;
import ascent.state.GameMenuState;
import ascent.state.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class GameTest {
    private Game game;
    private GUI guiMock;

    @BeforeEach
    public void setUp() {
        guiMock = mock(GUI.class);
        game = new Game(guiMock);
    }

    @Test
    public void testGameLoopExecutesAndClosesGui() throws IOException, InterruptedException {
        State<?> stateMock = mock(State.class);

        doAnswer(invocation -> {
            ((Game) invocation.getArgument(0)).popState();
            return null;
        }).when(stateMock).step(any(), any(), anyLong());

        game.pushState(stateMock);
        game.start();

        verify(stateMock).step(eq(game), eq(guiMock), anyLong());
        verify(guiMock).close();
    }

    @Test
    public void testSleepExecutionCalledWhenFast() throws Exception {
        Game gameSpy = spy(game);
        doReturn(1000L).doReturn(1005L).when(gameSpy).getCurrentTime();
        doNothing().when(gameSpy).sleepExecution(anyLong());

        State<?> stateMock = mock(State.class);
        doAnswer(invocation -> {
            ((Game) invocation.getArgument(0)).popState();
            return null;
        }).when(stateMock).step(any(), any(), anyLong());

        gameSpy.pushState(stateMock);
        gameSpy.start();

        verify(gameSpy).sleepExecution(11L);
    }

    @Test
    public void testSleepNotCalledWhenExactFrameTime() throws Exception {
        Game gameSpy = spy(game);
        doReturn(1000L).doReturn(1016L).when(gameSpy).getCurrentTime();

        State<?> stateMock = mock(State.class);
        doAnswer(invocation -> {
            ((Game) invocation.getArgument(0)).popState();
            return null;
        }).when(stateMock).step(any(), any(), anyLong());

        gameSpy.pushState(stateMock);
        gameSpy.start();

        verify(gameSpy, never()).sleepExecution(anyLong());
    }

    @Test
    public void testInterruptedException() throws Exception {
        Game gameSpy = spy(game);
        doReturn(1000L).doReturn(1000L).when(gameSpy).getCurrentTime();

        State<?> stateMock = mock(State.class);
        doAnswer(invocation -> {
            ((Game) invocation.getArgument(0)).popState();
            return null;
        }).when(stateMock).step(any(), any(), anyLong());

        gameSpy.pushState(stateMock);

        doThrow(new InterruptedException()).when(gameSpy).sleepExecution(anyLong());

        assertDoesNotThrow(() -> gameSpy.start());

        verify(guiMock).close();
    }

    @Test
    public void testStartPushesMenuIfStackEmpty() throws IOException, InterruptedException {
        Game gameSpy = spy(game);
        doNothing().when(gameSpy).pushState(any(State.class));

        gameSpy.start();

        verify(gameSpy).pushState(any(GameMenuState.class));
        verify(guiMock).close();
    }

    @Test
    public void testProtectedSleepExecution() throws InterruptedException {
        long start = System.currentTimeMillis();
        game.sleepExecution(10);
        long end = System.currentTimeMillis();

        assertTrue(end - start >= 5);
    }

    @Test
    public void testProtectedGetCurrentTime() {
        assertTrue(game.getCurrentTime() > 0);
    }

    @Test
    public void testMain() throws IOException {
        try (MockedConstruction<GUI> mockedGui = mockConstruction(GUI.class);
                MockedConstruction<Game> mockedGame = mockConstruction(Game.class,
                        (mock, context) -> doNothing().when(mock).start())) {

            assertDoesNotThrow(() -> {
                try {
                    Game.main(new String[] {});
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            assertTrue(mockedGame.constructed().size() >= 1);
            verify(mockedGame.constructed().get(0)).start();
        }
    }
}
