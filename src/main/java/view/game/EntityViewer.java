package view.game;

import gui.GUI;
import model.entities.Entity;

public interface EntityViewer<T extends Entity> {
    void draw(T entity, GUI gui);
}
