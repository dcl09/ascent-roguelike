package Ascent.model.entities;

import Ascent.model.entities.components.Stats;
import Ascent.model.entities.monster.Monster;
import Ascent.model.entities.monster.MonsterType;
import Ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    @Nested
    class ConstructorTests {

        @Test
        void emptyConstructorCreatesInactiveMonster() {
            Monster monster = new Monster();

            assertEquals(new Position(0, 0), monster.getPosition());
            assertFalse(monster.isActive());
        }
    }

    @Nested
    class StatsTests {

        private Monster monster;

        @BeforeEach
        void setUp() {
            monster = new Monster();
            monster.reset(MonsterType.GOBLIN, new Position(0, 0));
        }

        @Test
        void monsterHasGoblinStats() {
            Stats stats = monster.getStats();

            assertEquals(30, stats.getMaxHealth());
            assertEquals(30, stats.getHealth());
            assertEquals(3, stats.getDamage());
            assertEquals(3, stats.getSpeed());
        }

        @Test
        void getMovementSpeedReturnsStatsSpeed() {
            assertEquals(3, monster.getMovementSpeed());
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
        void resetWithMonsterTypeSetsPosition() {
            monster.reset(MonsterType.SKELETON, pos);

            assertEquals(pos, monster.getPosition());
            assertEquals('s', monster.getSymbol());
            assertEquals("#c2c2d1", monster.getColor());
            assertEquals(20, monster.getStats().getMaxHealth());
            assertEquals(4, monster.getStats().getDamage());
            assertEquals(2, monster.getStats().getSpeed());
            assertTrue(monster.isActive());
        }

        @Test
        void resetWithDragonTypeSetsCorrectStats() {
            monster.reset(MonsterType.DRAGON, pos);

            assertEquals('R', monster.getSymbol());
            assertEquals(120, monster.getStats().getMaxHealth());
            assertEquals(15, monster.getStats().getDamage());
            assertEquals(4, monster.getStats().getSpeed());
        }
    }

    @Nested
    class CombatantTests {

        private Monster monster;

        @BeforeEach
        void setUp() {
            monster = new Monster();
            monster.reset(MonsterType.GOBLIN, new Position(0, 0));
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
