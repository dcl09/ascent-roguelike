package Ascent.view.menu;

import Ascent.gui.GUI;
import Ascent.model.menu.Menu;
import Ascent.view.Viewer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class MenuViewer<T extends Menu> extends Viewer<T> {

    private final List<String> titleArt;
    public MenuViewer(T menu) throws IOException {
        super(menu);
        this.titleArt = Files.readAllLines(Path.of("resources/ascii-title-text-art.txt"));
    }

    @Override
    protected void drawEntities(GUI gui) {
        int y = 5;

        for (int i = 0; i < titleArt.size(); i++) {
            String line = titleArt.get(i);
            int x = 5;
            gui.drawText(x, y + i, line, "#FFFFFF");
        }

        drawEntries(gui, y+ titleArt.size() + 3);
    }

    private void drawEntries(GUI gui, int y) {
        for (int i = 0; i < getModel().getNumberEntries(); i++) {
            gui.drawText(
                    5,
                    y + i * 2,
                    getModel().getEntry(i),
                    getModel().isSelected(i) ? "#a67c00" : "#FFFFFF");
        }
    }
}
