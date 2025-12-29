package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Chest;
import ascent.model.entities.Player;
import ascent.model.game.floor.Floor;
import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChestControllerTest {

    @Mock
    Floor floor;
    @Mock
    Player player;
    @Mock
    Chest chest;
    @Mock
    Game game;

    ChestController controller;

    @BeforeEach
    void setUp() {
        controller = new ChestController(floor);
        when(floor.getPlayer()).thenReturn(player);
    }

    @Test
    void ifThereAreNotInteractingChestsDoNothing() {
        when(floor.getInteractingChest()).thenReturn(null);

        controller.step(game, ACTION.INTERACT, 1000L);

        verify(chest, never()).getContainedItem();
        verify(floor, never()).setInteractingChest(any(Chest.class));
    }

    @Nested
    class ApplyItem {

        private HealthRestore healthRestore;
        private Weapon weapon;

        @BeforeEach
        void setUp() {
            when(floor.getInteractingChest()).thenReturn(chest);
        }

        @Test
        void itemNull() {
            when(chest.getContainedItem()).thenReturn(null);

            controller.step(game, ACTION.USE_POTION_0, 1000L);

            verify(chest, never()).takeItem();
        }

        @Test
        void healthRestore() {
            healthRestore = mock(HealthRestore.class);
            when(chest.getContainedItem()).thenReturn(healthRestore);

            controller.step(game, ACTION.USE_POTION_0, 1000L);

            verify(healthRestore).consume(player);
            verify(chest).takeItem();
            verify(floor).setInteractingChest(null);
        }

        @Test
        void notHealthRestore() {
            weapon = mock(Weapon.class);
            when(chest.getContainedItem()).thenReturn(weapon);

            controller.step(game, ACTION.USE_POTION_0, 1000L);

            verify(weapon).use(player);
            verify(chest).takeItem();
            verify(floor).setInteractingChest(null);
        }
    }

    @Nested
    class StoreItem {

        private HealthRestore healthRestore;
        private Weapon weapon;

        @BeforeEach
        void setUp() {
            when(floor.getInteractingChest()).thenReturn(chest);
        }

        @Test
        void addsItemToInventoryWhenItemIsHealthRestoreAndInventoryHasSpace() {
            healthRestore = mock(HealthRestore.class);
            when(chest.getContainedItem()).thenReturn(healthRestore);
            when(player.hasInventorySpace()).thenReturn(true);

            controller.step(game, ACTION.USE_POTION_1, 1000L);

            verify(player).addConsumable(healthRestore);
            verify(chest).takeItem();
            verify(floor).setInteractingChest(null);
        }

        @Test
        void doesNothingWhenItemIsNotHealthRestore() {
            weapon = mock(Weapon.class);
            when(chest.getContainedItem()).thenReturn(weapon);

            controller.step(game, ACTION.USE_POTION_1, 1000L);

            verify(floor, never()).setInteractingChest(null);
        }

        @Test
        void doesNothingWhenInventoryDoesNotHaveSpace() {
            healthRestore = mock(HealthRestore.class);
            when(chest.getContainedItem()).thenReturn(healthRestore);
            when(player.hasInventorySpace()).thenReturn(false);


            controller.step(game, ACTION.USE_POTION_1, 1000L);

            verify(floor, never()).setInteractingChest(null);
        }
    }

    @Test
    void actionIsMenu() {
        when(floor.getInteractingChest()).thenReturn(chest);

        controller.step(game, ACTION.MENU, 1000L);

        verify(floor).setInteractingChest(null);
    }

    @Test
    void actionIsINTERACT() {
        when(floor.getInteractingChest()).thenReturn(chest);

        controller.step(game, ACTION.INTERACT, 1000L);

        verify(floor).setInteractingChest(null);
    }
}
