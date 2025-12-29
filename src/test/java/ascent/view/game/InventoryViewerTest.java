package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Player;
import ascent.model.entities.components.Inventory;
import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

public class InventoryViewerTest {
    /* There's one line of code in the InventoryViewer class that is impossible to get coverage,
    since it is the default of a switch case of a variable that is initialized in a private function */
    private InventoryViewer viewer;
    private GUI gui;
    private Inventory inventory;
    private Player player;

    private static final String TITLE_COLOR = "#f2a65e";
    private static final String ITEM_COLOR = "#ffe478";
    private static final String EMPTY_COLOR = "#606070";

    @BeforeEach
    void setUp() {
        viewer = new InventoryViewer();
        inventory = mock(Inventory.class);
        gui = mock(GUI.class);
        player = mock(Player.class);
        when(player.getInventory()).thenReturn(inventory);
    }

    @Test
    void nullPlayerImpliesNoInventory() {
        viewer.drawInventory(gui, null, 10, 10);
        verifyNoInteractions(gui);
    }

    @Test
    void testDrawInventoryWithItems() {
        Weapon weapon = mock(Weapon.class);
        when(weapon.getName()).thenReturn("sword");
        when(weapon.getBonusDamage()).thenReturn(5);

        Map<ArmourSlot, Armour> armours = new HashMap<>();

        Armour armour1 = mock(Armour.class);
        when(armour1.getBonusResistanceToDamage()).thenReturn(5);
        when(armour1.getName()).thenReturn("carapace");
        armours.put(ArmourSlot.HEAD, armour1);

        List<HealthRestore> consumables = new ArrayList<>();
        HealthRestore potion1 = mock(HealthRestore.class);
        when(potion1.getName()).thenReturn("Big potion");
        when(potion1.getRestoredHealth()).thenReturn(5);
        consumables.add(potion1);
        HealthRestore potion2 = mock(HealthRestore.class);
        when(potion2.getName()).thenReturn("Small potion");
        when(potion2.getRestoredHealth()).thenReturn(5);
        consumables.add(potion2);


        when(inventory.getEquippedWeapon()).thenReturn(weapon);
        when(inventory.getConsumables()).thenReturn(consumables);
        when(inventory.getEquippedArmour()).thenReturn(armours);

        viewer.drawInventory(gui, player, 10, 10);

        verify(gui).drawText(eq(10), eq(12), contains("INVENTORY"), eq(TITLE_COLOR));

        verify(gui).drawText(eq(10), eq(14), contains("sword"), eq(ITEM_COLOR));
        verify(gui).drawText(eq(10), eq(14), contains("(+5 DMG)"), anyString());

        verify(gui).drawText(eq(10), eq(16), contains("Armour"), eq(TITLE_COLOR));

        verify(gui).drawText(eq(10), eq(18), contains("carapace"), eq(ITEM_COLOR));

        verify(gui).drawText(eq(10), eq(24), contains("Potions"), eq(TITLE_COLOR));

        verify(gui).drawText(eq(10), eq(26), contains("Big potion"), eq(ITEM_COLOR));

        verify(gui).drawText(eq(10), eq(27), contains("Small potion"), eq(ITEM_COLOR));

        verify(gui).drawText(eq(10), eq(28), contains("[3]"), eq(EMPTY_COLOR));
    }

    @Test
    void testDrawEmptyInventory() {
        when(inventory.getEquippedWeapon()).thenReturn(null);
        when(inventory.getEquippedArmour()).thenReturn(Collections.emptyMap());
        when(inventory.getConsumables()).thenReturn(Collections.emptyList());

        viewer.drawInventory(gui, player, 10, 10);

        verify(gui).drawText(eq(10), eq(14), contains("Weapon: -"), eq(EMPTY_COLOR));

        verify(gui).drawText(eq(10), eq(18), contains("HEAD"), eq(EMPTY_COLOR));
        verify(gui).drawText(eq(10), eq(19), contains("CHEST"), eq(EMPTY_COLOR));
        verify(gui).drawText(eq(10), eq(22), contains("FEET"), eq(EMPTY_COLOR));

        verify(gui).drawText(eq(10), eq(26), contains("[1]"), eq(EMPTY_COLOR));
        verify(gui).drawText(eq(10), eq(30), contains("[5]"), eq(EMPTY_COLOR));
        verify(gui, never()).drawText(anyInt(), eq(31), anyString(), anyString());
    }
}
