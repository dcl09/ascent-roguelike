package model.items;

import model.items.Armour.Armour;
import model.items.Armour.ArmourSlot;

import java.util.Random;

public class ItemFactory {
    // We separated the ids between different types of items to make it easier to add new ones
    // We created MIN e MAX to facilitate random number generation
    // Potions between 1 and 10
    private static final int HEALTH_POTION_SMALL = 1;
    private static final int HEALTH_POTION_MEDIUM = 2;
    private static final int HEALTH_POTION_LARGE = 3;
    private static final int POTION_MIN = 1;
    private static final int POTION_MAX = 3;
    private static final int SWORD = 11;

    // Weapons between 11 and 20
    private static final int AXE = 12;
    private static final int KNIFE = 13;
    private static final int WEAPON_MIN = 11;
    private static final int WEAPON_MAX = 13;
    private static final int HEAD_ARMOUR_BASIC = 21;

    // Armour between 21 and 30
    private static final int CHEST_ARMOUR_BASIC = 22;
    private static final int LEGS_ARMOUR_BASIC = 23;
    private static final int FEET_ARMOUR_BASIC = 24;
    private static final int ARMS_ARMOUR_BASIC = 25;
    private static final int ARMOUR_MIN = 21;
    private static final int ARMOUR_MAX = 25;
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

    public Item createItem(int id) {
        switch (id) {
            case HEALTH_POTION_SMALL:
                return new HealthRestore(id, "Small Health Potion", 15);
            case HEALTH_POTION_MEDIUM:
                return new HealthRestore(id, "Medium Health Potion", 30);
            case HEALTH_POTION_LARGE:
                return new HealthRestore(id, "Large Health Potion", 60);

            case SWORD:
                return new Weapon(id, "Sword", 10);
            case AXE:
                return new Weapon(id, "Axe", 12);
            case KNIFE:
                return new Weapon(id, "Knife", 6);

            case HEAD_ARMOUR_BASIC:
                return new Armour(id, "Basic Helmet", 3, ArmourSlot.HEAD);
            case CHEST_ARMOUR_BASIC:
                return new Armour(id, "Basic Chestplate", 8, ArmourSlot.CHEST);
            case LEGS_ARMOUR_BASIC:
                return new Armour(id, "Basic Leggings", 5, ArmourSlot.LEGS);
            case FEET_ARMOUR_BASIC:
                return new Armour(id, "Basic Boots", 2, ArmourSlot.FEET);
            case ARMS_ARMOUR_BASIC:
                return new Armour(id, "Basic Bracers", 4, ArmourSlot.ARMS);

            default:
                return null;
        }
    }

    //  Method to improve readability and simplicity of code
    //  Makes it easier to generate random items

    private int randomIdInRange(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public Weapon createRandomWeapon() {
        int randomId = randomIdInRange(WEAPON_MIN, WEAPON_MAX);
        return (Weapon) createItem(randomId);
    }

    public Armour createRandomArmour() {
        int randomId = randomIdInRange(ARMOUR_MIN, ARMOUR_MAX);
        return (Armour) createItem(randomId);
    }

    public HealthRestore createRandomPotion() {
        int randomId = randomIdInRange(POTION_MIN, POTION_MAX);
        return (HealthRestore) createItem(randomId);
    }

    public Item createRandomItem() {
        int category = random.nextInt(3);
        int randomId;
        switch (category) {
            case 0:
                return createRandomWeapon();
            case 1:
                return createRandomArmour();
            case 2:
                return createRandomPotion();
            default:
                return null;
        }
    }
}