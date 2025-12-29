package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Chest;
import ascent.model.entities.Player;
import ascent.model.entities.components.Inventory;
import ascent.model.entities.components.Stats;
import ascent.model.entities.monster.Monster;
import ascent.model.game.floor.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class GameHUDViewerTest {

    private GameHUDViewer gameHUDViewer;
    private GUI gui;
    private Floor floor;
    private Player player;
    private Stats playerStats;

    @BeforeEach
    void setUp() {
        gameHUDViewer = new GameHUDViewer();

        gui = Mockito.mock(GUI.class);
        floor = Mockito.mock(Floor.class);
        player = Mockito.mock(Player.class);
        playerStats = Mockito.mock(Stats.class);

        when(floor.getPlayer()).thenReturn(player);
        when(player.getStats()).thenReturn(playerStats);
        when(player.getInventory()).thenReturn(Mockito.mock(Inventory.class));

        when(floor.getCurrLevel()).thenReturn(1);
    }

    @Test
    void testDrawSideEffects_FullHUD() {
        Monster monster = mock(Monster.class);
        Stats monsterStats = mock(Stats.class);
        when(monsterStats.isDead()).thenReturn(false);
        when(monster.getStats()).thenReturn(monsterStats);
        when(floor.getLastAttackedMonster()).thenReturn(monster);

        when(playerStats.isDead()).thenReturn(false);
        Chest chest = mock(Chest.class);
        when(floor.getInteractingChest()).thenReturn(chest);

        gameHUDViewer.draw(gui, floor, 20);

        verify(gui).drawText(eq(20), eq(0), contains("FLOOR 1"), anyString());
        verify(gui).drawText(eq(20), eq(2), contains("PLAYER"), anyString());

        verify(gui).drawText(eq(20), eq(9), contains("INVENTORY"), anyString());

        verify(gui).drawText(eq(20), eq(29), contains("Chest"), anyString());

        verify(gui).drawText(eq(20), eq(38), contains("MONSTER"), anyString());
    }

    @Test
    void testDrawSideEffects_NoMonster() {
        when(floor.getLastAttackedMonster()).thenReturn(null);

        gameHUDViewer.draw(gui, floor, 20);

        verify(gui).drawText(eq(20), eq(0), contains("FLOOR 1"), anyString());

        verify(gui, never()).drawText(eq(20), eq(38), contains("MONSTER"), anyString());
    }
}