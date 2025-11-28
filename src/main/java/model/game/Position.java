// ==================== POSITION CLASS ====================
package model.game;

import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position offset(int dx, int dy) {
        return new Position(this.x + dx, this.y + dy);
    }

    public int distanceTo(Position other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public boolean isAdjacentTo(Position other) {
        return distanceTo(other) == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /* tool for debugging/logging purposes? */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}