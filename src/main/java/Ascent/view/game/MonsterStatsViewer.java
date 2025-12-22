package Ascent.view.game;

import Ascent.gui.GUI;

public class MonsterStatsViewer extends StatsViewer {
    private static final String TITLE_COLOR = "#eb564b";
    private static final String MONSTER_COLOR = "#ffb5b5";
    private static final String HEALTH_COLOR = "#f05837";

    @Override
    protected int drawTitle(GUI gui, int x, int y) {
        gui.drawText(x, y, "=== MONSTER ===", TITLE_COLOR);
        return 1;
    }

    @Override
    protected String getHealthBarColor() {
        return HEALTH_COLOR;
    }

    @Override
    protected String getStatsColor() {
        return MONSTER_COLOR;
    }
}
