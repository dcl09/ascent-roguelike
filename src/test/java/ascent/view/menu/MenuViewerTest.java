package ascent.view.menu;

import ascent.gui.GUI;
import ascent.model.menu.Menu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class MenuViewerTest {
    private MenuViewer<Menu> viewer;
    private GUI gui;
    private Menu menu;
    private final String TEST_FILE = "test-title-text.txt";
    private final Path TEST_PATH = Path.of("resources/" + TEST_FILE);

    @BeforeEach
    public void setUp() throws IOException {
        if (!Files.exists(Path.of("resources"))) {
            Files.createDirectory(Path.of("resources"));
        }
        Files.write(TEST_PATH, Arrays.asList("Line1", "Line2"));
        gui = mock(GUI.class);
        menu = mock(Menu.class);
        viewer = new MenuViewer<>(menu, TEST_FILE) {};
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(TEST_PATH);
    }

    @Test
    void testIfDrawEntitiesDrawsCorrectly() {
        when(menu.getNumberEntries()).thenReturn(3);
        when(menu.getEntry(0)).thenReturn("A");
        when(menu.getEntry(1)).thenReturn("B");
        when(menu.getEntry(2)).thenReturn("C");
        when(menu.isSelected(0)).thenReturn(false);
        when(menu.isSelected(1)).thenReturn(true);
        when(menu.isSelected(2)).thenReturn(false);
        when(menu.getEntry(3)).thenThrow(new IndexOutOfBoundsException("Boundary Breached"));

        viewer.drawEntities(gui);

        verify(gui).drawText(eq(31), eq(13), eq("Line1"), eq("#FFFFFF"));

        verify(gui).drawText(eq(31), eq(14), eq("Line2"), eq("#FFFFFF"));

        verify(gui).drawText(eq(50), eq(18), eq("A"), eq("#FFFFFF"));

        verify(gui).drawText(eq(50), eq(20), eq("B"), eq("#a67c00"));

        verify(gui).drawText(eq(50), eq(22), eq("C"), eq("#FFFFFF"));

        verify(gui, never()).drawText(anyInt(), eq(24), anyString(), anyString());
    }

    @Test
    void testEmptyMenu() {
        when(menu.getNumberEntries()).thenReturn(0);

        viewer.drawEntities(gui);

        verify(gui).drawText(eq(31), eq(13), eq("Line1"), anyString());

        verify(gui, never()).drawText(eq(50), anyInt(), anyString(), anyString());
    }
}


