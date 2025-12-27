package ascent.view.game.interaction;

import ascent.gui.GUI;
import ascent.model.entities.Chest;
import ascent.model.items.HealthRestore;
import ascent.model.items.Item;
import ascent.model.items.Weapon;
import ascent.model.items.armour.Armour;

public class ChestInteractionDialog implements InteractionDialog<Chest> {
    private static final String TITLE_COLOR = "#FFD700";
    private static final String ITEM_COLOR = "#FFFFFF";
    private static final String OPTION_COLOR = "#88FFFF";

    @Override
    public String getTitle() {
        return "--- Chest ---";
    }

    @Override
    public void draw(GUI gui, Chest chest, int x, int y) {
        if (chest == null)
            return;

        if (chest.isOpened() && chest.getContainedItem() != null) {
            drawOpenedChestWithItem(gui, chest, x, y);
        } else if (chest.isOpened() && chest.getContainedItem() == null) {
            gui.drawText(x, y, chest.getInteractionMessage(), "#888888");
        } else {
            drawPrompt(gui, x, y, chest.getInteractionMessage());
        }
    }

    public void drawPrompt(GUI gui, int x, int y, String message) {
        gui.drawText(x, y, message, "#FFFFFF");
    }

    private void drawOpenedChestWithItem(GUI gui, Chest chest, int x, int y) {
        Item item = chest.getContainedItem();

        gui.drawText(x, y, "=== CHEST OPENED ===", TITLE_COLOR);
        gui.drawText(x, y + 2, chest.getInteractionMessage(), ITEM_COLOR);
        drawItemDetails(gui, item, x, y + 3);

        if (item instanceof HealthRestore) {
            gui.drawText(x, y + 5, "[1] USE NOW (+HP)", OPTION_COLOR);
            gui.drawText(x + 18, y + 5, "[2] SAVE TO BAG", OPTION_COLOR);
        } else {
            gui.drawText(x, y + 5, "[1] EQUIP", OPTION_COLOR);
        }
        gui.drawText(x, y + 6, "[E] LEAVE", OPTION_COLOR);
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
