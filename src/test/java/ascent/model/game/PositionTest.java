package ascent.model.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertNotEquals(position, null);
    }

    @Test
    void equalsReturnsFalseForDifferentType() {
        Position position = new Position(5, 10);
        String notAPosition = "not a position";

        assertNotEquals(position, notAPosition);
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

    @Test
    void hashCodeMatchesObjectsHash() {
        Position position = new Position(5, 10);
        assertEquals(java.util.Objects.hash(5, 10), position.hashCode());
    }

    @Test
    void toStringReturnsCorrectString() {
        Position position = new Position(5, 10);
        assertEquals("(5, 10)", position.toString());
    }

    @Test
    void getRandomAdjacentReturnsAdjacentPosition() {
        Position position = new Position(5, 10);
        java.util.Random mockRandom = mock(java.util.Random.class);

        when(mockRandom.nextInt(4)).thenReturn(0);
        assertEquals(position.getUp(), position.getRandomAdjacent(mockRandom));

        when(mockRandom.nextInt(4)).thenReturn(1);
        assertEquals(position.getRight(), position.getRandomAdjacent(mockRandom));

        when(mockRandom.nextInt(4)).thenReturn(2);
        assertEquals(position.getDown(), position.getRandomAdjacent(mockRandom));

        when(mockRandom.nextInt(4)).thenReturn(3);
        assertEquals(position.getLeft(), position.getRandomAdjacent(mockRandom));
    }

    @Test
    void getRandomAdjacentUsesDefaultRandom() {
        Position position = new Position(5, 10);
        Position adjacent = position.getRandomAdjacent();
        assertNotNull(adjacent);

        int dx = Math.abs(adjacent.getX() - position.getX());
        int dy = Math.abs(adjacent.getY() - position.getY());
        assertEquals(1, dx + dy);
    }
}
