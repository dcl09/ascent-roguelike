package Ascent.model.entities;

import Ascent.model.entities.interfaces.Interactor;
import Ascent.model.game.Position;
import Ascent.model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChestTest {

    private Chest chest;
    private Player player;

    @BeforeEach
    void setUp() {
        chest = new Chest(new Position(5, 5), "YELLOW");
        player = new Player(new Position(4, 5));
    }

    @Test
    void chestStartsClosed() {
        assertEquals('C', chest.getSymbol());
        assertFalse(chest.isOpened());
    }

    @Test
    void chestHasCorrectPosition() {
        assertEquals(new Position(5, 5), chest.getPosition());
    }

    @Test
    void chestHasCorrectColor() {
        assertEquals("YELLOW", chest.getColor());
    }

    @Test
    void chestContainsItem() {
        assertNotNull(chest.getContainedItem());
    }

    @Test
    void canInteractWhenClosed() {
        assertTrue(chest.canInteract());
    }

    @Test
    void cannotInteractWhenOpened() {
        chest.interact(player);
        assertFalse(chest.canInteract());
    }

    @Test
    void interactOpensChest() {
        chest.interact(player);
        assertTrue(chest.isOpened());
        assertEquals('O', chest.getSymbol());
    }

    @Test
    void interactDoesNothingWhenAlreadyOpen() {
        chest.interact(player);
        Item itemBefore = chest.getContainedItem();

        chest.interact(player);
        assertTrue(chest.isOpened());
        assertEquals(itemBefore, chest.getContainedItem());
    }

    @Test
    void interactOnlyWorksWithPlayer() {
        // Simple Stub: it represents an Interactor which is not a Player
        Interactor nonPlayer = new Interactor() {
            @Override public boolean canInteract() { return true; }
            @Override public void interactWith(Ascent.model.entities.interfaces.Interactable t) {}
        };

        chest.interact(nonPlayer);

        // Chest should not open with Interactor that is not a Player
        assertFalse(chest.isOpened());
    }

    @Test
    void chestWithValidItemIdContainsCorrectItem() {
        Chest chestWithSword = new Chest(new Position(5, 5), "YELLOW", 11);
        assertEquals("Sword", chestWithSword.getContainedItem().getName());
    }

    @Test
    void chestWithInvalidItemIdFallsBackToRandomItem() {
        Chest chestWithInvalidId = new Chest(new Position(5, 5), "YELLOW", 999);
        assertNotNull(chestWithInvalidId.getContainedItem());
    }
}