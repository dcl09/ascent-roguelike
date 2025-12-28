package ascent.view.game;

import ascent.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PlayerStatsViewerTest {
    private PlayerStatsViewer viewer;
    private GUI gui;
    @BeforeEach
    public void setup() {
        viewer = new PlayerStatsViewer();
        gui = mock(GUI.class);
    }

    @Test
    public void drawTitleDrawsCorrectly() {
        int result = viewer.drawTitle(gui, 5 , 5);

        assertEquals(3, result);
        verify(gui).drawText(eq(5), eq(5), eq("*** PLAYER ***"), eq("#6476e8"));
        verify(gui).drawText(eq(5), eq(7), eq("=== STATS ==="), eq("#86a7ed"));
    }

    @Test
    public void getHealthBarColorReturnsCorrectColor() {
        assertEquals("#3ca370", viewer.getHealthBarColor());
    }

    @Test
    public void getStatsColorReturnsCorrectColor() {
        assertEquals("#86a7ed", viewer.getStatsColor());
    }
}
