package ascent.controller.game;

import ascent.Game;
import ascent.gui.ACTION;
import ascent.model.entities.Player;
import ascent.model.entities.components.Stats;
import ascent.model.entities.monster.Monster;
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
    Stats stats;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        floor = mock(Floor.class);
        monster = mock(Monster.class);
        pathFinder = mock(PathFinder.class);
        player = mock(Player.class);
        playerPos = mock(Position.class);
        stats = mock(Stats.class);

        // return monster to floor
        when(floor.getMonsters()).thenReturn(List.of(monster));
        when(monster.getStats()).thenReturn(stats);
        when(monster.getPosition()).thenReturn(mock(Position.class));

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


}
