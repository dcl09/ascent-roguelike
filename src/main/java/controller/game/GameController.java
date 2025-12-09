package controller.game;

import controller.Controller;
import model.game.floor.Floor;
// Todo:Remake game loop, process key and check conditions in game

public abstract class GameController extends Controller<Floor> {
    public GameController(Floor floor) {
    super(floor);
    }
}

