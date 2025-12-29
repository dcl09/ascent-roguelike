package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.entities.components.Stats;
import ascent.model.entities.monster.Monster;
import ascent.model.entities.monster.MonsterType;
import ascent.model.game.PathFinder;
import ascent.model.game.Position;
import ascent.model.game.floor.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class MonsterControllerTest {
    Game game;
    Floor floor;
    Monster monster;
    PathFinder pathFinder;
    MonsterController monsterController;
    Player player;
    Position playerPos;
    Position monsterPos;
    Stats stats;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        floor = mock(Floor.class);
        monster = mock(Monster.class);
        pathFinder = mock(PathFinder.class);
        player = mock(Player.class);
        playerPos = mock(Position.class);
        monsterPos = mock(Position.class);
        stats = mock(Stats.class);

        // return monster to floor
        when(floor.getMonsters()).thenReturn(List.of(monster));
        when(monster.getStats()).thenReturn(stats);
        when(monster.getPosition()).thenReturn(monsterPos);
        when(monster.getMonsterType()).thenReturn(MonsterType.GOBLIN);

        // player stubs
        when(floor.getPlayer()).thenReturn(player);
        when(player.getPosition()).thenReturn(playerPos);

        monsterController = new MonsterController(floor, pathFinder);
    }

    @Test
    void inactiveMonstersDoNotMove() {
        when(monster.isActive()).thenReturn(false);

        monsterController.step(game, ACTION.NONE, 1000L);
        verify(floor, never()).moveMonster(any(), any());
    }

    @Test
    void deadMonstersDoNotMove() {
        when(monster.isActive()).thenReturn(true);
        when(stats.isDead()).thenReturn(true);

        monsterController.step(game, ACTION.NONE, 1000L);
        verify(floor, never()).moveMonster(any(Position.class), any(Position.class));
    }
    
    @Test
    void monsterDoesNotAggroOutsideRange() {
        when(monster.isActive()).thenReturn(true);
        when(stats.isDead()).thenReturn(false);
        when(monster.getPosition()).thenReturn(monsterPos);
        when(monsterPos.getX()).thenReturn(11);
        when(monsterPos.getY()).thenReturn(11);
        when(monsterPos.getRandomAdjacent()).thenReturn(mock(Position.class));

        when(player.getPosition()).thenReturn(new Position(1,1));

        when(pathFinder.findNextStep(any(), any())).thenReturn(null);

        monsterController.step(game, ACTION.NONE, 1000L);
        verify(monsterPos, times(1)).getRandomAdjacent();
    }

    @Test
    void monsterAggroInsideRange() {
        when(monster.isActive()).thenReturn(true);
        when(stats.isDead()).thenReturn(false);
        when(monster.getPosition()).thenReturn(monsterPos);
        when(monsterPos.getX()).thenReturn(3);
        when(monsterPos.getY()).thenReturn(3);
        when(monsterPos.getRandomAdjacent()).thenReturn(mock(Position.class));

        when(player.getPosition()).thenReturn(new Position(1,1));

        when(pathFinder.findNextStep(any(), any())).thenReturn(new Position(1,2));

        monsterController.step(game, ACTION.NONE, 1000L);
        verify(monsterPos, never()).getRandomAdjacent();
    }

    @Test
    void monsterDoesNotMoveBeforeCooldownExpires() {
        when(monster.isActive()).thenReturn(true);
        when(stats.isDead()).thenReturn(false);
        when(monster.getMovementSpeed()).thenReturn(1);
        when(monster.getPosition()).thenReturn(monsterPos);
        when(monsterPos.getX()).thenReturn(0);
        when(monsterPos.getY()).thenReturn(1);
        when(monsterPos.getRandomAdjacent()).thenReturn(new Position(0, 2));
        MonsterType monsterType = mock(MonsterType.class);
        when(monster.getMonsterType()).thenReturn(monsterType);
        when(monsterType.getAggroRange()).thenReturn(0.0);



        when(player.getPosition()).thenReturn(new Position(0, 0));

        monsterController.step(game, ACTION.NONE, 600L);
        monsterController.step(game, ACTION.NONE, 800L);

        verify(floor, times(1)).moveMonster(any(), any());
    }

    @Test
    void monsterMovesWhenCooldownExactlyExpires() {
        when(monster.isActive()).thenReturn(true);
        when(stats.isDead()).thenReturn(false);
        when(monster.getMovementSpeed()).thenReturn(1);
        when(monster.getPosition()).thenReturn(monsterPos);
        when(monsterPos.getX()).thenReturn(0);
        when(monsterPos.getY()).thenReturn(1);
        when(monsterPos.getRandomAdjacent()).thenReturn(new Position(0, 2));
        MonsterType monsterType = mock(MonsterType.class);
        when(monster.getMonsterType()).thenReturn(monsterType);
        when(monsterType.getAggroRange()).thenReturn(0.0);

        when(player.getPosition()).thenReturn(new Position(0, 0));

        monsterController.step(game, ACTION.NONE, 0L);
        monsterController.step(game, ACTION.NONE, 550L);

        verify(floor, times(2)).moveMonster(any(), any());
    }


}
