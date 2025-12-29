package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.entities.components.LOOKING;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;
import ascent.model.items.armour.ArmourSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {
    @Mock
    Floor floor;
    @Mock
    Player player;
    @Mock
    Game game;

    @InjectMocks
    PlayerController controller;

    @BeforeEach
    void setUp() {
        when(floor.getPlayer()).thenReturn(player);
    }

    @ParameterizedTest
    @MethodSource("movementDirections")
    void playerMovementBlockedWhenCooldownActive(ACTION action, LOOKING looking) {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(looking)).thenReturn(new Position(1, 1));

        controller.step(game, action, 1000L);
        controller.step(game, action, 1050L);

        verify(floor, times(1)).movePlayer(new Position(1, 1), 1000L);
        verify(floor, never()).movePlayer(any(), eq(1050L));
    }

    @ParameterizedTest
    @MethodSource("movementDirections")
    void playerMovementAllowedWhenCooldownInactive(ACTION action, LOOKING looking) {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(looking)).thenReturn(new Position(1, 1));

        controller.step(game, action, 1000L);
        controller.step(game, action, 1100L);

        verify(floor, times(1)).movePlayer(new Position(1, 1), 1000L);
        verify(floor, times(1)).movePlayer(new Position(1, 1), 1100L);
    }

    @ParameterizedTest
    @MethodSource("movementDirections")
    void playerMovementBlockedOnEdgeOfCooldown(ACTION action, LOOKING looking) {
        when(player.getMovementSpeed()).thenReturn(3);
        when(player.moveToward(looking)).thenReturn(new Position(1, 1));

        controller.step(game, action, 1000L);
        controller.step(game, action, 1099L);

        verify(floor, times(1)).movePlayer(new Position(1, 1), 1000L);
        verify(floor, never()).movePlayer(any(), eq(1099L));
    }

    static Stream<Arguments> movementDirections() {
        return Stream.of(
                Arguments.of(ACTION.UP, LOOKING.UP),
                Arguments.of(ACTION.DOWN, LOOKING.DOWN),
                Arguments.of(ACTION.LEFT, LOOKING.LEFT),
                Arguments.of(ACTION.RIGHT, LOOKING.RIGHT));
    }

    @ParameterizedTest
    @MethodSource("lookDirections")
    void playerLooksCorrectly(LOOKING looking, ACTION action) {
        controller.step(game, action, 1000L);
        verify(player, times(1)).setLookingDirection(looking);
        verify(floor, never()).movePlayer(any(), anyLong());
    }

    static Stream<Arguments> lookDirections() {
        return Stream.of(
                Arguments.of(LOOKING.UP, ACTION.LOOK_UP),
                Arguments.of(LOOKING.DOWN, ACTION.LOOK_DOWN),
                Arguments.of(LOOKING.LEFT, ACTION.LOOK_LEFT),
                Arguments.of(LOOKING.RIGHT, ACTION.LOOK_RIGHT));
    }

    @ParameterizedTest
    @MethodSource("potionActions")
    void usingPotionExecutesCorrectly(int potionIndex) {
        ACTION action = ACTION.valueOf("USE_POTION_" + potionIndex);
        controller.step(game, action, 1000L);
        verify(player, times(1)).consumeItem(potionIndex);
        verify(floor, never()).movePlayer(any(), anyLong());
        verify(player, never()).getMovementSpeed();
    }

    static IntStream potionActions() {
        return IntStream.range(0, 5);
    }

    @ParameterizedTest
    @MethodSource("armourSlots")
    void unequippingArmourExecutesCorrectly(ArmourSlot slot, ACTION action) {
        controller.step(game, action, 1000L);
        verify(player, times(1)).unequipArmour(slot);
        verify(floor, never()).movePlayer(any(), anyLong());
        verify(player, never()).getMovementSpeed();
    }

    static Stream<Arguments> armourSlots() {
        return Stream.of(
                Arguments.of(ArmourSlot.HEAD, ACTION.UNEQUIP_HEAD),
                Arguments.of(ArmourSlot.CHEST, ACTION.UNEQUIP_CHEST),
                Arguments.of(ArmourSlot.ARMS, ACTION.UNEQUIP_ARMS),
                Arguments.of(ArmourSlot.LEGS, ACTION.UNEQUIP_LEGS),
                Arguments.of(ArmourSlot.FEET, ACTION.UNEQUIP_FEET));
    }

    @Test
    void unequippingWeaponExecutesCorrectly() {
        controller.step(game, ACTION.UNEQUIP_WEAPON, 1000L);
        verify(player, times(1)).equipWeapon(null);
        verify(floor, never()).movePlayer(any(), anyLong());
        verify(player, never()).getMovementSpeed();
    }
}