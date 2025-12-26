package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Player;

public class PlayerViewer implements EntityViewer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        gui.drawChar(player.getPosition().getX(), player.getPosition().getY(), player.getSymbol(), player.getColor());
    }
}
