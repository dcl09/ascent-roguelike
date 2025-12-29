package ascent.view.game.components;

import ascent.gui.GUI;
import ascent.model.entities.components.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class HealthBarViewerTest {
    private HealthBarViewer viewer;
    private GUI gui;
    private Stats stats;

    @BeforeEach
    void setUp() {
        viewer = new HealthBarViewer();
        gui = mock(GUI.class);
        stats = mock(Stats.class);
    }

    @Test
    void testDrawDefaultBarLength(){
        when(stats.getHealth()).thenReturn(100);
        when(stats.getMaxHealth()).thenReturn(100);
        viewer.draw(gui, stats, 10, 10, "#000000");
        verify(gui).drawText(eq(10), eq(10), contains("HP: [████████████████████] 100/100"), eq("#000000"));
    }

    @Test
    void testDrawBlockFillingCalculations(){
        when(stats.getHealth()).thenReturn(25);
        when(stats.getMaxHealth()).thenReturn(100);

        viewer.draw(gui, stats, 10, 10, "#000000");
        verify(gui).drawText(eq(10), eq(10), eq("HP: [█████░░░░░░░░░░░░░░░] 25/100"), eq("#000000"));
    }

    @Test
    void testDrawSendsToGUI(){
        when(stats.getHealth()).thenReturn(25);
        when(stats.getMaxHealth()).thenReturn(100);

        viewer.draw(gui, stats, 10, 10, "#000000");
        verify(gui, times(1)).drawText(eq(10), eq(10), anyString(), eq("#000000"));
    }

    @Test
    void testDrawCustomBarLength(){
        when(stats.getHealth()).thenReturn(125);
        when(stats.getMaxHealth()).thenReturn(125);
        viewer.draw(gui, stats, 10, 10, "#000000", 25);
        verify(gui).drawText(eq(10), eq(10), eq("HP: [█████████████████████████] 125/125"), eq("#000000"));
    }
}
