package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Player;

public class PlayerViewer implements EntityViewer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        /* use default drawChar method from GUI class to draw player
        we draw based on the values of the member fields of the player object */
        gui.drawChar(player.getPosition().getX(), player.getPosition().getY(), player.getSymbol(), player.getColor());
    }
}
