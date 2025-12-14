package Ascent.state;

import Ascent.controller.Controller;
import Ascent.controller.menu.StartMenuController;
import Ascent.model.menu.StartMenu;
import Ascent.view.Viewer;
import Ascent.view.menu.StartMenuViewer;

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
