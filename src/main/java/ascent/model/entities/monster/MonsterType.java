package ascent.model.entities.monster;

import ascent.model.entities.components.Stats;

public enum MonsterType {
    GOBLIN('g', "#5dde87", 30, 3, 3, 10, 500),
    ORC('O', "#3ca370", 55, 7, 2, 8, 850),
    DRAGON('R', "#eb564b", 120, 15, 4, 15, 1100),
    SKELETON('s', "#c2c2d1", 20, 4, 2, 7, 550),
    ZOMBIE('Z', "#7e7e8f", 45, 5, 1, 5, 750);

    private final char symbol;
    private final String color;
    private final int baseHealth;
    private final int baseDamage;
    private final int baseSpeed;
    private final double aggroRange;
    private final long baseAttackCooldown;

    MonsterType(char symbol, String color, int baseHealth, int baseDamage, int baseSpeed, double aggroRange,
            long baseAttackCooldown) {
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

    public double getAggroRange() {
        return aggroRange;
    }

    public long getBaseAttackCooldown() {
        return baseAttackCooldown;
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
