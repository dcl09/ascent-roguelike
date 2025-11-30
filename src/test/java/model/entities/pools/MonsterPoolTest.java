package model.entities.pools;

import model.entities.Monster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterPoolTest {

    private MonsterPool pool;

    // Pool is a singleton
    @BeforeEach
    void setUp() {
        pool = MonsterPool.getInstance();
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
        if (monster != null) {
            assertTrue(monster.isActive());
            pool.release(monster); // Reset pool state
        }
    }

    @Test
    void acquireReducesAvailable() {
        int before = pool.available();
        Monster monster = pool.acquire();
        if (monster != null) {
            assertEquals(before - 1, pool.available());
            pool.release(monster);
        }
    }

    @Test
    void releaseIncreasesAvailable() {
        Monster monster = pool.acquire();
        if (monster != null) {
            int before = pool.available();
            pool.release(monster);
            assertEquals(before + 1, pool.available());
        }
    }

    @Test
    void releaseDeactivatesMonster() {
        Monster monster = pool.acquire();
        if (monster != null) {
            pool.release(monster);
            assertFalse(monster.isActive());
        }
    }

    @Test
    void releaseNullDoesNotThrow() {
        assertDoesNotThrow(() -> pool.release(null));
    }

    @Test
    void availableReturnsNonNegative() {
        assertTrue(pool.available() >= 0);
    }
}
