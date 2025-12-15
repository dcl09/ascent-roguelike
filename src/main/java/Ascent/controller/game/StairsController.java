package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.entities.Stairs;
import Ascent.model.game.floor.FileLevelBuilder;
import Ascent.model.game.floor.Floor;
import Ascent.state.GameState;

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
            FileLevelBuilder builder = new FileLevelBuilder("levels/level" + newLevel + ".txt");
            player.setPosition(builder.findPlayerSpawn());
            game.pushState(new GameState(builder.createFloor(player, newLevel)));
        }
    }
}
