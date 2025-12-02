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

        while (pool.available() < 30) {
            pool.release(new Monster());
        }
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
        pool.release(monster);
    }

    @Test
    void acquireReducesAvailable() {
        int before = pool.available();
        Monster monster = pool.acquire();
        assertEquals(before - 1, pool.available());
        pool.release(monster);
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
    void releaseNullDoesNotThrow() {
        assertDoesNotThrow(() -> pool.release(null));
    }

    @Test
    void availableReturnsNonNegative() {
        assertTrue(pool.available() >= 0);
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
        java.util.List<Monster> acquired = new java.util.ArrayList<>();
        while (pool.hasAvailable()) {
            acquired.add(pool.acquire());
        }

        assertThrows(MonsterPoolEmptyException.class, () -> pool.acquire());

        for (Monster m : acquired) {
            pool.release(m);
        }
    }
}