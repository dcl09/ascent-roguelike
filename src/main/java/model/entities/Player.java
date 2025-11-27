package model.entities;

import model.entities.components.Stats;
import model.entities.interfaces.Combatant;
import model.entities.interfaces.Interactable;
import model.entities.interfaces.Interactor;
import model.entities.interfaces.Movable;
import model.game.Position;

public class Player extends Entity implements Combatant, Movable, Interactor {
    private final Stats stats;

    public Player(Position position, char symbol, String color) {
        super(position, symbol, color);
        this.stats = new Stats(100, 1, 1);
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    @Override
    public boolean moveTo(Position newPosition) {
        if (!canMoveTo(newPosition)) return false;
        setPosition(newPosition);
        return true;
    }

    @Override
    public boolean canMoveTo(Position position) {
        //todo: Falta adicionar outras restrições para movimento
        return isAlive() && position != null;
    }

    @Override
    public int getMovementSpeed() {
        return stats.getSpeed();
    }

    @Override
    public boolean canInteract() {
        return !stats.isDead();
    }

    @Override
    public void interactWith(Interactable target) {
        if (target.canInteract() && target.canInteract()){
            target.interact(this);
        }
    }
    //todo: Add method to apply or store in the inventory items

}
