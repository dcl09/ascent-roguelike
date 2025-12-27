package ascent.controller.menu;

import ascent.Game;
import ascent.controller.Controller;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.game.floor.FileLevelFactory;
import ascent.model.menu.StartMenu;
import ascent.state.GameState;

import java.io.IOException;

public class StartMenuController extends Controller<StartMenu> {
    public StartMenuController(StartMenu startMenu) {
        super(startMenu);
    }

    @Override
    public void step(Game game, ACTION action, long time) throws IOException {
        switch (action) {
            case UP -> getModel().prevEntry();
            case DOWN -> getModel().nextEntry();
            case SELECT -> {
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
