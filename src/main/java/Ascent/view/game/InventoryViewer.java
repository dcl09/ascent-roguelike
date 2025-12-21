package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Player;
import Ascent.model.entities.components.Inventory;
import Ascent.model.items.HealthRestore;
import Ascent.model.items.Weapon;
import Ascent.model.items.armour.Armour;
import Ascent.model.items.armour.ArmourSlot;

import java.util.List;
import java.util.Map;

public class InventoryViewer {
    private static final String TITLE_COLOR = "#FFD700";
    private static final String ITEM_COLOR = "#88FF88";
    private static final String EMPTY_COLOR = "#666666";

    public void drawInventory(GUI gui, Player player, int x, int y) {
        if (player == null)
            return;

        Inventory inventory = player.getInventory();

        // Weapon Section
        drawWeapon(gui, inventory.getEquippedWeapon(), x, y);

        // Armour Section
        drawArmour(gui, inventory.getEquippedArmour(), x, y + 3);

        // Potions Section
        drawPotions(gui, inventory.getConsumables(), x, y + 11);
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

            String key;
            switch (slot) {
                case HEAD -> key = "[ALT+2] ";
                case CHEST -> key = "[ALT+3] ";
                case ARMS -> key = "[ALT+4] ";
                case LEGS -> key = "[ALT+5] ";
                case FEET -> key = "[ALT+6] ";
                default -> key = "      ";
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
