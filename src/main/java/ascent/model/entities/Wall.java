package ascent.model.entities;

import ascent.model.game.Position;

public class Wall extends Entity {

    public Wall(Position position) {
        super(position, '#', "#606070");
    }
}
