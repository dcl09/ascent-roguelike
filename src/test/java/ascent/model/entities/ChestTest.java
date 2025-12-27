package ascent.model.entities;

import ascent.model.entities.interfaces.Interactor;
import ascent.model.game.Position;
import ascent.model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

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
        Interactor nonPlayer = mock(Interactor.class);
        when(nonPlayer.canInteract()).thenReturn(true);
        chest.interact(nonPlayer);
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

    @Test
    void chestWithItemTakenDoesNotHaveItem(){
        Chest chestWithSword = new Chest(new Position(5, 5), "YELLOW", 11);
        Interactor player = mock(Player.class);
        when(player.canInteract()).thenReturn(true);
        chestWithSword.interact(player);
        chestWithSword.takeItem();
        assertNull(chestWithSword.getContainedItem());
    }

    @Test
    void chestClosedCanNotHaveItemTaken(){
        Exception exception = assertThrows(TakeItemWhenChestClosedException.class, () -> {
            chest.takeItem();
        });
        assertEquals("Tried to take item from chest when closed", exception.getMessage());
    }

    @Test
    void chestOpenedWithoutItemHasMessageWithEmpty(){
        Chest chestWithSword = new Chest(new Position(5, 5), "YELLOW", 11);
        Interactor player = mock(Player.class);
        when(player.canInteract()).thenReturn(true);
        chestWithSword.interact(player);
        chestWithSword.takeItem();
        assertEquals("Chest is empty", chestWithSword.getInteractionMessage());
    }

    @Test
    void chestClosedHasMessageWithInteract(){
        Chest chestWithSword = new Chest(new Position(5, 5), "YELLOW", 11);
        assertEquals("Press E to interact", chestWithSword.getInteractionMessage());
    }

    @Test
    void chestOpenedWithItemHasMessageWithFound(){
        Chest chestWithSword = new Chest(new Position(5, 5), "YELLOW", 11);
        Interactor player = mock(Player.class);
        when(player.canInteract()).thenReturn(true);
        chestWithSword.interact(player);
        assertEquals("Found: Sword", chestWithSword.getInteractionMessage());
    }
}