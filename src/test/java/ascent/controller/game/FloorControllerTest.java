package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Chest;
import ascent.model.entities.Player;
import ascent.model.entities.components.Stats;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;
import ascent.model.menu.Endscreen;
import ascent.model.menu.GameMenu;
import ascent.state.EndscreenState;
import ascent.state.GameMenuState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FloorControllerTest {

    private FloorController floorController;
    private Floor floor;
    private Game game;
    private Player player;
    private Stats stats;

    // Sub-controllers mocks
    private PlayerController playerController;
    private MonsterController monsterController;
    private ChestController chestController;
    private DoorController doorController;
    private StairsController stairsController;

    @BeforeEach
    void setUp() {
        floor = mock(Floor.class);
        game = mock(Game.class);
        player = mock(Player.class);
        stats = mock(Stats.class);

        // Sub-controllers setup (always needed for constructor)
        playerController = mock(PlayerController.class);
        monsterController = mock(MonsterController.class);
        chestController = mock(ChestController.class);
        doorController = mock(DoorController.class);
        stairsController = mock(StairsController.class);

        floorController = new FloorController(floor, playerController, monsterController, chestController, doorController, stairsController);
    }

    private void configurePlayerAlive() {
        when(floor.getPlayer()).thenReturn(player);
        when(player.getStats()).thenReturn(stats);
        when(stats.isDead()).thenReturn(false);
    }

    @Test
    void stepQuitActionPopsState() throws IOException {
        floorController.step(game, ACTION.QUIT, 100);

        verify(game).popState();
        verify(floor, never()).getInteractingChest();
    }

    @Test
    void stepMenuActionPushesGameMenuState() throws IOException {
        floorController.step(game, ACTION.MENU, 100);

        ArgumentCaptor<GameMenuState> captor = ArgumentCaptor.forClass(GameMenuState.class);
        verify(game).pushState(captor.capture());
        
        assertNotNull(captor.getValue());
        verify(floor, never()).getInteractingChest();
    }

    @Test
    void stepPlayerIsDeadPushesEndscreen() throws IOException {
        when(floor.getPlayer()).thenReturn(player);
        when(player.getStats()).thenReturn(stats);
        when(stats.isDead()).thenReturn(true);

        floorController.step(game, ACTION.NONE, 100);

        verify(game).popState();
        ArgumentCaptor<EndscreenState> captor = ArgumentCaptor.forClass(EndscreenState.class);
        verify(game).pushState(captor.capture());
        
        verify(floor, never()).getInteractingChest();
    }

    @Test
    void stepWithActiveChestInteractsWithChestOnly() throws IOException {
        configurePlayerAlive();
        
        Chest activeChest = mock(Chest.class);
        when(floor.getInteractingChest()).thenReturn(activeChest);

        floorController.step(game, ACTION.INTERACT, 100);

        verify(chestController).step(game, ACTION.INTERACT, 100);
        verify(monsterController).step(game, ACTION.INTERACT, 100);
        
        verify(playerController, never()).step(any(), any(), anyLong());
    }

    @Test
    void stepNormalFlowExecutesControllers() throws IOException {
        configurePlayerAlive();
        
        when(floor.getInteractingChest()).thenReturn(null);

        floorController.step(game, ACTION.UP, 100);

        verify(playerController).step(game, ACTION.UP, 100);
        verify(monsterController).step(game, ACTION.UP, 100);
        verify(chestController, never()).step(any(), any(), anyLong());
    }

    @Nested
    class InteractActionTests {

        @BeforeEach
        void setUpInteract() {
            configurePlayerAlive();
            
            when(floor.getInteractingChest()).thenReturn(null);
            when(player.canInteract()).thenReturn(true);
            when(player.facing()).thenReturn(new Position(5, 5));
        }

        @Test
        void interactWithDoor() throws IOException {
            when(floor.isDoor(any(Position.class))).thenReturn(true);

            floorController.step(game, ACTION.INTERACT, 100);

            verify(doorController).step(game, ACTION.INTERACT, 100);
            verify(playerController).step(game, ACTION.INTERACT, 100);
        }

        @Test
        void interactWithStairs() throws IOException {
            when(floor.isStairs(any(Position.class))).thenReturn(true);
            when(floor.isDoor(any(Position.class))).thenReturn(false);
            when(floor.isChest(any(Position.class))).thenReturn(false);

            floorController.step(game, ACTION.INTERACT, 100);

            verify(stairsController).step(game, ACTION.INTERACT, 100);
        }

        @Test
        void interactWithClosedChest() throws IOException {
            when(floor.isChest(any(Position.class))).thenReturn(true);
            when(floor.isDoor(any())).thenReturn(false);
            
            Chest chest = mock(Chest.class);
            when(chest.isOpened()).thenReturn(false);
            when(floor.getChestAt(any(Position.class))).thenReturn(chest);

            floorController.step(game, ACTION.INTERACT, 100);

            verify(chest).interact(player);
            verify(floor).setInteractingChest(chest);
        }

        @Test
        void interactWithAlreadyOpenChest() throws IOException {
            when(floor.isChest(any(Position.class))).thenReturn(true);
            when(floor.isDoor(any())).thenReturn(false);

            Chest chest = mock(Chest.class);
            when(chest.isOpened()).thenReturn(true);
            when(floor.getChestAt(any(Position.class))).thenReturn(chest);

            floorController.step(game, ACTION.INTERACT, 100);

            verify(chest, never()).interact(player);
            verify(floor).setInteractingChest(chest);
        }
        
        @Test
        void interactActionButPlayerCannotInteract() throws IOException {
            when(player.canInteract()).thenReturn(false);
            
            floorController.step(game, ACTION.INTERACT, 100);
            
            verify(doorController, never()).step(any(), any(), anyLong());
            verify(stairsController, never()).step(any(), any(), anyLong());
            verify(floor, never()).getChestAt(any());
        }
    }
}
