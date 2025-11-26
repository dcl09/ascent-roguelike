package model.entities.items;

public class Weapon extends Items {
    protected int bonusDamage;
    protected int atkrange;

    Weapon(int level, int damage, int atkcd, int atkrange) {
        super(level);
        this.bonusDamage = damage;
        this.atkrange = atkrange;
    }

    public int getDamage() {
        return bonusDamage;
    }

    public void setBonusDamage(int damage) {
        this.bonusDamage = damage;
    }

    public int getAtkrange() {
        return atkrange;
    }

    public void setAtkrange(int atkrange) {
        this.atkrange = atkrange;
    }
}
