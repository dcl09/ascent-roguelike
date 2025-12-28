package ascent.model.entities.components;

import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InventoryTest {

    private Inventory inventory;
    private static final int MAX_CONSUMABLES = 3;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(MAX_CONSUMABLES);
    }

    @Nested
    class WeaponTests {
        private Weapon mockWeapon;

        @BeforeEach
        void setUpWeapon() {
            mockWeapon = mock(Weapon.class);
        }

        @Test
        void noWeaponEquippedInitially() {
            assertNull(inventory.getEquippedWeapon());
        }

        @Test
        void equipWeaponSuccessfully() {
            inventory.equipWeapon(mockWeapon);
            assertEquals(mockWeapon, inventory.getEquippedWeapon());
        }

        @Test
        void equipWeaponReturnsOldWeapon() {
            Weapon oldWeapon = mock(Weapon.class);
            inventory.equipWeapon(oldWeapon);

            Weapon returned = inventory.equipWeapon(mockWeapon);

            assertEquals(oldWeapon, returned);
            assertEquals(mockWeapon, inventory.getEquippedWeapon());
        }

        @Test
        void equipWeaponReturnsNullWhenNoPreviousWeapon() {
            Weapon returned = inventory.equipWeapon(mockWeapon);
            assertNull(returned);
        }

        @Test
        void equipWeaponAcceptsNull() {
            inventory.equipWeapon(mockWeapon);

            Weapon returned = inventory.equipWeapon(null);

            assertEquals(mockWeapon, returned);
            assertNull(inventory.getEquippedWeapon());
        }
    }

    @Nested
    class ArmourTests {
        private Armour mockHelmet;
        private Armour mockChestplate;

        @BeforeEach
        void setUpArmour() {
            mockHelmet = mock(Armour.class);
            when(mockHelmet.getSlot()).thenReturn(ArmourSlot.HEAD);

            mockChestplate = mock(Armour.class);
            when(mockChestplate.getSlot()).thenReturn(ArmourSlot.CHEST);
        }

        @Test
        void noArmourInSlotInitially() {
            assertNull(inventory.getArmour(ArmourSlot.HEAD));
            assertNull(inventory.getArmour(ArmourSlot.CHEST));
        }

        @Test
        void equipArmourInCorrectSlot() {
            inventory.equipArmour(mockHelmet);

            assertEquals(mockHelmet, inventory.getArmour(ArmourSlot.HEAD));
            assertNull(inventory.getArmour(ArmourSlot.CHEST));
        }

        @Test
        void equipArmourReturnsOldArmour() {
            Armour oldHelmet = mock(Armour.class);
            when(oldHelmet.getSlot()).thenReturn(ArmourSlot.HEAD);

            inventory.equipArmour(oldHelmet);

            Armour returned = inventory.equipArmour(mockHelmet);

            assertEquals(oldHelmet, returned);
            assertEquals(mockHelmet, inventory.getArmour(ArmourSlot.HEAD));
        }

        @Test
        void equipNullArmourReturnsNull() {
            Armour returned = inventory.equipArmour(null);
            assertNull(returned);
        }

        @Test
        void multipleArmourPiecesInDifferentSlots() {
            inventory.equipArmour(mockHelmet);
            inventory.equipArmour(mockChestplate);

            assertEquals(mockHelmet, inventory.getArmour(ArmourSlot.HEAD));
            assertEquals(mockChestplate, inventory.getArmour(ArmourSlot.CHEST));
        }

        @Test
        void unequipArmourRemovesArmourInSpecifiedSlot() {
            inventory.equipArmour(mockHelmet);

            Armour returned = inventory.unequipArmour(ArmourSlot.HEAD);

            assertEquals(mockHelmet, returned);
            assertNull(inventory.getArmour(ArmourSlot.HEAD));
        }

        @Test
        void unequipArmourReturnsNullIfSlotEmpty() {
            Armour returned = inventory.unequipArmour(ArmourSlot.FEET);
            assertNull(returned);
        }

        @Test
        void getEquippedArmourReturnsDefensiveCopy() {
            inventory.equipArmour(mockHelmet);
            Map<ArmourSlot, Armour> armourMap = inventory.getEquippedArmour();

            assertEquals(1, armourMap.size());
            assertEquals(mockHelmet, armourMap.get(ArmourSlot.HEAD));

            armourMap.clear();

            assertEquals(mockHelmet, inventory.getArmour(ArmourSlot.HEAD));
        }
    }

    @Nested
    class ConsumableTests {
        private HealthRestore mockPotion;

        @BeforeEach
        void setUpConsumable() {
            mockPotion = mock(HealthRestore.class);
        }

        @Test
        void noConsumablesInitially() {
            List<HealthRestore> consumables = inventory.getConsumables();
            assertTrue(consumables.isEmpty());
        }

        @Test
        void addConsumableSuccessfully() {
            inventory.addConsumable(mockPotion);

            assertEquals(1, inventory.getConsumables().size());
            assertEquals(mockPotion, inventory.getConsumables().get(0));
        }

        @Test
        void addConsumableRespectsLimit() {
            for (int i = 0; i < MAX_CONSUMABLES; i++) {
                inventory.addConsumable(mock(HealthRestore.class));
            }

            assertThrows(InventoryFullException.class, () -> inventory.addConsumable(mockPotion));

            assertEquals(MAX_CONSUMABLES, inventory.getConsumables().size());
        }

        @Test
        void removeConsumableSuccessfully() {
            inventory.addConsumable(mockPotion);

            HealthRestore removed = inventory.removeConsumable(0);

            assertEquals(mockPotion, removed);
            assertTrue(inventory.getConsumables().isEmpty());
        }

        @Test
        void removeConsumableWithNegativeIndex() {
            inventory.addConsumable(mockPotion);
            HealthRestore removed = inventory.removeConsumable(-1);
            assertNull(removed);
        }

        @Test
        void removeConsumableWithIndexOutOfBounds() {
            inventory.addConsumable(mockPotion);
            HealthRestore removed = inventory.removeConsumable(1);
            assertNull(removed);
        }

        @Test
        void hasSpaceWhenNotFull() {
            inventory.addConsumable(mockPotion);
            assertTrue(inventory.hasSpaceForConsumable());
        }

        @Test
        void noSpaceWhenFull() {
            for (int i = 0; i < MAX_CONSUMABLES; i++) {
                inventory.addConsumable(mock(HealthRestore.class));
            }
            assertFalse(inventory.hasSpaceForConsumable());
        }

        @Test
        void getConsumablesReturnsDefensiveCopy() {
            inventory.addConsumable(mockPotion);
            List<HealthRestore> listCopy = inventory.getConsumables();

            listCopy.clear();

            assertEquals(1, inventory.getConsumables().size());
        }
    }
}