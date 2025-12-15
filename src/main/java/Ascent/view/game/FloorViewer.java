package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.Chest;
import Ascent.model.entities.Door;
import Ascent.model.entities.Entity;
import Ascent.model.entities.monster.Monster;
import Ascent.model.game.Position;
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

        int statsX = getModel().getWidth() + 3;

        drawLevelCounter(gui, statsX, 2);
        inventoryViewer.draw(gui, getModel().getPlayer(), statsX, 4);

        Monster lastMonster = getModel().getLastAttackedMonster();
        if (lastMonster != null) {
            statsViewer.drawMonsterStats(gui, lastMonster, statsX, 26);
        }
        Chest interactingChest = getModel().getInteractingChest();
        Position playerFacing = getModel().getPlayer().facing();

        if (interactingChest != null) {
            gui.drawText(statsX, 32, "--- Chest ---", "#FFD700");
            chestInteractionViewer.draw(gui, interactingChest, statsX, 33);
        } else {
            Chest facingChest = getModel().getChestAt(playerFacing);
            Door facingDoor = getModel().getDoorAt(playerFacing);

            if (facingChest != null) {
                gui.drawText(statsX, 32, "--- Chest ---", "#FFD700");
                if (facingChest.isOpened() && facingChest.getContainedItem() == null) {
                    gui.drawText(statsX, 34, "Chest is empty", "#888888");
                } else {
                    gui.drawText(statsX, 34, "Press E to interact", "#FFFFFF");
                }
            } else if (facingDoor != null) {
                gui.drawText(statsX, 32, "--- Door ---", "#FFD700");
                if (facingDoor.isOpen()) {
                    gui.drawText(statsX, 34, "Press E to close", "#FFFFFF");
                } else {
                    gui.drawText(statsX, 34, "Press E to open", "#FFFFFF");
                }
            }
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
        gui.drawText(x, y, "--- FLOOR " + getModel().getCurrLevel() + "---", "WHITE");
    }
}
