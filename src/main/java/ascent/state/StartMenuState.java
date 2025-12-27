package ascent.state;

import ascent.controller.Controller;
import ascent.controller.menu.StartMenuController;
import ascent.model.menu.StartMenu;
import ascent.view.Viewer;
import ascent.view.menu.StartMenuViewer;

import java.io.IOException;

public class StartMenuState extends State<StartMenu> {
    public StartMenuState(StartMenu startMenu) throws IOException {
        super(startMenu);
    }

    @Override
    protected Viewer<StartMenu> getViewer() throws IOException {
        return new StartMenuViewer(getModel());
    }

    @Override
    protected Controller<StartMenu> getController() {
        return new StartMenuController(getModel());
    }
}
