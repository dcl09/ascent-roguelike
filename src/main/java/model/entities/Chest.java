package model.entities;

import model.entities.interfaces.Interactable;
import model.entities.interfaces.Interactor;
import model.game.Position;
import model.items.armour.Armour;
import model.items.HealthRestore;
import model.items.Item;
import model.items.Weapon;
import model.items.factories.ItemFactory;

public class Chest extends Entity implements Interactable {
    private static final char CLOSED_SYMBOL = 'C';
    private static final char OPEN_SYMBOL = 'O';

    private boolean opened;

    final ItemFactory itemFactory;
    private final Item containedItem;

    public Chest(Position position, String color) {
        super(position, CLOSED_SYMBOL, color);
        this.opened = false;
        this.itemFactory = ItemFactory.getInstance();
        this.containedItem = itemFactory.createRandomItem();
    }

    @Override
    public boolean canInteract() {
        return !opened;
    }

    @Override
    public void interact(Interactor interactor) {
        if (!canInteract() || !(interactor instanceof Player player)) {
            return;
        }
        opened = true;
        symbol = OPEN_SYMBOL;
        containedItem.use(player);
    }

    public Item getContainedItem() {
        return containedItem;
    }

    public boolean isOpened() {
        return opened;
    }
}
