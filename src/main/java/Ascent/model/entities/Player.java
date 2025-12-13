package Ascent.model.entities;

import Ascent.model.entities.components.LOOKING;
import Ascent.model.entities.components.Inventory;
import Ascent.model.entities.components.Stats;
import Ascent.model.entities.interfaces.Combatant;
import Ascent.model.entities.interfaces.Interactable;
import Ascent.model.entities.interfaces.Interactor;
import Ascent.model.game.Position;
import Ascent.model.items.armour.Armour;
import Ascent.model.items.HealthRestore;
import Ascent.model.items.Weapon;

public class Player extends MovableEntity implements Combatant, Interactor {
    private final Stats stats;
    private final Inventory inventory;
    private LOOKING looking = LOOKING.UP;

    public Player(Position position) {
        super(position, '@', "YELLOW_BRIGHT");
        this.stats = new Stats(100, 1, 1);
        this.inventory = new Inventory(10);
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    public Inventory getInventory() { return inventory; }

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
        if (target.canInteract()) {
            target.interact(this);
        }
    }

    public LOOKING lookingDirection() {
        return looking;
    }

    public void setLookingDirection(LOOKING looking) {
        this.looking = looking;
    }

    public Position facing(){
        return switch (looking) {
            case RIGHT -> new Position(position.getX() + 1, position.getY());
            case DOWN -> new Position(position.getX(), position.getY() + 1);
            case LEFT -> new Position(position.getX() - 1, position.getY());
            default -> new Position(position.getX(), position.getY() - 1);
        };
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
