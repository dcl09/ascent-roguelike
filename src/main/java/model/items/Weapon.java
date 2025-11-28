package model.items;

import model.entities.Player;

public class Weapon extends Item {
    //Podemos colocar atackRange
    protected int bonusDamage;

    public Weapon(Integer id, String name, int bonusDamage) {
        super(id, name);
        this.bonusDamage = bonusDamage;
    }

    public int getBonusDamage() {
        return bonusDamage;
    }

    public void onEquip(Player player) {
        player.getStats().addDamage(bonusDamage);
    }

    public void onUnequip(Player player) {
        player.getStats().loseDamage(bonusDamage);
    }
}
