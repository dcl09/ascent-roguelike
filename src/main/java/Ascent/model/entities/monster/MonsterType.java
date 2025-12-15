package Ascent.model.entities.monster;

import Ascent.model.entities.components.Stats;

public enum MonsterType {
    GOBLIN('g', "GREEN", 30, 3, 3),
    ORC('O', "#154734", 0, 8, 2),
    DRAGON('R', "RED", 150, 20, 4),
    SKELETON('s', "WHITE", 25, 5, 2),
    ZOMBIE('Z', "#808080 ", 50, 6, 1);

    private final char symbol;
    private final String color;
    private final int baseHealth;
    private final int baseDamage;
    private final int baseSpeed;

    MonsterType(char symbol, String color, int baseHealth, int baseDamage, int baseSpeed) {
        this.symbol = symbol;
        this.color = color;
        this.baseHealth = baseHealth;
        this.baseDamage = baseDamage;
        this.baseSpeed = baseSpeed;
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
