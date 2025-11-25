package model.entities.components;

public class Stats {
    private int health;
    private int maxHealth;
    private int damage;
    private int speed;

    public Stats(int maxHealth, int damage, int speed) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.speed = speed;
    }

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getDamage() { return damage; }
    public int getSpeed() { return speed; }

    public float getHealthPercentage() {
        return (float) health / maxHealth;
    }
}
