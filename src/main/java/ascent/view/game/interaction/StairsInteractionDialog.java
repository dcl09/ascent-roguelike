package ascent.view.game.interaction;

import ascent.gui.GUI;
import ascent.model.entities.Stairs;

public class StairsInteractionDialog implements InteractionDialog<Stairs> {
    private static final String TEXT_COLOR = "#FFFFFF";

    @Override
    public String getTitle() {
        return "--- Stairs ---";
    }

    @Override
    public void draw(GUI gui, Stairs stairs, int x, int y) {
        if (stairs == null)
            return;

        gui.drawText(x, y, stairs.getInteractionMessage(), TEXT_COLOR);
    }
}
