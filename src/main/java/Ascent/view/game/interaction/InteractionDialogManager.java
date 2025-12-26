package Ascent.view.game.interaction;

import Ascent.gui.GUI;
import Ascent.model.entities.Chest;
import Ascent.model.entities.Door;
import Ascent.model.entities.Stairs;
import Ascent.model.game.Position;
import Ascent.model.game.floor.Floor;

public class InteractionDialogManager {
    private final ChestInteractionDialog chestDialog;
    private final DoorInteractionDialog doorDialog;
    private final StairsInteractionDialog stairsDialog;

    public InteractionDialogManager() {
        this.chestDialog = new ChestInteractionDialog();
        this.doorDialog = new DoorInteractionDialog();
        this.stairsDialog = new StairsInteractionDialog();
    }

    public void draw(GUI gui, Floor floor, int x, int y) {
        Chest interactingChest = floor.getInteractingChest();

        if (interactingChest != null) {
            gui.drawText(x, y, chestDialog.getTitle(), "#FFD700");
            chestDialog.draw(gui, interactingChest, x, y + 1);
            return;
        }

        Position playerFacing = floor.getPlayer().facing();
        Chest facingChest = floor.getChestAt(playerFacing);

        if (facingChest != null) {
            gui.drawText(x, y, chestDialog.getTitle(), "#FFD700");
            chestDialog.drawPrompt(gui, x, y + 2, facingChest.getInteractionMessage());
            return;
        }

        Door facingDoor = floor.getDoorAt(playerFacing);
        if (facingDoor != null) {
            gui.drawText(x, y, doorDialog.getTitle(), "#FFD700");
            doorDialog.draw(gui, facingDoor, x, y + 2);
            return;
        }

        Stairs stairs = floor.getStairs();
        if (stairs != null && stairs.getPosition().equals(playerFacing)) {
            gui.drawText(x, y, stairsDialog.getTitle(), "#FFD700");
            stairsDialog.draw(gui, stairs, x, y + 2);
        }
    }
}
