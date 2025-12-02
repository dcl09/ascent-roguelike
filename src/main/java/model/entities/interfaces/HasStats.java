package model.entities.interfaces;

import model.entities.components.Stats;

public interface HasStats {
    Stats getStats();
    default boolean isAlive() {
        return !getStats().isDead();
    }
    default int getMovementSpeed() {
        return getStats().getSpeed();
    }
}
