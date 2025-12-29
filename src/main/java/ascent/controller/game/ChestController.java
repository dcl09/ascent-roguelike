package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Chest;
import ascent.model.entities.Player;
import ascent.model.game.floor.Floor;
import ascent.model.items.HealthRestore;
import ascent.model.items.Item;

public class ChestController extends GameController {
    public ChestController(Floor floor) {
        super(floor);
    }

    @Override
    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();
        Chest chest = getModel().getInteractingChest();

        if (chest == null) return;

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
            potion.consume(player);
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
