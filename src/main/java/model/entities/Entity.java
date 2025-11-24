package model.entities;

import model.game.Position;

public class Entity {
    protected Position position;
    private char Symbol;
    private String color;

    public Entity(Position position) {
        this.position = position;
    }

    public char getSymbol() {
        return Symbol;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
