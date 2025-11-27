package model;

import model.entities.Entity;
import model.entities.Player;

public class GameModel {
    // todo: add new private variables and initialize in constructor.
    private Player player;

    public GameModel(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
