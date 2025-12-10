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

    @Test
    void setPositionUpdatesPosition() {
        Position newPosition = new Position(20, 30);

        entity.setPosition(newPosition);

        assertEquals(newPosition, entity.getPosition());
    }

    @Test
    void setSymbolUpdatesSymbol() {
        entity.setSymbol('X');

        assertEquals('X', entity.getSymbol());
    }

    @Test
    void setColorUpdatesColor() {
        entity.setColor("RED");

        assertEquals("RED", entity.getColor());
    }

    @Test
    void setPositionAcceptsNull() {
        entity.setPosition(null);

        assertNull(entity.getPosition());
    }
}