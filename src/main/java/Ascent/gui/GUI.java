package Ascent.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GUI {
    private final Screen screen;
    private final TextGraphics graphics;

    public GUI(int width, int height) throws IOException {
        Terminal terminal = createTerminal(width, height);
        this.screen = createScreen(terminal);
        this.graphics = screen.newTextGraphics();
    }

    public Terminal createTerminal(int width, int height) throws IOException {
        TerminalSize terminalSize = new TerminalSize(width, height);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(terminalSize);
        return terminalFactory.createTerminal();
    }

    public Screen createScreen(Terminal terminal) throws IOException {
        Screen screen;
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    /* placeholder implementation */
    public ACTION processKey() throws IOException {
        KeyStroke key = screen.pollInput();
        if (key == null)
            return ACTION.NONE;

        return switch (key.getKeyType()) {
            case EOF -> ACTION.QUIT;
            case Escape -> ACTION.MENU;
            case Enter -> ACTION.SELECT;
            case ArrowUp -> key.isShiftDown() ? ACTION.LOOK_UP : ACTION.UP;
            case ArrowDown -> key.isShiftDown() ? ACTION.LOOK_DOWN : ACTION.DOWN;
            case ArrowLeft -> key.isShiftDown() ? ACTION.LOOK_LEFT : ACTION.LEFT;
            case ArrowRight -> key.isShiftDown() ? ACTION.LOOK_RIGHT : ACTION.RIGHT;
            case Character -> processCharacter(key);
            default -> ACTION.NONE;
        };
    }

    private ACTION processCharacter(KeyStroke key) {
        char c = key.getCharacter();
        boolean shift = key.isShiftDown();

        return switch (Character.toLowerCase(c)) {
            case 'e' -> ACTION.INTERACT;
            case 'w' -> shift ? ACTION.LOOK_UP : ACTION.UP;
            case 'a' -> shift ? ACTION.LOOK_LEFT : ACTION.LEFT;
            case 's' -> shift ? ACTION.LOOK_DOWN : ACTION.DOWN;
            case 'd' -> shift ? ACTION.LOOK_RIGHT : ACTION.RIGHT;
            case '0' -> ACTION.USE_POTION_0;
            case '1' -> ACTION.USE_POTION_1;
            case '2' -> ACTION.USE_POTION_2;
            case '3' -> ACTION.USE_POTION_3;
            case '4' -> ACTION.USE_POTION_4;
            case '5' -> ACTION.USE_POTION_5;
            case '6' -> ACTION.USE_POTION_6;
            case '7' -> ACTION.USE_POTION_7;
            case '8' -> ACTION.USE_POTION_8;
            case '9' -> ACTION.USE_POTION_9;
            default -> ACTION.NONE;
        };
    }

    public void drawChar(int x, int y, char c, String color) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(x, y, "" + c);
    }

    public void drawText(int x, int y, String text, String color) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(x, y + 1, text);
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
