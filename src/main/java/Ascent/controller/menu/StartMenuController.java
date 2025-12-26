package Ascent.controller.menu;

import Ascent.Game;
import Ascent.controller.Controller;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.game.floor.FileLevelFactory;
import Ascent.model.menu.StartMenu;
import Ascent.state.GameState;

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
                    Player player = new Player(new Ascent.model.game.Position(0, 0));
                    game.pushState(new GameState(factory.createLevel(1, player)));
                }
            }
            default -> {
            }
        }
    }
}
