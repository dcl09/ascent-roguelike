package ascent.model.items;

import ascent.model.entities.Player;
import ascent.model.game.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HealthRestoreTest {

    private Player player;
    private HealthRestore potion;

    @BeforeEach
    void setUp() {
        player = new Player(new Position(0, 0));
        potion = new HealthRestore(1, "Health Potion", 30);
    }

    @Test
    void constructorSetsId() {
        assertEquals(1, potion.getId());
    }

    @Test
    void constructorSetsName() {
        assertEquals("Health Potion", potion.getName());
    }

    @Test
    void constructorSetsRestoredHealth() {
        assertEquals(30, potion.getRestoredHealth());
    }

    @Test
    void consumeHealsPlayer() {
        player.getStats().takeDamage(50);

        potion.consume(player);

        assertEquals(80, player.getStats().getHealth());
    }

    @Test
    void consumeDoesNotExceedMaxHealth() {
        player.getStats().takeDamage(10);

        potion.consume(player);

        assertEquals(100, player.getStats().getHealth());
    }

    @Test
    void consumeAtFullHealthDoesNothing() {
        potion.consume(player);

        assertEquals(100, player.getStats().getHealth());
    }

    @Test
    void useAddsToInventoryAndReturnsNull() {
        Item returned = potion.use(player);

        assertNull(returned);
        assertEquals(1, player.getInventory().getConsumables().size());
    }

    // This test purpose is to check if, when the inventory is full, the potion is returned

    @Test
    void useReturnsItemWhenInventoryFull() {
        for (int i = 0; i < 10; i++) {
            player.addConsumable(new HealthRestore(i, "Filler", 10));
        }

        Item returned = potion.use(player);

        assertEquals(potion, returned);
    }

}