package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Stairs;

public class StairsViewer implements EntityViewer<Stairs> {
    @Override
    public void draw(Stairs stairs, GUI gui) {
        gui.drawChar(stairs.getPosition().getX(), stairs.getPosition().getY(), stairs.getSymbol(), stairs.getColor());
    }
}

