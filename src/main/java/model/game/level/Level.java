package model.game.level;

import model.entities.*;
import model.game.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private final int width;
    private final int height;

    private Player player;
    private List<Monster> monsters;
    private List<Chest> chests;
    private Map<Position, Wall> walls;
    private List<Door> doors;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.monsters = new ArrayList<>();
        this.chests = new ArrayList<>();
        this.walls = new HashMap<>();
        this.doors = new ArrayList<>();
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
        this.monsters = monsters;
    }

    public List<Chest> getChests() {
        return chests;
    }

    public void setChests(List<Chest> chests) {
        this.chests = chests;
    }

    public Collection<Wall> getWalls() {
        return walls.values();
    }

    public void setWalls(List<Wall> walls) {
        this.walls.clear();
        for (Wall wall : walls) {
            this.walls.put(wall.getPosition(), wall);
        }
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public boolean isWall(Position position) {
        return walls.containsKey(position);
    }

    public boolean isMonster(Position position) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position))
                return true;
        }
        return false;
    }

    public boolean isChest(Position position) {
        for (Chest chest : chests) {
            if (chest.getPosition().equals(position))
                return true;
        }
        return false;
    }

    public Chest getChestAt(Position position) {
        for (Chest chest : chests) {
            if (chest.getPosition().equals(position))
                return chest;
        }
        return null;
    }

    public boolean isDoor(Position position) {
        for (Door door : doors) {
            if (door.getPosition().equals(position))
                return true;
        }
        return false;
    }

    public Door getDoorAt(Position position) {
        for (Door door : doors) {
            if (door.getPosition().equals(position))
                return door;
        }
        return null;
    }
}