package Ascent.model.entities;

import Ascent.model.entities.interfaces.Interactable;
import Ascent.model.entities.interfaces.Interactor;
import Ascent.model.game.Position;
import Ascent.model.items.Item;
import Ascent.model.items.factories.InvalidItemIdException;
import Ascent.model.items.factories.ItemFactory;

public class Chest extends Entity implements Interactable {
    private static final char CLOSED_SYMBOL = 'C';
    private static final char OPEN_SYMBOL = 'O';

    private boolean opened;

    private final ItemFactory itemFactory;
    private Item containedItem;

    public Chest(Position position, String color) {
        this(position, color, -1); // -1 indicates random item
    }

    public Chest(Position position, String color, int itemId) {
        super(position, CLOSED_SYMBOL, color);
        this.opened = false;
        this.itemFactory = ItemFactory.getInstance();

        if (itemId == -1) {
            this.containedItem = itemFactory.createRandomItem();
        } else {
            try {
                this.containedItem = itemFactory.createItem(itemId);
            } catch (InvalidItemIdException e) {
                System.err.println("Warning: " + e.getMessage() + ". Creating random item.");
                this.containedItem = itemFactory.createRandomItem();
            }
        }
    }

    @Override
    public boolean canInteract() {
        return !opened;
    }

    @Override
    public void interact(Interactor interactor) {
        if (!canInteract() || !(interactor instanceof Player)) {
            return;
        }
        opened = true;
        symbol = OPEN_SYMBOL;
    }

    public Item getContainedItem() {
        return containedItem;
    }

    public boolean isOpened() {
        return opened;
    }

    public Item takeItem() {
        Item item = containedItem;
        containedItem = null;
        return item;
    }

    @Override
    public String getInteractionMessage() {
        if (opened) {
            return containedItem != null ? "Found: " + containedItem.getName() : "Chest is empty";
        }
        return "Press E to interact";
    }
}
