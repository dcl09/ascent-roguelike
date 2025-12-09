import controller.game.GameController;
import gui.GUI;
import model.GameModel;
import model.entities.Player;
import model.game.Position;
import model.game.floor.BaseplateBuilder;
import model.game.floor.Floor;
import view.GameView;

import java.io.IOException;

public class Game {
    public static void main(String[] args) throws IOException {
        GUI gui = new GUI(40, 20);
        Player player = new Player(new Position(20,10));
        BaseplateBuilder builder = new BaseplateBuilder(40, 20, 4);
        Floor floor = builder.createFloor(player);

        GameModel model = new GameModel(player, floor);
        GameView view = new GameView(model);
        GameController controller = new GameController(view, model, gui);

        controller.run();
    }
}
