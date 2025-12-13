package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.components.LOOKING;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;
// todo: implement getter of specific monster infront of player, import game & implement time
public class PlayerController extends GameController{

    public PlayerController(Floor floor) {
        super(floor);
    }

    public void movePlayerUp(){
        movePlayer(getModel().getPlayer().getPosition().getUp());
        getModel().getPlayer().setLookingDirection(LOOKING.UP);
    }

    public void movePlayerDown(){
        movePlayer(getModel().getPlayer().getPosition().getDown());
        getModel().getPlayer().setLookingDirection(LOOKING.DOWN);
    }

    public void movePlayerLeft(){
        movePlayer(getModel().getPlayer().getPosition().getLeft());
        getModel().getPlayer().setLookingDirection(LOOKING.LEFT);
    }

    public void movePlayerRight(){
        movePlayer(getModel().getPlayer().getPosition().getRight());
        getModel().getPlayer().setLookingDirection(LOOKING.RIGHT);
    }

    public void movePlayer(Position position){
        if (!getModel().isWall(position)){
            if (getModel().isMonster(position));
                // hurt monster in position
            else getModel().getPlayer().setPosition(position);
        }
    }

    public void step(Game game, ACTION action, long time){
        if (action == ACTION.UP) movePlayerUp();
        if (action == ACTION.DOWN) movePlayerDown();
        if (action == ACTION.LEFT) movePlayerLeft();
        if (action == ACTION.RIGHT) movePlayerRight();
    }
}
