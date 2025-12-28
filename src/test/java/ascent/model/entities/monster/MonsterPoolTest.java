package ascent.model.entities.monster;

import ascent.model.game.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
class MonsterPoolTest {

    @Nested
    class ConstructorTests {

        @Test
        void emptyConstructorCreatesInactiveMonster() {
            Monster monster = new Monster();

            assertEquals(new Position(0, 0), monster.getPosition());
            assertFalse(monster.isActive());
        }
    }


    @Nested
    class FunctionTests {

        private MonsterPool pool;

        // Pool is a singleton
        @BeforeEach
        void setUp() throws NoSuchFieldException, IllegalAccessException {
            Field instance = MonsterPool.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
            pool = MonsterPool.getInstance();
        }

        @Test
        void testPoolHasCorrectInitialSize() {
            assertEquals(100, pool.available());
        }

        @Test
        void getInstanceReturnsSameInstance() {
            MonsterPool pool1 = MonsterPool.getInstance();
            MonsterPool pool2 = MonsterPool.getInstance();
            assertSame(pool1, pool2);
        }

        @Test
        void acquireReturnsActiveMonster() {
            Monster monster = pool.acquire();
            assertTrue(monster.isActive());
        }

        @Test
        void acquireReducesAvailable() {
            int before = pool.available();
            Monster monster = pool.acquire();
            assertEquals(before - 1, pool.available());
        }

        @Test
        void releaseIncreasesAvailable() {
            Monster monster = pool.acquire();
            int before = pool.available();
            pool.release(monster);
            assertEquals(before + 1, pool.available());
        }

        @Test
        void releaseDeactivatesMonster() {
            Monster monster = pool.acquire();
            pool.release(monster);
            assertFalse(monster.isActive());
        }

        @Test
        void releaseNullDoesNotThrowOrChangeSize() {
            int initSize = pool.available();
            pool.release(null);
            assertEquals(initSize, pool.available());
        }

        @Test
        void availableReturnsNonNegative() {
            assertEquals(100, pool.available());
        }

        @Test
        void hasAvailableReturnsTrueWhenPoolNotEmpty() {
            assertTrue(pool.hasAvailable());
        }

        @Test
        void hasAvailableReturnsFalseWhenPoolEmpty() {
            java.util.List<Monster> acquired = new java.util.ArrayList<>();
            while (pool.hasAvailable()) {
                acquired.add(pool.acquire());
            }

            assertFalse(pool.hasAvailable());

            for (Monster m : acquired) {
                pool.release(m);
            }
        }

        @Test
        void acquireThrowsExceptionWhenPoolEmpty() {
            for (int i = 0; i < 100; i++) {
                pool.acquire();
            }
            assertThrows(MonsterPoolEmptyException.class, () -> pool.acquire());
        }
    }
}