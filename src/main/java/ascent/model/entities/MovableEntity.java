package ascent.model.entities;

import ascent.model.entities.interfaces.Movable;
import ascent.model.game.Position;

public abstract class MovableEntity extends Entity implements Movable {
    public MovableEntity(Position position, char symbol, String color) {
        super(position, symbol, color);
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
