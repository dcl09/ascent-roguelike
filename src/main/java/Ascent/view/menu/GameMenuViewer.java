package Ascent.view.menu;

import Ascent.model.menu.GameMenu;

import java.io.IOException;

public class GameMenuViewer extends MenuViewer<GameMenu> {
    public GameMenuViewer(GameMenu gameMenu) throws IOException {
        super(gameMenu, "ascii-title-text-art.txt");
    }
}
