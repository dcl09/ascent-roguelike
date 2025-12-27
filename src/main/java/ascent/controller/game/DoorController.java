package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Door;
import ascent.model.entities.Player;
import ascent.model.game.floor.Floor;

public class DoorController extends GameController {
    public DoorController(Floor floor) {
        super(floor);
    }

    @Override
    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();
        Door door = getModel().getDoorAt(player.facing());

        if (door != null) {
            door.interact(player);
        }
    }
}
