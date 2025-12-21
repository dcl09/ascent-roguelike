package Ascent.view.game.interaction;

import Ascent.gui.GUI;

public interface InteractionDialog<T> {
    void draw(GUI gui, T entity, int x, int y);

    String getTitle();
}
