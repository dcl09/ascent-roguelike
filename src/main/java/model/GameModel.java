package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import model.entities.Player;
import model.entities.Monster;
import model.entities.Wall;
import model.entities.Chest;

import java.io.IOException;
import java.util.List;

public class GameModel {
    private final Player player;
    private final List<Monster> monsters;
    private final List<Wall> walls;
    private final List<Chest> chests;
    /* todo: add list of items, inventory and other stuff here */

    public GameModel(Player player, List<Monster> monsters, List<Wall> walls, List<Chest> chests) {
        this.player = player;
        this.monsters = monsters;
        this.walls = walls;
        this.chests = chests;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Chest> getChests() {
        return chests;
    }

}

