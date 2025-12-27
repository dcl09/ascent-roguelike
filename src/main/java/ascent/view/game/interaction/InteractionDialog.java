package ascent.view.game.interaction;

import ascent.gui.GUI;

public interface InteractionDialog<T> {
    void draw(GUI gui, T entity, int x, int y);

    String getTitle();
}
