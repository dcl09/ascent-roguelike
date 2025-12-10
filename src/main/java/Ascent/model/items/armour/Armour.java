package Ascent.model.items.armour;

import Ascent.model.entities.Player;
import Ascent.model.items.EquippableItem;
import Ascent.model.items.Item;

public class Armour extends EquippableItem {
    protected final int bonusResistanceToDamage;
    protected final ArmourSlot slot;

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

    @Override
    public Item use(Player player) {
        return player.equipArmour(this);
    }
}
