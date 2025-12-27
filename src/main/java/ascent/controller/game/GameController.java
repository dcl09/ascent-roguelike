package ascent.controller.game;

import ascent.controller.Controller;
import ascent.model.game.floor.Floor;

public abstract class GameController extends Controller<Floor> {
    public GameController(Floor floor) {
    super(floor);
    }
}

