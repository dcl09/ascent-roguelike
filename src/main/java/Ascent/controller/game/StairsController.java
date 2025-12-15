package Ascent.controller.game;

import Ascent.Game;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.entities.Stairs;
import Ascent.model.game.floor.FileLevelBuilder;
import Ascent.model.game.floor.Floor;
import Ascent.model.menu.Winscreen;
import Ascent.state.GameState;
import Ascent.state.WinscreenState;

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
                game.pushState(new WinscreenState(new Winscreen()));
        }
    }
}
