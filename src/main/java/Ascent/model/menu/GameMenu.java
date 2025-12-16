package Ascent.model.menu;

import java.util.Arrays;

public class GameMenu extends Menu {
    public GameMenu() {
        super();
        entries.addAll(Arrays.asList("RESUME"/* , "SETTINGS" */, "MAIN MENU"));
    }

}
