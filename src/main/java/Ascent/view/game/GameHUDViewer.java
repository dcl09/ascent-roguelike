package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.game.floor.Floor;
import Ascent.view.game.interaction.InteractionDialogManager;

public class GameHUDViewer {
    private static final int LEVEL_INFO_Y = 0;
    private static final int PLAYER_STATS_Y = 2;
    private static final int INVENTORY_Y = 7;
    private static final int DIALOGS_Y = 29;
    private static final int MONSTER_STATS_Y = 38;

    private static final String FLOOR_LEVEL_COLOR = "#eb564b";

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
        drawLevelInfo(gui, floor, hudX, LEVEL_INFO_Y);
        playerStatsViewer.draw(gui, floor.getPlayer().getStats(), hudX, PLAYER_STATS_Y);
        inventoryViewer.drawInventory(gui, floor.getPlayer(), hudX, INVENTORY_Y);
        interactionDialogManager.draw(gui, floor, hudX, DIALOGS_Y);

        if (floor.getLastAttackedMonster() != null) {
            monsterStatsViewer.draw(gui, floor.getLastAttackedMonster().getStats(), hudX, MONSTER_STATS_Y);
        }
    }

    private void drawLevelInfo(GUI gui, Floor floor, int x, int y) {
        gui.drawText(x, y, "*** FLOOR " + floor.getCurrLevel() + " ***", FLOOR_LEVEL_COLOR);
    }
}
