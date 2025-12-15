package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.monster.Monster;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MonsterController extends GameController {
    private static final long BASE_MOVEMENT_COOLDOWN = 600;
    private final Map<Monster, Long> lastMovementTimes;

    public MonsterController(Floor floor) {
        super(floor);
        this.lastMovementTimes = new HashMap<>();
    }

    private long getMovementCooldown(Monster monster) {
        int speed = Math.max(1, monster.getMovementSpeed());
        return BASE_MOVEMENT_COOLDOWN / speed;
    }

    public boolean moveMonster(Position initialPosition, Position finalPosition) {
        return getModel().moveMonster(initialPosition, finalPosition);
    }

    @Override
    public void step(Game game, ACTION action, long time) {
        Collection<Monster> monsters = new ArrayList<>(getModel().getMonsters());

        for (Monster monster : monsters) {
            if (!monster.isActive() || monster.getStats().isDead()) {
                lastMovementTimes.remove(monster);
                continue;
            }

            long lastMove = lastMovementTimes.getOrDefault(monster, 0L);
            long cooldown = getMovementCooldown(monster);

            if (time - lastMove >= cooldown) {
                moveMonster(monster.getPosition(), monster.getPosition().getRandomAdjacent());
                lastMovementTimes.put(monster, time);
            }
        }
    }
}
