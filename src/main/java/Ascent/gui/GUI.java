package Ascent.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
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

        switch (key.getKeyType()) {
            case EOF:
                return ACTION.QUIT;
            case ArrowUp:
                if (key.isShiftDown())
                    return ACTION.LOOK_UP;
                return ACTION.UP;
            case ArrowDown:
                if (key.isShiftDown())
                    return ACTION.LOOK_DOWN;
                return ACTION.DOWN;
            case ArrowLeft:
                if (key.isShiftDown())
                    return ACTION.LOOK_LEFT;
                return ACTION.LEFT;
            case ArrowRight:
                if (key.isShiftDown())
                    return ACTION.LOOK_RIGHT;
                return ACTION.RIGHT;
            case Escape:
                return ACTION.MENU;
            case Enter:
                return ACTION.SELECT;
            case Character:
                switch (Character.toLowerCase(key.getCharacter())) {
                    case 'e':
                        return ACTION.INTERACT;
                    case 'w':
                        if (key.isShiftDown())
                            return ACTION.LOOK_UP;
                        return ACTION.UP;
                    case 'a':
                        if (key.isShiftDown())
                            return ACTION.LOOK_LEFT;
                        return ACTION.LEFT;
                    case 's':
                        if (key.isShiftDown())
                            return ACTION.LOOK_DOWN;
                        return ACTION.DOWN;
                    case 'd':
                        if (key.isShiftDown())
                            return ACTION.LOOK_RIGHT;
                        return ACTION.RIGHT;
                }
        }
        return ACTION.NONE;
    }

    public void drawChar(int x, int y, char c, String color) {
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(x, y, "" + c);
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
