package Ascent.model.entities;

import Ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private Entity entity;
    private Position initialPosition;

    @BeforeEach
    void setUp() {
        initialPosition = new Position(5, 10);
        entity = new Entity(initialPosition, 'E', "GREEN");
    }

    @Test
    void constructorSetsPosition() {
        assertEquals(initialPosition, entity.getPosition());
    }

    @Test
    void constructorSetsSymbol() {
        assertEquals('E', entity.getSymbol());
    }

    @Test
    void constructorSetsColor() {
        assertEquals("GREEN", entity.getColor());
    }

}