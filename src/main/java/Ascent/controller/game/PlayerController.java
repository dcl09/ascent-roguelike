package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.entities.components.LOOKING;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;
import Ascent.model.items.armour.ArmourSlot;

public class PlayerController extends GameController {

    private static final long BASE_MOVEMENT_COOLDOWN_MS = 300;
    private long lastMovementTime = 0;

    public PlayerController(Floor floor) {
        super(floor);
    }

    private long getMovementCooldown() {
        int speed = Math.max(1, getModel().getPlayer().getMovementSpeed());
        return BASE_MOVEMENT_COOLDOWN_MS / speed;
    }

    public void movePlayer(Position position, long time) {
        getModel().movePlayer(position, time);
    }

    @Override
    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();

        if (handleInventoryAction(action, player)) {
            return;
        }

        // looking actions have no cooldown
        switch (action) {
            case LOOK_UP -> {
                player.setLookingDirection(LOOKING.UP);
                return;
            }
            case LOOK_DOWN -> {
                player.setLookingDirection(LOOKING.DOWN);
                return;
            }
            case LOOK_LEFT -> {
                player.setLookingDirection(LOOKING.LEFT);
                return;
            }
            case LOOK_RIGHT -> {
                player.setLookingDirection(LOOKING.RIGHT);
                return;
            }
            default -> {
            }
        }

        // movement actions have cooldown
        if (time - lastMovementTime < getMovementCooldown()) {
            return;
        }

        boolean moved = false;
        switch (action) {
            case UP -> {
                movePlayer(player.moveToward(LOOKING.UP), time);
                moved = true;
            }
            case DOWN -> {
                movePlayer(player.moveToward(LOOKING.DOWN), time);
                moved = true;
            }
            case LEFT -> {
                movePlayer(player.moveToward(LOOKING.LEFT), time);
                moved = true;
            }
            case RIGHT -> {
                movePlayer(player.moveToward(LOOKING.RIGHT), time);
                moved = true;
            }
            default -> {
            }
        }

        if (moved) {
            lastMovementTime = time;
        }
    }

    private boolean handleInventoryAction(ACTION action, Player player) {
        int potionIndex = switch (action) {
            case USE_POTION_0 -> 0;
            case USE_POTION_1 -> 1;
            case USE_POTION_2 -> 2;
            case USE_POTION_3 -> 3;
            case USE_POTION_4 -> 4;
            default -> -1;
        };

        if (potionIndex >= 0) {
            player.consumeItem(potionIndex);
            return true;
        }

        boolean handled = switch (action) {
            case UNEQUIP_WEAPON -> {
                player.equipWeapon(null);
                yield true;
            }
            case UNEQUIP_HEAD -> {
                player.unequipArmour(ArmourSlot.HEAD);
                yield true;
            }
            case UNEQUIP_CHEST -> {
                player.unequipArmour(ArmourSlot.CHEST);
                yield true;
            }
            case UNEQUIP_ARMS -> {
                player.unequipArmour(ArmourSlot.ARMS);
                yield true;
            }
            case UNEQUIP_LEGS -> {
                player.unequipArmour(ArmourSlot.LEGS);
                yield true;
            }
            case UNEQUIP_FEET -> {
                player.unequipArmour(ArmourSlot.FEET);
                yield true;
            }
            default -> false;
        };

        return handled;
    }
}
