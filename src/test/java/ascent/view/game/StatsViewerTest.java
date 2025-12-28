package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.components.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class StatsViewerTest {
    private StatsViewer viewer;
    private GUI gui;
    private Stats stats;
    @BeforeEach
    void setup() {
        viewer = new StatsViewer() {
            @Override
            protected int drawTitle(GUI gui, int x, int y) {
                gui.drawText(x, y, "=== TEST ===", "#000000");
                return 1;
            }

            @Override
            protected String getHealthBarColor() {
                return "#HEALTH";
            }

            @Override
            protected String getStatsColor() {
                return "#STATS";
            }
        };
        gui = mock(GUI.class);
        stats = mock(Stats.class);
    }

    @Test
    void cantDrawStatsWhenNull() {
        viewer.draw(gui, null, 5, 5);
        verifyNoInteractions(gui);
    }

    @Test
    void cantDrawStatsWhenDead(){
        when(stats.isDead()).thenReturn(true);
        viewer.draw(gui, stats, 5, 5);
        verifyNoInteractions(gui);
    }

    @Test
    void testDrawStats(){
        when(stats.isDead()).thenReturn(false);
        when(stats.getDamage()).thenReturn(5);
        when(stats.getSpeed()).thenReturn(6);
        when(stats.getResistanceToDamage()).thenReturn(7);

        viewer.draw(gui, stats, 5, 5);
        verify(gui).drawText(eq(5), eq(5), contains("TEST"), eq("#000000"));
        verify(gui).drawText(eq(5), eq(7), contains("HP:"), eq("#HEALTH"));
        verify(gui).drawText(eq(5), eq(8), contains("DMG: 5"), eq("#STATS"));
    }
}
