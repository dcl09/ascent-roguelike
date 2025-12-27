package ascent.model.items;

import ascent.model.entities.Player;
import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {

    private Player player;
    private Weapon sword;

    @BeforeEach
    void setUp() {
        player = new Player(new Position(0, 0));
        sword = new Weapon(1, "Sword", -2, 10);
    }

    @Test
    void constructorSetsId() {
        assertEquals(1, sword.getId());
    }

    @Test
    void constructorSetsName() {
        assertEquals("Sword", sword.getName());
    }

    @Test
    void constructorSetsChangeInSpeed() {
        assertEquals(-2, sword.getChangeInSpeed());
    }

    @Test
    void constructorSetsBonusDamage() {
        assertEquals(10, sword.getBonusDamage());
    }

    @Nested
    class EquipmentTests {

        @Test
        void onEquipAddsDamage() {
            int initialDamage = player.getStats().getDamage();

            sword.onEquip(player);

            assertEquals(initialDamage + 10, player.getStats().getDamage());
        }

        @Test
        void onEquipAddsSpeed() {
            int initialSpeed = player.getStats().getSpeed();

            sword.onEquip(player);

            assertEquals(initialSpeed - 2, player.getStats().getSpeed());
        }

        @Test
        void onUnequipRemovesDamage() {
            sword.onEquip(player);
            int damageAfterEquip = player.getStats().getDamage();

            sword.onUnequip(player);

            assertEquals(damageAfterEquip - 10, player.getStats().getDamage());
        }

        @Test
        void onUnequipRemovesSpeedPenalty() {
            sword.onEquip(player);

            sword.onUnequip(player);

            assertEquals(7, player.getStats().getSpeed()); // base restored
        }
    }

    @Test
    void useEquipsWeaponAndReturnsOld() {
        Item returned = sword.use(player);

        assertNull(returned);
        assertEquals(sword, player.getInventory().getEquippedWeapon());
    }

    @Test
    void useReturnsOldWeapon() {
        Weapon axe = new Weapon(2, "Axe", 0, 15);
        sword.use(player);

        Item returned = axe.use(player);

        assertEquals(sword, returned);
    }
}