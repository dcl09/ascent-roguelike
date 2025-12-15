package Ascent.controller.game;

import Ascent.gui.ACTION;
import Ascent.model.entities.Chest;
import Ascent.Game;
import Ascent.model.entities.Player;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

public class ChestController extends GameController {
    // Todo: Import game & implement time
    public ChestController(Floor floor) {
        super(floor);
    }

    public void step(Game game, ACTION action, long time) {
        Player player = getModel().getPlayer();
        Chest chest = getModel().getChestAt(player.facing());
        chest.interact(player);
        /*
         * Position checkForChest = getModel().getPlayer().facing();
         * if (getModel().isChest(checkForChest)) {
         * for (Chest chest : getModel().getChests()) {
         * if (chest.getPosition().equals(checkForChest)) {
         * if (chest.canInteract()) {
         * getModel().getPlayer().interactWith(chest);
         * }
         * break;
         * }
         * }
         * }
         */
    }
}
