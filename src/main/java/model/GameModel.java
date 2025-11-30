package model;

import model.entities.*;
import model.entities.pools.MonsterPool;
import model.game.level.*;

public class GameModel {
    private final Level level;
    private final Player player;

    /* todo: add list of items, inventory and other stuff here */

    public GameModel(Player player, Level level) {
        this.player = player;
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public Level getLevel() {
        return level;
    }
}

