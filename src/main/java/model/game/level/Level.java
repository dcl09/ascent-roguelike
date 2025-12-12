package model.game.level;

import model.entities.*;
import model.game.Position;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private final int width;
    private final int height;

    private Player player;
    private List<Monster> monsters;
    private List<Chest> chests;
    private List<Wall> walls;
    private List<Door> doors;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        this.monsters = new ArrayList<>();
        this.chests = new ArrayList<>();
        this.walls = new ArrayList<>();
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

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public boolean isWall(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) return true;
        }
        return false;
    }

    public boolean isMonster(Position position) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position)) return true;
        }
        return false;
    }

    public boolean isChest(Position position) {
        for (Chest chest : chests) {
            if (chest.getPosition().equals(position)) return true;
        }
        return false;
    }

    public Chest getChestAt(Position position) {
        for (Chest chest : chests) {
            if (chest.getPosition().equals(position)) return chest;
        }
        return null;
    }

    public boolean isDoor(Position position) {
        for (Door door : doors) {
            if (door.getPosition().equals(position)) return true;
        }
        return false;
    }

    public Door getDoorAt(Position position) {
        for (Door door : doors) {
            if (door.getPosition().equals(position)) return door;
        }
        return null;
    }
}