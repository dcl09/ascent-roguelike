package ascent.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GUITest {

    @Mock
    Screen screen;

    @Mock
    TextGraphics graphics;

    GUI gui;

    @BeforeEach
    void setUp() {
        when(screen.newTextGraphics()).thenReturn(graphics);
        gui = new GUI(screen);
    }

    @Test
    void drawChar() {
        gui.drawChar(10, 20, 'X', "#FFFFFF");

        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        verify(graphics).putString(10, 20, "X");
    }

    @Test
    void drawText() {
        gui.drawText(5, 5, "Hello", "#FF0000");

        verify(graphics).setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        verify(graphics).putString(5, 6, "Hello");
    }

    @Test
    void clear() {
        gui.clear();
        verify(screen).clear();
    }

    @Test
    void refresh() throws IOException {
        gui.refresh();
        verify(screen).refresh();
    }

    @Test
    void close() throws IOException {
        gui.close();
        verify(screen).close();
    }

    @Test
    void createTerminalReference() throws IOException {
        assertNotNull(gui.createTerminalFactory());
    }

    @Test
    void createTerminalScreenReference() throws IOException {
        Terminal terminal = mock(Terminal.class);
        when(terminal.getTerminalSize()).thenReturn(new TerminalSize(10, 10));

        try {
            Screen s = gui.createTerminalScreen(terminal);
            assertNotNull(s);
        } catch (Exception e) {

        }
    }

    @Test
    void createTerminal() throws IOException {
        GUI guiSpy = spy(new GUI(screen));
        DefaultTerminalFactory mockFactory = mock(DefaultTerminalFactory.class);
        Terminal mockTerminal = mock(Terminal.class);

        doReturn(mockFactory).when(guiSpy).createTerminalFactory();
        when(mockFactory.createTerminal()).thenReturn(mockTerminal);

        Terminal result = guiSpy.createTerminal(80, 24);

        verify(mockFactory).setInitialTerminalSize(any(TerminalSize.class));
        verify(mockFactory).createTerminal();
        assertEquals(mockTerminal, result);
    }

    @Test
    void createScreen() throws IOException {
        GUI guiSpy = spy(new GUI(screen));
        Terminal mockTerminal = mock(Terminal.class);
        Screen mockScreen = mock(Screen.class);

        doReturn(mockScreen).when(guiSpy).createTerminalScreen(mockTerminal);

        Screen result = guiSpy.createScreen(mockTerminal);

        verify(mockScreen).setCursorPosition(null);
        verify(mockScreen).startScreen();
        verify(mockScreen).doResizeIfNecessary();
        assertEquals(mockScreen, result);
    }

    @Test
    void legacyConstructor() throws IOException {
        Screen mockScreen = mock(Screen.class);
        TextGraphics mockGraphics = mock(TextGraphics.class);
        when(mockScreen.newTextGraphics()).thenReturn(mockGraphics);

        new GUI(10, 10) {
            @Override
            public Terminal createTerminal(int width, int height) {
                return mock(Terminal.class);
            }

            @Override
            public Screen createScreen(Terminal terminal) {
                return mockScreen;
            }
        };
    }

    @ParameterizedTest
    @MethodSource("keyProvider")
    void processKey(KeyStroke inputKey, ACTION expectedAction) throws IOException {
        when(screen.pollInput()).thenReturn(inputKey);

        ACTION result = gui.processKey();

        assertEquals(expectedAction, result);
    }

    static Stream<Arguments> keyProvider() {
        return Stream.of(
                // EOF
                Arguments.of(new KeyStroke(KeyType.EOF), ACTION.QUIT),

                // General Keys
                Arguments.of(new KeyStroke(KeyType.Escape), ACTION.MENU),
                Arguments.of(new KeyStroke(KeyType.Enter), ACTION.SELECT),

                // Unmapped Key
                Arguments.of(new KeyStroke(KeyType.Delete), ACTION.NONE),

                // No Input
                Arguments.of(null, ACTION.NONE),

                // Arrows, with and without shift
                Arguments.of(new KeyStroke(KeyType.ArrowUp), ACTION.UP),
                Arguments.of(new KeyStroke(KeyType.ArrowUp, false, false, true), ACTION.LOOK_UP),

                Arguments.of(new KeyStroke(KeyType.ArrowDown), ACTION.DOWN),
                Arguments.of(new KeyStroke(KeyType.ArrowDown, false, false, true), ACTION.LOOK_DOWN),

                Arguments.of(new KeyStroke(KeyType.ArrowLeft), ACTION.LEFT),
                Arguments.of(new KeyStroke(KeyType.ArrowLeft, false, false, true), ACTION.LOOK_LEFT),

                Arguments.of(new KeyStroke(KeyType.ArrowRight), ACTION.RIGHT),
                Arguments.of(new KeyStroke(KeyType.ArrowRight, false, false, true), ACTION.LOOK_RIGHT),

                // Characters

                // Movement (WASD)
                Arguments.of(new KeyStroke('w', false, false), ACTION.UP),
                Arguments.of(new KeyStroke('W', false, false), ACTION.UP),
                Arguments.of(new KeyStroke('w', false, false, true), ACTION.LOOK_UP),

                Arguments.of(new KeyStroke('a', false, false), ACTION.LEFT),
                Arguments.of(new KeyStroke('A', false, false), ACTION.LEFT),
                Arguments.of(new KeyStroke('a', false, false, true), ACTION.LOOK_LEFT),

                Arguments.of(new KeyStroke('s', false, false), ACTION.DOWN),
                Arguments.of(new KeyStroke('S', false, false), ACTION.DOWN),
                Arguments.of(new KeyStroke('s', false, false, true), ACTION.LOOK_DOWN),

                Arguments.of(new KeyStroke('d', false, false), ACTION.RIGHT),
                Arguments.of(new KeyStroke('D', false, false), ACTION.RIGHT),
                Arguments.of(new KeyStroke('d', false, false, true), ACTION.LOOK_RIGHT),

                // Interaction
                Arguments.of(new KeyStroke('e', false, false), ACTION.INTERACT),
                Arguments.of(new KeyStroke('E', false, false), ACTION.INTERACT),

                // Numeric Actions (Normal = Potion, Alt = Unequip)
                Arguments.of(new KeyStroke('1', false, false), ACTION.USE_POTION_0),
                Arguments.of(new KeyStroke('1', false, true), ACTION.UNEQUIP_WEAPON),

                Arguments.of(new KeyStroke('2', false, false), ACTION.USE_POTION_1),
                Arguments.of(new KeyStroke('2', false, true), ACTION.UNEQUIP_HEAD),

                Arguments.of(new KeyStroke('3', false, false), ACTION.USE_POTION_2),
                Arguments.of(new KeyStroke('3', false, true), ACTION.UNEQUIP_CHEST),

                Arguments.of(new KeyStroke('4', false, false), ACTION.USE_POTION_3),
                Arguments.of(new KeyStroke('4', false, true), ACTION.UNEQUIP_ARMS),

                Arguments.of(new KeyStroke('5', false, false), ACTION.USE_POTION_4),
                Arguments.of(new KeyStroke('5', false, true), ACTION.UNEQUIP_LEGS),

                Arguments.of(new KeyStroke('6', false, false), ACTION.NONE),
                Arguments.of(new KeyStroke('6', false, true), ACTION.UNEQUIP_FEET),

                // Random character
                Arguments.of(new KeyStroke('x', false, false), ACTION.NONE));
    }
}
