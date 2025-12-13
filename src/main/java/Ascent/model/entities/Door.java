package Ascent.model.entities;

import Ascent.model.game.Position;

public class Door extends Entity {
    private static final char CLOSED_SYMBOL = 'D';
    private static final char OPEN_SYMBOL = 'd';

    private boolean isOpen;

    public Door(Position position) {
        super(position, CLOSED_SYMBOL, "WHITE");
        this.isOpen = true;
        updateSymbol();
    }

    private void updateSymbol() {
        this.symbol = isOpen ? OPEN_SYMBOL : CLOSED_SYMBOL;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close() {
        this.isOpen = false;
        updateSymbol();
    }

    public void open() {
        this.isOpen = true;
        updateSymbol();
    }
}
