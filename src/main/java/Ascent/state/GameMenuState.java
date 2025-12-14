package Ascent.state;

import Ascent.controller.Controller;
import Ascent.controller.menu.GameMenuController;
import Ascent.model.menu.GameMenu;
import Ascent.view.Viewer;
import Ascent.view.menu.GameMenuViewer;

import java.io.IOException;

public class GameMenuState extends State<GameMenu> {
    public GameMenuState(GameMenu gameMenu) throws IOException {
        super(gameMenu);
    }

    @Override
    protected Viewer<GameMenu> getViewer() throws IOException {
        return new GameMenuViewer(getModel());
    }

    @Override
    protected Controller<GameMenu> getController() {
        return new GameMenuController(getModel());
    }
}
