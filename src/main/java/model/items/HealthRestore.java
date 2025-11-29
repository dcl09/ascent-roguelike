package model.items;

import model.entities.Player;

public class HealthRestore extends Item{
    protected final int restoredHealth;

    public HealthRestore(Integer id, String name, int restoredHealth) {
        super(id, name);
        this.restoredHealth = restoredHealth;
    }

    public int getRestoredHealth() {
        return restoredHealth;
    }

    public void consume(Player player) {
        player.getStats().heal(restoredHealth);
    }
}
