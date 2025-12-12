package Ascent.model.game.floor;
import Ascent.model.entities.*;
import java.util.List;

public abstract class FloorBuilder {
    public Floor createFloor(Player player) {
        Floor floor = new Floor(getWidth(), getHeight());

        floor.setPlayer(player);
        floor.setMonsters(createMonsters());
        floor.setChests(createChests());
        floor.setWalls(createWalls());

        return floor;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected abstract List<Wall> createWalls();

    protected abstract List<Chest> createChests();

    protected abstract List<Monster> createMonsters();

}
