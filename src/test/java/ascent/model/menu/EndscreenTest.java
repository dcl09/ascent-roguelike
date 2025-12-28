package ascent.model.menu;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EndscreenTest {
    @Test
    void checkifEntriesAreCorrect() {
        Endscreen endscreen = new Endscreen(true);
        assertEquals(endscreen.entries, Arrays.asList("RESTART", "TO MAIN MENU"));
    }

    @Test
    void isWinReturnsCorrectBoolean(){
        Endscreen endscreen = new Endscreen(true);
        assertTrue(endscreen.isWin());
        endscreen = new Endscreen(false);
        assertFalse(endscreen.isWin());
    }
}
