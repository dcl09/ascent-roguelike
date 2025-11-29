package model.entities.interfaces;

import model.game.Position;

public interface Movable {
    //boolean moveTo(Position newPosition);
    boolean canMoveTo(Position position);
}
