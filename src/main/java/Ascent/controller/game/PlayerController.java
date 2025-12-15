package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.entities.components.LOOKING;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

// todo: implement getter of specific monster infront of player, import game & implement time
public class PlayerController extends GameController {
    private static final long BASE_MOVEMENT_COOLDOWN = 300;
    private long lastMovementTime = 0;

    public PlayerController(Floor floor) {
        super(floor);
    }

    private long getMovementCooldown() {
        int speed = Math.max(1, getModel().getPlayer().getMovementSpeed());
        return BASE_MOVEMENT_COOLDOWN / speed;
    }

    public void movePlayer(Position position) {
        getModel().movePlayer(position);
    }

    @Override
    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();

        // looking actions have no cooldown
        switch (action) {
            case LOOK_UP -> { player.setLookingDirection(LOOKING.UP); return; }
            case LOOK_DOWN -> { player.setLookingDirection(LOOKING.DOWN); return; }
            case LOOK_LEFT -> { player.setLookingDirection(LOOKING.LEFT); return; }
            case LOOK_RIGHT -> { player.setLookingDirection(LOOKING.RIGHT); return; }
            default -> {}
        }

        // movement actions have cooldown
        if (time - lastMovementTime < getMovementCooldown()) {
            return;
        }

        boolean moved = false;
        switch (action) {
            case UP -> { movePlayer(player.moveToward(LOOKING.UP)); moved = true; }
            case DOWN -> { movePlayer(player.moveToward(LOOKING.DOWN)); moved = true; }
            case LEFT -> { movePlayer(player.moveToward(LOOKING.LEFT)); moved = true; }
            case RIGHT -> { movePlayer(player.moveToward(LOOKING.RIGHT)); moved = true; }
            default -> {}
        }

        if (moved) {
            lastMovementTime = time;
        }
    }
}
