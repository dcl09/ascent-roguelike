package view.game;

import gui.GUI;
import model.entities.Monster;

public class MonsterViewer implements EntityViewer<Monster> {
    @Override
    public void draw(Monster monster, GUI gui) {
        /* same as PlayerViewer */
        gui.drawChar(monster.getPosition().getX(), monster.getPosition().getY(), monster.getSymbol(), monster.getColor());
    }
}
