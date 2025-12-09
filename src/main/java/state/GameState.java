package state;


import controller.Controller;
import controller.game.FloorController;
import model.game.floor.Floor;
import view.Viewer;
import view.game.*;

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
