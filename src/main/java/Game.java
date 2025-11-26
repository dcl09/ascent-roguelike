import controller.GameController;
import model.GameModel;
import model.entities.Player;
import model.game.Position;
import view.GameView;

import java.io.IOException;

public class Game {
    public static void main(String[] args) throws IOException {
        Player player = new Player(new Position(10,10), 'P', "black");
        GameModel model = new GameModel(player);
        GameView view = new GameView(model);
        GameController controller = new GameController(view, model);
        controller.run();
    }
}
