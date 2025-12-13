package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Door;

public class DoorViewer implements EntityViewer<Door> {
    @Override
    public void draw(Door door, GUI gui) {
        gui.drawChar(door.getPosition().getX(), door.getPosition().getY(), door.getSymbol(), door.getColor());
    }
}
