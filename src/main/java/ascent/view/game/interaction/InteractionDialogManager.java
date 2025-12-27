package ascent.view.game.interaction;

import ascent.gui.GUI;
import ascent.model.entities.Chest;
import ascent.model.entities.Door;
import ascent.model.entities.Stairs;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;

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
