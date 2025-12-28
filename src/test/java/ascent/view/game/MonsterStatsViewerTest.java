package ascent.view.game;

import ascent.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MonsterStatsViewerTest {
    private MonsterStatsViewer viewer;
    private GUI gui;

    @BeforeEach
    public void setup() {
        viewer = new MonsterStatsViewer();
        gui = mock(GUI.class);
    }

    @Test
    public void drawTitleDrawsCorrectly() {
        int result = viewer.drawTitle(gui, 5, 5);

        assertEquals(1, result);
        verify(gui).drawText(eq(5), eq(5), eq("=== MONSTER ==="), eq("#eb564b"));
    }

    @Test
    public void getHealthBarColorReturnsCorrectColor() {
        assertEquals("#f05837", viewer.getHealthBarColor());
    }

    @Test
    public void getStatsColorReturnsCorrectColor() {
        assertEquals("#ffb5b5", viewer.getStatsColor());
    }
}