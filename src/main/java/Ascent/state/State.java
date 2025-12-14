package Ascent.state;

import Ascent.Game;
import Ascent.controller.Controller;
import Ascent.gui.ACTION;
import Ascent.gui.GUI;
import Ascent.view.Viewer;

import java.io.IOException;

public abstract class State<Type> {
    private final Type model;
    private final Controller<Type> controller;
    private final Viewer<Type> viewer;

    public State(Type model) throws IOException {
        this.model = model;
        this.viewer = getViewer();
        this.controller = getController();
    }

    protected abstract Controller<Type> getController();

    protected abstract Viewer<Type> getViewer() throws IOException;

    public Type getModel() {
        return model;
    }

    public void step(Game game, GUI gui , long time) throws IOException {
        ACTION action = gui.processKey();
        controller.step(game, action, time);
        viewer.draw(gui);
    }
}
