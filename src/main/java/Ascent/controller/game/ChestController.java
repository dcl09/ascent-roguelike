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
            // Open Chest Logic
            if (action == ACTION.INTERACT) {
                Chest targetChest = getModel().getChestAt(player.facing());

                if (targetChest != null) {
                    if (!targetChest.isOpened()) {
                        targetChest.interact(player);
                    }
                    if (targetChest.getContainedItem() != null) {
                        getModel().setInteractingChest(targetChest);
                    }
                }
            }
            return;
        }

        // Interaction Logic
        switch (action) {
            case USE_POTION_0 -> applyItem(chest, player); // (1) Use/Equip
            case USE_POTION_1 -> storeItem(chest, player); // (2) Store Potion
            case MENU, INTERACT -> getModel().setInteractingChest(null); // (Esc/E) Leave
            default -> {
            }
        }
    }

    private void applyItem(Chest chest, Player player) {
        Item item = chest.getContainedItem();
        if (item == null)
            return;

        Item returnedItem = item.use(player);

        if (returnedItem == null) {
            chest.takeItem();
        }
        getModel().setInteractingChest(null);
    }

    private void storeItem(Chest chest, Player player) {
        Item item = chest.getContainedItem();

        // Only potions can be stored via '2'
        if (item instanceof HealthRestore hr && player.hasInventorySpace()) {
            player.addConsumable(hr);
            chest.takeItem(); // Remove from chest
            getModel().setInteractingChest(null);
        }
    }
}
