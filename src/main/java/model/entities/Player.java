package model.entities;

import model.entities.components.Inventory;
import model.entities.components.Stats;
import model.entities.interfaces.Combatant;
import model.entities.interfaces.Interactable;
import model.entities.interfaces.Interactor;
import model.entities.interfaces.Movable;
import model.game.Position;
import model.items.armour.Armour;
import model.items.HealthRestore;
import model.items.Weapon;

public class Player extends Entity implements Combatant, Movable, Interactor {
    private final Stats stats;
    private final Inventory inventory;

    public Player(Position position, char symbol, String color) {
        super(position, symbol, color);
        this.stats = new Stats(100, 1, 1);
        this.inventory = new Inventory(10);
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    public Inventory getInventory() { return inventory; }

    /*
    @Override
    public boolean moveTo(Position newPosition) {
        if (!canMoveTo(newPosition)) return false;
        setPosition(newPosition);
        return true;
    }
    */

    @Override
    public boolean canMoveTo(Position position) {
        //todo: Falta adicionar outras restrições para movimento
        return isAlive() && position != null;
    }

    @Override
    public int getMovementSpeed() {
        return stats.getSpeed();
    }

    @Override
    public boolean canInteract() {
        return !stats.isDead();
    }

    @Override
    public void interactWith(Interactable target) {
        if (target.canInteract() && target.canInteract()) {
            target.interact(this);
        }
    }

    public Weapon equipWeapon(Weapon weapon) {
        Weapon oldWeapon = inventory.getEquippedWeapon();
        if (oldWeapon != null) {
            oldWeapon.onUnequip(this);
        }
        inventory.equipWeapon(weapon);
        if (weapon != null) {
            weapon.onEquip(this);
        }
        return oldWeapon;
    }

    public Armour equipArmour(Armour armour) {
        if (armour == null) return null;
        Armour oldArmour = inventory.getArmour(armour.getSlot());
        if (oldArmour != null) {
            oldArmour.onUnequip(this);
        }
        inventory.equipArmour(armour);
        armour.onEquip(this);
        return oldArmour;
    }

    public boolean addConsumable(HealthRestore item) {
        return inventory.addConsumable(item);
    }

    public HealthRestore removeConsumable(int index) {
        return inventory.removeConsumable(index);
    }

    public void consumeItem(int index) {
        HealthRestore item = inventory.removeConsumable(index);
        if (item != null) {
            item.consume(this);
        }
    }

    public boolean hasInventorySpace() {
        return inventory.hasSpaceForConsumable();
    }
}
