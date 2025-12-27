package ascent.model.entities.components;

import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(3); // maxConsumables = 3
    }

    @Nested
    class WeaponTests {

        @Test
        void noWeaponEquippedInitially() {
            assertNull(inventory.getEquippedWeapon());
        }

        @Test
        void equipWeaponSuccessfully() {
            Weapon sword = new Weapon(1, "Sword", 0, 10);

            inventory.equipWeapon(sword);

            assertEquals(sword, inventory.getEquippedWeapon());
        }

        @Test
        void equipWeaponReturnsOldWeapon() {
            Weapon sword = new Weapon(1, "Sword", 0, 10);
            Weapon axe = new Weapon(2, "Axe", -2, 15);

            inventory.equipWeapon(sword);
            Weapon returned = inventory.equipWeapon(axe);

            assertEquals(sword, returned);
            assertEquals(axe, inventory.getEquippedWeapon());
        }

        @Test
        void equipWeaponReturnsNullWhenNoWeapon() {
            Weapon sword = new Weapon(1, "Sword", 0, 10);

            Weapon returned = inventory.equipWeapon(sword);

            assertNull(returned);
        }

        @Test
        void equipWeaponAcceptsNull() {
            Weapon sword = new Weapon(1, "Sword", 0, 10);
            inventory.equipWeapon(sword);

            inventory.equipWeapon(null);

            assertNull(inventory.getEquippedWeapon());
        }
    }

    @Nested
    class ArmourTests {

        @Test
        void noArmourInSlotInitially() {
            assertNull(inventory.getArmour(ArmourSlot.HEAD));
            assertNull(inventory.getArmour(ArmourSlot.CHEST));
        }

        @Test
        void equipArmourInCorrectSlot() {
            Armour helmet = new Armour(1, "Helmet", 0, 5, ArmourSlot.HEAD);

            inventory.equipArmour(helmet);

            assertEquals(helmet, inventory.getArmour(ArmourSlot.HEAD));
            assertNull(inventory.getArmour(ArmourSlot.CHEST));
        }

        @Test
        void equipArmourReturnsOldArmour() {
            Armour helmet1 = new Armour(1, "Old Helmet", 0, 3, ArmourSlot.HEAD);
            Armour helmet2 = new Armour(2, "New Helmet", 0, 8, ArmourSlot.HEAD);

            inventory.equipArmour(helmet1);
            Armour returned = inventory.equipArmour(helmet2);

            assertEquals(helmet1, returned);
            assertEquals(helmet2, inventory.getArmour(ArmourSlot.HEAD));
        }

        @Test
        void equipNullArmourReturnsNull() {
            Armour returned = inventory.equipArmour(null);

            assertNull(returned);
        }

        @Test
        void multipleArmourPiecesInDifferentSlots() {
            Armour helmet = new Armour(1, "Helmet", 0, 5, ArmourSlot.HEAD);
            Armour chestplate = new Armour(2, "Chestplate", -2, 10, ArmourSlot.CHEST);

            inventory.equipArmour(helmet);
            inventory.equipArmour(chestplate);

            assertEquals(helmet, inventory.getArmour(ArmourSlot.HEAD));
            assertEquals(chestplate, inventory.getArmour(ArmourSlot.CHEST));
        }
    }

    @Nested
    class ConsumableTests {

        @Test
        void noConsumablesInitially() {
            List<HealthRestore> consumables = inventory.getConsumables();

            assertTrue(consumables.isEmpty());
        }

        @Test
        void addConsumableSuccessfully() {
            HealthRestore potion = new HealthRestore(1, "Potion", 25);

            boolean added = inventory.addConsumable(potion);

            assertTrue(added);
            assertEquals(1, inventory.getConsumables().size());
        }

        @Test
        void addConsumableRespectsLimit() {
            for (int i = 0; i < 3; i++) {
                inventory.addConsumable(new HealthRestore(i, "Potion" + i, 10));
            }

            HealthRestore extraPotion = new HealthRestore(99, "Extra", 50);
            boolean added = inventory.addConsumable(extraPotion);

            assertFalse(added);
            assertEquals(3, inventory.getConsumables().size());
        }

        @Test
        void removeConsumableSuccessfully() {
            HealthRestore potion1 = new HealthRestore(1, "Potion1", 10);
            HealthRestore potion2 = new HealthRestore(2, "Potion2", 20);
            inventory.addConsumable(potion1);
            inventory.addConsumable(potion2);

            HealthRestore removed = inventory.removeConsumable(0);

            assertEquals(potion1, removed);
            assertEquals(1, inventory.getConsumables().size());
        }

        @Test
        void removeConsumableWithNegativeIndex() {
            inventory.addConsumable(new HealthRestore(1, "Potion", 10));

            HealthRestore removed = inventory.removeConsumable(-1);

            assertNull(removed);
        }

        @Test
        void removeConsumableWithIndexOutOfBounds() {
            inventory.addConsumable(new HealthRestore(1, "Potion", 10));

            HealthRestore removed = inventory.removeConsumable(5);

            assertNull(removed);
        }

        @Test
        void hasSpaceWhenNotFull() {
            inventory.addConsumable(new HealthRestore(1, "Potion", 10));

            assertTrue(inventory.hasSpaceForConsumable());
        }

        @Test
        void noSpaceWhenFull() {
            for (int i = 0; i < 3; i++) {
                inventory.addConsumable(new HealthRestore(i, "Potion", 10));
            }

            assertFalse(inventory.hasSpaceForConsumable());
        }

        @Test
        void getConsumablesReturnsDefensiveCopy() {
            HealthRestore potion = new HealthRestore(1, "Potion", 10);
            inventory.addConsumable(potion);

            List<HealthRestore> consumables = inventory.getConsumables();
            consumables.clear();

            assertEquals(1, inventory.getConsumables().size());
        }
    }
}