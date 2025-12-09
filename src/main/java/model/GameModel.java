package model;

import gui.ACTION;
import model.entities.*;
import model.game.floor.*;

public class GameModel {
    private final Floor floor;
    private final Player player;

    /* todo: add list of items, inventory and other stuff here */

    public GameModel(Player player, Floor floor) {
        this.player = player;
        this.floor = floor;
    }

    public Player getPlayer() {
        return player;
    }

    public Floor getFloor() {
        return floor;
    }

}

