package view;

import gui.GUI;
import model.GameModel;
import model.game.level.Level;
import view.game.*;

import java.io.IOException;

public class GameView {
    private final GameModel model;

    private final PlayerViewer playerViewer;
    private final MonsterViewer monsterViewer;
    private final WallViewer wallViewer;
    private final ChestViewer chestViewer;

    public GameView(GameModel model,
                    PlayerViewer playerViewer,
                    MonsterViewer monsterViewer,
                    WallViewer wallViewer,
                    ChestViewer chestViewer) {
        this.model = model;
        this.playerViewer = playerViewer;
        this.monsterViewer = monsterViewer;
        this.wallViewer = wallViewer;
        this.chestViewer = chestViewer;
    }

    public GameView(GameModel model) {
        this(model, new PlayerViewer(), new MonsterViewer(), new WallViewer(), new ChestViewer());
    }

    public void draw(GUI gui) throws IOException {
        gui.clear();

        Level currlevel = model.getLevel();

        // Draw walls
        for (var wall : currlevel.getWalls()) {
            wallViewer.draw(wall, gui);
        }

        // Draw monsters
        for (var monster : currlevel.getMonsters()) {
            monsterViewer.draw(monster, gui);
        }

        /* Draw chests
        for (var chest : model.getChests()) {
            chestViewer.draw(chest, gui);
        }
        */
        // Draw player (last so it appears on top)
        playerViewer.draw(model.getPlayer(), gui);

        gui.refresh();
    }
}
