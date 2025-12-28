package ascent.model.menu;

import java.util.Arrays;

public class GameMenu extends Menu {
    public GameMenu(boolean isGameRunning) {
        super();
        if (isGameRunning)
            entries.addAll(Arrays.asList("RESUME"/* , "SETTINGS" */, "MAIN MENU"));
        else
            entries.addAll(Arrays.asList("START"/* , "SETTINGS" */, "EXIT"));
    }

}
