package Ascent.model.entities;

import Ascent.model.entities.interfaces.Interactable;
import Ascent.model.game.Position;

public class Stairs extends Entity implements Interactable {
    public Stairs(Position position) {
        super(position, '=', "#ffe478");
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    @Override
    public String getInteractionMessage() {
        return "Press E to use stairs";
    }
}
