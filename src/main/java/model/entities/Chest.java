package model.entities;

import model.entities.interfaces.Interactable;
import model.entities.interfaces.Interactor;
import model.game.Position;
import model.items.Armour.Armour;
import model.items.HealthRestore;
import model.items.Item;
import model.items.Weapon;

public class Chest extends Entity implements Interactable {
    private static final char CLOSED_SYMBOL = 'C';
    private static final char OPEN_SYMBOL = 'O';

    //todo: Colocar item proveniente da Object Pool
    //todo: Falta adicionar geração de items
    private boolean opened;
    private Item containedItem;

    public Chest(Position position, String color) {
        super(position, CLOSED_SYMBOL, color);
        this.opened = false;
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
        Weapon oldWeapon = player.equipWeapon(weapon);
        containedItem = oldWeapon;
    }

    private void handleArmourPickup(Player player, Armour armour) {
        Armour oldArmour = player.equipArmour(armour);
        containedItem = oldArmour;
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
