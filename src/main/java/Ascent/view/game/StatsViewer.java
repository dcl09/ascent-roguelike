package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.monster.Monster;
import Ascent.model.entities.Player;
import Ascent.model.entities.components.Stats;

public class StatsViewer {
    private static final String PLAYER_COLOR = "#4488FF"; // Blue
    private static final String MONSTER_COLOR = "#FF4444"; // Red

    public void drawStats(GUI gui, int x, int y, Stats stats, String color) {
        gui.drawText(x, y + 1, "HP:  " + stats.getHealth() + "/" + stats.getMaxHealth(), color);
        gui.drawText(x, y + 2, "DMG: " + stats.getDamage(), color);
        gui.drawText(x, y + 3, "SPD: " + stats.getSpeed(), color);
        gui.drawText(x, y + 4, "ARM: " + stats.getResistanceToDamage(), color);
    }

    public void drawPlayerStats(GUI gui, Player player, int x, int y) {
        if (player == null)
            return;
        Stats stats = player.getStats();
        String color = PLAYER_COLOR;

        gui.drawText(x, y, "--- PLAYER ---", color);
        drawStats(gui, x, y, stats, color);
    }

    public void drawMonsterStats(GUI gui, Monster monster, int x, int y) {
        if (monster == null || monster.getStats().isDead()) {
            return;
        }

        Stats stats = monster.getStats();
        String color = MONSTER_COLOR;

        gui.drawText(x, y, "--- MONSTER ---", color);
        drawStats(gui, x, y, stats, color);
    }

}
