package Ascent.model.game.floor;

import Ascent.model.entities.*;
import Ascent.model.entities.monster.Monster;

import java.util.List;

public abstract class FloorBuilder {
    public Floor createFloor(Player player) {
        Floor floor = new Floor(getWidth(), getHeight(), 1);

        floor.setPlayer(player);
        floor.setMonsters(getMonsters());
        floor.setChests(getChests());
        floor.setWalls(getWalls());
        floor.setDoors(getDoors());
        floor.setStairs(getStairs());

        return floor;
    }

    public Floor createFloor(Player player, int currLevel) {
        Floor floor = new Floor(getWidth(), getHeight(), currLevel);

        floor.setPlayer(player);
        floor.setMonsters(getMonsters());
        floor.setChests(getChests());
        floor.setWalls(getWalls());
        floor.setDoors(getDoors());
        floor.setStairs(getStairs());

        return floor;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> getWalls();

    protected abstract List<Chest> getChests();

    protected abstract List<Monster> getMonsters();

    protected abstract List<Door> getDoors();

    protected abstract Stairs getStairs();
}
