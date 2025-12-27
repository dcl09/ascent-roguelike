package ascent.view.game;

import ascent.gui.GUI;

public class PlayerStatsViewer extends StatsViewer {
    private static final String PLAYER_COLOR = "#6476e8";
    private static final String STATS_COLOR = "#86a7ed";
    private static final String HEALTH_COLOR = "#3ca370";

    @Override
    protected int drawTitle(GUI gui, int x, int y) {
        gui.drawText(x, y, "*** PLAYER ***", PLAYER_COLOR);
        gui.drawText(x, y + 2, "=== STATS ===", STATS_COLOR);
        return 3;
    }

    @Override
    protected String getHealthBarColor() {
        return HEALTH_COLOR;
    }

    @Override
    protected String getStatsColor() {
        return STATS_COLOR;
    }
}
