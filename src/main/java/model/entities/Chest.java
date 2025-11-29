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
    private Item containedItem;

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
        if (containedItem instanceof Weapon) {
            handleWeaponPickup(player, (Weapon) containedItem);
        } else if (containedItem instanceof Armour) {
            handleArmourPickup(player, (Armour) containedItem);
        } else if (containedItem instanceof HealthRestore) {
            handleConsumablePickup(player, (HealthRestore) containedItem);
        }
    }

    private void handleWeaponPickup(Player player, Weapon weapon) {
        containedItem = player.equipWeapon(weapon);
    }

    private void handleArmourPickup(Player player, Armour armour) {
        containedItem = player.equipArmour(armour);
    }

    private void handleConsumablePickup(Player player, HealthRestore item) {
        boolean added = player.addConsumable(item);
        if (added) {
            containedItem = null;
        }
    }

    public Item getContainedItem() {
        return containedItem;
    }

    public boolean isOpened() {
        return opened;
    }
}
