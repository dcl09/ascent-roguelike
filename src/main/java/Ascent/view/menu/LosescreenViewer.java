package Ascent.view.menu;

import Ascent.model.menu.Losescreen;

import java.io.IOException;

public class LosescreenViewer extends MenuViewer<Losescreen> {
    public LosescreenViewer(Losescreen losescreen) throws IOException {
        super(losescreen, "losescreen-text.txt");
    }
}
