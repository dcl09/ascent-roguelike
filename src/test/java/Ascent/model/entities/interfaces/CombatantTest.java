package Ascent.model.entities.interfaces;

import Ascent.model.entities.monster.Monster;
import Ascent.model.entities.Player;
import Ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatantTest {

    private Player attacker;
    private Monster target;

    @BeforeEach
    void setUp() {
        attacker = new Player(new Position(0, 0));
        target = new Monster(new Position(1, 0));
    }

    // Basic damage application
    @Test
    void attackDealsDamageToTarget() {
        int initialHealth = target.getStats().getHealth();
        int attackerDamage = attacker.getStats().getDamage();

        attacker.attack(target);

        assertEquals(initialHealth - attackerDamage, target.getStats().getHealth());
    }

    // attack(null) must throw
    @Test
    void attackThrowsExceptionForNullTarget() {
        assertThrows(IllegalArgumentException.class, () -> attacker.attack(null));
    }

    // Dead attackers cannot deal damage
    @Test
    void attackDoesNothingIfAttackerDead() {
        attacker.getStats().takeDamage(100);
        int targetHealthBefore = target.getStats().getHealth();

        attacker.attack(target);

        assertEquals(targetHealthBefore, target.getStats().getHealth());
    }

    // Cannot damage a dead target
    @Test
    void attackDoesNothingIfTargetDead() {
        target.getStats().takeDamage(100);
        int targetHealthBefore = target.getStats().getHealth();

        attacker.attack(target);

        assertEquals(targetHealthBefore, target.getStats().getHealth());
    }

    // Basic damage reception
    @Test
    void receiveDamageReducesHealth() {
        int initialHealth = target.getStats().getHealth();

        target.receiveDamage(20);

        assertEquals(initialHealth - 20, target.getStats().getHealth());
    }
}
