package ascent.model.entities;

import ascent.model.entities.interfaces.Interactor;
import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DoorTest {

    private Door door;
    private Interactor interactor;

    @BeforeEach
    void setUp() {
        interactor = mock(Interactor.class);
        door = new Door(new Position(5, 5));
    }

    @Test
    void constructorSetsDefaultProperties() {
        assertFalse(door.isOpen());
        assertEquals('D', door.getSymbol());
        assertEquals("#86a7ed", door.getColor());
        assertEquals(new Position(5, 5), door.getPosition());
    }

    @Test
    void canInteractReturnsTrue() {
        assertTrue(door.canInteract());
    }

    @Test
    void interactOpensWhenClosed() {
        door.interact(interactor);
        assertTrue(door.isOpen());
        assertEquals('d', door.getSymbol());
    }

    @Test
    void interactClosesWhenOpen() {
        door.interact(interactor);
        door.interact(interactor);
        assertFalse(door.isOpen());
        assertEquals('D', door.getSymbol());
    }

    @Test
    void isWalkableDependsOnOpenState() {
        assertFalse(door.isWalkable());

        door.interact(interactor);
        assertTrue(door.isWalkable());

        door.interact(interactor);
        assertFalse(door.isWalkable());
    }

    @Test
    void getInteractionMessageReturnsCorrectly() {
        assertEquals("Press E to open", door.getInteractionMessage());

        door.interact(interactor);
        assertEquals("Press E to close", door.getInteractionMessage());
    }
}
