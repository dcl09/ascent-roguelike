package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.entities.Stairs;
import ascent.model.game.floor.FileLevelFactory;
import ascent.model.game.floor.Floor;
import ascent.model.menu.Endscreen;
import ascent.state.EndscreenState;
import ascent.state.GameState;

import java.io.IOException;

public class StairsController extends GameController {
    private FileLevelFactory fileLevelFactory;

    public StairsController(Floor floor) {
        this(floor, new FileLevelFactory());
    }

    public StairsController(Floor floor, FileLevelFactory fileLevelFactory) {
        super(floor);
        this.fileLevelFactory = fileLevelFactory;
    }

    @Override
    public void step(Game game, ACTION action, long time) throws IOException {
        Player player = getModel().getPlayer();
        Stairs stairs = getModel().getStairs();
        if (player.facing().equals(stairs.getPosition())) {
            game.popState();
            int newLevel = getModel().getCurrLevel() + 1;
            try {
                Floor newFloor = fileLevelFactory.createLevel(newLevel, player);
                game.pushState(new GameState(newFloor));
            } catch (IOException e) {
                game.pushState(new EndscreenState(new Endscreen(true)));
            }
        }
    }
}
