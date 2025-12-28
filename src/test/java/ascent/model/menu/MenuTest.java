package ascent.model.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu(Arrays.asList("a", "b", "c")){};
        menu.currEntry = 0;
    }

    @Test
    void cycleForward() {
        menu.nextEntry();
        assertEquals(1, menu.currEntry);
        assertEquals("b", menu.getCurrEntry());
        menu.nextEntry();
        assertEquals(2, menu.currEntry);
        assertEquals("c", menu.getCurrEntry());
        menu.nextEntry();
        assertEquals(0, menu.currEntry);
        assertEquals("a", menu.getCurrEntry());
    }

    @Test
    void cycleBackward() {
        menu.prevEntry();
        assertEquals(2, menu.currEntry);
        assertEquals("c", menu.getCurrEntry());

        menu.prevEntry();
        assertEquals(1, menu.currEntry);
        assertEquals("b", menu.getCurrEntry());

        menu.prevEntry();
        assertEquals(0, menu.currEntry);
        assertEquals("a", menu.getCurrEntry());
    }

    @Test
    void getCurrEntryReturnsCurrEntry() {
        assertEquals("a", menu.getCurrEntry());
    }

    @Test
    void getNumberEntriesReturnsNumberEntries(){
        assertEquals(3, menu.getNumberEntries());
    }

    @Test
    void isSelectedReturnsCorrectBool(){
        assertTrue(menu.isSelected(0));
        assertFalse(menu.isSelected(1));
        assertFalse(menu.isSelected(2));
        menu.prevEntry();
        assertFalse(menu.isSelected(0));
        assertTrue(menu.isSelected(2));
    }

    @Test
    void getEntryReturnsCorrectEntry(){
        assertEquals("a", menu.getEntry(0));
        assertEquals("b", menu.getEntry(1));
        assertEquals("c", menu.getEntry(2));
    }
}
