package model.entities.items;

public class IsWeapon extends Items {
    int damage;
    int atkcd;
    int atkrange;

    IsWeapon(int level, int damage, int atkcd, int atkrange) {
        super(level);
        this.damage = damage;
        this.atkcd = atkcd;
        this.atkrange = atkrange;
    }
}
