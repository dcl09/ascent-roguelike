package controller;

import model.GameModel;
import view.GameView;

public class GameController {
    // todo: add new private variables and initialize in constructor.
    private GameModel model;
    private GameView view;

    public GameController(GameView view, GameModel model){
        this.view = view;
        this.model = model;
    }
    public void run(){
        }
}

