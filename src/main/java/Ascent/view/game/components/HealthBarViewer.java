package Ascent.view.game.components;

import Ascent.gui.GUI;
import Ascent.model.entities.components.Stats;

public class HealthBarViewer {
    private static final int DEFAULT_BAR_LENGTH = 20;

    public void draw(GUI gui, Stats stats, int x, int y, String color) {
        draw(gui, stats, x, y, color, DEFAULT_BAR_LENGTH);
    }

    public void draw(GUI gui, Stats stats, int x, int y, String color, int barLength) {
        int current = stats.getHealth();
        int max = stats.getMaxHealth();
        int filled = (int) ((float) current / max * barLength);

        StringBuilder bar = new StringBuilder("HP: [");
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "█" : "░");
        }
        bar.append("] ").append(current).append("/").append(max);

        gui.drawText(x, y, bar.toString(), color);
    }
}
