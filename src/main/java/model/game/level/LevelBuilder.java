package model.game.level;

import model.entities.*;
import java.util.List;

public abstract class LevelBuilder {

    public Level createLevel(Player player) {
        Level level = new Level(getWidth(), getHeight());

        level.setPlayer(player);
        level.setMonsters(createMonsters());
        level.setChests(createChests());
        level.setWalls(createWalls());

        return level;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> createWalls();

    protected abstract List<Chest> createChests();

    protected abstract List<Monster> createMonsters();
}