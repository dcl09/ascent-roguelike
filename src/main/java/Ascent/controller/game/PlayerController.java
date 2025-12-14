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

        switch (action) {
            case UP -> movePlayer(player.moveToward(LOOKING.UP));
            case DOWN -> movePlayer(player.moveToward(LOOKING.DOWN));
            case LEFT -> movePlayer(player.moveToward(LOOKING.LEFT));
            case RIGHT -> movePlayer(player.moveToward(LOOKING.RIGHT));

            case LOOK_UP -> player.setLookingDirection(LOOKING.UP);
            case LOOK_DOWN -> player.setLookingDirection(LOOKING.DOWN);
            case LOOK_LEFT -> player.setLookingDirection(LOOKING.LEFT);
            case LOOK_RIGHT -> player.setLookingDirection(LOOKING.RIGHT);

            default -> {
            }
        }
    }
}
