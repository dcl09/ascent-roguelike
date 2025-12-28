package ascent.model.menu;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMenuTest {
    @Test
    void gameMenuCheckIfEntriesAreCorrect(){
        GameMenu gameMenu = new GameMenu(true);
        assertEquals(gameMenu.entries, Arrays.asList("RESUME", "MAIN MENU"));
        gameMenu = new GameMenu(false);
        assertEquals(gameMenu.entries, Arrays.asList("START", "EXIT"));
    }
}
