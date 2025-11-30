package controller;

import model.GameModel;
import model.game.Position;
import view.GameView;
import gui.*;

import java.io.IOException;

public class GameController {
    // todo: add new private variables and initialize in constructor.
    private final GameModel model;
    private final GameView view;
    private final GUI gui;

    public GameController(GameView view, GameModel model, GUI gui){
        this.view = view;
        this.model = model;
        this.gui = gui;
    }
    public void run() throws IOException {
        view.draw(gui);

        while (true){
            ACTION curraction = gui.processKey();
            if (curraction == ACTION.QUIT)
                break;
            if (curraction != null)
                processAction(curraction);
            // move monsters
            gui.refresh();
        }

        gui.close();
    }

    public void processAction(ACTION action){
        Position currentPos = model.getPlayer().getPosition();
        Position newPos = null;

        switch (action) {
            case UP:
                newPos = currentPos.getUp();
                if (conditionals(newPos))
                    model.getPlayer().moveUp();
                break;
            case DOWN:
                newPos = currentPos.getDown();
                if (conditionals(newPos))
                    model.getPlayer().moveDown();
                break;
            case LEFT:
                newPos = currentPos.getLeft();
                if (conditionals(newPos))
                    model.getPlayer().moveLeft();
                break;
            case RIGHT:
                newPos = currentPos.getRight();
                if (conditionals(newPos))
                    model.getPlayer().moveRight();
                break;
            default:
                break;
        }
    }

    public boolean conditionals(Position position) {
        if (position == null)
            return false;
        if (!model.getLevel().isWall(position))
            return true;
        return false;
    }

}

