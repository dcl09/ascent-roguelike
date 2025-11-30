package gui;

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

    public GUI(int width, int height) throws IOException {
        Terminal terminal = createTerminal(width, height);
        this.screen = createScreen(terminal);
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
        /* change to pollInput after clock implementation */
        KeyStroke key = screen.readInput();
        if (key == null) return null;

        switch (key.getKeyType()) {
            case KeyType.EOF:
                return ACTION.QUIT;
            case KeyType.ArrowUp:
                return ACTION.UP;
            case KeyType.ArrowDown:
                return ACTION.DOWN;
            case KeyType.ArrowLeft:
                return ACTION.LEFT;
            case  KeyType.ArrowRight:
                return ACTION.RIGHT;
            case KeyType.Escape:
                return ACTION.MENU;
            case KeyType.Enter:
                return ACTION.SELECT;
            case KeyType.Character:
                if (key.getCharacter() == 'e') return ACTION.INTERACT;
        }
        return ACTION.NONE;
    }

    public void drawChar(int x, int y, char c, String color){
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.Factory.fromString(color));
        tg.putString(x, y, "" + c);
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

    public void close() throws IOException {
        screen.close();
    }

    public void clear(){
        screen.clear();
    }

}
