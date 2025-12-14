package Ascent.controller.menu;

import Ascent.Game;
import Ascent.controller.Controller;
import Ascent.gui.ACTION;
import Ascent.model.entities.Player;
import Ascent.model.game.floor.FileLevelBuilder;
import Ascent.model.menu.StartMenu;
import Ascent.state.GameState;

import java.io.IOException;

public class StartMenuController extends Controller<StartMenu> {
    public StartMenuController(StartMenu startMenu){ super(startMenu);}

    @Override
    public void step(Game game, ACTION action, long time) throws IOException {
        switch (action){
            case UP:
                getModel().prevEntry();
                break;
            case DOWN:
                getModel().nextEntry();
                break;
            case SELECT:
                if (getModel().getCurrEntry().equals("EXIT"))
                    game.popState();
                if (getModel().getCurrEntry().equals("START")){
                    FileLevelBuilder builder = new FileLevelBuilder("levels/level1.txt");
                    game.pushState(new GameState(builder.createFloor(new Player(builder.findPlayerSpawn()))));
                }
        }
    }
}
