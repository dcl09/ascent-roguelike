package Ascent.state;

import Ascent.controller.Controller;
import Ascent.controller.menu.EndscreenController;
import Ascent.model.menu.Endscreen;
import Ascent.view.Viewer;
import Ascent.view.menu.EndscreenViewer;

import java.io.IOException;

public class EndscreenState extends State<Endscreen> {
    public EndscreenState(Endscreen endscreen) throws IOException {
        super(endscreen);
    }

    @Override
    protected Controller<Endscreen> getController() {
        return new EndscreenController(getModel());
    }

    @Override
    protected Viewer<Endscreen> getViewer() throws IOException {
        return new EndscreenViewer(getModel());
    }
}
