package Ascent.view.menu;

import Ascent.model.menu.StartMenu;
import Ascent.model.menu.Winscreen;

import java.io.IOException;

public class WinscreenViewer extends MenuViewer<Winscreen> {
    public WinscreenViewer(Winscreen winscreen) throws IOException {
        super(winscreen, "winscreen-text.txt");
    }
}
