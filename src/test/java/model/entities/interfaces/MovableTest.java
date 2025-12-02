package model.entities.interfaces;

import model.entities.Player;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovableTest {

    private Player movable;

    @BeforeEach
    void setUp() {
        movable = new Player(new Position(5, 5));
    }

    @Test
    void moveUpDecreasesY() {
        movable.moveUp();
        assertEquals(new Position(5, 4), movable.getPosition());
    }

    @Test
    void moveDownIncreasesY() {
        movable.moveDown();
        assertEquals(new Position(5, 6), movable.getPosition());
    }

    @Test
    void moveLeftDecreasesX() {
        movable.moveLeft();
        assertEquals(new Position(4, 5), movable.getPosition());
    }

    @Test
    void moveRightIncreasesX() {
        movable.moveRight();
        assertEquals(new Position(6, 5), movable.getPosition());
    }

    @Test
    void multipleMovesAccumulate() {
        movable.moveUp();
        movable.moveUp();
        movable.moveRight();
        assertEquals(new Position(6, 3), movable.getPosition());
    }
}