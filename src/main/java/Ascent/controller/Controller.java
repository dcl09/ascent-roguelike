package Ascent.controller;
import Ascent.Game;
import Ascent.gui.ACTION;

import java.io.IOException;

public abstract class Controller<Type> {
    //Todo: Implement time
    private final Type model;
    public Controller(Type model) {
        this.model = model;
    }

    public Type getModel() {
        return model;
    }

    public abstract void step(Game game, ACTION action, long time) throws IOException;
}
