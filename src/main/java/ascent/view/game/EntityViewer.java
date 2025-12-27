package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Entity;

public interface EntityViewer<T extends Entity> {
    void draw(T entity, GUI gui);
}
