package ascent.view.game.interaction;

import ascent.gui.GUI;
import ascent.model.entities.Door;

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

        gui.drawText(x, y, door.getInteractionMessage(), TEXT_COLOR);
    }
}
