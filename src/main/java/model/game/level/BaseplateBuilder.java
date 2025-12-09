package model.game.level;

import model.entities.*;
import model.entities.pools.MonsterPool;
import model.entities.pools.MonsterPoolEmptyException;
import model.game.Position;

import java.util.ArrayList;
import java.util.List;

public class BaseplateBuilder extends LevelBuilder {
    private final MonsterPool pool;

    private final int numberOfMonsters;
    private final int width;
    private final int height;

    public BaseplateBuilder(int width, int height, int numberOfMonsters) {
        this.width = width;
        this.height = height;
        this.numberOfMonsters = numberOfMonsters;
        this.pool = MonsterPool.getInstance();
    }

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
            walls.add(new Wall(new Position(x, 0)));
            walls.add(new Wall(new Position(x, height - 1)));
        }

        for (int y = 1; y < height - 1; y++) {
            walls.add(new Wall(new Position(0, y)));
            walls.add(new Wall(new Position(width - 1, y)));
        }

        return walls;
    }

    @Override
    protected List<Chest> createChests() {
        // BaseplateBuilder não cria chests
        return new ArrayList<>();
    }

    @Override
    protected List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        for (int iter = 0; iter < numberOfMonsters; iter++) {
            Position spawnLocation;
            switch (iter % 4) {
                case 0:
                    spawnLocation = new Position(1, 1);
                    break;
                case 1:
                    spawnLocation = new Position(width - 2, height - 2);
                    break;
                case 2:
                    spawnLocation = new Position(width - 2, 1);
                    break;
                case 3:
                    spawnLocation = new Position(1, height - 2);
                    break;
                default:
                    spawnLocation = new Position(1, 1);
            }
            try {
                Monster monster = pool.acquire();
                monster.reset(spawnLocation);
                monsters.add(monster);
            } catch (MonsterPoolEmptyException e) {
                System.err.println("Aviso: " + e.getMessage() +
                        " Apenas " + monsters.size() + " monstros criados.");
                break;
            }
        }
        return monsters;
    }
}