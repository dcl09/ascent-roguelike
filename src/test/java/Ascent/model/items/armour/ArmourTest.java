package Ascent.model.items.armour;

import Ascent.model.entities.Player;
import Ascent.model.game.Position;
import Ascent.model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArmourTest {

    private Player player;
    private Armour helmet;

    @BeforeEach
    void setUp() {
        player = new Player(new Position(0, 0));
        helmet = new Armour(1, "Helmet", -1, 5, ArmourSlot.HEAD);
    }

    @Test
    void constructorSetsResistance() {
        assertEquals(5, helmet.getBonusResistanceToDamage());
    }

    @Test
    void constructorSetsSlot() {
        assertEquals(ArmourSlot.HEAD, helmet.getSlot());
    }

    @Test
    void constructorSetsSpeedChange() {
        assertEquals(-1, helmet.getChangeInSpeed());
    }

    @Test
    void onEquipAddsResistance() {
        helmet.onEquip(player);

        assertEquals(5, player.getStats().getResistanceToDamage());
    }

    @Test
    void onEquipModifiesSpeed() {
        helmet.onEquip(player);

        assertEquals(0, player.getStats().getSpeed()); // 1 - 1
    }

    @Test
    void onUnequipRemovesResistance() {
        helmet.onEquip(player);
        helmet.onUnequip(player);

        assertEquals(0, player.getStats().getResistanceToDamage());
    }

    @Test
    void onUnequipRestoresSpeed() {
        helmet.onEquip(player);
        helmet.onUnequip(player);

        assertEquals(1, player.getStats().getSpeed());
    }

    @Test
    void useEquipsArmourReturnsNull() {
        Item returned = helmet.use(player);

        assertNull(returned);
        assertEquals(helmet, player.getInventory().getArmour(ArmourSlot.HEAD));
    }

    @Test
    void useReturnsOldArmourFromSameSlot() {
        Armour oldHelmet = new Armour(2, "Old Helmet", 0, 3, ArmourSlot.HEAD);
        oldHelmet.use(player);

        Item returned = helmet.use(player);

        assertEquals(oldHelmet, returned);
    }
}