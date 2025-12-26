package Ascent.controller.menu;

import Ascent.Game;
import Ascent.controller.Controller;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.game.floor.FileLevelBuilder;
import Ascent.model.menu.Endscreen;
import Ascent.state.GameState;

import java.io.IOException;

public class EndscreenController extends Controller<Endscreen> {
    public EndscreenController(Endscreen endscreen) {
        super(endscreen);
    }

    @Override
    public void step(Game game, ACTION action, long time) throws IOException {
        switch (action) {
            case UP:
                getModel().prevEntry();
                break;
            case DOWN:
                getModel().nextEntry();
                break;
            case SELECT:
                if (getModel().getCurrEntry().equals("TO MAIN MENU")) {
                    game.popState();
                    game.popState();
                } else if (getModel().getCurrEntry().equals("RESTART")) {
                    game.popState();
                    FileLevelBuilder builder = new FileLevelBuilder("levels/level1.txt");
                    game.pushState(new GameState(builder.createFloor(new Player(builder.findPlayerSpawn()))));
                }
                break;
        }
    }
}
