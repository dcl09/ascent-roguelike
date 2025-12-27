package ascent.model.entities;

import ascent.model.entities.interfaces.Interactable;
import ascent.model.entities.interfaces.Interactor;
import ascent.model.game.Position;

public class Door extends Entity implements Interactable {
    private static final char CLOSED_SYMBOL = 'D';
    private static final char OPEN_SYMBOL = 'd';

    private boolean isOpen;

    public Door(Position position) {
        super(position, CLOSED_SYMBOL, "#86a7ed");
        this.isOpen = false;
        this.symbol = CLOSED_SYMBOL;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    @Override
    public void interact(Interactor interactor) {
        if (isOpen) {
            this.isOpen = false;
            this.symbol = CLOSED_SYMBOL;
        } else {
            this.isOpen = true;
            this.symbol = OPEN_SYMBOL;
        }
    }

    @Override
    public boolean isWalkable() {
        return isOpen;
    }

    @Override
    public String getInteractionMessage() {
        return isOpen ? "Press E to close" : "Press E to open";
    }
}
