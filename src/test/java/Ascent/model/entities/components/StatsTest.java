package Ascent.model.entities.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {
    private Stats stats;

    @BeforeEach
    void setUp() {
        stats = new Stats(100, 10, 5);
    }

    @Nested
    class ConstructorTests {

        @Test
        void constructorSetsHealthToMaxHealth() {
            assertEquals(100, stats.getHealth());
            assertEquals(100, stats.getMaxHealth());
        }

        @Test
        void constructorSetsDamage() {
            assertEquals(10, stats.getDamage());
        }

        @Test
        void constructorSetsSpeed() {
            assertEquals(5, stats.getSpeed());
        }

        @Test
        void constructorSetsResistanceToZero() {
            assertEquals(0, stats.getResistanceToDamage());
        }
    }

    @Nested
    class TakeDamageTests {

        @Test
        void takeDamageReducesHealth() {
            stats.takeDamage(30);

            assertEquals(70, stats.getHealth());
        }

        @Test
        void takeDamageDoesNotGoBelowZero() {
            stats.takeDamage(150);

            assertEquals(0, stats.getHealth());
        }

        @Test
        void takeDamageUsesResistanceFirst() {
            stats.addResistanceToDamage(20);
            stats.takeDamage(15);

            assertEquals(100, stats.getHealth());
            assertEquals(5, stats.getResistanceToDamage());
        }

        @Test
        void takeDamageOverflowsFromResistance() {
            stats.addResistanceToDamage(10);
            stats.takeDamage(25);

            assertEquals(85, stats.getHealth());
            assertEquals(0, stats.getResistanceToDamage());
        }

        @Test
        void takeDamageWithZeroDoesNothing() {
            stats.takeDamage(0);

            assertEquals(100, stats.getHealth());
        }
    }

    @Nested
    class HealTests {

        @Test
        void healIncreasesHealth() {
            stats.takeDamage(50);
            stats.heal(30);

            assertEquals(80, stats.getHealth());
        }

        @Test
        void healDoesNotExceedMaxHealth() {
            stats.takeDamage(20);
            stats.heal(50);

            assertEquals(100, stats.getHealth());
        }

        @Test
        void healAtFullHealthDoesNothing() {
            stats.heal(50);

            assertEquals(100, stats.getHealth());
        }
    }

    @Nested
    class ResistanceTests {

        @Test
        void addResistanceIncreases() {
            stats.addResistanceToDamage(15);

            assertEquals(15, stats.getResistanceToDamage());
        }

        @Test
        void loseResistanceDecreases() {
            stats.addResistanceToDamage(20);
            stats.loseResistanceToDamage(5);

            assertEquals(15, stats.getResistanceToDamage());
        }

        @Test
        void loseResistanceDoesNotGoBelowZero() {
            stats.addResistanceToDamage(10);
            stats.loseResistanceToDamage(20);

            assertEquals(0, stats.getResistanceToDamage());
        }
    }

    @Nested
    class DamageModifierTests {

        @Test
        void addDamageIncreasesDamage() {
            stats.addDamage(5);

            assertEquals(15, stats.getDamage());
        }

        @Test
        void loseDamageDecreasesDamage() {
            stats.loseDamage(3);

            assertEquals(7, stats.getDamage());
        }

        @Test
        void loseDamageDoesNotGoBelowZero() {
            stats.loseDamage(20);

            assertEquals(0, stats.getDamage());
        }
    }

    @Nested
    class SpeedModifierTests {

        @Test
        void addSpeedIncreasesSpeed() {
            stats.addSpeed(3);

            assertEquals(8, stats.getSpeed());
        }

        @Test
        void loseSpeedDecreasesSpeed() {
            stats.loseSpeed(2);

            assertEquals(3, stats.getSpeed());
        }

        @Test
        void loseSpeedDoesNotGoBelowZero() {
            stats.loseSpeed(10);

            assertEquals(0, stats.getSpeed());
        }
    }

    @Nested
    class StateTests {

        @Test
        void isDeadReturnsFalseWhenAlive() {
            assertFalse(stats.isDead());
        }

        @Test
        void isDeadReturnsTrueWhenHealthIsZero() {
            stats.takeDamage(100);

            assertTrue(stats.isDead());
        }

        @Test
        void getHealthPercentageCalculatesCorrectly() {
            stats.takeDamage(25);

            assertEquals(0.75f, stats.getHealthPercentage(), 0.001f);
        }

        @Test
        void getHealthPercentageReturnsOneAtFullHealth() {
            assertEquals(1.0f, stats.getHealthPercentage(), 0.001f);
        }

        @Test
        void getHealthPercentageReturnsZeroWhenDead() {
            stats.takeDamage(100);

            assertEquals(0.0f, stats.getHealthPercentage(), 0.001f);
        }
    }

    @Nested
    class ResetTests {

        @Test
        void resetRestoresAllValues() {
            // Change Ascent.state
            stats.takeDamage(50);
            stats.addResistanceToDamage(20);
            stats.addDamage(5);
            stats.addSpeed(3);

            // Reset with new values
            stats.reset(80, 15, 7);

            assertEquals(80, stats.getHealth());
            assertEquals(80, stats.getMaxHealth());
            assertEquals(15, stats.getDamage());
            assertEquals(7, stats.getSpeed());
            assertEquals(0, stats.getResistanceToDamage());
        }
    }
}
