package Ascent.model.entities;

import Ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    private Entity wall;
    private Position initialPosition;

    @BeforeEach
    void setUp() {
        initialPosition = new Position(5, 10);
        wall = new Wall(initialPosition);
    }

    @Test
    void wallHasCorrectSymbol() {
        assertEquals('#', wall.getSymbol());
    }

    @Test
    void wallHasCorrectColor() {
        assertEquals("WHITE", wall.getColor());
    }

    @Test
    void wallHasCorrectPosition() {
        assertEquals(initialPosition, wall.getPosition());
    }
}