package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Monster;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

public class MonsterController extends GameController {
// Todo: Implement time and related checks & import game
    private long lastMovement;

    public MonsterController(Floor floor){ super(floor); }

    public void moveMonster(Monster monster, Position position){
        if (!getModel().isWall(position)){
            if (getModel().getPlayer().getPosition().equals(position));
                // hurt player in position
            else monster.setPosition(position);
        }
    }

    public void step(Game game, ACTION action/*, long time*/){
        // Implement check if enough time has elapsed since last movement
        for (Monster monster : getModel().getMonsters()) {
            // Implement check if monster is aware of player, else random movement
            moveMonster(monster, monster.getPosition().getRandomAdjacent());
        }
    }
}
