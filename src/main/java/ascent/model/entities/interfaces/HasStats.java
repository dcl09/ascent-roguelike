package ascent.model.entities.interfaces;

import ascent.model.entities.components.Stats;

public interface HasStats {
    Stats getStats();
    default boolean isAlive() {
        return !getStats().isDead();
    }
    default int getMovementSpeed() {
        return getStats().getSpeed();
    }
}
