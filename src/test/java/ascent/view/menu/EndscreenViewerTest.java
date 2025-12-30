package ascent.view.menu;

import ascent.gui.GUI;
import ascent.model.menu.Endscreen;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EndscreenViewerTest {

    @Test
    public void testInitializationWinScreen() throws IOException {
        EndscreenViewer viewer = new EndscreenViewer(new Endscreen(true));
        GUI gui = mock(GUI.class);
        viewer.drawEntities(gui);
        List<String> header = Files.readAllLines(Path.of("resources/winscreen-text.txt"));
        int y = 13;
        int titleX = 31;
        for (int i = 0; i < header.size(); i++) {
            String line = header.get(i);
            verify(gui).drawText(eq(titleX), eq(y + i), eq(line), eq("#FFFFFF"));
        }
    }
    @Test
    public void testDrawEntitiesLoseScreen() throws IOException {

        EndscreenViewer viewer = new EndscreenViewer(new Endscreen(false));
        GUI gui = mock(GUI.class);
        viewer.drawEntities(gui);
        List<String> header = Files.readAllLines(Path.of("resources/losescreen-text.txt"));
        int y = 13;
        int titleX = 31;
        for (int i = 0; i < header.size(); i++) {
            String line = header.get(i);
            verify(gui).drawText(eq(titleX), eq(y + i), eq(line), eq("#FFFFFF"));
        }
    }
}
