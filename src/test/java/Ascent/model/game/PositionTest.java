package Ascent.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void constructorInitializesCoordinates() {
        Position position = new Position(5, 10);

        assertEquals(5, position.getX());
        assertEquals(10, position.getY());
    }

    // This test ensures Position can hold negative values,
    // even if negative coordinates may not be used in the actual game screen.

    @Test
    void acceptsNegativeCoordinates() {
        Position position = new Position(-3, -7);

        assertEquals(-3, position.getX());
        assertEquals(-7, position.getY());
    }

    @Test
    void acceptsZeroCoordinates() {
        Position position = new Position(0, 0);

        assertEquals(0, position.getX());
        assertEquals(0, position.getY());
    }

    @Test
    void equalsReturnsTrueForSameCoordinates() {
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(5, 10);

        assertEquals(pos1, pos2);
    }

    @Test
    void equalsReturnsFalseForDifferentX() {
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(6, 10);

        assertNotEquals(pos1, pos2);
    }

    @Test
    void equalsReturnsFalseForDifferentY() {
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(5, 11);

        assertNotEquals(pos1, pos2);
    }

    @Test
    void equalsReturnsFalseForNull() {
        Position position = new Position(5, 10);

        assertNotEquals(null, position);
    }

    @Test
    void equalsReturnsFalseForDifferentType() {
        Position position = new Position(5, 10);
        String notAPosition = "not a position";

        assertNotEquals(notAPosition, position);
    }

    @Test
    void equalsIsReflexive() {
        Position position = new Position(5, 10);

        assertEquals(position, position);
    }

    @Test
    void hashCodeIsEqualForEqualPositions() {
        Position pos1 = new Position(5, 10);
        Position pos2 = new Position(5, 10);

        assertEquals(pos1.hashCode(), pos2.hashCode());
    }

    @Test
    void hashCodeIsConsistent() {
        Position position = new Position(5, 10);
        int hash1 = position.hashCode();
        int hash2 = position.hashCode();

        assertEquals(hash1, hash2);
    }
}
