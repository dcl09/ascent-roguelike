package model.entities;

import model.entities.components.Stats;
import model.entities.interfaces.Combatant;
import model.game.Position;

public class Player extends Entity implements Combatant {
    private final Stats stats;

    protected char symbol;
    protected String color;

    public Player (Position position, char symbol, String color) {
        super(position, symbol, color);
        this.stats = new Stats(100, 1, 1);
    }

    @Override
    public Stats getStats() {
        return stats;
    }
}
