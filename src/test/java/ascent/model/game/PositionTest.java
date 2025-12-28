package ascent.model.game;

import org.junit.jupiter.api.Test;

import java.util.Objects;

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

        // we have to the test explicitly like this for it to work!!! <- DO NOT CHANGE
        assertFalse(position.equals(null));
    }

    @Test
    void equalsReturnsFalseForDifferentType() {
        Position position = new Position(5, 10);
        String notAPosition = "not a position";

        // we have to the test explicitly like this for it to work!!! <- DO NOT CHANGE
        assertFalse(position.equals(notAPosition));
    }

    @Test
    void equalsIsReflexive() {
        Position position = new Position(5, 10);

        assertEquals(position, position);
    }

    @Test
    void hashCodeReturnsValidHash() {
        Position position = new Position(5, 5);
        assertEquals(Objects.hash(position.getX(), position.getY()),position.hashCode());
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

    @Test
    void toStringReturnsValidOutput() {
        Position position = new Position(5, 10);
        assertEquals("(" + position.getX() + ", " + position.getY() + ")", position.toString());
    }

    @Test
    void getRandomAdjacentReturnsNonNullPosition() {
        Position position = new Position(5, 10);
        assertNotNull(position.getRandomAdjacent());
    }

    // pitest being annoying: episode 9999999999999999999
    // yeah this test is basically useless but im leaving it here now for reference
    @Test
    void getRandomAdjacentReturnsValidPosition() {
        Position position = new Position(5, 5);
        Position adjacentPos = position.getRandomAdjacent();
        assertTrue( (Math.abs(position.getX() - adjacentPos.getX()) == 1 && Math.abs(position.getY() - adjacentPos.getY()) == 1) || (Math.abs(position.getX() - adjacentPos.getX()) == 1 && Math.abs(position.getY() - adjacentPos.getY()) == 0) || (Math.abs(position.getX() - adjacentPos.getX()) == 0 && Math.abs(position.getY() - adjacentPos.getY()) == 1));
    }
}
