package view.game;

import gui.GUI;
import model.entities.Chest;

public class ChestViewer implements EntityViewer<Chest> {
    @Override
    public void draw(Chest chest, GUI gui) {
        gui.drawChar(chest.getPosition().getX(), chest.getPosition().getY(), chest.getSymbol(), chest.getColor());
    }
}
