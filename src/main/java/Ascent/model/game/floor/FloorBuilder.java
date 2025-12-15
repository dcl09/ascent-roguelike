package Ascent.model.game.floor;

import Ascent.model.entities.*;
import Ascent.model.entities.monster.Monster;

import java.util.List;

public abstract class FloorBuilder {
    public Floor createFloor(Player player) {
        Floor floor = new Floor(getWidth(), getHeight(), 1);

        floor.setPlayer(player);
        floor.setMonsters(createMonsters());
        floor.setChests(createChests());
        floor.setWalls(createWalls());
        floor.setDoors(createDoors());
        floor.setStairs(createStairs());

        return floor;
    }

    public Floor createFloor(Player player, int currLevel) {
        Floor floor = new Floor(getWidth(), getHeight(), currLevel);

        floor.setPlayer(player);
        floor.setMonsters(createMonsters());
        floor.setChests(createChests());
        floor.setWalls(createWalls());
        floor.setDoors(createDoors());
        floor.setStairs(createStairs());

        return floor;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> createWalls();

    protected abstract List<Chest> createChests();

    protected abstract List<Monster> createMonsters();

    protected abstract List<Door> createDoors();

    protected abstract Stairs createStairs();
}
