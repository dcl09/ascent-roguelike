package Ascent.model.game.floor;
import Ascent.model.entities.*;
import Ascent.model.entities.pools.MonsterPool;
import Ascent.model.entities.pools.MonsterPoolEmptyException;
import Ascent.model.game.Position;

import java.util.ArrayList;
import java.util.List;

/* Temporary level builder for first submission */

public class BaseplateBuilder extends FloorBuilder {
    private final MonsterPool pool;
    private final int numberOfMonsters;
    private final int numberOfChests;
    private final int width;
    private final int height;

    public BaseplateBuilder(int width, int height, int numberOfMonsters, int numberOfChests) {
        this.width = width;
        this.height = height;
        this.numberOfMonsters = numberOfMonsters;
        this.numberOfChests = numberOfChests;
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

    protected List<Chest> createChests() {
        List<Chest> chests = new ArrayList<>();
        for (int iter = 0; iter < numberOfChests; iter++){
        Position SpawnLocation = new Position(5, 10);
        chests.add(new Chest(SpawnLocation, "Yellow", 1 ));
        }
        return chests;
    }

    @Override
    protected List<Monster> createMonsters(){
        List<Monster> monsters = new ArrayList<>();
        for (int iter = 0; iter < numberOfMonsters; iter++) {
            Position SpawnLocation;
            switch ( iter % 4){
                case 0:
                    SpawnLocation = new Position(1, 1);
                    break;
                case 1:
                    SpawnLocation = new Position(width - 2, height - 2);
                    break;
                case 2:
                    SpawnLocation = new Position(width - 2, 1);
                    break;
                case 3:
                    SpawnLocation = new Position(1, height - 2);
                    break;
                default:
                    SpawnLocation = new Position(1, 1);
            }
            try {
                Monster monster = pool.acquire();
                monster.reset(SpawnLocation);
                monsters.add(monster);
            }   catch (MonsterPoolEmptyException e) {
                System.err.println("Aviso: " + e.getMessage() +
                        " Apenas " + monsters.size() + " monstros criados.");
                break;
            }
        }
        return monsters;
    }
}
