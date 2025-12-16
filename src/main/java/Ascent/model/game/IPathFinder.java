package Ascent.model.game;

/**
 * Interface for pathfinding algorithms.
 * Allows dependency injection and testing with mock implementations.
 */
public interface IPathFinder {
    /**
     * Finds the next step in a path from start to target.
     * 
     * @param start  Starting position
     * @param target Target position
     * @return Next position to move to, or null if no path exists
     */
    Position findNextStep(Position start, Position target);
}
