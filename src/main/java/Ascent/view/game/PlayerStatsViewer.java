package Ascent.view.game;

import Ascent.gui.GUI;

public class PlayerStatsViewer extends StatsViewer {
    private static final String TITLE_COLOR = "#FFD700";
    private static final String PLAYER_COLOR = "#4488FF";

    @Override
    protected void drawTitle(GUI gui, int x, int y) {
        gui.drawText(x, y, "=== PLAYER ===", TITLE_COLOR);
    }

    @Override
    protected String getHealthBarColor() {
        return PLAYER_COLOR;
    }

    @Override
    protected String getStatsColor() {
        return PLAYER_COLOR;
    }
}
