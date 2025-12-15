package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Stairs;

public class StairsViewer implements EntityViewer<Stairs> {
    @Override
    public void draw(Stairs stairs, GUI gui) {
        gui.drawChar(stairs.getPosition().getX(), stairs.getPosition().getY(), stairs.getSymbol(), stairs.getColor());
    }
}

