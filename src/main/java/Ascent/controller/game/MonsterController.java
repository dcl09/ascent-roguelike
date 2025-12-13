package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Monster;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

import java.util.ArrayList;
import java.util.Collection;

public class MonsterController extends GameController {
    // Todo: Implement time and related checks & import game
    private long lastMovement;

    public MonsterController(Floor floor) {
        super(floor);
    }

    public boolean moveMonster(Position initialPosition, Position finalPosition) {
        return getModel().moveMonster(initialPosition, finalPosition);
    }

    public void step(Game game, ACTION action, long time) {
        // Implement check if enough time has elapsed since last movement
        if (time - lastMovement > 500) {
            Collection<Monster> monsters = new ArrayList<>(getModel().getMonsters());
            for (Monster monster : monsters) {
                // Implement check if monster is aware of player, else random movement
                moveMonster(monster.getPosition(), monster.getPosition().getRandomAdjacent());
            }
            lastMovement = time;
        }
    }
}
