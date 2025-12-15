package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Chest;
import Ascent.model.entities.Player;

import Ascent.model.game.floor.Floor;
import Ascent.model.menu.GameMenu;
import Ascent.state.GameMenuState;

import java.io.IOException;

// Todo: set states, implement time & import game
public class FloorController extends GameController {

    private final PlayerController playercontroller;
    private final MonsterController monstercontroller;
    private final ChestController chestcontroller;
    private final DoorController doorcontroller;
    private final StairsController stairscontroller;

    public FloorController(Floor floor) {
        super(floor);
        this.playercontroller = new PlayerController(floor);
        this.monstercontroller = new MonsterController(floor);
        this.chestcontroller = new ChestController(floor);
        this.doorcontroller = new DoorController(floor);
        this.stairscontroller = new StairsController(floor);
    }

    public void step(Game game, ACTION action, long time) throws IOException {
        if (action == ACTION.QUIT)
            /* set Ascent.state to start menu */
            game.popState();
        else if (action == ACTION.MENU)
            game.pushState(new GameMenuState(new GameMenu()));
        else if (getModel().getPlayer().getStats().isDead())
            /* set Ascent.state to endscreen */
            game.popState();
        else {
            playercontroller.step(game, action, time);
            if (action == ACTION.INTERACT && getModel().getPlayer().canInteract()) {
                if (getModel().isDoor(getModel().getPlayer().facing())) {
                    doorcontroller.step(game, action, time);
                }

                if (getModel().isChest(getModel().getPlayer().facing())) {
                    chestcontroller.step(game, action, time);
                }

                if (getModel().isStairs(getModel().getPlayer().facing())) {
                    stairscontroller.step(game, action, time);
                }
            }
            monstercontroller.step(game, action, time);

        }
    }

    private void handlePotionUsage(ACTION action) {
        Player player = getModel().getPlayer();
        int potionIndex = switch (action) {
            case USE_POTION_0 -> 0;
            case USE_POTION_1 -> 1;
            case USE_POTION_2 -> 2;
            case USE_POTION_3 -> 3;
            case USE_POTION_4 -> 4;
            case USE_POTION_5 -> 5;
            case USE_POTION_6 -> 6;
            case USE_POTION_7 -> 7;
            case USE_POTION_8 -> 8;
            case USE_POTION_9 -> 9;
            default -> -1;
        };

        if (potionIndex >= 0) {
            player.consumeItem(potionIndex);
        }
    }
}
