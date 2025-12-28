package ascent.model.game.floor;

import ascent.model.entities.*;
import ascent.model.entities.monster.Monster;
import ascent.model.entities.monster.MonsterPool;
import ascent.model.entities.monster.MonsterType;
import ascent.model.game.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileLevelBuilderTest {

    @Nested
    class ConstructorTests {

        @Test
        void emptyLevelThrowsException() {
            assertThrows(IllegalArgumentException.class,
                    () -> new FileLevelBuilder(new String[] {}));
        }

        @Test
        void validLevelCreatesBuilder() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "..." });
            assertNotNull(builder);
        }

        @Test
        void constructorWithPathLoadsFile(@TempDir Path tempDir) throws IOException {
            Path levelFile = tempDir.resolve("test_level.txt");
            Files.writeString(levelFile, "###\n#.#\n###");

            FileLevelBuilder builder = new FileLevelBuilder(levelFile);

            assertEquals(3, builder.getWidth());
            assertEquals(3, builder.getHeight());
            assertEquals(8, builder.getWalls().size());
        }

        @Test
        void constructorWithStringPathLoadsFile(@TempDir Path tempDir) throws IOException {
            Path levelFile = tempDir.resolve("test_level2.txt");
            Files.writeString(levelFile, "...\n...\n...");

            FileLevelBuilder builder = new FileLevelBuilder(levelFile.toString());

            assertEquals(3, builder.getWidth());
            assertEquals(3, builder.getHeight());
        }
    }

    @Nested
    class DimensionTests {

        @Test
        void getWidthReturnsMaxLineWidth() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    "###",
                    "#####",
                    "##"
            });
            assertEquals(5, builder.getWidth());
        }

        @Test
        void getHeightReturnsLineCount() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    "###",
                    "###",
                    "###"
            });
            assertEquals(3, builder.getHeight());
        }

        @Test
        void shortLinesArePaddedCorrectly() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    "#####",
                    "#",
                    "##"
            });
            assertEquals(5, builder.getWidth());
            assertEquals(8, builder.getWalls().size());
        }
    }

    @Nested
    class EntityParsingTests {

        @Test
        void parsesWalls() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "###" });
            assertEquals(3, builder.getWalls().size());
            assertEquals(new Position(0, 0), builder.getWalls().get(0).getPosition());
        }

        @Test
        void parsesDoors() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { ".D." });
            assertEquals(1, builder.getDoors().size());
            assertEquals(new Position(1, 0), builder.getDoors().get(0).getPosition());
        }

        @Test
        void parsesStairs() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { ".=." });
            assertNotNull(builder.getStairs());
            assertEquals(new Position(1, 0), builder.getStairs().getPosition());
        }

        @Test
        void parsesRandomChest() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { ".C." });
            assertEquals(1, builder.getChests().size());
            assertEquals(new Position(1, 0), builder.getChests().get(0).getPosition());
        }

        @Test
        void parsesChestWithItemId() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { ".1." });
            assertEquals(1, builder.getChests().size());
        }

        @Test
        void parsesGoblinMonster() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { ".g." });
            assertEquals(1, builder.getMonsters().size());
            assertEquals(new Position(1, 0), builder.getMonsters().get(0).getPosition());
            assertEquals(MonsterType.GOBLIN, builder.getMonsters().get(0).getMonsterType());
        }

        @Test
        void parsesMultipleMonsterTypes() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "gOsZ" });
            assertEquals(4, builder.getMonsters().size());
            assertEquals(MonsterType.GOBLIN, builder.getMonsters().get(0).getMonsterType());
            assertEquals(MonsterType.ORC, builder.getMonsters().get(1).getMonsterType());
            assertEquals(MonsterType.SKELETON, builder.getMonsters().get(2).getMonsterType());
            assertEquals(MonsterType.ZOMBIE, builder.getMonsters().get(3).getMonsterType());
        }

        @Test
        void emptySpaceCreatesNoEntities() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "..." });
            assertTrue(builder.getWalls().isEmpty());
            assertTrue(builder.getDoors().isEmpty());
            assertTrue(builder.getChests().isEmpty());
            assertTrue(builder.getMonsters().isEmpty());
            assertNull(builder.getStairs());
        }
    }

    @Nested
    class PlayerSpawnTests {

        @Test
        void findsPlayerSpawnPosition() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "..@.." });
            Position spawn = builder.findPlayerSpawn();
            assertEquals(new Position(2, 0), spawn);
        }

        @Test
        void findsPlayerSpawnInMultilineLevel() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    ".....",
                    "..@..",
                    "....."
            });
            assertEquals(new Position(2, 1), builder.findPlayerSpawn());
        }

        @Test
        void returnsDefaultCenterWhenNoPlayer() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    ".....",
                    ".....",
                    "....."
            });
            Position spawn = builder.findPlayerSpawn();
            assertEquals(new Position(2, 1), spawn);
        }

        @Test
        void playerAtOrigin() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "@...." });
            assertEquals(new Position(0, 0), builder.findPlayerSpawn());
        }

        @Test
        void playerAtEndOfLine() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] { "....@" });
            assertEquals(new Position(4, 0), builder.findPlayerSpawn());
        }
    }

    @Nested
    class FloorBuildingTests {

        @Test
        void createFloorReturnsFloorWithCorrectDimensions() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    "#####",
                    "#...#",
                    "#####"
            });
            Player player = new Player(new Position(1, 1));
            Floor floor = builder.createFloor(player);

            assertNotNull(floor);
            assertEquals(5, floor.getWidth());
            assertEquals(3, floor.getHeight());
        }

        @Test
        void createFloorIncludesAllEntities() {
            FileLevelBuilder builder = new FileLevelBuilder(new String[] {
                    "#D#",
                    ".C.",
                    "#=#"
            });
            Player player = new Player(new Position(1, 1));
            Floor floor = builder.createFloor(player);

            assertFalse(floor.getWalls().isEmpty());
            assertFalse(floor.getDoors().isEmpty());
            assertFalse(floor.getChests().isEmpty());
            assertNotNull(floor.getStairs());
        }
    }

    @Nested
    class MonsterPoolExhaustionTests {

        @Test
        void handlesMonsterPoolExhaustion() {
            MonsterPool pool = MonsterPool.getInstance();
            List<Monster> acquiredMonsters = new ArrayList<>();

            while (pool.hasAvailable()) {
                acquiredMonsters.add(pool.acquire());
            }

            FileLevelBuilder builder = new FileLevelBuilder(new String[] { ".g." });
            assertEquals(0, builder.getMonsters().size());

            for (Monster monster : acquiredMonsters) {
                pool.release(monster);
            }
        }
    }
}
