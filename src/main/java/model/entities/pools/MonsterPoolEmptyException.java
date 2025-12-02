package model.entities.pools;

public class MonsterPoolEmptyException extends RuntimeException {

    public MonsterPoolEmptyException() {
        super("Monster pool is empty");
    }

    public MonsterPoolEmptyException(String message) {
        super(message);
    }
}