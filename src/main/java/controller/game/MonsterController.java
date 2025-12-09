package controller.game;

import gui.ACTION;
import model.entities.Monster;
import model.game.Position;
import model.game.floor.Floor;

public class MonsterController extends GameController {

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
            // Implement check if monster is aware of player, else random chance for random movement
            moveMonster(monster, monster.getPosition().getRandomAdjacent());
        }
    }
}
