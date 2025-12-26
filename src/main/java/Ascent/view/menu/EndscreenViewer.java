package Ascent.view.menu;

import Ascent.model.menu.Endscreen;

import java.io.IOException;

public class EndscreenViewer extends MenuViewer<Endscreen> {
    public EndscreenViewer(Endscreen endscreen) throws IOException {
        super(endscreen, (endscreen.isWin()) ? "winscreen-text.txt" : "losescreen-text.txt");
    }
}
