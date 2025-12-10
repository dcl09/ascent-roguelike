package Ascent.state;


import Ascent.controller.Controller;
import Ascent.controller.game.FloorController;
import Ascent.model.game.floor.Floor;
import Ascent.view.Viewer;
import Ascent.view.game.*;

public class GameState extends State<Floor> {
    public GameState(Floor floor) {
        super(floor);
    }

    @Override
    protected Viewer<Floor> getViewer() {
        return new GameViewer(getModel());
    }

    @Override
    protected Controller<Floor> getController() {
        return new FloorController(getModel());
    }
}
