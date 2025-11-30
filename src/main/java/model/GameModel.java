package model;

import model.entities.*;
import model.game.level.*;

public class GameModel {
    private final Level level;
    private final Player player;

    /* todo: add list of items, inventory and other stuff here */

    public GameModel(Player player) {
        this.player = player;
        BaseplateBuilder builder = new BaseplateBuilder(40, 20 ,4);
        this.level = builder.createLevel(this.player);
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }

}

