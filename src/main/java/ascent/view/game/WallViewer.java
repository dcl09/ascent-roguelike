package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Wall;

public class WallViewer implements EntityViewer<Wall> {
    @Override
    public void draw(Wall wall, GUI gui) {
        gui.drawChar(wall.getPosition().getX(), wall.getPosition().getY(), wall.getSymbol(), wall.getColor());
    }
}
