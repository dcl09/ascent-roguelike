package model.entities;

import model.game.Position;

public class Wall extends Entity{

    public Wall (Position position) {
        super(position, '#', "WHITE");
    }
}
