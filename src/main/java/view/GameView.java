package view;



import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import model.GameModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class GameView {
    // todo: add new private variables and initialize in constructor.
    private GameModel model;
    private Screen screen;

    public GameView(GameModel model){
        this.model = model;
    }

    public void draw() throws IOException {
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        Terminal terminal = factory.createTerminal();
        /* this MIGHT be causing errors
        ((AWTTerminalFrame)terminal).addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
        */

        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);   // we don't need a cursor
        screen.startScreen();             // screens must be started
        screen.doResizeIfNecessary();     // resize screen if necessary

        screen.setCharacter(model.getPlayer().getPosition().getX(), model.getPlayer().getPosition().getY(), TextCharacter.fromCharacter(model.getPlayer().getSymbol())[0]);
        screen.refresh();
    }

    public KeyStroke getKeyStroke() throws IOException {
        return screen.readInput();
    }

    public void closeScreen() throws IOException {
        screen.close();
    }

    public void refresh() throws IOException {
        screen.refresh();
    }

}