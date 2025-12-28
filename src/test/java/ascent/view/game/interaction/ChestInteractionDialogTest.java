package ascent.view.game.interaction;

import ascent.gui.GUI;
import ascent.model.entities.Chest;
import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ChestInteractionDialogTest {
    private ChestInteractionDialog dialog;
    private GUI gui;
    private Chest chest;

    @BeforeEach
    public void setup() {
        dialog = new ChestInteractionDialog();
        gui = Mockito.mock(GUI.class);
        chest = Mockito.mock(Chest.class);
    }

    @Test
    void getTitleReturnsCorrectTitle(){
        assertEquals("--- Chest ---", dialog.getTitle());
    }

    @Test
    void drawWithNullChestDoesNothing(){
        dialog.draw(gui,null, 1, 1);
        verifyNoInteractions(gui);
    }

    @Test
    void drawClosedChestDrawsOpenMessage(){
        when(chest.isOpened()).thenReturn(false);
        when(chest.getInteractionMessage()).thenReturn("closedchest");
        dialog.draw(gui, chest, 1, 1);
        verify(gui).drawText(eq(1), eq(1), eq("closedchest"), eq("#FFFFFF"));
        verify(gui, never()).drawText(anyInt(), anyInt(), contains("OPENED"), anyString());
    }

    @Test
    void drawOpenedChestDrawsEmptyMessage(){
        when(chest.isOpened()).thenReturn(true);
        when(chest.getContainedItem()).thenReturn(null);
        when(chest.getInteractionMessage()).thenReturn("emptychest");
        dialog.draw(gui, chest, 1, 1);
        verify(gui).drawText(eq(1), eq(1), eq("emptychest"), eq("#888888"));
    }

    @Test
    void drawOpenedChestWithWeaponTests(){
        when(chest.isOpened()).thenReturn(true);
        Weapon weapon = Mockito.mock(Weapon.class);
        when(weapon.getBonusDamage()).thenReturn(10);
        when(weapon.getChangeInSpeed()).thenReturn(2);
        when(chest.getContainedItem()).thenReturn(weapon);
        when(chest.getInteractionMessage()).thenReturn("openedchest");

        dialog.draw(gui, chest, 1, 10);

        verify(gui).drawText(eq(1), eq(10), eq("=== CHEST OPENED ==="), anyString());
        verify(gui).drawText(eq(1), eq(12), eq("openedchest"), anyString());
        verify(gui).drawText(eq(1), eq(13), contains("+10 DMG"), anyString());
        verify(gui).drawText(eq(1), eq(15), eq("[1] EQUIP"), anyString());
        verify(gui).drawText(eq(1), eq(16), eq("[E] LEAVE"), anyString());
    }

    @Test
    void drawOpenedChestWithArmourDrawsDetails() {
        when(chest.isOpened()).thenReturn(true);
        Armour mockArmour = mock(Armour.class);
        when(mockArmour.getSlot()).thenReturn(ArmourSlot.CHEST);
        when(mockArmour.getBonusResistanceToDamage()).thenReturn(5);
        when(mockArmour.getChangeInSpeed()).thenReturn(-1);
        when(chest.getContainedItem()).thenReturn(mockArmour);

        dialog.draw(gui, chest, 1, 10);

        verify(gui).drawText(eq(1), eq(13), contains("CHEST: +5 ARM"), anyString());
    }

    @Test
    void drawOpenedChestWithPotionDrawsDetails() {
        when(chest.isOpened()).thenReturn(true);
        HealthRestore mockPotion = mock(HealthRestore.class);
        when(mockPotion.getRestoredHealth()).thenReturn(15);
        when(chest.getContainedItem()).thenReturn(mockPotion);

        dialog.draw(gui, chest, 10, 10);

        verify(gui).drawText(eq(10), eq(13), contains("+15 HP"), anyString());
        verify(gui).drawText(eq(10), eq(15), contains("USE NOW"), anyString());
        verify(gui).drawText(eq(28), eq(15), contains("SAVE TO BAG"), anyString());
    }
}
