package model.entities;

import model.entities.components.Stats;
import model.entities.interfaces.Combatant;
import model.game.Position;

public class Monster extends Entity implements Combatant {
    private final Stats stats;

    public Monster (Position position, char symbol, String color) {
        super(position, symbol, color);
        this.stats = new Stats(90, 2, 2);
    }

    @Override
    public Stats getStats() {
        return stats;
    }
}
