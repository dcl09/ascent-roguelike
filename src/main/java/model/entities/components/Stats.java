package model.entities.components;

public class Stats {
    private int health;
    private int maxHealth;
    private int damage;
    private int speed;
    private int resistanceToDamage;

    public Stats(int maxHealth, int damage, int speed) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.speed = speed;
        this.resistanceToDamage = 0;
    }

    //Method to help reset in Pools

    public void reset(int maxHealth, int damage, int speed) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.speed = speed;
        this.resistanceToDamage = 0;
    }

    public void takeDamage(int amount) {
        if (resistanceToDamage >= amount){
            resistanceToDamage -= amount;
        }
        else {
            health = Math.max(0, health - amount + resistanceToDamage);
            resistanceToDamage = 0;
        }
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public void addResistanceToDamage(int amount) {
        resistanceToDamage += amount;
    }

    public void loseResistanceToDamage(int amount) {
        resistanceToDamage = Math.max(0, resistanceToDamage - amount);
    }

    public void addDamage(int amount) {
        damage += amount;
    }

    public void loseDamage(int amount) {
        damage = Math.max(0, damage - amount);
    }

    public void addSpeed(int amount) {
        speed += amount;
    }

    public void loseSpeed(int amount) {
        speed = Math.max(0, speed - amount);
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getResistanceToDamage() {
        return resistanceToDamage;
    }

    public float getHealthPercentage() {
        return (float) health / maxHealth;
    }
}
