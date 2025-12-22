package Ascent.view.game.interaction;

import Ascent.gui.GUI;
import Ascent.model.entities.Door;

public class DoorInteractionDialog implements InteractionDialog<Door> {
    private static final String TEXT_COLOR = "#FFFFFF";

    @Override
    public String getTitle() {
        return "--- Door ---";
    }

    @Override
    public void draw(GUI gui, Door door, int x, int y) {
        if (door == null)
            return;

        if (door.isOpen()) {
            gui.drawText(x, y, "Press E to close", TEXT_COLOR);
        } else {
            gui.drawText(x, y, "Press E to open", TEXT_COLOR);
        }
    }
}
