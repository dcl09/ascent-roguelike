package ascent.model.menu;

import java.util.Arrays;

public class Endscreen extends Menu{
    boolean win;
    public Endscreen(boolean win) {
        super();
        this.win = win;
        entries.addAll(Arrays.asList("RESTART", "TO MAIN MENU"));
    }

    public boolean isWin() {
        return win;
    }
}
