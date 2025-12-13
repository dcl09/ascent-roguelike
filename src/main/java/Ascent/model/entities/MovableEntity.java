package Ascent.model.entities;

import Ascent.model.entities.interfaces.Movable;
import Ascent.model.game.Position;

public abstract class MovableEntity extends Entity implements Movable {
    public MovableEntity(Position position, char symbol, String color) {
        super(position, symbol, color);
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
