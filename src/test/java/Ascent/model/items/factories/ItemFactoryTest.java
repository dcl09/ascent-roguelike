package Ascent.model.items.factories;

import Ascent.model.items.HealthRestore;
import Ascent.model.items.Item;
import Ascent.model.items.Weapon;
import Ascent.model.items.armour.Armour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {

    private ItemFactory factory;

    @BeforeEach
    void setUp() {
        factory = ItemFactory.getInstance();
    }

    // Test singleton: getInstance should always return the same instance
    @Test
    void getInstanceReturnsSameInstance() {
        ItemFactory instance1 = ItemFactory.getInstance();
        ItemFactory instance2 = ItemFactory.getInstance();

        assertSame(instance1, instance2);
    }

    @Nested
    class CreateItemTests {

        // Potions (IDs 1-3)
        // createItem(1) creates Small Health Potion
        @Test
        void createSmallPotion() {
            Item item = factory.createItem(1);

            assertInstanceOf(HealthRestore.class, item);
            assertEquals("Small Health Potion", item.getName());
            assertEquals(15, ((HealthRestore) item).getRestoredHealth());
        }

        // createItem(2) creates Medium Health Potion
        @Test
        void createMediumPotion() {
            Item item = factory.createItem(2);

            assertInstanceOf(HealthRestore.class, item);
            assertEquals("Medium Health Potion", item.getName());
            assertEquals(30, ((HealthRestore) item).getRestoredHealth());
        }

        // createItem(3) creates Large Health Potion
        @Test
        void createLargePotion() {
            Item item = factory.createItem(3);

            assertInstanceOf(HealthRestore.class, item);
            assertEquals(60, ((HealthRestore) item).getRestoredHealth());
        }

        // Weapons (IDs 11-13)
        // createItem(11) creates Sword
        @Test
        void createSword() {
            Item item = factory.createItem(11);

            assertInstanceOf(Weapon.class, item);
            assertEquals("Sword", item.getName());
        }

        // createItem(12) creates Axe
        @Test
        void createAxe() {
            Item item = factory.createItem(12);

            assertInstanceOf(Weapon.class, item);
            assertEquals("Axe", item.getName());
        }

        // createItem(13) creates Knife
        @Test
        void createKnife() {
            Item item = factory.createItem(13);

            assertInstanceOf(Weapon.class, item);
            assertEquals("Knife", item.getName());
        }

        // Armour (IDs 21-25)
        // createItem(21) creates Basic Helmet
        @Test
        void createHelmet() {
            Item item = factory.createItem(21);

            assertInstanceOf(Armour.class, item);
            assertEquals("Basic Helmet", item.getName());
        }

        // createItem with invalid ID throws InvalidItemIdException
        @Test
        void createItemWithInvalidIdThrowsException() {
            assertThrows(InvalidItemIdException.class, () -> factory.createItem(999));
        }

        @Test
        void invalidItemIdExceptionContainsCorrectId() {
            InvalidItemIdException exception = assertThrows(
                    InvalidItemIdException.class,
                    () -> factory.createItem(999)
            );
            assertEquals(999, exception.getInvalidId());
        }
    }

    @Nested
    class CreateRandomTests {

        // createRandomWeapon should return a Weapon
        @Test
        void createRandomWeaponReturnsWeapon() {
            Weapon weapon = factory.createRandomWeapon();

            assertNotNull(weapon);
            assertInstanceOf(Weapon.class, weapon);
        }

        // createRandomArmour should return an Armour
        @Test
        void createRandomArmourReturnsArmour() {
            Armour armour = factory.createRandomArmour();

            assertNotNull(armour);
            assertInstanceOf(Armour.class, armour);
        }

        // createRandomPotion should return a HealthRestore
        @Test
        void createRandomPotionReturnsPotion() {
            HealthRestore potion = factory.createRandomPotion();

            assertNotNull(potion);
            assertInstanceOf(HealthRestore.class, potion);
        }

        // createRandomItem should never return null, test multiple times
        @Test
        void createRandomItemReturnsNonNull() {
            for (int i = 0; i < 20; i++) {
                Item item = factory.createRandomItem();
                assertNotNull(item);
            }
        }
    }
}