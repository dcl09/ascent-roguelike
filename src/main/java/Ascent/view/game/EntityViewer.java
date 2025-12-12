package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Entity;

public interface EntityViewer<T extends Entity> {
    void draw(T entity, GUI gui);
}
