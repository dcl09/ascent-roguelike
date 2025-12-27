package ascent.model.game;

import ascent.model.game.floor.Floor;

import java.util.*;

public class PathFinder implements IPathFinder {
    private final Floor floor;

    public PathFinder(Floor floor) {
        this.floor = floor;
    }

    private static boolean isAdjacent(Position a, Position b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());
        return (dx + dy) == 1;
    }

    private static Position getFirstStepFromPath(Map<Position, Position> parentMap, Position current, Position start) {
        Position step = current;

        if (parentMap.get(step) == null)
            return null;

        while (!parentMap.get(step).equals(start)) {
            step = parentMap.get(step);
        }

        return step;
    }

    @Override
    public Position findNextStep(Position start, Position target) {
        if (start.equals(target))
            return start;

        if (isAdjacent(start, target)) {
            return target;
        }

        Deque<Position> positionQueue = new ArrayDeque<>();
        Set<Position> visited = new HashSet<>();
        Map<Position, Position> parentMap = new HashMap<>();

        positionQueue.add(start);
        visited.add(start);

        while (!positionQueue.isEmpty()) {
            Position current = positionQueue.remove();

            Position[] adjacentPositions = {
                    current.getUp(),
                    current.getDown(),
                    current.getLeft(),
                    current.getRight()
            };

            for (Position neighbour : adjacentPositions) {
                if (neighbour.equals(target)) {
                    return getFirstStepFromPath(parentMap, current, start);
                }

                else if (floor.isWalkable(neighbour)) {
                    if (visited.contains(neighbour))
                        continue;

                    visited.add(neighbour);
                    parentMap.put(neighbour, current);
                    positionQueue.add(neighbour);
                }
            }
        }

        return null;
    }
}
