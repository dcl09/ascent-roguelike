package ascent.model.items.factories;

import ascent.model.items.HealthRestore;
import ascent.model.items.Item;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;

import java.util.Random;

public class ItemFactory {
    // We separated the ids between different types of items to make it easier to
    // add
    // new ones
    // We created MIN e MAX to facilitate random number generation
    // We added bounds to eliminate the need to use +1 when generating random
    // numbers
    private static final int HEALTH_POTION_SMALL = 1;
    private static final int HEALTH_POTION_MEDIUM = 2;
    private static final int HEALTH_POTION_LARGE = 3;
    private static final int POTION_MIN = 1;
    private static final int POTION_BOUND = 4;
    private static final int SWORD = 11;

    private static final int AXE = 12;
    private static final int KNIFE = 13;
    private static final int WEAPON_MIN = 11;
    private static final int WEAPON_BOUND = 14;
    private static final int HEAD_ARMOUR_BASIC = 21;

    private static final int CHEST_ARMOUR_BASIC = 22;
    private static final int LEGS_ARMOUR_BASIC = 23;
    private static final int FEET_ARMOUR_BASIC = 24;
    private static final int ARMS_ARMOUR_BASIC = 25;
    private static final int ARMOUR_MIN = 21;
    private static final int ARMOUR_BOUND = 26;
    private static ItemFactory instance;
    private final Random random;

    private ItemFactory() {
        random = new Random();
    }

    public static ItemFactory getInstance() {
        if (instance == null) {
            instance = new ItemFactory();
        }
        return instance;
    }

    public Item createItem(int id) throws InvalidItemIdException {
        switch (id) {
            case HEALTH_POTION_SMALL:
                return new HealthRestore(id, "Small Health Potion", 15);
            case HEALTH_POTION_MEDIUM:
                return new HealthRestore(id, "Medium Health Potion", 30);
            case HEALTH_POTION_LARGE:
                return new HealthRestore(id, "Large Health Potion", 60);

            case SWORD:
                return new Weapon(id, "Sword", -1, 8);
            case AXE:
                return new Weapon(id, "Axe", -2, 12);
            case KNIFE:
                return new Weapon(id, "Knife", 0, 5);

            case HEAD_ARMOUR_BASIC:
                return new Armour(id, "Basic Helmet", 0, 3, ArmourSlot.HEAD);
            case CHEST_ARMOUR_BASIC:
                return new Armour(id, "Basic Chestplate", -4, 8, ArmourSlot.CHEST);
            case LEGS_ARMOUR_BASIC:
                return new Armour(id, "Basic Leggings", -3, 5, ArmourSlot.LEGS);
            case FEET_ARMOUR_BASIC:
                return new Armour(id, "Basic Boots", 0, 2, ArmourSlot.FEET);
            case ARMS_ARMOUR_BASIC:
                return new Armour(id, "Basic Bracers", -1, 4, ArmourSlot.ARMS);

            default:
                throw new InvalidItemIdException(id);
        }
    }

    public Weapon createRandomWeapon() {
        int randomId = random.nextInt(WEAPON_MIN, WEAPON_BOUND);
        return (Weapon) createItem(randomId);
    }

    public Armour createRandomArmour() {
        int randomId = random.nextInt(ARMOUR_MIN, ARMOUR_BOUND);
        return (Armour) createItem(randomId);
    }

    public HealthRestore createRandomPotion() {
        int randomId = random.nextInt(POTION_MIN, POTION_BOUND);
        return (HealthRestore) createItem(randomId);
    }

    public Item createRandomItem() {
        int category = random.nextInt(3);
        switch (category) {
            case 0:
                return createRandomWeapon();
            case 1:
                return createRandomArmour();
            default:
                return createRandomPotion();
        }
    }
}