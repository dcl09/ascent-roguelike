package ascent.model.entities;

import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ascent.model.entities.Stairs;

import static org.junit.jupiter.api.Assertions.*;

public class StairsTest {

    private Stairs stairs;

    @BeforeEach
    void setUp() {
        stairs = new Stairs(new Position(5, 5));
    }

    @Test
    void constructorSetsDefaultProperties() {
        assertEquals('=', stairs.getSymbol());
        assertEquals("#ffe478", stairs.getColor());
        assertEquals(new Position(5, 5), stairs.getPosition());
    }

    @Test
    void isWalkableAlwaysTrue() {
        assertTrue(stairs.isWalkable());
    }

    @Test
    void canInteractAlwaysTrue() {
        assertTrue(stairs.canInteract());
    }

    @Test
    void getInteractionMessageReturnsCorrectly(){
        assertEquals("Press E to use stairs", stairs.getInteractionMessage());
    }
}
