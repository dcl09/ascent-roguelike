package Ascent.model.entities.components;

import Ascent.model.items.HealthRestore;
import Ascent.model.items.Weapon;
import Ascent.model.items.armour.Armour;
import Ascent.model.items.armour.ArmourSlot;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
public class Inventory {
    private final Map<ArmourSlot, Armour> equippedArmour;
    private final List<HealthRestore> consumables;
    private final int maxConsumables;
    private Weapon equippedWeapon;

    public Inventory(int maxConsumables) {
        this.maxConsumables = maxConsumables;
        this.equippedArmour = new EnumMap<>(ArmourSlot.class);
        this.consumables = new ArrayList<>();
    }

    @SuppressWarnings("UnusedReturnValue")
    public Weapon equipWeapon(Weapon newWeapon) {
        Weapon oldWeapon = this.equippedWeapon;
        this.equippedWeapon = newWeapon;
        return oldWeapon;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    @SuppressWarnings("UnusedReturnValue")
    public Armour equipArmour(Armour newArmour) {
        if (newArmour == null) return null;
        return equippedArmour.put(newArmour.getSlot(), newArmour);
    }

    public Armour getArmour(ArmourSlot slot) {
        return equippedArmour.get(slot);
    }

    public boolean addConsumable(HealthRestore item) {
        if (consumables.size() >= maxConsumables) {
            return false;
        }
        return consumables.add(item);
    }

    public HealthRestore removeConsumable(int index) {
        if (index < 0 || index >= consumables.size()) {
            return null;
        }
        return consumables.remove(index);
    }

    public boolean hasSpaceForConsumable() {
        return consumables.size() < maxConsumables;
    }

    // A copy of consumables is created so that, if it is changed, it will not affect the Inventory
    public List<HealthRestore> getConsumables() {
        return new ArrayList<>(consumables);
    }
}
