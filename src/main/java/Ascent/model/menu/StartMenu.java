package Ascent.model.menu;

import java.util.Arrays;

public class StartMenu extends Menu {

    public StartMenu() {
        super();
        entries.addAll(Arrays.asList("START"/* , "SETTINGS" */, "EXIT"));
    }
}
