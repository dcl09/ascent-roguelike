package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.game.floor.Floor;
import Ascent.view.game.interaction.InteractionDialogManager;

public class GameHUDViewer {
    private final PlayerStatsViewer playerStatsViewer;
    private final MonsterStatsViewer monsterStatsViewer;
    private final InventoryViewer inventoryViewer;
    private final InteractionDialogManager interactionDialogManager;

    public GameHUDViewer() {
        this.playerStatsViewer = new PlayerStatsViewer();
        this.monsterStatsViewer = new MonsterStatsViewer();
        this.inventoryViewer = new InventoryViewer();
        this.interactionDialogManager = new InteractionDialogManager();
    }

    public void draw(GUI gui, Floor floor, int hudX) {
        drawLevelInfo(gui, floor, hudX, 2);

        playerStatsViewer.draw(gui, floor.getPlayer().getStats(), hudX, 4);

        inventoryViewer.drawInventory(gui, floor.getPlayer(), hudX, 11);

        if (floor.getLastAttackedMonster() != null) {
            monsterStatsViewer.draw(gui, floor.getLastAttackedMonster().getStats(), hudX, 26);
        }

        interactionDialogManager.draw(gui, floor, hudX, 32);
    }

    private void drawLevelInfo(GUI gui, Floor floor, int x, int y) {
        gui.drawText(x, y, "--- FLOOR " + floor.getCurrLevel() + " ---", "WHITE");
    }
}
