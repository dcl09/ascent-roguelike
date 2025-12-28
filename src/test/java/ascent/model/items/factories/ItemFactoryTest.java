package ascent.model.items.factories;

import ascent.model.items.HealthRestore;
import ascent.model.items.Item;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
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

    @Test
    void getInstanceReturnsSameInstance() {
        ItemFactory instance1 = ItemFactory.getInstance();
        ItemFactory instance2 = ItemFactory.getInstance();

        assertSame(instance1, instance2);
    }

    @Nested
    class CreateItemTests {

        @Test
        void createSmallPotion() {
            Item item = factory.createItem(1);

            assertInstanceOf(HealthRestore.class, item);
            assertEquals("Small Health Potion", item.getName());
            assertEquals(15, ((HealthRestore) item).getRestoredHealth());
        }

        @Test
        void createMediumPotion() {
            Item item = factory.createItem(2);

            assertInstanceOf(HealthRestore.class, item);
            assertEquals("Medium Health Potion", item.getName());
            assertEquals(30, ((HealthRestore) item).getRestoredHealth());
        }

        @Test
        void createLargePotion() {
            Item item = factory.createItem(3);

            assertInstanceOf(HealthRestore.class, item);
            assertEquals(60, ((HealthRestore) item).getRestoredHealth());
        }

        @Test
        void createSword() {
            Item item = factory.createItem(11);

            assertInstanceOf(Weapon.class, item);
            assertEquals("Sword", item.getName());
        }

        @Test
        void createAxe() {
            Item item = factory.createItem(12);

            assertInstanceOf(Weapon.class, item);
            assertEquals("Axe", item.getName());
        }

        @Test
        void createKnife() {
            Item item = factory.createItem(13);

            assertInstanceOf(Weapon.class, item);
            assertEquals("Knife", item.getName());
        }

        @Test
        void createHelmet() {
            Item item = factory.createItem(21);
            assertInstanceOf(Armour.class, item);
            assertEquals("Basic Helmet", item.getName());
        }

        @Test
        void createChestplate() {
            Item item = factory.createItem(22);
            assertInstanceOf(Armour.class, item);
            assertEquals("Basic Chestplate", item.getName());
        }

        @Test
        void createLeggings() {
            Item item = factory.createItem(23);
            assertInstanceOf(Armour.class, item);
            assertEquals("Basic Leggings", item.getName());
        }

        @Test
        void createBoots() {
            Item item = factory.createItem(24);
            assertInstanceOf(Armour.class, item);
            assertEquals("Basic Boots", item.getName());
        }

        @Test
        void createBracers() {
            Item item = factory.createItem(25);
            assertInstanceOf(Armour.class, item);
            assertEquals("Basic Bracers", item.getName());
        }

        @Test
        void createItemWithInvalidIdThrowsException() {
            assertThrows(InvalidItemIdException.class, () -> factory.createItem(999));
        }

        @Test
        void invalidItemIdExceptionContainsCorrectId() {
            InvalidItemIdException exception = assertThrows(
                    InvalidItemIdException.class,
                    () -> factory.createItem(999));
            assertEquals(999, exception.getInvalidId());
        }
    }

    @Nested
    class CreateRandomTests {

        @Test
        void createRandomWeaponReturnsWeapon() {
            Weapon weapon = factory.createRandomWeapon();

            assertNotNull(weapon);
            assertInstanceOf(Weapon.class, weapon);
        }

        @Test
        void createRandomArmourReturnsArmour() {
            Armour armour = factory.createRandomArmour();

            assertNotNull(armour);
            assertInstanceOf(Armour.class, armour);
        }

        @Test
        void createRandomPotionReturnsPotion() {
            HealthRestore potion = factory.createRandomPotion();

            assertNotNull(potion);
            assertInstanceOf(HealthRestore.class, potion);
        }

        @Test
        void createRandomItemReturnsNonNull() {
            for (int i = 0; i < 20; i++) {
                Item item = factory.createRandomItem();
                assertNotNull(item);
            }
        }
    }
}