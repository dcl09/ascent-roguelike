import controller.GameController;
import gui.GUI;
import model.GameModel;
import model.entities.Player;
import model.game.Position;
import model.game.level.FileLevelBuilder;
import model.game.level.Level;
import view.GameView;

import java.io.File;
import java.io.IOException;

public class Game {
    public static void main(String[] args) {
        try {
            String levelPath = "levels/level1.txt";
            File levelFile = new File(levelPath);

            if (!levelFile.exists()) {
                System.err.println("ERROR: Level file not found!");
                System.err.println("Create level1.txt in /levels");
                System.err.println("The file should be in: " + levelFile.getAbsolutePath());
                return;
            }

            FileLevelBuilder builder = new FileLevelBuilder(levelPath);
            Position playerSpawn = builder.findPlayerSpawn();
            Player player = new Player(playerSpawn);
            Level level = builder.createLevel(player);

            int width = level.getWidth();
            int height = level.getHeight();

            GUI gui = new GUI(width, height);
            GameModel model = new GameModel(player, level);
            GameView view = new GameView(model);
            GameController controller = new GameController(view, model, gui);

            controller.run();

        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
            e.printStackTrace();
        }
    }
}