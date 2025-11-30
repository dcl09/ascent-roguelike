package model.items;

import model.entities.Player;

public class Weapon extends EquippableItem {
    protected final int bonusDamage;

    public Weapon(Integer id, String name, int changeInSpeed, int bonusDamage) {
        super(id, name, changeInSpeed);
        this.bonusDamage = bonusDamage;
    }

    public int getBonusDamage() {
        return bonusDamage;
    }


    @Override
    protected void onEquipStats(Player player) {
        player.getStats().addDamage(bonusDamage);
    }

    @Override
    protected void onUnequipStats(Player player) {
        player.getStats().loseDamage(bonusDamage);
    }

    @Override
    public Item use(Player player) {
        return player.equipWeapon(this);
    }
}
