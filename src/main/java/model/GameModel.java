package model;

import model.entities.*;
import model.game.*;
import model.game.level.BaseplateBuilder;


import java.io.IOException;
import java.util.List;

public class GameModel {
    private final Level level;
    private final Player player;

    /* todo: add list of items, inventory and other stuff here */

    public GameModel(Player player) {
        this.player = player;
        BaseplateBuilder builder = new BaseplateBuilder(50, 50 ,4);
        this.level = builder.createLevel();
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

}

