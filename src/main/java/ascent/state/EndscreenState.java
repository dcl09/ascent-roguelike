package ascent.state;

import ascent.controller.Controller;
import ascent.controller.menu.EndscreenController;
import ascent.model.menu.Endscreen;
import ascent.view.Viewer;
import ascent.view.menu.EndscreenViewer;

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
