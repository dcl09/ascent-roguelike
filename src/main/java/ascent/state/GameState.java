package ascent.state;


import ascent.controller.Controller;
import ascent.controller.game.FloorController;
import ascent.model.game.floor.Floor;
import ascent.view.Viewer;
import ascent.view.game.FloorViewer;

import java.io.IOException;

public class GameState extends State<Floor> {
    public GameState(Floor floor) throws IOException {
        super(floor);
    }

    @Override
    protected Viewer<Floor> getViewer() {
        return new FloorViewer(getModel());
    }

    @Override
    protected Controller<Floor> getController() {
        return new FloorController(getModel());
    }
}
