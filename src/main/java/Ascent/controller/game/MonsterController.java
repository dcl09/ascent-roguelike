package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.monster.Monster;
import Ascent.model.game.IPathFinder;
import Ascent.model.game.PathFinder;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MonsterController extends GameController {
    private static final long BASE_MOVEMENT_COOLDOWN = 550;
    private final Map<Monster, Long> lastAttackTimes;
    private final Map<Monster, Long> lastMovementTimes;
    private final IPathFinder pathFinder; // Use interface for DIP

    public MonsterController(Floor floor) {
        super(floor);
        this.lastMovementTimes = new HashMap<>();
        this.lastAttackTimes = new HashMap<>();
        this.pathFinder = new PathFinder(floor);
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
        Position playerPos = getModel().getPlayer().getPosition();

        for (Monster monster : monsters) {
            if (!monster.isActive() || monster.getStats().isDead()) {
                lastMovementTimes.remove(monster);
                lastAttackTimes.remove(monster);
                continue;
            }

            long lastMove = lastMovementTimes.getOrDefault(monster, 0L);
            long movementCooldown = getMovementCooldown(monster);

            if (time - lastMove < movementCooldown) {
                continue;
            }

            Position monsterPos = monster.getPosition();
            double distanceToPlayer = Math.abs(monsterPos.getX() - playerPos.getX())
                    + Math.abs(monsterPos.getY() - playerPos.getY());

            Position nextStep;
            if (distanceToPlayer <= monster.getMonsterType().getAggroRange()) {
                nextStep = pathFinder.findNextStep(monsterPos, playerPos);
                if (nextStep == null) {
                    nextStep = monsterPos.getRandomAdjacent();
                }
            } else {
                nextStep = monsterPos.getRandomAdjacent();
            }

            if (nextStep.equals(playerPos)) {
                long lastAttack = lastAttackTimes.getOrDefault(monster, 0L);
                long attackCooldown = monster.getAttackCooldown();

                if (time - lastAttack >= attackCooldown) {
                    moveMonster(monsterPos, nextStep);
                    lastAttackTimes.put(monster, time);
                }

            } else {
                moveMonster(monsterPos, nextStep);
            }

            lastMovementTimes.put(monster, time);
        }
    }
}
