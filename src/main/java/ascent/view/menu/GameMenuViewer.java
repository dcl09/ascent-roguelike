package ascent.view.menu;

import ascent.model.menu.GameMenu;

import java.io.IOException;

public class GameMenuViewer extends MenuViewer<GameMenu> {
    public GameMenuViewer(GameMenu gameMenu) throws IOException {
        super(gameMenu, "ascii-title-text-art.txt");
    }
}
