package Ascent.model.entities.monster;

import Ascent.model.entities.MovableEntity;
import Ascent.model.entities.components.Stats;
import Ascent.model.entities.interfaces.Combatant;
import Ascent.model.game.Position;

public class Monster extends MovableEntity implements Combatant {
    // todo: review aggroRange implementation (i think its a little unorganized)
    private final Stats stats;
    private MonsterType monsterType;
    private boolean active;

    // Empty Constructor for pool
    public Monster() {
        super(new Position(0, 0), 'M', "RED");
        this.stats = new Stats(0, 0, 0);
        this.active = false;
        this.monsterType = null;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void reset(MonsterType monsterType, Position position) {
        setPosition(position);
        setSymbol(monsterType.getSymbol());
        setColor(monsterType.getColor());
        stats.reset(monsterType.getBaseHealth(), monsterType.getBaseDamage(), monsterType.getBaseSpeed());
        this.monsterType = monsterType;
        this.active = true;
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    @Override
    public int getMovementSpeed() {
        return stats.getSpeed();
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }
}
