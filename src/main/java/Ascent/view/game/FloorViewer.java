package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Entity;
import Ascent.model.game.floor.Floor;
import Ascent.view.Viewer;

import java.util.Collection;

public class FloorViewer extends Viewer<Floor> {
    private final GameHUDViewer gameHUDViewer;

    public FloorViewer(Floor floor) {
        super(floor);
        this.gameHUDViewer = new GameHUDViewer();
    }

    @Override
    public void drawEntities(GUI gui) {
        drawEntities(gui, getModel().getWalls(), new WallViewer());
        drawEntities(gui, getModel().getDoors(), new DoorViewer());
        drawEntity(gui, getModel().getStairs(), new StairsViewer());
        drawEntities(gui, getModel().getChests(), new ChestViewer());
        drawEntities(gui, getModel().getMonsters(), new MonsterViewer());
        drawEntity(gui, getModel().getPlayer(), new PlayerViewer());

        int hudX = getModel().getWidth() + 18;
        gameHUDViewer.draw(gui, getModel(), hudX);
    }

    private <T extends Entity> void drawEntities(GUI gui, Collection<T> entities, EntityViewer<T> viewer) {
        for (T entity : entities)
            drawEntity(gui, entity, viewer);
    }

    private <T extends Entity> void drawEntity(GUI gui, T entity, EntityViewer<T> viewer) {
        viewer.draw(entity, gui);
    }
}
