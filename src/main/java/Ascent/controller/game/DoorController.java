package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Door;
import Ascent.model.entities.Player;
import Ascent.model.game.floor.Floor;

public class DoorController extends GameController {
    public DoorController(Floor floor) {
        super(floor);
    }

    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();
        Door door = getModel().getDoorAt(player.facing());
        door.interact(player);
    }
}

