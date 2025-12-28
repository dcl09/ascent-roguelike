package ascent.model.game;

import ascent.model.game.floor.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PathFinderTest {

    private Floor floor;
    private PathFinder pathFinder;

    @BeforeEach
    void setUp() {
        floor = Mockito.mock(Floor.class);
        pathFinder = new PathFinder(floor);
    }

    @Test
    void findNextStepReturnsPlayerPositionIfPlayerEqualsTarget() {
        Position p = new Position(1,1);
        assertEquals(p, pathFinder.findNextStep(p, p));
    }

    @Test
    void findNextStepReturnsTargetIfAdjacent() {
        Position start = new Position(1, 1);
        Position target = new Position(1, 2);
        assertEquals(target, pathFinder.findNextStep(start, target));
    }

    @Test
    void findNextStepReturnsNullIfUnreachable(){
        Position start = new Position(1, 1);
        Position target = new Position(1, 3);
        when(floor.isWalkable(any(Position.class))).thenReturn(true);
        when(floor.isWalkable(new Position(0, 1))).thenReturn(false);
        when(floor.isWalkable(new Position(1, 0))).thenReturn(false);
        when(floor.isWalkable(new Position(1, 2))).thenReturn(false);
        when(floor.isWalkable(new Position(2, 1))).thenReturn(false);
        assertNull(pathFinder.findNextStep(start, target));
    }

    @Test
    void findNextStepIgnoresDiagonalsInAdjacency(){
        Position start = new Position(1, 1);
        Position target = new Position(2, 2);
        Position expectedNextStep1 = new Position(1, 2);
        when(floor.isWalkable(any(Position.class))).thenReturn(true);
        when(floor.isWalkable(new Position(2, 1))).thenReturn(false);
        assertEquals(expectedNextStep1, pathFinder.findNextStep(start, target));
    }

    @Test
    void findNextStepHandlesVisitedNodesCorrectly() {
        Position start = new Position(0, 0);
        Position target = new Position(0, 2);
        when(floor.isWalkable(any(Position.class))).thenReturn(true);
        assertNotNull(pathFinder.findNextStep(start, target));
    }

    @Test
    void pathfindInAStraightLineToTriggerBackTracking() {
        Position start = new Position(1, 1);
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 3);
        Position target = new Position(1, 4);
        when(floor.isWalkable(any(Position.class))).thenReturn(false);
        when(floor.isWalkable(start)).thenReturn(true);
        when(floor.isWalkable(p1)).thenReturn(true);
        when(floor.isWalkable(p2)).thenReturn(true);
        when(floor.isWalkable(target)).thenReturn(true);
        assertEquals(p1, pathFinder.findNextStep(start,target));
    }

    @Test
    void getFirstStepReturnsNullIfParentMapIsBroken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = PathFinder.class.getDeclaredMethod("getFirstStepFromPath", Map.class, Position.class, Position.class);
        method.setAccessible(true);
        Map<Position, Position> emptyMap = new HashMap<>();
        Position start = new Position(0, 0);
        Position current = new Position(5, 5);
        Position result = (Position) method.invoke(null, emptyMap, current, start);
        assertNull(result);
    }


}