package ascent.model.game.floor;

import ascent.model.entities.*;
import ascent.model.entities.monster.Monster;
import ascent.model.entities.monster.MonsterPool;
import ascent.model.entities.monster.MonsterType;
import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    private Floor floor;
    private Player player;

    @BeforeEach
    void setUp() {
        floor = new Floor(10, 10, 1);
        player = new Player(new Position(5, 5));
        floor.setPlayer(player);
    }

    @Nested
    class ConstructorAndGettersTests {

        @Test
        void constructorInitializesCorrectly() {
            Floor f = new Floor(20, 15, 3);
            assertEquals(20, f.getWidth());
            assertEquals(15, f.getHeight());
            assertEquals(3, f.getCurrLevel());
        }

        @Test
        void getPlayerReturnsSetPlayer() {
            assertEquals(player, floor.getPlayer());
        }

        @Test
        void getStairsReturnsSetStairs() {
            Stairs stairs = new Stairs(new Position(9, 9));
            floor.setStairs(stairs);
            assertEquals(stairs, floor.getStairs());
        }
    }

    @Nested
    class EntitySettersTests {

        @Test
        void setWallsPopulatesMap() {
            List<Wall> walls = List.of(
                    new Wall(new Position(0, 0)),
                    new Wall(new Position(1, 0)));
            floor.setWalls(walls);
            assertEquals(2, floor.getWalls().size());
        }

        @Test
        void setWallsClearsPreviousWalls() {
            floor.setWalls(List.of(new Wall(new Position(0, 0)), new Wall(new Position(1, 0))));
            floor.setWalls(List.of(new Wall(new Position(2, 0))));
            assertEquals(1, floor.getWalls().size());
        }

        @Test
        void setDoorsPopulatesMap() {
            List<Door> doors = List.of(new Door(new Position(2, 2)));
            floor.setDoors(doors);
            assertEquals(1, floor.getDoors().size());
        }

        @Test
        void setDoorsClearsPreviousDoors() {
            floor.setDoors(List.of(new Door(new Position(0, 0)), new Door(new Position(1, 0))));
            floor.setDoors(List.of(new Door(new Position(2, 0))));
            assertEquals(1, floor.getDoors().size());
        }

        @Test
        void setChestsPopulatesMap() {
            List<Chest> chests = List.of(new Chest(new Position(3, 3), "#fff"));
            floor.setChests(chests);
            assertEquals(1, floor.getChests().size());
        }

        @Test
        void setChestsClearsPreviousChests() {
            floor.setChests(List.of(new Chest(new Position(0, 0), "#fff"), new Chest(new Position(1, 0), "#fff")));
            floor.setChests(List.of(new Chest(new Position(2, 0), "#fff")));
            assertEquals(1, floor.getChests().size());
        }

        @Test
        void setMonstersPopulatesMap() {
            Monster m = createMonster(new Position(4, 4));
            floor.setMonsters(List.of(m));
            assertEquals(1, floor.getMonsters().size());
        }

        @Test
        void setMonstersClearsPreviousMonsters() {
            floor.setMonsters(List.of(createMonster(new Position(0, 0)), createMonster(new Position(1, 0))));
            floor.setMonsters(List.of(createMonster(new Position(2, 0))));
            assertEquals(1, floor.getMonsters().size());
        }
    }

    @Nested
    class PositionCheckTests {

        @Test
        void isWallReturnsTrueForWallPosition() {
            floor.setWalls(List.of(new Wall(new Position(1, 1))));
            assertTrue(floor.isWall(new Position(1, 1)));
            assertFalse(floor.isWall(new Position(2, 2)));
        }

        @Test
        void isMonsterReturnsTrueForMonsterPosition() {
            Monster m = createMonster(new Position(2, 2));
            floor.setMonsters(List.of(m));
            assertTrue(floor.isMonster(new Position(2, 2)));
            assertFalse(floor.isMonster(new Position(3, 3)));
        }

        @Test
        void isChestReturnsTrueForChestPosition() {
            floor.setChests(List.of(new Chest(new Position(3, 3), "#fff")));
            assertTrue(floor.isChest(new Position(3, 3)));
            assertFalse(floor.isChest(new Position(4, 4)));
        }

        @Test
        void isDoorReturnsTrueForDoorPosition() {
            floor.setDoors(List.of(new Door(new Position(4, 4))));
            assertTrue(floor.isDoor(new Position(4, 4)));
            assertFalse(floor.isDoor(new Position(5, 5)));
        }

        @Test
        void isStairsReturnsTrueForStairsPosition() {
            floor.setStairs(new Stairs(new Position(9, 9)));
            assertTrue(floor.isStairs(new Position(9, 9)));
            assertFalse(floor.isStairs(new Position(0, 0)));
        }

        @Test
        void getChestAtReturnsCorrectChest() {
            Chest chest = new Chest(new Position(3, 3), "#fff");
            floor.setChests(List.of(chest));
            assertEquals(chest, floor.getChestAt(new Position(3, 3)));
            assertNull(floor.getChestAt(new Position(0, 0)));
        }

        @Test
        void getDoorAtReturnsCorrectDoor() {
            Door door = new Door(new Position(4, 4));
            floor.setDoors(List.of(door));
            assertEquals(door, floor.getDoorAt(new Position(4, 4)));
            assertNull(floor.getDoorAt(new Position(0, 0)));
        }
    }

    @Nested
    class MovePlayerTests {

        @BeforeEach
        void setUpStairs() {
            floor.setStairs(new Stairs(new Position(9, 9)));
        }

        @Test
        void movePlayerToEmptySpaceSucceeds() {
            boolean moved = floor.movePlayer(new Position(6, 5), 0);
            assertTrue(moved);
            assertEquals(new Position(6, 5), player.getPosition());
        }

        @Test
        void movePlayerToWallFails() {
            floor.setWalls(List.of(new Wall(new Position(6, 5))));
            boolean moved = floor.movePlayer(new Position(6, 5), 0);
            assertFalse(moved);
            assertEquals(new Position(5, 5), player.getPosition());
        }

        @Test
        void movePlayerToChestFails() {
            floor.setChests(List.of(new Chest(new Position(6, 5), "#fff")));
            boolean moved = floor.movePlayer(new Position(6, 5), 0);
            assertFalse(moved);
        }

        @Test
        void movePlayerToClosedDoorFails() {
            Door door = new Door(new Position(6, 5));
            floor.setDoors(List.of(door));
            boolean moved = floor.movePlayer(new Position(6, 5), 0);
            assertFalse(moved);
        }

        @Test
        void movePlayerToOpenDoorSucceeds() {
            Door door = new Door(new Position(6, 5));
            door.interact(player);
            floor.setDoors(List.of(door));
            boolean moved = floor.movePlayer(new Position(6, 5), 0);
            assertTrue(moved);
        }

        @Test
        void movePlayerToStairsFails() {
            floor.setStairs(new Stairs(new Position(6, 5)));
            boolean moved = floor.movePlayer(new Position(6, 5), 0);
            assertFalse(moved);
        }

        @Test
        void movePlayerToMonsterAttacksMonster() {
            Monster monster = createMonster(new Position(6, 5));
            floor.setMonsters(List.of(monster));
            int initialHealth = monster.getStats().getHealth();

            boolean moved = floor.movePlayer(new Position(6, 5), 1000);

            assertFalse(moved);
            assertTrue(monster.getStats().getHealth() < initialHealth);
            assertEquals(monster, floor.getLastAttackedMonster());
        }

        @Test
        void attackCooldownPreventsRapidAttacks() {
            Monster monster = createMonster(new Position(6, 5));
            floor.setMonsters(List.of(monster));

            floor.movePlayer(new Position(6, 5), 0);
            int healthAfterFirst = monster.getStats().getHealth();

            floor.movePlayer(new Position(6, 5), 100);
            assertEquals(healthAfterFirst, monster.getStats().getHealth());
        }

        @Test
        void attackAllowedExactlyAtCooldown() {
            Monster monster = createMonster(new Position(6, 5));
            floor.setMonsters(List.of(monster));
            long cooldown = player.getAttackCooldown();

            floor.movePlayer(new Position(6, 5), 0);
            int healthAfterFirst = monster.getStats().getHealth();

            floor.movePlayer(new Position(6, 5), cooldown);
            assertTrue(monster.getStats().getHealth() < healthAfterFirst);
        }

        @Test
        void cooldownUsesSubtractionNotAddition() {
            Monster monster = createMonster(new Position(6, 5));
            floor.setMonsters(List.of(monster));

            floor.movePlayer(new Position(6, 5), 500);
            int healthAfterFirst = monster.getStats().getHealth();

            floor.movePlayer(new Position(6, 5), 600);
            assertEquals(healthAfterFirst, monster.getStats().getHealth());
        }

        @Test
        void killingMonsterRemovesFromFloor() {
            Monster monster = createMonster(new Position(6, 5));
            monster.getStats().takeDamage(1000);
            floor.setMonsters(List.of(monster));

            floor.movePlayer(new Position(6, 5), 1000);

            assertFalse(floor.isMonster(new Position(6, 5)));
        }

        @Test
        void killingMonsterReleasesToPool() {
            MonsterPool pool = MonsterPool.getInstance();
            int availableBefore = pool.available();

            Monster monster = createMonster(new Position(6, 5));
            monster.getStats().takeDamage(1000);
            floor.setMonsters(List.of(monster));

            floor.movePlayer(new Position(6, 5), 1000);

            assertEquals(availableBefore + 1, pool.available());
        }
    }

    @Nested
    class MoveMonsterTests {

        @Test
        void moveMonsterToEmptySpaceSucceeds() {
            Monster monster = createMonster(new Position(3, 3));
            floor.setMonsters(List.of(monster));

            boolean moved = floor.moveMonster(new Position(3, 3), new Position(4, 3));

            assertTrue(moved);
            assertEquals(new Position(4, 3), monster.getPosition());
            assertTrue(floor.isMonster(new Position(4, 3)));
            assertFalse(floor.isMonster(new Position(3, 3)));
        }

        @Test
        void moveMonsterFromInvalidPositionThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> floor.moveMonster(new Position(0, 0), new Position(1, 1)));
        }

        @Test
        void moveMonsterToWallFails() {
            Monster monster = createMonster(new Position(3, 3));
            floor.setMonsters(List.of(monster));
            floor.setWalls(List.of(new Wall(new Position(4, 3))));

            boolean moved = floor.moveMonster(new Position(3, 3), new Position(4, 3));

            assertFalse(moved);
            assertEquals(new Position(3, 3), monster.getPosition());
        }

        @Test
        void moveMonsterToChestFails() {
            Monster monster = createMonster(new Position(3, 3));
            floor.setMonsters(List.of(monster));
            floor.setChests(List.of(new Chest(new Position(4, 3), "#fff")));

            assertFalse(floor.moveMonster(new Position(3, 3), new Position(4, 3)));
        }

        @Test
        void moveMonsterToClosedDoorFails() {
            Monster monster = createMonster(new Position(3, 3));
            floor.setMonsters(List.of(monster));
            floor.setDoors(List.of(new Door(new Position(4, 3))));

            assertFalse(floor.moveMonster(new Position(3, 3), new Position(4, 3)));
        }

        @Test
        void moveMonsterToOpenDoorSucceeds() {
            Monster monster = createMonster(new Position(3, 3));
            floor.setMonsters(List.of(monster));
            Door door = new Door(new Position(4, 3));
            door.interact(player);
            floor.setDoors(List.of(door));

            assertTrue(floor.moveMonster(new Position(3, 3), new Position(4, 3)));
        }

        @Test
        void moveMonsterToAnotherMonsterFails() {
            Monster m1 = createMonster(new Position(3, 3));
            Monster m2 = createMonster(new Position(4, 3));
            floor.setMonsters(List.of(m1, m2));

            assertFalse(floor.moveMonster(new Position(3, 3), new Position(4, 3)));
        }

        @Test
        void moveMonsterToPlayerAttacksPlayer() {
            Monster monster = createMonster(new Position(4, 5));
            floor.setMonsters(List.of(monster));
            int initialHealth = player.getStats().getHealth();

            boolean moved = floor.moveMonster(new Position(4, 5), new Position(5, 5));

            assertFalse(moved);
            assertTrue(player.getStats().getHealth() < initialHealth);
        }
    }

    @Nested
    class IsWalkableTests {

        @Test
        void emptyPositionIsWalkable() {
            assertTrue(floor.isWalkable(new Position(0, 0)));
        }

        @Test
        void wallPositionNotWalkable() {
            floor.setWalls(List.of(new Wall(new Position(1, 1))));
            assertFalse(floor.isWalkable(new Position(1, 1)));
        }

        @Test
        void chestPositionNotWalkable() {
            floor.setChests(List.of(new Chest(new Position(2, 2), "#fff")));
            assertFalse(floor.isWalkable(new Position(2, 2)));
        }

        @Test
        void monsterPositionNotWalkable() {
            Monster m = createMonster(new Position(3, 3));
            floor.setMonsters(List.of(m));
            assertFalse(floor.isWalkable(new Position(3, 3)));
        }

        @Test
        void closedDoorNotWalkable() {
            floor.setDoors(List.of(new Door(new Position(4, 4))));
            assertFalse(floor.isWalkable(new Position(4, 4)));
        }

        @Test
        void openDoorIsWalkable() {
            Door door = new Door(new Position(4, 4));
            door.interact(player);
            floor.setDoors(List.of(door));
            assertTrue(floor.isWalkable(new Position(4, 4)));
        }
    }

    @Nested
    class InteractingChestTests {

        @Test
        void interactingChestStartsNull() {
            assertNull(floor.getInteractingChest());
        }

        @Test
        void setInteractingChestUpdatesValue() {
            Chest chest = new Chest(new Position(1, 1), "#fff");
            floor.setInteractingChest(chest);
            assertEquals(chest, floor.getInteractingChest());
        }

        @Test
        void clearInteractingChest() {
            Chest chest = new Chest(new Position(1, 1), "#fff");
            floor.setInteractingChest(chest);
            floor.setInteractingChest(null);
            assertNull(floor.getInteractingChest());
        }
    }

    private Monster createMonster(Position position) {
        Monster monster = new Monster();
        monster.reset(MonsterType.GOBLIN, position);
        return monster;
    }
}
