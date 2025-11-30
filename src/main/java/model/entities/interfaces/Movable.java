package model.entities.interfaces;

import model.game.Position;

public interface Movable {
    Position getPosition();
    void setPosition(Position position);

    default void moveUp() {
        setPosition(getPosition().getUp());
    }

    default void moveDown() {
        setPosition(getPosition().getDown());
    }

    default void moveLeft() {
        setPosition(getPosition().getLeft());
    }

    default void moveRight() {
        setPosition(getPosition().getRight());
    }
}
