package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Wall;

public class WallViewer implements EntityViewer<Wall> {
    @Override
    public void draw(Wall wall, GUI gui) {
        gui.drawChar(wall.getPosition().getX(), wall.getPosition().getY(), wall.getSymbol(), wall.getColor());
    }
}
