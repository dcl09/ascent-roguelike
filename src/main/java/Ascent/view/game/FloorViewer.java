package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Chest;
import Ascent.model.entities.Entity;
import Ascent.model.entities.monster.Monster;
import Ascent.model.game.floor.Floor;
import Ascent.view.Viewer;

import java.util.Collection;

public class FloorViewer extends Viewer<Floor> {
    private final StatsViewer statsViewer = new StatsViewer();
    private final InventoryViewer inventoryViewer = new InventoryViewer();
    private final ChestInteractionViewer chestInteractionViewer = new ChestInteractionViewer();

    public FloorViewer(Floor floor) {
        super(floor);
    }

    @Override
    public void drawEntities(GUI gui) {
        drawEntities(gui, getModel().getWalls(), new WallViewer());
        drawEntities(gui, getModel().getDoors(), new DoorViewer());
        drawEntity(gui, getModel().getStairs(), new StairsViewer());
        drawEntities(gui, getModel().getChests(), new ChestViewer());
        drawEntities(gui, getModel().getMonsters(), new MonsterViewer());
        drawEntity(gui, getModel().getPlayer(), new PlayerViewer());

        int statsX = getModel().getWidth() + 2;

        drawLevelCounter(gui, statsX, 2);
        statsViewer.drawPlayerStats(gui, getModel().getPlayer(), statsX, 5);

        // Draw player full inventory
        inventoryViewer.draw(gui, getModel().getPlayer(), statsX, 1);

        // Draw monster stats below player inventory
        Monster lastMonster = getModel().getLastAttackedMonster();
        statsViewer.drawMonsterStats(gui, lastMonster, statsX, 12);
        if (lastMonster != null) {
            statsViewer.drawMonsterStats(gui, lastMonster, statsX, 22);
        }

        Chest interactingChest = getModel().getInteractingChest();
        if (interactingChest != null) {
            chestInteractionViewer.draw(gui, interactingChest, statsX, 28);
        }
    }

    private <T extends Entity> void drawEntities(GUI gui, Collection<T> entities, EntityViewer<T> viewer) {
        for (T entity : entities)
            drawEntity(gui, entity, viewer);
    }

    private <T extends Entity> void drawEntity(GUI gui, T entity, EntityViewer<T> viewer) {
        viewer.draw(entity, gui);
    }

    void drawLevelCounter(GUI gui, int x, int y) {
        gui.drawText(x, y, "--- FLOOR " + getModel().getCurrLevel() +"---", "WHITE");
    }
}
