package ascent.model.entities;

import ascent.model.entities.components.Stats;
import ascent.model.entities.interfaces.Interactable;
import ascent.model.game.Position;
import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(new Position(5, 5));
    }

    @Nested
    class ConstructorTests {

        @Test
        void playerHasCorrectSymbol() {
            assertEquals('►', player.getSymbol());
        }

        @Test
        void playerHasCorrectColor() {
            assertEquals("#6476e8", player.getColor());
        }

        @Test
        void playerHasCorrectPosition() {
            assertEquals(new Position(5, 5), player.getPosition());
        }

        @Test
        void playerHasDefaultStats() {
            Stats stats = player.getStats();
            assertEquals(100, stats.getMaxHealth());
            assertEquals(8, stats.getDamage());
            assertEquals(5, stats.getSpeed());
        }

        @Test
        void playerHasInventoryWith10Slots() {
            for (int i = 0; i < 10; i++) {
                assertTrue(player.addConsumable(new HealthRestore(i, "Potion", 10)));
            }
            assertFalse(player.addConsumable(new HealthRestore(99, "Extra", 10)));
        }
    }

    @Nested
    class WeaponTests {

        private Weapon sword;
        private Weapon knife;

        @BeforeEach
        void setUpWeapons() {
            sword = new Weapon(1, "Sword", 2, 10);
            knife = new Weapon(2, "Knife", 0, 5);
        }

        @Test
        void equipWeaponAppliesBonuses() {
            player.equipWeapon(sword);
            assertEquals(18, player.getStats().getDamage());
            assertEquals(7, player.getStats().getSpeed());
        }

        @Test
        void equipWeaponRemovesOldBonuses() {
            player.equipWeapon(sword);
            player.equipWeapon(knife);

            assertEquals(13, player.getStats().getDamage());
            assertEquals(5, player.getStats().getSpeed());
        }

        @Test
        void equipWeaponReturnsOldWeapon() {
            Weapon axe = new Weapon(2, "Axe", -1, 15);
            player.equipWeapon(sword);
            Weapon returned = player.equipWeapon(axe);

            assertEquals(sword, returned);
        }

        @Test
        void equipNullWeaponRemovesCurrent() {
            player.equipWeapon(sword);
            player.equipWeapon(null);

            assertEquals(8, player.getStats().getDamage());
            assertEquals(5, player.getStats().getSpeed());
        }
    }

    @Nested
    class ArmourTests {

        @Test
        void equipArmourAppliesBonuses() {
            Armour chestplate = new Armour(1, "Chestplate", -2, 10, ArmourSlot.CHEST);
            player.equipArmour(chestplate);

            assertEquals(10, player.getStats().getResistanceToDamage());
            assertEquals(3, player.getStats().getSpeed()); // 5 - 2
        }

        @Test
        void equipArmourRemovesOldBonuses() {
            Armour oldChest = new Armour(1, "Old", 0, 5, ArmourSlot.CHEST);
            Armour newChest = new Armour(2, "New", 0, 8, ArmourSlot.CHEST);

            player.equipArmour(oldChest);
            player.equipArmour(newChest);

            assertEquals(8, player.getStats().getResistanceToDamage());
        }

        @Test
        void equipNullArmourDoesNothing() {
            Armour returned = player.equipArmour(null);
            assertNull(returned);
            assertEquals(0, player.getStats().getResistanceToDamage());
        }
    }

    @Nested
    class ConsumableTests {

        @Test
        void addConsumableSuccessfully() {
            HealthRestore potion = new HealthRestore(1, "Potion", 25);
            assertTrue(player.addConsumable(potion));
        }

        @Test
        void removeConsumableSuccessfully() {
            HealthRestore potion = new HealthRestore(1, "Potion", 25);
            player.addConsumable(potion);
            HealthRestore removed = player.removeConsumable(0);

            assertEquals(potion, removed);
        }

        @Test
        void consumeItemAppliesEffectAndRemoves() {
            player.getStats().takeDamage(50);
            HealthRestore potion = new HealthRestore(1, "Potion", 30);
            player.addConsumable(potion);

            player.consumeItem(0);

            assertEquals(80, player.getStats().getHealth());
            assertTrue(player.getInventory().getConsumables().isEmpty());
        }

        @Test
        void consumeItemWithInvalidIndexDoesNothing() {
            int healthBefore = player.getStats().getHealth();
            player.consumeItem(99);
            assertEquals(healthBefore, player.getStats().getHealth());
        }

        @Test
        void hasInventorySpaceChecksAvailability() {
            assertTrue(player.hasInventorySpace());
            for (int i = 0; i < 10; i++) {
                player.addConsumable(new HealthRestore(i, "Potion", 10));
            }
            assertFalse(player.hasInventorySpace());
        }
    }

    @Nested
    class InteractionTests {

        @Test
        void canInteractWhenAlive() {
            assertTrue(player.canInteract());
        }

        @Test
        void cannotInteractWhenDead() {
            player.getStats().takeDamage(100);
            assertFalse(player.canInteract());
        }

        // Test if player calls, without errors, the method interact in target when it
        // can interact

        @Test
        void interactWithCallsTargetInteract() {
            Interactable mockTarget = mock(Interactable.class);
            when(mockTarget.canInteract()).thenReturn(true);

            player.interactWith(mockTarget);

            verify(mockTarget).interact(player);
        }
    }
}
