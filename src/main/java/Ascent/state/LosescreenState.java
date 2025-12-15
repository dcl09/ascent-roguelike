package Ascent.state;

import Ascent.controller.Controller;
import Ascent.controller.menu.LosescreenController;
import Ascent.model.menu.Losescreen;
import Ascent.view.Viewer;
import Ascent.view.menu.LosescreenViewer;

import java.io.IOException;

public class LosescreenState extends State<Losescreen> {
    public LosescreenState(Losescreen losescreen) throws IOException {
        super(losescreen);
    }

    @Override
    protected Controller<Losescreen> getController() {
        return new LosescreenController(getModel());
    }

    @Override
    protected Viewer<Losescreen> getViewer() throws IOException {
        return new LosescreenViewer(getModel());
    }
}
