package model.entities.items;

public class HealthRestore extends Item{
    protected int restoredHealth;

    public HealthRestore(Integer id, String name, int restoredHealth) {
        super(id, name);
        this.restoredHealth = restoredHealth;
    }

    public int getRestoredHealth() {
        return restoredHealth;
    }
}
