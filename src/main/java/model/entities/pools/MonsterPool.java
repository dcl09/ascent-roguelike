package model.entities.pools;

import model.entities.Monster;
import java.util.ArrayDeque;
import java.util.Deque;

public class MonsterPool {
    private static MonsterPool instance;
    private final Deque<Monster> pool;
    private static final int SIZE = 30;

    private MonsterPool() {
        pool = new ArrayDeque<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            pool.push(new Monster());
        }
    }

    public static MonsterPool getInstance() {
        if (instance == null) {
            instance = new MonsterPool();
        }
        return instance;
    }

    public Monster acquire() {
        if (pool.isEmpty()){
            throw new MonsterPoolEmptyException();
        }
        Monster m = pool.pop();
        m.activate();
        return m;
    }

    public boolean hasAvailable() {
        return !pool.isEmpty();
    }

    public void release(Monster monster) {
        if (monster == null) return;
        monster.deactivate();
        pool.push(monster);
    }

    public int available() {
        return pool.size();
    }
}