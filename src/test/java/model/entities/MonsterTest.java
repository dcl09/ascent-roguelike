package model.entities;

import model.entities.components.Stats;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Nested
    class ConstructorTests {

        private Position pos;

        @BeforeEach
        void setUp() {
            pos = new Position(5, 10);
        }

        @Test
        void emptyConstructorCreatesInactiveMonster() {
            Monster monster = new Monster();

            assertEquals(new Position(0, 0), monster.getPosition());
            assertEquals('M', monster.getSymbol());
            assertEquals("RED", monster.getColor());
            assertFalse(monster.isActive());
        }

        @Test
        void positionConstructorCreatesActiveMonster() {
            Monster monster = new Monster(pos);

            assertEquals(pos, monster.getPosition());
            assertTrue(monster.isActive());
        }

        @Test
        void fullConstructorSetsAllParameters() {
            Monster monster = new Monster(pos, 'X', "BLUE");

            assertEquals(pos, monster.getPosition());
            assertEquals('X', monster.getSymbol());
            assertEquals("BLUE", monster.getColor());
        }
    }

    @Nested
    class StatsTests {

        private Monster monster;

        @BeforeEach
        void setUp() {
            monster = new Monster();
        }

        @Test
        void monsterHasDefaultStats() {
            Stats stats = monster.getStats();

            assertEquals(90, stats.getMaxHealth());
            assertEquals(90, stats.getHealth());
            assertEquals(2, stats.getDamage());
            assertEquals(2, stats.getSpeed());
        }

        @Test
        void getMovementSpeedReturnsStatsSpeed() {
            assertEquals(2, monster.getMovementSpeed());
        }
    }

    @Nested
    class ActivationTests {

        private Monster monster;

        @BeforeEach
        void setUp() {
            monster = new Monster();
        }

        @Test
        void activateMakesMonsterActive() {
            monster.activate();
            assertTrue(monster.isActive());
        }

        @Test
        void deactivateMakesMonsterInactive() {
            monster.activate();
            monster.deactivate();
            assertFalse(monster.isActive());
        }
    }

    @Nested
    class ResetTests {

        private Monster monster;
        private Position pos;

        @BeforeEach
        void setUp() {
            monster = new Monster();
            pos = new Position(10, 20);
        }

        @Test
        void resetWithPositionRestoresDefaults() {
            monster.getStats().takeDamage(50);
            monster.reset(pos);

            assertEquals(pos, monster.getPosition());
            assertEquals(90, monster.getStats().getHealth());
            assertTrue(monster.isActive());
        }

        @Test
        void fullResetSetsAllValues() {
            monster.reset(pos, 'Z', "GREEN", 150, 5, 3);

            assertEquals(pos, monster.getPosition());
            assertEquals('Z', monster.getSymbol());
            assertEquals("GREEN", monster.getColor());
            assertEquals(150, monster.getStats().getMaxHealth());
            assertEquals(5, monster.getStats().getDamage());
            assertEquals(3, monster.getStats().getSpeed());
        }
    }

    @Nested
    class MovementTests {

        private Monster monster;

        @BeforeEach
        void setUp() {
            Position start = new Position(5, 5);
            monster = new Monster(start);
        }

        @Test
        void moveUpChangesPositionY() {
            monster.moveUp();
            assertEquals(4, monster.getPosition().getY());
            assertEquals(5, monster.getPosition().getX());
        }

        @Test
        void moveDownChangesPositionY() {
            monster.moveDown();
            assertEquals(6, monster.getPosition().getY());
            assertEquals(5, monster.getPosition().getX());
        }

        @Test
        void moveLeftChangesPositionX() {
            monster.moveLeft();
            assertEquals(4, monster.getPosition().getX());
            assertEquals(5, monster.getPosition().getY());
        }

        @Test
        void moveRightChangesPositionX() {
            monster.moveRight();
            assertEquals(6, monster.getPosition().getX());
            assertEquals(5, monster.getPosition().getY());
        }
    }

    @Nested
    class CombatantTests {

        private Monster monster;

        @BeforeEach
        void setUp() {
            monster = new Monster();
        }

        @Test
        void isAliveWhenHealthPositive() {
            assertTrue(monster.isAlive());
        }

        @Test
        void isNotAliveWhenHealthZero() {
            monster.getStats().takeDamage(100);
            assertFalse(monster.isAlive());
        }
    }
}
