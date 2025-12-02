package model.game.floor;
import model.entities.*;
import java.util.List;

public abstract class FloorBuilder {
    public Floor createFloor(Player player) {
        Floor floor = new Floor(getWidth(), getHeight());

        floor.setPlayer(player);
        floor.setMonsters(createMonsters());
        /*
        NOT IMPLEMENTED
        floor.setChests(createChests());
         */
        floor.setWalls(createWalls());

        return floor;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> createWalls();
    /*
    NOT IMPLEMENTED
    protected abstract List<Chests> createChests();
    */
    protected abstract List<Monster> createMonsters();

}
