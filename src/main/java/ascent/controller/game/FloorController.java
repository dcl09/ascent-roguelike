package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Chest;

import ascent.model.game.floor.Floor;
import ascent.model.menu.Endscreen;
import ascent.model.menu.GameMenu;
import ascent.state.EndscreenState;
import ascent.state.GameMenuState;

import java.io.IOException;

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
        if (action == ACTION.QUIT) {
            game.popState();
            return;
        }

        if (action == ACTION.MENU) {
            game.pushState(new GameMenuState(new GameMenu(true)));
            return;
        }

        if (getModel().getPlayer().getStats().isDead()) {
            game.popState();
            game.pushState(new EndscreenState(new Endscreen(false)));
            return;
        }

        Chest activeChest = getModel().getInteractingChest();
        if (activeChest != null) {
            chestcontroller.step(game, action, time);
            monstercontroller.step(game, action, time);
            return;
        }

        playercontroller.step(game, action, time);

        if (action == ACTION.INTERACT && getModel().getPlayer().canInteract()) {
            if (getModel().isDoor(getModel().getPlayer().facing())) {
                doorcontroller.step(game, action, time);
            } else if (getModel().isChest(getModel().getPlayer().facing())) {
                Chest chest = getModel().getChestAt(getModel().getPlayer().facing());
                if (chest != null) {
                    if (!chest.isOpened()) {
                        chest.interact(getModel().getPlayer());
                    }
                    getModel().setInteractingChest(chest);
                }
            } else if (getModel().isStairs(getModel().getPlayer().facing())) {
                stairscontroller.step(game, action, time);
            }
        }

        monstercontroller.step(game, action, time);
    }
}
