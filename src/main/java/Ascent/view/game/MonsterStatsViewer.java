package Ascent.view.game;

import Ascent.gui.GUI;

public class MonsterStatsViewer extends StatsViewer {
    private static final String TITLE_COLOR = "#FFD700";
    private static final String MONSTER_COLOR = "#FF4444";

    @Override
    protected void drawTitle(GUI gui, int x, int y) {
        gui.drawText(x, y, "=== MONSTER ===", TITLE_COLOR);
    }

    @Override
    protected String getHealthBarColor() {
        return MONSTER_COLOR;
    }

    @Override
    protected String getStatsColor() {
        return MONSTER_COLOR;
    }
}
