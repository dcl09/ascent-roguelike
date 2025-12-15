package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Chest;
import Ascent.model.items.HealthRestore;
import Ascent.model.items.Item;
import Ascent.model.items.Weapon;
import Ascent.model.items.armour.Armour;

public class ChestInteractionViewer {
    private static final String TITLE_COLOR = "#FF8844"; // Orange
    private static final String ITEM_COLOR = "#FFFFFF"; // White
    private static final String OPTION_COLOR = "#88FFFF"; // Cyan

    public void draw(GUI gui, Chest chest, int x, int y) {
        if (chest == null)
            return;

        gui.drawText(x, y, "CHEST CONTENTS:", "#FFFFFF");
        Item item = chest.getContainedItem();

        gui.drawText(x, y, "=== CHEST OPENED ===", TITLE_COLOR);

        gui.drawText(x, y + 2, "Found: " + item.getName(), ITEM_COLOR);
        drawItemDetails(gui, item, x, y + 3);

        if (item instanceof HealthRestore) {
            gui.drawText(x, y + 5, "[1] USE NOW (+HP)", OPTION_COLOR);
            gui.drawText(x + 18, y + 5, "[2] SAVE TO BAG", OPTION_COLOR);
        } else {
            gui.drawText(x, y + 5, "[1] EQUIP", OPTION_COLOR);
        }
        gui.drawText(x, y + 6, "[E/ESC] LEAVE", OPTION_COLOR);
    }

    private void drawItemDetails(GUI gui, Item item, int x, int y) {
        String details = "";

        if (item instanceof Weapon weapon) {
            details = String.format("(+%d DMG, %+d SPD)",
                    weapon.getBonusDamage(), weapon.getChangeInSpeed());
        } else if (item instanceof Armour armour) {
            details = String.format("(%s: +%d ARM, %+d SPD)",
                    armour.getSlot().name(), armour.getBonusResistanceToDamage(), armour.getChangeInSpeed());
        } else if (item instanceof HealthRestore potion) {
            details = String.format("(+%d HP)", potion.getRestoredHealth());
        }

        if (!details.isEmpty()) {
            gui.drawText(x, y, details, ITEM_COLOR);
        }
    }
}
