package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Entity;
import Ascent.model.entities.Monster;
import Ascent.model.game.floor.Floor;
import Ascent.view.Viewer;

import java.util.Collection;

public class GameViewer extends Viewer<Floor> {
    private final StatsViewer statsViewer = new StatsViewer();

    public GameViewer(Floor floor) {
        super(floor);
    }

    @Override
    public void drawEntities(GUI gui) {
        drawEntities(gui, getModel().getWalls(), new WallViewer());
        drawEntities(gui, getModel().getDoors(), new DoorViewer());
        drawEntities(gui, getModel().getChests(), new ChestViewer());
        drawEntities(gui, getModel().getMonsters(), new MonsterViewer());
        drawEntity(gui, getModel().getPlayer(), new PlayerViewer());

        int statsX = getModel().getWidth() + 2;
        statsViewer.drawPlayerStats(gui, getModel().getPlayer(), statsX, 2);

        Monster lastMonster = getModel().getLastAttackedMonster();
        statsViewer.drawMonsterStats(gui, lastMonster, statsX, 9);
    }

    private <T extends Entity> void drawEntities(GUI gui, Collection<T> entities, EntityViewer<T> viewer) {
        for (T entity : entities)
            drawEntity(gui, entity, viewer);
    }

    private <T extends Entity> void drawEntity(GUI gui, T entity, EntityViewer<T> viewer) {
        viewer.draw(entity, gui);
    }
}
