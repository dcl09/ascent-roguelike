package model.game.level;

import model.entities.*;
import model.game.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Level {
    private final int width;
    private final int height;

    private Player player;
    private List<Monster> monsters;
    private List<Chest> chests;
    private Map<Position, Wall> walls;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.monsters = new ArrayList<>();
        this.chests = new ArrayList<>();
        this.walls = new HashMap<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters != null ? monsters : new ArrayList<>();
    }

    public List<Chest> getChests() {
        return chests;
    }

    public void setChests(List<Chest> chests) {
        this.chests = chests != null ? chests : new ArrayList<>();
    }

    public List<Wall> getWalls() {
        return List.copyOf(walls.values());
    }

    public void setWalls(List<Wall> walls) {
        this.walls = new HashMap<>();
        if (walls != null) {
            for (Wall wall : walls) {
                this.walls.put(wall.getPosition(), wall);
            }
        }
    }

    public boolean isWall(Position position) {
        return walls.containsKey(position);
    }

    public boolean isMonster(Position position) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isChest(Position position) {
        for (Chest chest : chests) {
            if (chest.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public Chest getChestAt(Position position) {
        for (Chest chest : chests) {
            if (chest.getPosition().equals(position)) {
                return chest;
            }
        }
        return null;
    }
}