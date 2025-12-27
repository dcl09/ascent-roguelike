package ascent.model.entities.components;

import ascent.model.game.Position;

public enum LOOKING {

    UP(0, -1, '▲'),
    DOWN(0, 1, '▼'),
    LEFT(-1, 0, '◄'),
    RIGHT(1, 0, '►');

    private final int deltaX;
    private final int deltaY;
    private final char symbol;

    LOOKING(int deltaX, int deltaY, char symbol) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.symbol = symbol;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public char getSymbol() {
        return symbol;
    }

    public Position move(Position initPosition) {
        return new Position(
                initPosition.getX() + this.deltaX,
                initPosition.getY() + this.deltaY);
    }
}
