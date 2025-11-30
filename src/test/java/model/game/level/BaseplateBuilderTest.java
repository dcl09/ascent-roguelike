package model.game.level;

import model.entities.*;
import model.entities.pools.MonsterPool;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BaseplateBuilderTest {

    @BeforeEach
    void setUp() {
        MonsterPool pool = MonsterPool.getInstance();
        while (pool.available() < 30) {
            pool.release(new Monster());
        }
    }

    @Test
    void createLevel_ShouldSetCorrectDimensions() {
        BaseplateBuilder builder = new BaseplateBuilder(30, 20, 4);
        Player player = new Player(new Position(15, 10));

        Level level = builder.createLevel(player);

        assertEquals(30, level.getWidth());
        assertEquals(20, level.getHeight());
    }

    @Test
    void createLevel_ShouldSetPlayer() {
        BaseplateBuilder builder = new BaseplateBuilder(30, 20, 4);
        Player player = new Player(new Position(15, 10));

        Level level = builder.createLevel(player);

        assertEquals(player, level.getPlayer());
    }

    @Test
    void createLevel_ShouldCreateBorderWalls() {
        BaseplateBuilder builder = new BaseplateBuilder(10, 8, 0);
        Level level = builder.createLevel(new Player(new Position(5, 4)));

        List<Wall> walls = level.getWalls();

        // Verify corners
        assertTrue(level.isWall(new Position(0, 0)));
        assertTrue(level.isWall(new Position(9, 0)));
        assertTrue(level.isWall(new Position(0, 7)));
        assertTrue(level.isWall(new Position(9, 7)));
    }

    @Test
    void createLevel_ShouldCreateRequestedNumberOfMonsters() {
        BaseplateBuilder builder = new BaseplateBuilder(20, 20, 4);
        Level level = builder.createLevel(new Player(new Position(10, 10)));

        assertEquals(4, level.getMonsters().size());
    }

    @Test
    void createLevel_MonstersShouldSpawnInCorners() {
        BaseplateBuilder builder = new BaseplateBuilder(20, 20, 4);
        Level level = builder.createLevel(new Player(new Position(10, 10)));

        List<Monster> monsters = level.getMonsters();

        // Check which monsters are in the expected corners.
        assertTrue(monsters.stream().anyMatch(m ->
                m.getPosition().equals(new Position(1, 1))));
        assertTrue(monsters.stream().anyMatch(m ->
                m.getPosition().equals(new Position(18, 18))));
    }

    @Test
    void createLevel_ShouldHandlePoolExhaustion() {
        MonsterPool pool = MonsterPool.getInstance();
        java.util.List<Monster> acquired = new java.util.ArrayList<>();
        while (pool.hasAvailable()) {
            acquired.add(pool.acquire());
        }

        BaseplateBuilder builder = new BaseplateBuilder(20, 20, 10);
        Level level = builder.createLevel(new Player(new Position(10, 10)));

        assertEquals(0, level.getMonsters().size());

        for (Monster m : acquired) {
            pool.release(m);
        }
    }
}