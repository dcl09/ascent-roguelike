package model.game.level;
import model.entities.*;
import model.entities.pools.MonsterPool;
import model.game.Position;

import java.util.ArrayList;
import java.util.List;

/* Temporary level builder for first submission */

public class BaseplateBuilder extends LevelBuilder{
    private MonsterPool pool = MonsterPool.getInstance();
    private List<Monster> monsters;
    private int number_monsters;

    @Override
    protected int getWidth() {
        return width;
    }

    @Override
    protected int getHeight() {
        return height;
    }

    @Override
    protected List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 0));
            walls.add(new Wall(x, height - 1));
        }

        for (int y = 1; y < height - 1; y++) {
            walls.add(new Wall(0, y));
            walls.add(new Wall(width - 1, y));
        }

        return walls;
    }

    @Override
    protected Player createPlayer() {
        return new Player(new Position(width, height));
    }
}
