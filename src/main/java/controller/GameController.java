package controller;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import model.GameModel;
import view.GameView;

import java.io.IOException;

public class GameController {
    // todo: add new private variables and initialize in constructor.
    private final GameModel model;
    private final GameView view;

    public GameController(GameView view, GameModel model){
        this.view = view;
        this.model = model;
    }
    public void run() throws IOException {
        view.draw();

        while (true){
            KeyStroke key = view.getKeyStroke();
            if (!conditionals(key))
                break;
            view.refresh();
        }
    }

    public boolean conditionals(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.Escape) {
            // chamar funcao de abrir e fechar menu//
        } else if (key.getKeyType() == KeyType.Character){
                // placeholder //
        } else model.processKey(key);
        return key.getKeyType() != KeyType.EOF;
    }
}

