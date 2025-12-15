package Ascent.view.game;

import Ascent.gui.GUI;
import Ascent.model.entities.monster.Monster;
import Ascent.model.entities.monster.MonsterType;
import Ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MonsterViewerTest {
    private MonsterViewer monsterViewer;
    private GUI gui;

    @BeforeEach
    void setup() {
        monsterViewer = new MonsterViewer();
        gui = Mockito.mock(GUI.class);
    }

    private Monster createMonster(Position pos) {
        Monster monster = new Monster();
        monster.reset(MonsterType.GOBLIN, pos);
        return monster;
    }

    @Test
    void drawMonsterAtValidPosition() {
        Monster monster = createMonster(new Position(5, 8));
        monsterViewer.draw(monster, gui);
        Mockito.verify(gui).drawChar(5, 8, 'g', "GREEN");
    }

    @Test
    void drawMonsterAfterMovingOnce() {
        Monster monster = createMonster(new Position(5, 5));
        monster.moveDown();

        monsterViewer.draw(monster, gui);
        Mockito.verify(gui).drawChar(5, 6, 'g', "GREEN");
    }

    @Test
    void drawMonsterAfterMultipleMoves() {
        Monster monster = createMonster(new Position(10, 10));

        monster.moveUp();
        monster.moveUp();
        monster.moveRight();
        monster.moveDown();
        monsterViewer.draw(monster, gui);

        // 10,10 -> 10,9 -> 10,8 -> 11,8 -> 11,9
        Mockito.verify(gui).drawChar(11, 9, 'g', "GREEN");
    }

    @Test
    void drawMonsterWithModifiedColor() {
        Monster monster = createMonster(new Position(5, 5));
        monster.setColor("BLUE");

        monsterViewer.draw(monster, gui);
        Mockito.verify(gui).drawChar(5, 5, 'g', "BLUE");
    }
}