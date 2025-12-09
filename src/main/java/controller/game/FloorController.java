package controller.game;

import gui.ACTION;
import model.game.floor.Floor;
// Todo: set states, implement time & import game
public class FloorController extends GameController {

    private final PlayerController playercontroller;
    private final MonsterController monstercontroller;
    private final ChestController chestcontroller;
//    private final DoorController doorcontroller;

    public FloorController(Floor floor) {
        super(floor);
        this.playercontroller = new PlayerController(floor);
        this.monstercontroller = new MonsterController(floor);
        this.chestcontroller = new ChestController(floor);
//        this.doorcontroller = new DoorController(Floor);
    }

    public void step(Game game, ACTION action /* long time */) {
        if (action == ACTION.QUIT) ;
            /* set state to start menu */
        else if (getModel().getPlayer().getStats().isDead()) ;
            /* set state to endscreen */
        else {
            playercontroller.step(game, action /*, time */);
            if (action == ACTION.INTERACT && getModel().getPlayer().canInteract()) {
                if (getModel().isChest(getModel().getPlayer().facing()))
                    chestcontroller.step(game, action /*, time */);
                /*else if (getModel().isDoor(getModel().getPlayer().facing()))
                    doorcontroller.step(game, action /, time /);
            }*/
                monstercontroller.step(game, action /*, time */);
            }
        }
    }
}

