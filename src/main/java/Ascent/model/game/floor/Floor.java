package Ascent.model.game.floor;

import Ascent.model.entities.*;
import Ascent.model.entities.monster.Monster;
import Ascent.model.game.Position;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {
    private final int width;
    private final int height;

    private Player player;
    private Map<Position, Monster> monsters;
    private Map<Position, Chest> chests;
    private Map<Position, Wall> walls;
    private Map<Position, Door> doors;
    private Monster lastAttackedMonster;

    public Floor(int width, int height) {
        this.width = width;
        this.height = height;
        this.monsters = new HashMap<>();
        this.chests = new HashMap<>();
        this.walls = new HashMap<>();
        this.doors = new HashMap<>();
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

    public Collection<Monster> getMonsters() {
        return monsters.values();
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters.clear();
        for (Monster monster : monsters) {
            this.monsters.put(monster.getPosition(), monster);
        }
    }

    public boolean moveMonster(Position initialPosition, Position finalPosition) {
        if (!monsters.containsKey(initialPosition)) {
            throw new IllegalArgumentException("Monster does not exist in the position " + initialPosition.toString());
        }
        Monster monster = monsters.get(initialPosition);
        if (player.getPosition().equals(finalPosition)) {
            // monster deals damage to player
            monster.attack(player);
            return false;
        }
        if (chests.containsKey(finalPosition)
                || walls.containsKey(finalPosition)
                || (doors.containsKey(finalPosition) && !doors.get(finalPosition).isOpen())) {
            return false;
        }
        monster.setPosition(finalPosition);
        monsters.put(finalPosition, monster);
        monsters.remove(initialPosition);
        return true;
    }

    public boolean movePlayer(Position finalPosition) {
        // initial basic damage logic
        if (monsters.containsKey(finalPosition)) {
            Monster currMonster = monsters.get(finalPosition);

            // player deals damage to monster
            player.attack(currMonster);
            lastAttackedMonster = currMonster;
            return false;
        } else if (chests.containsKey(finalPosition)
                || walls.containsKey(finalPosition)
                || (doors.containsKey(finalPosition) && !doors.get(finalPosition).isOpen())) {
            return false;
        }
        player.setPosition(finalPosition);
        return true;
    }

    public Collection<Chest> getChests() {
        return chests.values();
    }

    public void setChests(List<Chest> chests) {
        this.chests.clear();
        for (Chest chest : chests) {
            this.chests.put(chest.getPosition(), chest);
        }
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

    public Collection<Door> getDoors() {
        return doors.values();
    }

    public void setDoors(List<Door> doors) {
        this.doors.clear();
        for (Door door : doors) {
            this.doors.put(door.getPosition(), door);
        }
    }

    public boolean isWall(Position position) {
        return walls.containsKey(position);
    }

    public boolean isMonster(Position position) {
        return monsters.containsKey(position);
    }

    public boolean isChest(Position position) {
        return chests.containsKey(position);
    }

    public Chest getChestAt(Position position) {
        return chests.get(position);
    }

    public boolean isDoor(Position position) {
        return doors.containsKey(position);
    }

    public Door getDoorAt(Position position) {
        return doors.get(position);
    }

    public Monster getLastAttackedMonster() {
        return lastAttackedMonster;
    }
}
