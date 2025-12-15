package Ascent.state;

import Ascent.controller.Controller;
import Ascent.controller.menu.WinscreenController;
import Ascent.model.menu.Winscreen;
import Ascent.view.Viewer;
import Ascent.view.menu.WinscreenViewer;

import java.io.IOException;

public class WinscreenState extends State<Winscreen> {
    public WinscreenState(Winscreen winscreen) throws IOException {
        super(winscreen);
    }

    @Override
    protected Viewer<Winscreen> getViewer() throws IOException {
        return new WinscreenViewer(getModel());
    }

    @Override
    protected Controller<Winscreen> getController() {
        return new WinscreenController(getModel());
    }
}
