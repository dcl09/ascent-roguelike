package model.entities;

import model.game.Position;

public class Wall extends Entity{
    protected char symbol;
    protected String color;

    public Wall (Position position, char symbol, String color) {
        super(position);
        super.symbol = symbol;
        super.color = color;
    }
}
