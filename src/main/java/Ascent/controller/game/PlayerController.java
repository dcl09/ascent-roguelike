package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.entities.components.LOOKING;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

// todo: implement getter of specific monster infront of player, import game & implement time
public class PlayerController extends GameController {

    public PlayerController(Floor floor) {
        super(floor);
    }

    public void movePlayer(Position position) {
        getModel().movePlayer(position);
    }

    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();
        if (action == ACTION.UP)
            movePlayer(player.moveToward(LOOKING.UP));
        if (action == ACTION.DOWN)
            movePlayer(player.moveToward(LOOKING.DOWN));
        if (action == ACTION.LEFT)
            movePlayer(player.moveToward(LOOKING.LEFT));
        if (action == ACTION.RIGHT)
            movePlayer(player.moveToward(LOOKING.RIGHT));
    }
}
