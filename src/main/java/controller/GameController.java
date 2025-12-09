package controller;

import model.GameModel;
import model.entities.Chest;
import model.entities.Player;
import model.game.Position;
import model.game.level.Level;
import view.GameView;
import gui.*;

import java.io.IOException;

public class GameController {
    private final GameModel model;
    private final GameView view;
    private final GUI gui;

    public GameController(GameView view, GameModel model, GUI gui) {
        this.view = view;
        this.model = model;
        this.gui = gui;
    }

    public void run() throws IOException {
        view.draw(gui);

        while (true) {
            ACTION curraction = gui.processKey();
            if (curraction == ACTION.QUIT)
                break;
            if (curraction != null)
                processAction(curraction);
            view.draw(gui);
        }

        gui.close();
    }

    public void processAction(ACTION action) {
        Player player = model.getPlayer();
        Position currentPos = player.getPosition();
        Position newPos = null;

        switch (action) {
            case UP:
                newPos = currentPos.getUp();
                if (canMoveTo(newPos)) {
                    player.moveUp();
                }
                break;
            case DOWN:
                newPos = currentPos.getDown();
                if (canMoveTo(newPos)) {
                    player.moveDown();
                }
                break;
            case LEFT:
                newPos = currentPos.getLeft();
                if (canMoveTo(newPos)) {
                    player.moveLeft();
                }
                break;
            case RIGHT:
                newPos = currentPos.getRight();
                if (canMoveTo(newPos)) {
                    player.moveRight();
                }
                break;
            case INTERACT:
                tryInteract(player);
                break;
            default:
                break;
        }
    }

    private boolean canMoveTo(Position position) {
        if (position == null) {
            return false;
        }
        Level level = model.getLevel();
        if (level.isWall(position)) {
            return false;
        }
        if (level.isMonster(position)) {
            return false;
        }
        return true;
    }

    private void tryInteract(Player player) {
        Position playerPos = player.getPosition();
        Level level = model.getLevel();

        // Verificar chests adjacentes
        Position[] adjacentPositions = {
                playerPos.getUp(),
                playerPos.getDown(),
                playerPos.getLeft(),
                playerPos.getRight()
        };

        for (Position pos : adjacentPositions) {
            Chest chest = level.getChestAt(pos);
            if (chest != null && chest.canInteract()) {
                player.interactWith(chest);
                return;
            }
        }
    }
}