package controller.game;

import gui.ACTION;
import model.game.Position;
import model.game.floor.Floor;

public class DoorController extends GameController {
    // Todo: Implement doors & floor checks for doors, import game & implement time
    public DoorController(Floor floor){ super(floor); }

    public void step(Game game, ACTION action/*, long time*/) {
        Position checkForDoor = getModel().getPlayer().facing();
        /*
        if getModel().isDoor(checkForDoor)
            for (Door door : getModel().getDoors()) {
                if (door.getPosition().equals(checkForDoor)) {
                    if (door.canInteract()) {
                        getModel().getPlayer().interactWith(door);
                    }
                    break;
                }
            }
        }*/
    }
}

