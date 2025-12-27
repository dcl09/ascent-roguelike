package ascent.view.game;

import ascent.gui.GUI;
import ascent.model.entities.components.Stats;
import ascent.view.game.components.HealthBarViewer;

public abstract class StatsViewer {
    protected final HealthBarViewer healthBarViewer;

    public StatsViewer() {
        this.healthBarViewer = new HealthBarViewer();
    }

    public void draw(GUI gui, Stats stats, int x, int y) {
        if (stats == null || stats.isDead())
            return;

        int linesInTitle = drawTitle(gui, x, y);
        healthBarViewer.draw(gui, stats, x, y + linesInTitle + 1, getHealthBarColor());
        drawStats(gui, stats, x, y + linesInTitle + 2);
    }

    protected void drawStats(GUI gui, Stats stats, int x, int y) {
        String color = getStatsColor();
        gui.drawText(x, y, String.format("DMG: %-4d  SPD: %-3d  ARM: %d",
                stats.getDamage(), stats.getSpeed(), stats.getResistanceToDamage()), color);
    }

    protected abstract int drawTitle(GUI gui, int x, int y);

    protected abstract String getHealthBarColor();

    protected abstract String getStatsColor();
}
