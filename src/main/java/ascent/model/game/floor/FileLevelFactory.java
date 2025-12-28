package ascent.model.game.floor;

import ascent.model.entities.Player;

import java.io.IOException;

public class FileLevelFactory {
    public Floor createLevel(int levelNumber, Player player) throws IOException {
        String filename = "levels/level" + levelNumber + ".txt";
        FileLevelBuilder builder = createBuilder(filename);
        player.setPosition(builder.findPlayerSpawn());
        return builder.createFloor(player, levelNumber);
    }

    // Protected method to allow overriding/stubbing in unit tests
    protected FileLevelBuilder createBuilder(String filename) throws IOException {
        return new FileLevelBuilder(filename);
    }
}
