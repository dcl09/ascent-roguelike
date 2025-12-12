package Ascent.controller.game;

import Ascent.controller.Controller;
import Ascent.model.game.floor.Floor;

public abstract class GameController extends Controller<Floor> {
    public GameController(Floor floor) {
    super(floor);
    }
}

