package model.game.level;
import model.entities.*;
import model.game.Position;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Level {
    private final int width;
    private final int height;

    private Player player;

    private List<Monster> monsters;
    private List<Chest> chests;
    private Map<Position, Wall> walls;

    public Level(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer(){
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public List<Chest> getChests(){
        return chests;
    }

    public void setChests(List<Chest> chests){
        this.chests = chests;
    }

    public List<Wall> getWalls() {
        return List.copyOf(walls.values());
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls.stream()
                .collect(Collectors.toMap(Wall::getPosition, wall -> wall));
    }

    public boolean isWall(Position position) {
        return walls.containsKey(position);
    }

    public boolean isMonster(Position position) {
        for (Monster monster : monsters)
            if (monster.getPosition().equals(position))
                return true;
        return false;
    }
}
