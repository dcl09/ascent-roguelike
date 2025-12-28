package ascent.model.game.floor;

import ascent.model.entities.*;
import ascent.model.entities.monster.Monster;
import ascent.model.entities.monster.MonsterType;
import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorBuilderTest {

    private StubFloorBuilder builder;
    private Player player;

    private static class StubFloorBuilder extends FloorBuilder {
        @Override
        protected int getWidth() {
            return 10;
        }

        @Override
        protected int getHeight() {
            return 10;
        }

        @Override
        protected List<Wall> getWalls() {
            List<Wall> walls = new ArrayList<>();
            walls.add(new Wall(new Position(0, 0)));
            return walls;
        }

        @Override
        protected List<Chest> getChests() {
            List<Chest> chests = new ArrayList<>();
            chests.add(new Chest(new Position(1, 1), "gold"));
            return chests;
        }

        @Override
        protected List<Monster> getMonsters() {
            List<Monster> monsters = new ArrayList<>();
            Monster monster = new Monster();
            monster.reset(MonsterType.GOBLIN, new Position(2, 2));
            monsters.add(monster);
            return monsters;
        }

        @Override
        protected List<Door> getDoors() {
            List<Door> doors = new ArrayList<>();
            doors.add(new Door(new Position(3, 3)));
            return doors;
        }

        @Override
        protected Stairs getStairs() {
            return new Stairs(new Position(4, 4));
        }
    }

    @BeforeEach
    void setUp() {
        builder = new StubFloorBuilder();
        player = new Player(new Position(5, 5));
    }

    @Nested
    class CreateFloorWithDefaultLevelTests {

        @Test
        void createFloorReturnsNonNull() {
            assertNotNull(builder.createFloor(player));
        }

        @Test
        void createFloorSetsCorrectDimensions() {
            Floor floor = builder.createFloor(player);
            assertEquals(10, floor.getWidth());
            assertEquals(10, floor.getHeight());
        }

        @Test
        void createFloorSetsPlayer() {
            Floor floor = builder.createFloor(player);
            assertSame(player, floor.getPlayer());
        }

        @Test
        void createFloorSetsDefaultLevel() {
            Floor floor = builder.createFloor(player);
            assertEquals(1, floor.getCurrLevel());
        }

        @Test
        void createFloorSetsWalls() {
            Floor floor = builder.createFloor(player);
            assertFalse(floor.getWalls().isEmpty());
            assertTrue(floor.isWall(new Position(0, 0)));
        }

        @Test
        void createFloorSetsDoors() {
            Floor floor = builder.createFloor(player);
            assertFalse(floor.getDoors().isEmpty());
            assertTrue(floor.isDoor(new Position(3, 3)));
        }

        @Test
        void createFloorSetsChests() {
            Floor floor = builder.createFloor(player);
            assertFalse(floor.getChests().isEmpty());
            assertTrue(floor.isChest(new Position(1, 1)));
        }

        @Test
        void createFloorSetsMonsters() {
            Floor floor = builder.createFloor(player);
            assertFalse(floor.getMonsters().isEmpty());
            assertTrue(floor.isMonster(new Position(2, 2)));
        }

        @Test
        void createFloorSetsStairs() {
            Floor floor = builder.createFloor(player);
            assertNotNull(floor.getStairs());
            assertTrue(floor.isStairs(new Position(4, 4)));
        }
    }

    @Nested
    class CreateFloorWithCustomLevelTests {

        @Test
        void createFloorWithLevelSetsCorrectLevel() {
            Floor floor = builder.createFloor(player, 5);
            assertEquals(5, floor.getCurrLevel());
        }

        @Test
        void createFloorWithLevelSetsPlayer() {
            Floor floor = builder.createFloor(player, 3);
            assertSame(player, floor.getPlayer());
        }

        @Test
        void createFloorWithLevelSetsDimensions() {
            Floor floor = builder.createFloor(player, 2);
            assertEquals(10, floor.getWidth());
            assertEquals(10, floor.getHeight());
        }

        @Test
        void createFloorWithLevelSetsWalls() {
            Floor floor = builder.createFloor(player, 2);
            assertFalse(floor.getWalls().isEmpty());
            assertTrue(floor.isWall(new Position(0, 0)));
        }

        @Test
        void createFloorWithLevelSetsDoors() {
            Floor floor = builder.createFloor(player, 2);
            assertFalse(floor.getDoors().isEmpty());
            assertTrue(floor.isDoor(new Position(3, 3)));
        }

        @Test
        void createFloorWithLevelSetsChests() {
            Floor floor = builder.createFloor(player, 2);
            assertFalse(floor.getChests().isEmpty());
            assertTrue(floor.isChest(new Position(1, 1)));
        }

        @Test
        void createFloorWithLevelSetsMonsters() {
            Floor floor = builder.createFloor(player, 2);
            assertFalse(floor.getMonsters().isEmpty());
            assertTrue(floor.isMonster(new Position(2, 2)));
        }

        @Test
        void createFloorWithLevelSetsStairs() {
            Floor floor = builder.createFloor(player, 2);
            assertNotNull(floor.getStairs());
            assertTrue(floor.isStairs(new Position(4, 4)));
        }
    }
}
