package Ascent.model.entities;

import Ascent.model.entities.interfaces.Interactable;
import Ascent.model.entities.interfaces.Interactor;
import Ascent.model.game.Position;

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

    public void close() {
        this.isOpen = false;
        this.symbol = CLOSED_SYMBOL;
    }

    public void open() {
        this.isOpen = true;
        this.symbol = OPEN_SYMBOL;
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    @Override
    public void interact(Interactor interactor) {
        if (isOpen) {
            close();
        } else {
            open();
        }
    }
}
