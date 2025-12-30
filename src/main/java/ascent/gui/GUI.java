package ascent.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUI {
    private final Screen screen;
    private final TextGraphics graphics;
    private final Map<String, TextColor> colorCache;

    public GUI(Screen screen) {
        this.screen = screen;
        this.graphics = screen.newTextGraphics();
        this.colorCache = new HashMap<>();
    }

    public GUI(int width, int height) throws IOException {
        Terminal terminal = createTerminal(width, height);
        this.screen = createScreen(terminal);
        this.graphics = screen.newTextGraphics();
        this.colorCache = new HashMap<>();
    }

    public Terminal createTerminal(int width, int height) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height);
        DefaultTerminalFactory terminalFactory = createTerminalFactory();
        terminalFactory.setInitialTerminalSize(terminalSize);
        return terminalFactory.createTerminal();
    }

    protected DefaultTerminalFactory createTerminalFactory() {
        return new DefaultTerminalFactory();
    }

    public Screen createScreen(Terminal terminal) throws IOException {
        Screen screen;
        screen = createTerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    protected Screen createTerminalScreen(Terminal terminal) throws IOException {
        return new TerminalScreen(terminal);
    }

    public ACTION processKey() throws IOException {
        KeyStroke key = screen.pollInput();
        if (key == null)
            return ACTION.NONE;

        boolean shift = key.isShiftDown();

        return switch (key.getKeyType()) {
            case EOF -> ACTION.QUIT;
            case Escape -> ACTION.MENU;
            case Enter -> ACTION.SELECT;
            case ArrowUp -> shift ? ACTION.LOOK_UP : ACTION.UP;
            case ArrowDown -> shift ? ACTION.LOOK_DOWN : ACTION.DOWN;
            case ArrowLeft -> shift ? ACTION.LOOK_LEFT : ACTION.LEFT;
            case ArrowRight -> shift ? ACTION.LOOK_RIGHT : ACTION.RIGHT;
            case Character -> processCharacter(key);
            default -> ACTION.NONE;
        };
    }

    private ACTION processCharacter(KeyStroke key) {
        char c = key.getCharacter();
        boolean alt = key.isAltDown();
        boolean shift = key.isShiftDown();

        return switch (Character.toLowerCase(c)) {
            case 'e' -> ACTION.INTERACT;
            case 'w' -> shift ? ACTION.LOOK_UP : ACTION.UP;
            case 'a' -> shift ? ACTION.LOOK_LEFT : ACTION.LEFT;
            case 's' -> shift ? ACTION.LOOK_DOWN : ACTION.DOWN;
            case 'd' -> shift ? ACTION.LOOK_RIGHT : ACTION.RIGHT;
            case '1' -> alt ? ACTION.UNEQUIP_WEAPON : ACTION.USE_POTION_0;
            case '2' -> alt ? ACTION.UNEQUIP_HEAD : ACTION.USE_POTION_1;
            case '3' -> alt ? ACTION.UNEQUIP_CHEST : ACTION.USE_POTION_2;
            case '4' -> alt ? ACTION.UNEQUIP_ARMS : ACTION.USE_POTION_3;
            case '5' -> alt ? ACTION.UNEQUIP_LEGS : ACTION.USE_POTION_4;
            case '6' -> alt ? ACTION.UNEQUIP_FEET : ACTION.NONE;
            default -> ACTION.NONE;
        };
    }

    public void drawChar(int x, int y, char c, String color) {
        graphics.setForegroundColor(getColor(color));
        graphics.putString(x, y, "" + c);
    }

    public void drawText(int x, int y, String text, String color) {
        graphics.setForegroundColor(getColor(color));
        graphics.putString(x, y + 1, text);
    }

    private TextColor getColor(String color) {
        return colorCache.computeIfAbsent(color, TextColor.Factory::fromString);
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public void close() throws IOException {
        screen.close();
    }

    public void clear() {
        screen.clear();
    }

}
