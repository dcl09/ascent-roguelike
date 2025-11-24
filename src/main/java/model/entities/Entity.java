package model.entities;

public class Entity {
    /* todo: Add position attribute to generic entity */
    private char Symbol;
    private String color;

    public Entity() {
        /* Position is the only attribute of the generic class */
    }

    public char getSymbol() {
        return Symbol;
    }

    public String getColor() {
        return color;
    }
}
