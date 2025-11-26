package model.entities;

import model.game.Position;

public class Player extends Entity {
    /* todo: add player attributes */

    protected char symbol;
    protected String color;

    public Player (Position position, char symbol, String color) {
        super(position, symbol, color);
    }

}
