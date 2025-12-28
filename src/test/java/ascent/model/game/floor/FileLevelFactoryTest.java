package ascent.model.game.floor;

import ascent.model.entities.Player;
import ascent.model.game.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileLevelFactoryTest {

    @Test
    void createBuilderReturnsRealBuilder(@TempDir Path tempDir) throws IOException {
        FileLevelFactory factory = new FileLevelFactory();
        Path levelFile = tempDir.resolve("level1.txt");
        Files.writeString(levelFile, "###");

        FileLevelBuilder builder = factory.createBuilder(levelFile.toString());
        assertNotNull(builder);
    }

    @Test
    void createLevelSetsPlayerPositionAndReturnsFloor() throws IOException {
        TestableFileLevelFactory factory = new TestableFileLevelFactory();
        Player player = new Player(new Position(0, 0));

        Floor floor = factory.createLevel(1, player);

        assertNotNull(floor);
        assertEquals(new Position(5, 5), player.getPosition());
        assertEquals(1, floor.getCurrLevel());
        assertEquals("levels/level1.txt", factory.lastFilename);
    }

    private static class TestableFileLevelFactory extends FileLevelFactory {
        String lastFilename;

        @Override
        protected FileLevelBuilder createBuilder(String filename) {
            this.lastFilename = filename;
            return new StubFileLevelBuilder(new String[] { "stub" });
        }
    }

    private static class StubFileLevelBuilder extends FileLevelBuilder {
        public StubFileLevelBuilder(String[] levelLines) {
            super(new String[] { "." });
        }

        @Override
        public Position findPlayerSpawn() {
            return new Position(5, 5);
        }

        @Override
        public Floor createFloor(Player player, int currLevel) {
            return new Floor(10, 10, currLevel);
        }
    }
}
