package Ascent.model.game.floor;

import Ascent.model.entities.Player;

import java.io.IOException;

public class FileLevelFactory {
    public Floor createLevel(int levelNumber, Player player) throws IOException {
        String filename = "levels/level" + levelNumber + ".txt";
        FileLevelBuilder builder = new FileLevelBuilder(filename);
        if (player.getPosition() == null || player.getPosition().getX() == 0) {
            player.setPosition(builder.findPlayerSpawn());
        } else {
            player.setPosition(builder.findPlayerSpawn());
        }
        return builder.createFloor(player, levelNumber);
    }
}
