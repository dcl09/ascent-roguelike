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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MonsterControllerTest {
    private Game game;
    private Floor floor;
    private Monster monster;
    private PathFinder pathFinder;
    private MonsterController monsterController;
    private Player player;
    private Position playerPos;
    private Position monsterPos;
    private Stats stats;

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

        when(floor.getMonsters()).thenReturn(List.of(monster));
        when(floor.getPlayer()).thenReturn(player);
        when(monster.getStats()).thenReturn(stats);
        when(monster.getPosition()).thenReturn(monsterPos);
        when(player.getPosition()).thenReturn(playerPos);

        configureMonsterState(true, false);
        when(monster.getMovementSpeed()).thenReturn(1);
        when(monster.getMonsterType()).thenReturn(MonsterType.GOBLIN);

        monsterController = new MonsterController(floor, pathFinder);
    }

    private void configureMonsterState(boolean isActive, boolean isDead) {
        when(monster.isActive()).thenReturn(isActive);
        when(stats.isDead()).thenReturn(isDead);
    }

    private void configurePositions(int mx, int my, int px, int py) {
        Position realMPos = spy(new Position(mx, my));
        Position realPPos = spy(new Position(px, py));
        
        when(monster.getPosition()).thenReturn(realMPos);
        when(player.getPosition()).thenReturn(realPPos);
        
        this.monsterPos = realMPos; 
        this.playerPos = realPPos;
    }

    private void mockMonsterTypeWithAggro(double range) {
        MonsterType type = mock(MonsterType.class);
        when(type.getAggroRange()).thenReturn(range);
        when(monster.getMonsterType()).thenReturn(type);
    }

    @Test
    void moveMonsterReturnValueIsPropagated() {
        Position from = new Position(0, 0);
        Position to = new Position(1, 0);

        when(floor.moveMonster(from, to)).thenReturn(true);
        assertTrue(monsterController.moveMonster(from, to));

        when(floor.moveMonster(from, to)).thenReturn(false);
        assertFalse(monsterController.moveMonster(from, to));
    }

    @Test
    void inactiveMonstersDoNotMove() {
        configureMonsterState(false, false);
        monsterController.step(game, ACTION.NONE, 1000L);
        verify(floor, never()).moveMonster(any(), any());
    }

    @Test
    void deadMonstersDoNotMove() {
        configureMonsterState(true, true);
        monsterController.step(game, ACTION.NONE, 1000L);
        verify(floor, never()).moveMonster(any(), any());
    }

    @Test
    void monsterMovesRandomlyOutsideAggroRange() {
        configurePositions(20, 20, 0, 0);
        mockMonsterTypeWithAggro(5.0);

        monsterController.step(game, ACTION.NONE, 1000L);

        verify(monsterPos).getRandomAdjacent();
        verify(pathFinder, never()).findNextStep(any(), any());
        verify(floor).moveMonster(eq(monsterPos), any());
    }

    @Test
    void monsterFollowsPathInsideAggroRange() {
        configurePositions(2, 2, 0, 0);
        mockMonsterTypeWithAggro(5.0);
        
        Position nextStep = new Position(1, 1);
        when(pathFinder.findNextStep(any(), any())).thenReturn(nextStep);

        monsterController.step(game, ACTION.NONE, 1000L);

        verify(pathFinder).findNextStep(any(), any());
        verify(floor).moveMonster(monsterPos, nextStep);
    }
    
    @Test
    void monsterAggroAtExactBoundary() {
        configurePositions(5, 5, 5, 10);
        mockMonsterTypeWithAggro(5.0);
        
        Position nextStep = new Position(5, 9);
        when(pathFinder.findNextStep(any(), any())).thenReturn(nextStep);

        monsterController.step(game, ACTION.NONE, 1000L);

        verify(pathFinder).findNextStep(any(), any());
        verify(floor).moveMonster(monsterPos, nextStep);
    }

    @Test
    void monsterMovesRandomlyWhenPathFinderFailsInsideAggro() {
        configurePositions(2, 2, 0, 0);
        mockMonsterTypeWithAggro(5.0);
        
        when(pathFinder.findNextStep(any(), any())).thenReturn(null);
        Position randomStep = new Position(3, 3);
        doReturn(randomStep).when(monsterPos).getRandomAdjacent();

        monsterController.step(game, ACTION.NONE, 1000L);

        verify(monsterPos).getRandomAdjacent();
        verify(floor).moveMonster(monsterPos, randomStep);
    }

    @Test
    void monsterRespectsCooldownBeforeMoving() {
        configurePositions(0, 0, 10, 10);
        mockMonsterTypeWithAggro(0.0);
        
        monsterController.step(game, ACTION.NONE, 549L);
        verify(floor, never()).moveMonster(any(), any());
        
        monsterController.step(game, ACTION.NONE, 551L);
        verify(floor, times(1)).moveMonster(any(), any());
    }

    @Test
    void monsterMovesWhenCooldownExactlyExpires() {
        configurePositions(0, 0, 10, 10);
        mockMonsterTypeWithAggro(0.0);
        
        monsterController.step(game, ACTION.NONE, 550L);
        verify(floor, times(1)).moveMonster(any(), any());
    }

    @Test
    void monsterRespectsCooldownBetweenConsecutiveMoves() {
        configurePositions(0, 0, 10, 10);
        mockMonsterTypeWithAggro(0.0);
        
        monsterController.step(game, ACTION.NONE, 1000L);
        verify(floor, times(1)).moveMonster(any(), any());
        
        monsterController.step(game, ACTION.NONE, 1200L);
        
        verify(floor, times(1)).moveMonster(any(), any());
    }

    @Test
    void monsterMovementCooldownScalesWithSpeed() {
        configurePositions(0, 0, 10, 10);
        when(monster.getMovementSpeed()).thenReturn(2);
        mockMonsterTypeWithAggro(0.0);

        monsterController.step(game, ACTION.NONE, 275L);
        verify(floor, times(1)).moveMonster(any(), any());
    }

    @Test
    void monsterAttacksWhenAdjacentAndCooldownReady() {
        configurePositions(5, 5, 5, 6);
        mockMonsterTypeWithAggro(10.0);
        
        when(monster.getAttackCooldown()).thenReturn(500L);
        when(pathFinder.findNextStep(any(), any())).thenReturn(playerPos);
        
        monsterController.step(game, ACTION.NONE, 600L); 
        
        verify(floor).moveMonster(monsterPos, playerPos);
    }
    
    @Test
    void monsterAttacksAtExactCooldownBoundary() {
        configurePositions(5, 5, 5, 6);
        mockMonsterTypeWithAggro(10.0);
        when(monster.getMovementSpeed()).thenReturn(2); // Ensure move ready
        
        when(monster.getAttackCooldown()).thenReturn(500L);
        when(pathFinder.findNextStep(any(), any())).thenReturn(playerPos);
        
        monsterController.step(game, ACTION.NONE, 500L);
        
        verify(floor).moveMonster(monsterPos, playerPos);
    }

    @Test
    void monsterDoesNotAttackIfCooldownNotReady() {
        configurePositions(5, 5, 5, 6);
        mockMonsterTypeWithAggro(10.0);
        
        when(monster.getAttackCooldown()).thenReturn(1000L);
        when(pathFinder.findNextStep(any(), any())).thenReturn(playerPos);
        
        monsterController.step(game, ACTION.NONE, 600L);
        
        verify(floor, never()).moveMonster(any(), any());
    }
    
    @Test
    void monsterDoesNotAttackBeforeCooldownExpiresWithHighSum() {
        configurePositions(5, 5, 5, 6);
        mockMonsterTypeWithAggro(10.0);
        when(monster.getMovementSpeed()).thenReturn(2); // Ensure move ready
        
        when(monster.getAttackCooldown()).thenReturn(1000L);
        when(pathFinder.findNextStep(any(), any())).thenReturn(playerPos);
        
        monsterController.step(game, ACTION.NONE, 1000L);
        verify(floor, times(1)).moveMonster(monsterPos, playerPos);
        
        monsterController.step(game, ACTION.NONE, 1500L);
        
        verify(floor, times(1)).moveMonster(monsterPos, playerPos);
    }

    @Test
    void monsterMovesInsteadOfAttackingIfTargetIsNotPlayer() {
        configurePositions(5, 5, 5, 7);
        mockMonsterTypeWithAggro(10.0);
        
        when(monster.getAttackCooldown()).thenReturn(2000L);
        
        Position stepPos = new Position(5, 6);
        when(pathFinder.findNextStep(any(), any())).thenReturn(stepPos);
        
        monsterController.step(game, ACTION.NONE, 1000L);
        
        verify(floor).moveMonster(monsterPos, stepPos);
    }
}