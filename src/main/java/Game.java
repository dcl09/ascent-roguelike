import controller.GameController;
import gui.ACTION;
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

        /* bad example of game loop */
        controller.placeholder_refresh_GUI();
        while (true) {
            /* game loop here with placeholders */
            /* todo: we need to abstract the input processing,
                rendering and time (delta) updating to separate functions that all run independently in the game loop
                but constrained to the tick/time defined, with sleeps if they are ahead of schedule
                also calculate how optimized the game is to make sure our time window is slightly above the slowest frame draw time
                to make sure we dont go into have trouble
             */
            ACTION curraction = controller.placeholder_processKey();
            if (curraction == ACTION.QUIT) break;
            else if (curraction != null)
                controller.processAction(curraction);
            controller.placeholder_refresh_GUI();
        }
        controller.placeholder_close_GUI();
        /* */
    }
}
