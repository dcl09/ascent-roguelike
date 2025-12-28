package ascent.controller.menu;

import ascent.Game;
import ascent.controller.Controller;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.game.floor.FileLevelFactory;
import ascent.model.menu.GameMenu;
import ascent.state.GameState;

import java.io.IOException;

public class GameMenuController extends Controller<GameMenu> {
    public GameMenuController(GameMenu gameMenu) {
        super(gameMenu);
    }

    @Override
    public void step(Game game, ACTION action, long time) throws IOException {
        switch (action) {
            case UP -> getModel().prevEntry();
            case DOWN -> getModel().nextEntry();
            case SELECT -> {
                if (getModel().getCurrEntry().equals("MAIN MENU")) {
                    game.popState();
                    game.popState();
                }
                if (getModel().getCurrEntry().equals("RESUME"))
                    game.popState();
                if (getModel().getCurrEntry().equals("EXIT"))
                    game.popState();
                if (getModel().getCurrEntry().equals("START")) {
                    FileLevelFactory factory = new FileLevelFactory();
                    Player player = new Player(new ascent.model.game.Position(0, 0));
                    game.pushState(new GameState(factory.createLevel(1, player)));
                }
            }
            default -> {
            }
        }
    }
}
