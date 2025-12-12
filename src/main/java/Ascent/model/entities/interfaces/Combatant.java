package Ascent.model.entities.interfaces;

public interface Combatant extends HasStats {
    default void attack(Combatant target) {
        if (target == null) {
            throw new IllegalArgumentException("Cannot attack null target");
        }
        if (!this.isAlive() || !target.isAlive()) {
            return;
        }
        int damage = getStats().getDamage();
        target.receiveDamage(damage);
    }

    default void receiveDamage(int amount) {
        getStats().takeDamage(amount);
    }
}
