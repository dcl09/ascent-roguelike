package model.entities;

import model.entities.components.Stats;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Nested
    class ConstructorTests {

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
            Position pos = new Position(5, 10);
            Monster monster = new Monster(pos);

            assertEquals(pos, monster.getPosition());
            assertTrue(monster.isActive());
        }

        @Test
        void fullConstructorSetsAllParameters() {
            Position pos = new Position(3, 7);
            Monster monster = new Monster(pos, 'X', "BLUE");

            assertEquals(pos, monster.getPosition());
            assertEquals('X', monster.getSymbol());
            assertEquals("BLUE", monster.getColor());
        }
    }


    @Nested
    class StatsTests {

        @Test
        void monsterHasDefaultStats() {
            Monster monster = new Monster();
            Stats stats = monster.getStats();

            assertEquals(90, stats.getMaxHealth());
            assertEquals(90, stats.getHealth());
            assertEquals(2, stats.getDamage());
            assertEquals(2, stats.getSpeed());
        }

        @Test
        void getMovementSpeedReturnsStatsSpeed() {
            Monster monster = new Monster();

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

        @Test
        void resetWithPositionRestoresDefaults() {
            Monster monster = new Monster();
            monster.getStats().takeDamage(50);

            Position newPos = new Position(10, 20);
            monster.reset(newPos);

            assertEquals(newPos, monster.getPosition());
            assertEquals(90, monster.getStats().getHealth());
            assertTrue(monster.isActive());
        }

        @Test
        void fullResetSetsAllValues() {
            Monster monster = new Monster();
            Position newPos = new Position(15, 25);

            monster.reset(newPos, 'Z', "GREEN", 150, 5, 3);

            assertEquals(newPos, monster.getPosition());
            assertEquals('Z', monster.getSymbol());
            assertEquals("GREEN", monster.getColor());
            assertEquals(150, monster.getStats().getMaxHealth());
            assertEquals(5, monster.getStats().getDamage());
            assertEquals(3, monster.getStats().getSpeed());
        }
    }

    @Nested
    class MovementTests {

        @Test
        void moveUpChangesPositionY() {
            Position start = new Position(5, 5);
            Monster monster = new Monster(start);

            monster.moveUp();

            assertEquals(4, monster.getPosition().getY());
            assertEquals(5, monster.getPosition().getX());
        }

        @Test
        void moveDownChangesPositionY() {
            Position start = new Position(5, 5);
            Monster monster = new Monster(start);

            monster.moveDown();

            assertEquals(6, monster.getPosition().getY());
            assertEquals(5, monster.getPosition().getX());
        }

        @Test
        void moveLeftChangesPositionX() {
            Position start = new Position(5, 5);
            Monster monster = new Monster(start);

            monster.moveLeft();

            assertEquals(4, monster.getPosition().getX());
            assertEquals(5, monster.getPosition().getY());
        }

        @Test
        void moveRightChangesPositionX() {
            Position start = new Position(5, 5);
            Monster monster = new Monster(start);

            monster.moveRight();

            assertEquals(6, monster.getPosition().getX());
            assertEquals(5, monster.getPosition().getY());
        }
    }

    @Nested
    class CombatantTests {

        @Test
        void isAliveWhenHealthPositive() {
            Monster monster = new Monster();

            assertTrue(monster.isAlive());
        }

        @Test
        void isNotAliveWhenHealthZero() {
            Monster monster = new Monster();
            monster.getStats().takeDamage(100);

            assertFalse(monster.isAlive());
        }
    }
}