package view.game;

import gui.GUI;
import model.entities.Entity;
import model.game.floor.Floor;
import view.Viewer;

import java.util.List;

public class GameViewer extends Viewer<Floor> {
    // todo: add doors
    public GameViewer(Floor floor) {
        super(floor);
    }

    public void drawEntities(GUI gui){
        drawEntities(gui, getModel().getWalls(), new WallViewer());
        // drawEntities(gui, getModel().getDoors(), new DoorViewer());
        drawEntities(gui, getModel().getChests(), new ChestViewer());
        drawEntities(gui, getModel().getMonsters(), new MonsterViewer());
        drawEntity(gui, getModel().getPlayer(), new PlayerViewer());
    }

    private <T extends Entity> void drawEntities(GUI gui, List<T> entities, EntityViewer<T> viewer) {
        for (T entity : entities)
            drawEntity(gui, entity, viewer);
    }

    private <T extends Entity> void drawEntity(GUI gui, T entity, EntityViewer<T> viewer) {
        viewer.draw(entity, gui);
    }
}
