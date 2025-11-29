package model.items.Armour;

import model.entities.Player;
import model.items.EquippableItem;
import model.items.Item;

public class Armour extends EquippableItem {
    protected int bonusResistanceToDamage;
    protected ArmourSlot slot;

    public Armour(Integer id, String name, int changeInSpeed, int bonusResistanceToDamage, ArmourSlot slot) {
        super(id, name, changeInSpeed);
        this.bonusResistanceToDamage = bonusResistanceToDamage;
        this.slot = slot;
    }

    public int getBonusResistanceToDamage() {
        return bonusResistanceToDamage;
    }

    public ArmourSlot getSlot() {
        return slot;
    }

    @Override
    protected void onEquipStats(Player player) {
        player.getStats().addResistanceToDamage(bonusResistanceToDamage);
    }

    @Override
    protected void onUnequipStats(Player player) {
        player.getStats().loseResistanceToDamage(bonusResistanceToDamage);
    }
}
