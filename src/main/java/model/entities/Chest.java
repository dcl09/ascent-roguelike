package model.entities;

import model.game.Position;

public class Chest extends Entity {
    /* todo: actually implement Item stuff before doing chests
    protected Item iteminside;
    protected bool isOpen;
    (...)
    */

    public Chest (Position position, char symbol, String color) {
        super(position, symbol, color);
    }
}
