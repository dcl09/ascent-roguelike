package model.entities;

import model.game.Position;

public class Monster extends Entity {
    /* todo: add monster attributes */

    protected char symbol;
    protected String color;

    public Monster(Position position, char symbol, String color) {
        super(position);
        super.symbol = symbol;
        super.color = color;
    }

}
