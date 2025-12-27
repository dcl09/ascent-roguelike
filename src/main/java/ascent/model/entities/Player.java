package ascent.model.entities;

import ascent.model.entities.components.LOOKING;
import ascent.model.entities.components.Inventory;
import ascent.model.entities.components.Stats;
import ascent.model.entities.interfaces.Combatant;
import ascent.model.entities.interfaces.Interactable;
import ascent.model.entities.interfaces.Interactor;
import ascent.model.game.Position;
import ascent.model.items.armour.Armour;
import ascent.model.items.armour.ArmourSlot;
import ascent.model.items.HealthRestore;
import ascent.model.items.Weapon;

public class Player extends MovableEntity implements Combatant, Interactor {
    private static final long BASE_ATTACK_COOLDOWN = 400;
    private final Stats stats;
    private final Inventory inventory;
    private LOOKING looking;

    public Player(Position position) {
        super(position, '►', "#6476e8");
        this.stats = new Stats(100, 8, 7);
        this.inventory = new Inventory(10);
        this.looking = LOOKING.RIGHT;
    }

    @Override
    public Stats getStats() {
        return stats;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public int getMovementSpeed() {
        return stats.getSpeed();
    }

    @Override
    public boolean canInteract() {
        return !stats.isDead();
    }

    public long getAttackCooldown() {
        return BASE_ATTACK_COOLDOWN;
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
        setSymbol(looking.getSymbol());
    }

    public Position facing() {
        return looking.move(this.position);
    }

    public Position moveToward(LOOKING looking) {
        setLookingDirection(looking);
        return looking.move(this.position);
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
        if (armour == null)
            return null;
        Armour oldArmour = inventory.getArmour(armour.getSlot());
        if (oldArmour != null) {
            oldArmour.onUnequip(this);
        }
        inventory.equipArmour(armour);
        armour.onEquip(this);
        return oldArmour;
    }

    public Armour unequipArmour(ArmourSlot slot) {
        Armour oldArmour = inventory.getArmour(slot);
        if (oldArmour != null) {
            oldArmour.onUnequip(this);
            inventory.unequipArmour(slot);
        }
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
