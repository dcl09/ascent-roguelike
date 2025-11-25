package model.entities;

import model.game.Position;

public class Entity {
    protected Position position;
    protected char symbol;
    protected String color;

    public Entity(Position position) {
        this.position = position;
    }

    public char getSymbol() {
        return symbol;
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
