package model.items;

public class Armour extends Item{
    protected int bonusHealth;

    public Armour(Integer id, String name, int bonusHealth) {
        super(id, name);
        this.bonusHealth = bonusHealth;
    }

    public int getBonusHealth() {
        return bonusHealth;
    }

}
