package controller.game;

import controller.Controller;
import model.game.floor.Floor;

public abstract class GameController extends Controller<Floor> {
    public GameController(Floor floor) {
    super(floor);
    }
}

