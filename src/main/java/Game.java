import controller.GameController;
import model.GameModel;
import view.GameView;

public class Game {
    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(view, model);
        GameController.run();
    }
}
