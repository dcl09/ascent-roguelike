package model.entities.items;

public class Armour extends Items{
    protected int bonusHealth;

    Armour(int level, int bonusHealth) {
        super(level);
        this.bonusHealth = bonusHealth;
    }

    public int getBonusHealth() {
        return bonusHealth;
    }

    public void setBonusHealth(int bonusHealth) {
        this.bonusHealth = bonusHealth;
    }
}
