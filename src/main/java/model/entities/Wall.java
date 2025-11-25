package model.entities;

import model.game.Position;

public class Wall extends Entity{

    private char Symbol;

    public Wall(Position position, char symbol) {
        super(position);
        Symbol = symbol;
    }
}
