package Ascent.model.entities;

import Ascent.model.game.Position;

public class Wall extends Entity {

    public Wall(Position position) {
        super(position, '#', "#606070");
    }
}
