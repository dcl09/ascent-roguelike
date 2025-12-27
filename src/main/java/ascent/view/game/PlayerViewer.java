package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Player;

public class PlayerViewer implements EntityViewer<Player> {
    @Override
    public void draw(Player player, GUI gui) {
        gui.drawChar(player.getPosition().getX(), player.getPosition().getY(), player.getSymbol(), player.getColor());
    }
}
