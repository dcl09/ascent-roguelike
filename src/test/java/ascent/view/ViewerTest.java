package ascent.view;

import ascent.gui.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ViewerTest {
    private Viewer<String> viewer;
    private GUI gui;

    @BeforeEach
    void setUp() {
        viewer = new Viewer<>("test"){
            @Override
            protected void drawEntities(GUI gui) {
                gui.drawText(10, 10, "test", "#FFFFFF");
            }
        };
        gui = mock(GUI.class);
    }

    @Test
    void getModelReturnsCorrectModel() {
        assertEquals("test", viewer.getModel());
    }

    @Test
    void drawCallsAllFunction() throws IOException {
        viewer.draw(gui);
        verify(gui).clear();
        verify(gui).drawText(eq(10), eq(10), eq("test"), eq("#FFFFFF"));
        verify(gui).refresh();
    }
}
