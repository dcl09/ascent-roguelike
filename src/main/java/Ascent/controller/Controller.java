package Ascent.controller;
//import game; for when game is its own class;
import Ascent.Game;
import Ascent.gui.ACTION;

public abstract class Controller<Type> {
    //Todo: Implement time & import game
    private final Type model;
    public Controller(Type model) {
        this.model = model;
    }

    public Type getModel() {
        return model;
    }

    public abstract void step(Game game, ACTION action /*,long int time*/ );
}
