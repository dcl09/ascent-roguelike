package model.entities.items.Armour;

import model.entities.items.Item;

public class Armour extends Item {
    protected int bonusResistenceToDamage;
    protected ArmourSlot slot;

    public Armour(Integer id, String name, int resistenceToDamage, ArmourSlot slot) {
        super(id, name);
        this.bonusResistenceToDamage = resistenceToDamage;
        this.slot = slot;
    }

    public ArmourSlot getSlot() {
        return slot;
    }

    public int getBonusResistenceToDamage() {
        return bonusResistenceToDamage;
    }
}
