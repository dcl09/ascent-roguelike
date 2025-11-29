package model.entities;

import model.game.Position;

public class Entity {
    protected Position position;
    protected char symbol;
    protected String color;

    /* Entity should initialize all parameters that are common to all entities */
    public Entity(Position position, char symbol, String color) {
        this.position = position;
        this.symbol = symbol;
        this.color = color;
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

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
