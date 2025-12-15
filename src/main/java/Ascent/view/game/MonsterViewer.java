package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.monster.Monster;

public class MonsterViewer implements EntityViewer<Monster> {
    @Override
    public void draw(Monster monster, GUI gui) {
        /* same as PlayerViewer */
        gui.drawChar(monster.getPosition().getX(), monster.getPosition().getY(), monster.getSymbol(), monster.getColor());
    }
}
