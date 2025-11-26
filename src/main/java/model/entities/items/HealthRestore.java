package model.entities.items;

public class HealthRestore extends Items{
    protected int restoredHealth;

    HealthRestore(int level, int restoredHealth) {
        super(level);
        this.restoredHealth = restoredHealth;
    }

    public int getRestoredHealth() {
        return restoredHealth;
    }

    public void setRestoredHealth(int restoredHealth) {
        this.restoredHealth = restoredHealth;
    }
}
