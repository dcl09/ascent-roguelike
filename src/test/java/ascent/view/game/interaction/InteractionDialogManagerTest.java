package ascent.view.game.interaction;

import ascent.gui.GUI;
import ascent.model.entities.Chest;
import ascent.model.entities.Door;
import ascent.model.entities.Player;
import ascent.model.entities.Stairs;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class InteractionDialogManagerTest {
    private InteractionDialogManager manager;
    private GUI gui;
    private Floor floor;
    private Player player;

    @BeforeEach
    void setUp() {
        manager = new InteractionDialogManager();
        gui = mock(GUI.class);
        floor = mock(Floor.class);
        player = mock(Player.class);

        when(floor.getPlayer()).thenReturn(player);
        when(player.getPosition()).thenReturn(new Position(0, 0));
        when(player.facing()).thenReturn(new Position(0, 1));
    }
    @Test
    void testChestInteractionDialog() {
        Chest chest = mock(Chest.class);
        when(floor.getInteractingChest()).thenReturn(chest);
        when(chest.isOpened()).thenReturn(false);
        when(chest.getInteractionMessage()).thenReturn("interact");

        manager.draw(gui, floor, 10, 10);

        verify(gui).drawText(eq(10), eq(10), contains("Chest"), anyString());
        verify(gui).drawText(eq(10), eq(11), eq("interact"), anyString());
        verify(floor, never()).getChestAt(any());
    }

    @Test
    void testFacingChestInteractionDialog() {
        when(floor.getInteractingChest()).thenReturn(null);

        Chest chest = mock(Chest.class);
        when(floor.getChestAt(any(Position.class))).thenReturn(chest);
        when(chest.getInteractionMessage()).thenReturn("openchest");

        manager.draw(gui, floor, 10, 10);

        verify(gui).drawText(eq(10), eq(10), contains("Chest"), anyString());
        verify(gui).drawText(eq(10), eq(12), eq("openchest"), eq("#FFFFFF"));
        verify(floor, never()).getDoorAt(any());
    }

    @Test
    void testFacingDoorInteractionDialog() {
        when(floor.getInteractingChest()).thenReturn(null);
        when(floor.getChestAt(any())).thenReturn(null);

        Door door = mock(Door.class);
        when(floor.getDoorAt(any(Position.class))).thenReturn(door);
        when(door.getInteractionMessage()).thenReturn("doorlocked");

        manager.draw(gui, floor, 10, 10);

        verify(gui).drawText(eq(10), eq(10), contains("Door"), anyString());
        verify(gui, atLeastOnce()).drawText(anyInt(), eq(12), eq("doorlocked"), eq("#FFFFFF"));
    }

    @Test
    void testDrawFacingStairs_Priority4() {
        when(floor.getInteractingChest()).thenReturn(null);
        when(floor.getChestAt(any())).thenReturn(null);
        when(floor.getDoorAt(any())).thenReturn(null);

        Stairs stairs = mock(Stairs.class);
        Position position = new Position(0, 1);
        when(stairs.getPosition()).thenReturn(position);
        when(floor.getStairs()).thenReturn(stairs);
        when(stairs.getInteractionMessage()).thenReturn("interact");

        manager.draw(gui, floor, 10, 10);

        verify(gui).drawText(eq(10), eq(10), contains("Stairs"), anyString());
        verify(gui, atLeastOnce()).drawText(anyInt(), eq(10 + 2), eq("interact"), eq("#FFFFFF"));
    }


    @Test
    void testNoInteractionDialog() {
        when(floor.getInteractingChest()).thenReturn(null);
        when(floor.getChestAt(any())).thenReturn(null);
        when(floor.getDoorAt(any())).thenReturn(null);

        Stairs stairs = mock(Stairs.class);
        Position stairsPos = new Position(5, 5);
        when(stairs.getPosition()).thenReturn(stairsPos);
        when(floor.getStairs()).thenReturn(stairs);

        manager.draw(gui, floor, 10, 10);

        verifyNoInteractions(gui);
    }
}
