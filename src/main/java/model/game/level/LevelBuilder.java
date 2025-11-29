package model.game.level;

import java.util.List;

public class LevelBuilder {
    public Level createLevel() {
        Level level = new Level(getWidth(), getHeight());

        level.setPlayer(createPlayer());
        level.setMonsters(createMonsters());
        /*
        NOT IMPLEMENTED
        level.setChests(createChests());
         */
        level.setWalls(createWalls());

        return level;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> createWalls();
    /*
    NOT IMPLEMENTED
    protected abstract List<Chests> createChests();
    */
    protected abstract List<Monster> createMonsters();

    protected abstract Player createPlayer();
}
