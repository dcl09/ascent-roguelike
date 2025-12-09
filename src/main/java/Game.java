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
            // Debug: mostrar diretório atual
            String currentDir = System.getProperty("user.dir");
            System.out.println("Diretório atual: " + currentDir);

            // Caminho do ficheiro
            String levelPath = "levels/level1.txt";
            File levelFile = new File(levelPath);

            System.out.println("Procurando ficheiro: " + levelFile.getAbsolutePath());
            System.out.println("Ficheiro existe? " + levelFile.exists());

            if (!levelFile.exists()) {
                System.err.println("ERRO: Ficheiro não encontrado!");
                System.err.println("Cria a pasta 'levels' e o ficheiro 'level1.txt'");
                System.err.println("O ficheiro deve estar em: " + levelFile.getAbsolutePath());
                return;
            }

            // Carregar nível
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
            System.err.println("Erro ao carregar nível: " + e.getMessage());
            e.printStackTrace();
        }
    }
}