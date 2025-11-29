package view;

import gui.GUI;
import model.GameModel;
import view.game.*;

import java.io.IOException;

public class GameView {
    private final GameModel model;

    private final PlayerViewer playerViewer = new PlayerViewer();
    private final MonsterViewer monsterViewer = new MonsterViewer();
    private final WallViewer wallViewer = new WallViewer();
    private final ChestViewer chestViewer = new ChestViewer();

    public GameView(GameModel model) {
        this.model = model;
    }

    public void draw(GUI gui) throws IOException {
        gui.clear();

        // Draw walls
        for (var wall : model.getWalls()) {
            wallViewer.draw(wall, gui);
        }

        // Draw monsters
        for (var monster : model.getMonsters()) {
            monsterViewer.draw(monster, gui);
        }

        // Draw chests
        for (var chest : model.getChests()) {
            chestViewer.draw(chest, gui);
        }

        // Draw player (last so it appears on top)
        playerViewer.draw(model.getPlayer(), gui);

        gui.refresh();
    }
}
