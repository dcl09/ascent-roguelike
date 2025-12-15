package Ascent.controller.menu;

import Ascent.Game;
import Ascent.controller.Controller;
import Ascent.gui.ACTION;
import Ascent.model.menu.GameMenu;

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
            }
            default -> {
            }
        }
    }
}
