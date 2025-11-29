package model.entities;

import model.entities.components.Stats;
import model.entities.interfaces.Combatant;
import model.entities.interfaces.Movable;
import model.game.Position;

public class Monster extends Entity implements Combatant, Movable {
    private final Stats stats;
    private boolean active;

    // Empty Constructor for pool
    public Monster() {
        super(new Position(0, 0), 'M', "RED");
        this.stats = new Stats(90, 2, 2);
        this.active = false;
    }

    public Monster (Position position, char symbol, String color) {
        super(position, symbol, color);
        this.stats = new Stats(90, 2, 2);
        this.active = true;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void reset(Position position, char symbol, String color,
                      int health, int damage, int speed) {
        setPosition(position);
        setSymbol(symbol);
        setColor(color);
        stats.reset(health, damage, speed);
        this.active = true;
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    /*
    @Override
    public boolean moveTo(Position newPosition) {
        if (!canMoveTo(newPosition)) return false;
        setPosition(newPosition);
        return true;
    }
     */
    @Override
    public boolean canMoveTo(Position position) {
        //todo: Falta adicionar outras restrições para movimento
        return isAlive() && position != null;
    }

    @Override
    public int getMovementSpeed() {
        return stats.getSpeed();
    }
}
