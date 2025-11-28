package model.items;

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
}
