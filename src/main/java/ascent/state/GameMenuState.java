package ascent.state;

import ascent.controller.Controller;
import ascent.controller.menu.GameMenuController;
import ascent.model.menu.GameMenu;
import ascent.view.Viewer;
import ascent.view.menu.GameMenuViewer;

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
