package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.entities.Stairs;
import ascent.model.game.floor.FileLevelBuilder;
import ascent.model.game.floor.Floor;
import ascent.model.menu.Endscreen;
import ascent.state.EndscreenState;
import ascent.state.GameState;

import java.io.File;
import java.io.IOException;

public class StairsController extends GameController {
    public StairsController(Floor floor) {super (floor);}

    @Override
    public void step(Game game, ACTION action, long time) throws IOException {
        Player player = getModel().getPlayer();
        Stairs stairs = getModel().getStairs();
        if (player.facing().equals(stairs.getPosition())) {
            game.popState();
            int newLevel = getModel().getCurrLevel() + 1;
            String filepath = "levels/level" + newLevel + ".txt";
            if (new File(filepath).exists()) {
                FileLevelBuilder builder = new FileLevelBuilder(filepath);
                player.setPosition(builder.findPlayerSpawn());
                game.pushState(new GameState(builder.createFloor(player, newLevel)));
            }else
                game.pushState(new EndscreenState(new Endscreen(true)));
        }
    }
}
