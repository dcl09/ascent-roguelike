package ascent.controller.menu;

import ascent.Game;
import ascent.controller.Controller;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.game.floor.FileLevelBuilder;
import ascent.model.menu.Endscreen;
import ascent.state.GameState;

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
