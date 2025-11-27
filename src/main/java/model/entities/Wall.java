package model.entities;

import model.game.Position;

public class Wall extends Entity{

    public Wall (Position position, char symbol, String color) {
        super(position, symbol, color);
    }
}
