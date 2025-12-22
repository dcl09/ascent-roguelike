package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Chest;
import Ascent.model.entities.Player;
import Ascent.model.game.floor.Floor;
import Ascent.model.items.HealthRestore;
import Ascent.model.items.Item;

public class ChestController extends GameController {
    public ChestController(Floor floor) {
        super(floor);
    }

    @Override
    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();
        Chest chest = getModel().getInteractingChest();

        if (chest == null) {
            if (action == ACTION.INTERACT) {
                Chest targetChest = getModel().getChestAt(player.facing());

                if (targetChest != null) {
                    if (!targetChest.isOpened()) {
                        targetChest.interact(player);
                    }
                    getModel().setInteractingChest(targetChest);
                }
            }
            return;
        }

        // Interaction Logic
        switch (action) {
            case USE_POTION_0 -> applyItem(chest, player);
            case USE_POTION_1 -> storeItem(chest, player);
            case MENU, INTERACT -> getModel().setInteractingChest(null);
            default -> {
            }
        }
    }

    private void applyItem(Chest chest, Player player) {
        Item item = chest.getContainedItem();
        if (item == null)
            return;

        if (item instanceof HealthRestore potion) {
            potion.consumeNow(player);
        } else {
            item.use(player);
        }
        chest.takeItem();
        getModel().setInteractingChest(null);
    }

    private void storeItem(Chest chest, Player player) {
        Item item = chest.getContainedItem();

        if (item instanceof HealthRestore hr && player.hasInventorySpace()) {
            player.addConsumable(hr);
            chest.takeItem();
            getModel().setInteractingChest(null);
        }
    }
}
