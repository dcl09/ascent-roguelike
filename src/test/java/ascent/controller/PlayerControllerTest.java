package ascent.controller;

import ascent.Game;
import ascent.controller.game.PlayerController;
import ascent.gui.ACTION;
import ascent.gui.GUI;
import ascent.model.entities.*;
import ascent.model.entities.components.LOOKING;
import ascent.model.entities.monster.Monster;
import ascent.model.game.Position;
import ascent.model.game.floor.FileLevelFactory;
import ascent.model.game.floor.Floor;
import ascent.model.items.armour.ArmourSlot;
import ascent.model.menu.Endscreen;
import ascent.model.menu.GameMenu;
import ascent.state.EndscreenState;
import ascent.state.GameMenuState;
import ascent.state.GameState;
import ascent.state.State;
import ascent.view.Viewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyLong;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {
    @Mock Floor floor;
    @Mock Player player;
    @Mock Game game;

    @InjectMocks
    PlayerController controller;

    @BeforeEach
    void setUp() {
        when(floor.getPlayer()).thenReturn(player);
    }

    @Test
    void playerMovementBlockedWhenCooldownActive() {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(LOOKING.UP)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UP, 1000);
        controller.step(game, ACTION.UP, 1050);

        verify(floor, times(1)).movePlayer(any(Position.class), eq(1000L));
    }

    @Test
    void playerMovementAllowedWhenCooldownInactive() {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(LOOKING.UP)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UP, 1000L);
        controller.step(game, ACTION.UP, 1101L);

        verify(floor, times(1)).movePlayer(any(Position.class), eq(1000L));
        verify(floor, times(1)).movePlayer(any(Position.class), eq(1101L));
    }

    @Test
    void playerLooksUpCorrectly() {
        controller.step(game, ACTION.LOOK_UP, 1000L);
        verify(player, times(1)).setLookingDirection(LOOKING.UP);
    }

    @Test
    void playerLooksDownCorrectly() {
        controller.step(game, ACTION.LOOK_DOWN, 1000L);
        verify(player, times(1)).setLookingDirection(LOOKING.DOWN);
    }

    @Test
    void playerLooksLeftCorrectly() {
        controller.step(game, ACTION.LOOK_LEFT, 1000L);
        verify(player, times(1)).setLookingDirection(LOOKING.LEFT);
    }

    @Test
    void playerLooksRightCorrectly() {
        controller.step(game, ACTION.LOOK_RIGHT, 1000L);
        verify(player, times(1)).setLookingDirection(LOOKING.RIGHT);
    }

    @Test
    void playerMovesUpCorrectly() {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(LOOKING.UP)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UP, 1000L);

        verify(floor, times(1)).movePlayer(new Position(1,1), eq(1000L));
    }

    @Test
    void playerMovesDownCorrectly() {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(LOOKING.DOWN)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.DOWN, 1000L);

        verify(floor, times(1)).movePlayer(new Position(1,1), eq(1000L));
    }

    @Test
    void playerMovesLeftCorrectly() {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(LOOKING.LEFT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.LEFT, 1000L);

        verify(floor, times(1)).movePlayer(new Position(1,1), eq(1000L));
    }

    @Test
    void playerMovesRightCorrectly() {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.RIGHT, 1000L);

        verify(floor, times(1)).movePlayer(new Position(1,1), eq(1000L));
    }

    @Test
    void usingPotionBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.USE_POTION_0, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).consumeItem(0);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }

    @Test
    void unequippingWeaponBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UNEQUIP_WEAPON, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).equipWeapon(null);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }

    @Test
    void unequippingHeadBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UNEQUIP_HEAD, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).unequipArmour(ArmourSlot.HEAD);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }

    @Test
    void unequippingChestBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UNEQUIP_CHEST, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).unequipArmour(ArmourSlot.CHEST);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }

    @Test
    void unequippingArmsBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UNEQUIP_ARMS, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).unequipArmour(ArmourSlot.ARMS);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }

    @Test
    void unequippingLegsBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UNEQUIP_LEGS, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).unequipArmour(ArmourSlot.LEGS);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }

    @Test
    void unequippingFeetBlocksMovement() {
        when(player.moveToward(LOOKING.RIGHT)).thenReturn(new Position(1,1));

        controller.step(game, ACTION.UNEQUIP_FEET, 1000L);
        controller.step(game, ACTION.RIGHT, 1600L);
        verify(player, times(1)).unequipArmour(ArmourSlot.FEET);
        verify(floor, never()).movePlayer(any(Position.class), eq(1600L));
    }
    
}
