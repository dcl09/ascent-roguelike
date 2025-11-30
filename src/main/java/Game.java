import controller.GameController;
import gui.GUI;
import model.GameModel;
import model.entities.Player;
import model.game.Position;
import view.GameView;

import java.io.IOException;

public class Game {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI(40, 20);
        Player player = new Player(new Position(20,10));
        GameModel model = new GameModel(player);
        GameView view = new GameView(model);
        GameController controller = new GameController(view, model, gui);
        controller.run();
    }
}
