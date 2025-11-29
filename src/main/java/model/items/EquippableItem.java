package model.items;

import model.entities.Player;

public abstract class EquippableItem extends Item{

    protected int changeInSpeed;

    public EquippableItem(Integer id, String name, int changeInSpeed) {
        super(id, name);
        this.changeInSpeed = changeInSpeed;
    }

    public int getChangeInSpeed() {
        return changeInSpeed;
    }

    public final void onEquip(Player player) {
        player.getStats().addSpeed(changeInSpeed);

        // Method for the subclasses to apply not common attributes
        onEquipStats(player);
    }

    public final void onUnequip(Player player) {
        player.getStats().loseSpeed(changeInSpeed);

        // Method for the subclasses to remove not common attributes
        onUnequipStats(player);
    }

    protected abstract void onEquipStats(Player player);

    protected abstract void onUnequipStats(Player player);
}
