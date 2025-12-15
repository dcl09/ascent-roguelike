package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Chest;
import Ascent.model.entities.Player;
import Ascent.model.items.armour.ArmourSlot;

import Ascent.model.game.floor.Floor;
import Ascent.model.menu.GameMenu;
import Ascent.model.menu.Losescreen;
import Ascent.state.GameMenuState;
import Ascent.state.LosescreenState;

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
            game.pushState(new GameMenuState(new GameMenu()));
            return;
        }

        if (getModel().getPlayer().getStats().isDead()) {
            game.popState();
            game.pushState(new LosescreenState(new Losescreen()));
            return;
        }

        Chest activeChest = getModel().getInteractingChest();
        if (activeChest != null) {
            chestcontroller.step(game, action, time);
            monstercontroller.step(game, action, time);
            return;
        }

        handlePotionUsage(action);
        handleUnequip(action);
        playercontroller.step(game, action, time);
        if (action == ACTION.INTERACT && getModel().getPlayer().canInteract()) {
            if (getModel().isDoor(getModel().getPlayer().facing())) {
                doorcontroller.step(game, action, time);
            } else if (getModel().isChest(getModel().getPlayer().facing())) {
                chestcontroller.step(game, action, time);
            } else if (getModel().isStairs(getModel().getPlayer().facing())) {
                stairscontroller.step(game, action, time);
            }
        }

        monstercontroller.step(game, action, time);
    }

    private void handlePotionUsage(ACTION action) {
        Player player = getModel().getPlayer();
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
        }
    }

    private void handleUnequip(ACTION action) {
        Player player = getModel().getPlayer();
        switch (action) {
            case UNEQUIP_WEAPON -> player.equipWeapon(null);
            case UNEQUIP_HEAD -> player.unequipArmour(ArmourSlot.HEAD);
            case UNEQUIP_CHEST -> player.unequipArmour(ArmourSlot.CHEST);
            case UNEQUIP_ARMS -> player.unequipArmour(ArmourSlot.ARMS);
            case UNEQUIP_LEGS -> player.unequipArmour(ArmourSlot.LEGS);
            case UNEQUIP_FEET -> player.unequipArmour(ArmourSlot.FEET);
            default -> {
            }
        }
    }
}
