package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.*;
import ascent.model.entities.components.Inventory;
import ascent.model.entities.components.Stats;
import ascent.model.entities.monster.Monster;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FloorViewerTest {
    private FloorViewer floorViewer;
    private Floor floor;
    private GUI gui;
    private Player player;

    @BeforeEach
    void setUp() {
        floor = mock(Floor.class);
        gui = mock(GUI.class);
        player = mock(Player.class);

        when(player.getStats()).thenReturn(mock(Stats.class));
        when(player.getInventory()).thenReturn(mock(Inventory.class));
        when(floor.getPlayer()).thenReturn(player);

        floorViewer = new FloorViewer(floor);
    }

    @Test
    void testDrawEntities() {
        when(floor.getWidth()).thenReturn(20);
        when(floor.getCurrLevel()).thenReturn(1);

        Wall wall = mock(Wall.class);
        when(wall.getPosition()).thenReturn(new Position(1, 1));
        when(wall.getSymbol()).thenReturn('#');
        when(wall.getColor()).thenReturn("#333333");
        when(floor.getWalls()).thenReturn(List.of(wall));

        Door door = mock(Door.class);
        when(door.getPosition()).thenReturn(new Position(2, 2));
        when(door.getSymbol()).thenReturn('+');
        when(door.getColor()).thenReturn("#444444");
        when(floor.getDoors()).thenReturn(List.of(door));

        Stairs stairs = mock(Stairs.class);
        when(stairs.getPosition()).thenReturn(new Position(3, 3));
        when(stairs.getSymbol()).thenReturn('>');
        when(stairs.getColor()).thenReturn("#555555");
        when(floor.getStairs()).thenReturn(stairs);

        Chest chest = mock(Chest.class);
        when(chest.getPosition()).thenReturn(new Position(4, 4));
        when(chest.getSymbol()).thenReturn('C');
        when(chest.getColor()).thenReturn("#666666");
        when(floor.getChests()).thenReturn(List.of(chest));

        Monster monster = mock(Monster.class);
        when(monster.getPosition()).thenReturn(new Position(5, 5));
        when(monster.getSymbol()).thenReturn('M');
        when(monster.getColor()).thenReturn("#777777");
        when(floor.getMonsters()).thenReturn(List.of(monster));

        when(player.getPosition()).thenReturn(new Position(6, 6));
        when(player.getSymbol()).thenReturn('@');
        when(player.getColor()).thenReturn("#888888");

        floorViewer.drawEntities(gui);

        verify(gui, times(1)).drawChar(eq(1), eq(1), eq('#'), anyString());
        verify(gui, times(1)).drawChar(eq(2), eq(2), eq('+'), anyString());
        verify(gui, times(1)).drawChar(eq(3), eq(3), eq('>'), anyString());
        verify(gui, times(1)).drawChar(eq(4), eq(4), eq('C'), anyString());
        verify(gui, times(1)).drawChar(eq(5), eq(5), eq('M'), anyString());
        verify(gui, times(1)).drawChar(eq(6), eq(6), eq('@'), anyString());
        verify(gui).drawText(eq(26), eq(0), contains("FLOOR"), anyString());
        verify(gui).drawText(eq(26), eq(2), contains("PLAYER"), anyString());
    }
}

