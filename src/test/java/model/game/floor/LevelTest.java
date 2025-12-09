package model.game.floor;

import model.entities.*;
import model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    private Floor level;

    @BeforeEach
    void setUp() {
        level = new Floor(20, 15);
    }

    @Test
    void constructor_ShouldSetDimensions() {
        assertEquals(20, level.getWidth());
        assertEquals(15, level.getHeight());
    }

    @Test
    void setPlayer_ShouldStorePlayer() {
        Player player = new Player(new Position(5, 5));

        level.setPlayer(player);

        assertEquals(player, level.getPlayer());
    }

    @Test
    void isWall_WithWallPosition_ShouldReturnTrue() {
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(new Position(5, 5)));
        level.setWalls(walls);

        assertTrue(level.isWall(new Position(5, 5)));
    }

    @Test
    void isWall_WithEmptyPosition_ShouldReturnFalse() {
        List<Wall> walls = new ArrayList<>();
        walls.add(new Wall(new Position(5, 5)));
        level.setWalls(walls);

        assertFalse(level.isWall(new Position(10, 10)));
    }

    @Test
    void isMonster_WithMonsterPosition_ShouldReturnTrue() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(new Position(7, 7)));
        level.setMonsters(monsters);

        assertTrue(level.isMonster(new Position(7, 7)));
    }

    @Test
    void isMonster_WithEmptyPosition_ShouldReturnFalse() {
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(new Position(7, 7)));
        level.setMonsters(monsters);

        assertFalse(level.isMonster(new Position(1, 1)));
    }
}
