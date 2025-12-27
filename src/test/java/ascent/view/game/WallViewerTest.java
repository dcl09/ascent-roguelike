package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Wall;
import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WallViewerTest {
    private WallViewer wallViewer;
    private GUI gui;

    @BeforeEach
    void setup() {
        wallViewer = new WallViewer();
        gui = Mockito.mock(GUI.class);
    }

    @Test
    void drawWall() {
        Wall wall = new Wall(new Position(1, 2));
        wallViewer.draw(wall, gui);
        Mockito.verify(gui).drawChar(1, 2, '#', "#606070");
    }

    @Test
    void drawWallAtBoundary() {
        Wall wall = new Wall(new Position(39, 19));
        wallViewer.draw(wall, gui);
        Mockito.verify(gui).drawChar(39, 19, '#', "#606070");
    }
}