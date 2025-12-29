package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.Player;
import ascent.model.entities.components.Inventory;
import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;

import java.util.List;
import java.util.Map;

public class InventoryViewer {
    private static final String TITLE_COLOR = "#f2a65e";
    private static final String ITEM_COLOR = "#ffe478";
    private static final String EMPTY_COLOR = "#606070";

    public void drawInventory(GUI gui, Player player, int x, int y) {
        if (player == null)
            return;

        gui.drawText(x, y + 2, "=== INVENTORY ===", TITLE_COLOR);
        Inventory inventory = player.getInventory();

        // Weapon Section
        drawWeapon(gui, inventory.getEquippedWeapon(), x, y + 4);

        // Armour Section
        drawArmour(gui, inventory.getEquippedArmour(), x, y + 6);

        // Potions Section
        drawPotions(gui, inventory.getConsumables(), x, y + 14);
    }

    private void drawWeapon(GUI gui, Weapon weapon, int x, int y) {
        String key = "[ALT+1] ";
        if (weapon != null) {
            String weaponText = String.format("%sWeapon: %s (+%d DMG)",
                    key, weapon.getName(), weapon.getBonusDamage());
            gui.drawText(x, y, weaponText, ITEM_COLOR);
        } else {
            gui.drawText(x, y, key + "Weapon: -", EMPTY_COLOR);
        }
    }

    private void drawArmour(GUI gui, Map<ArmourSlot, Armour> armourMap, int x, int y) {
        gui.drawText(x, y, "--- Armour ---", TITLE_COLOR);

        ArmourSlot[] slots = { ArmourSlot.HEAD, ArmourSlot.CHEST, ArmourSlot.ARMS, ArmourSlot.LEGS, ArmourSlot.FEET };
        int offset = 2;

        for (ArmourSlot slot : slots) {
            Armour armour = armourMap.get(slot);
            String slotName = String.format("%-5s: ", slot.name());

            String key = "      ";
            switch (slot) {
                case HEAD -> key = "[ALT+2] ";
                case CHEST -> key = "[ALT+3] ";
                case ARMS -> key = "[ALT+4] ";
                case LEGS -> key = "[ALT+5] ";
                case FEET -> key = "[ALT+6] ";
            }

            if (armour != null) {
                String armourText = key + slotName + armour.getName() + " (+" + armour.getBonusResistanceToDamage()
                        + " ARM)";
                gui.drawText(x, y + offset, armourText, ITEM_COLOR);
            } else {
                gui.drawText(x, y + offset, key + slotName + "-", EMPTY_COLOR);
            }
            offset++;
        }
    }

    private void drawPotions(GUI gui, List<HealthRestore> potions, int x, int y) {
        gui.drawText(x, y, "--- Potions [1-5] ---", TITLE_COLOR);

        for (int i = 0; i < 5; i++) {
            String slot = "[" + (i + 1) + "]   ";

            if (i < potions.size()) {
                HealthRestore potion = potions.get(i);
                String potionText = slot + potion.getName() + " (+" + potion.getRestoredHealth() + " HP)";
                gui.drawText(x, y + 2 + i, potionText, ITEM_COLOR);
            } else {
                gui.drawText(x, y + 2 + i, slot + "-", EMPTY_COLOR);
            }
        }
    }
}
