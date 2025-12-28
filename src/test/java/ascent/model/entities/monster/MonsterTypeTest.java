package ascent.model.entities.monster;

import ascent.model.entities.components.Stats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTypeTest {
    @Nested
    class MonsterTypeTests {
        MonsterType monsterType;

        @BeforeEach
        void setUp() {
            monsterType = MonsterType.GOBLIN;
        }

        @Test
        void monsterTypeHasCorrectStats(){
            assertEquals('g', monsterType.getSymbol());
            assertEquals("#5dde87", monsterType.getColor());
            assertEquals(30, monsterType.getBaseHealth());
            assertEquals(3, monsterType.getBaseDamage());
            assertEquals(3, monsterType.getBaseSpeed());
            assertEquals(10, monsterType.getAggroRange());
            assertEquals(500, monsterType.getBaseAttackCooldown());
        }

        @Test
        void createBaseStatsCreatesCorrectStats(){
            Stats stats = monsterType.createBaseStats();
            assertEquals(30, stats.getMaxHealth());
            assertEquals(30, stats.getHealth());
            assertEquals(3, stats.getDamage());
            assertEquals(3, stats.getSpeed());
        }

        @Test
        void getTypeFromCharReturnsCorrectType(){
            assertSame(MonsterType.GOBLIN, MonsterType.getTypeFromChar('g'));
            assertSame(MonsterType.ORC, MonsterType.getTypeFromChar('O'));
            assertSame(MonsterType.DRAGON, MonsterType.getTypeFromChar('R'));
            assertSame(MonsterType.SKELETON, MonsterType.getTypeFromChar('s'));
            assertSame(MonsterType.ZOMBIE, MonsterType.getTypeFromChar('Z'));
            assertNull(MonsterType.getTypeFromChar('?'));
        }
    }
}
