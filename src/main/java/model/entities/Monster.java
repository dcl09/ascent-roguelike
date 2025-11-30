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

    public Monster(Position position){
        super (position, 'M', "RED");
        this.stats = new Stats(90, 2, 2);
        this.active = true;
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

    public void reset(Position position) {
        reset(position, 'M',"Red", 90, 2 , 2);
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    @Override
    public void moveUp(){
        position = position.getUp();
    }

    @Override
    public void moveDown(){
        position = position.getDown();
    }

    @Override
    public void moveLeft(){
        position = position.getLeft();
    }

    @Override
    public void moveRight(){
        position = position.getRight();
    }

    @Override
    public int getMovementSpeed() {
        return stats.getSpeed();
    }
}
