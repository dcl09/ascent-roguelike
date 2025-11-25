package model.entities;

import model.game.Position;

public class Wall extends Entity{
    private static final char WALL_SYMBOL = '#';
    public Wall(Position position) {
        super(position);
        this.symbol = 'W';
    }
}