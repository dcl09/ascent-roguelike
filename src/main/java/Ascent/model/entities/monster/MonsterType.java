package Ascent.model.entities.monster;

import Ascent.model.entities.components.Stats;

public enum MonsterType {
    GOBLIN('g', "GREEN", 30, 3, 3, 10, 500),
    ORC('O', "#154734", 60, 8, 2, 8, 900),
    DRAGON('R', "RED", 150, 20, 4, 20, 1200),
    SKELETON('s', "WHITE", 25, 5, 2, 7, 600),
    ZOMBIE('Z', "#808080", 50, 6, 1, 5, 700);

    private final char symbol;
    private final String color;
    private final int baseHealth;
    private final int baseDamage;
    private final int baseSpeed;
    private final double aggroRange;
    private final long baseAttackCooldown;

    MonsterType(char symbol, String color, int baseHealth, int baseDamage, int baseSpeed, double aggroRange, long baseAttackCooldown) {
        this.symbol = symbol;
        this.color = color;
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.baseSpeed = baseSpeed;
        this.aggroRange = aggroRange;
        this.baseAttackCooldown = baseAttackCooldown;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public double getAggroRange() { return aggroRange; }

    public long getBaseAttackCooldown() { return baseAttackCooldown; }

    public Stats createBaseStats() {
        return new Stats(baseHealth, baseDamage, baseSpeed);
    }

    public static MonsterType getTypeFromChar(char c) {
        return switch (c) {
            case 'g' -> GOBLIN;
            case 'O' -> ORC;
            case 'R' -> DRAGON;
            case 's' -> SKELETON;
            case 'Z' -> ZOMBIE;
            default -> null;
        };
    }
}
