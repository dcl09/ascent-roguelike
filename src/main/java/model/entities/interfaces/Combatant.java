package model.entities.interfaces;

public interface Combatant extends HasStats {
    default void attack(Combatant target) {
        if (target == null) throw new IllegalArgumentException("Cannot attack null target");
        if (!this.isAlive()) return;
        if (!target.isAlive()) return;

        target.defendFrom(this);
    }

    default void defendFrom(Combatant attacker) {
        int damage = attacker.getStats().getDamage();
        this.getStats().takeDamage(damage);

        if (this.getStats().isDead()) {
            // onDeath(); -> implement
        }
    }

    
}
