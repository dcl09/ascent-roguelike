package Ascent.model.game.floor;

import Ascent.model.entities.*;
import Ascent.model.entities.monster.Monster;
import Ascent.model.entities.monster.MonsterPool;
import Ascent.model.entities.monster.MonsterPoolEmptyException;
import Ascent.model.entities.monster.MonsterType;
import Ascent.model.game.Position;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// todo: add actual monsters to the legend (im lazy)
/*
    Legend:
 - '#' = Wall
 - '@' = Player spawn
 - 'M' = Monster
 - 'C' = Chest (random item)
 - '1'-'9' = Chest with specific item (1=Small Potion, 11=Sword, etc...)
 - '.' or ' ' = Empty space
 - 'D' = Door
 - '=' = Stairs
 */

public class FileLevelBuilder extends FloorBuilder {

    private final char[][] grid;
    private final int width;
    private final int height;
    private final MonsterPool monsterPool;

    private final List<Wall> cachedWalls = new ArrayList<>();
    private final List<Monster> cachedMonsters = new ArrayList<>();
    private final List<Chest> cachedChests = new ArrayList<>();
    private final List<Door> cachedDoors = new ArrayList<>();
    private Stairs cachedStairs;

    private static final char WALL = '#';
    private static final char PLAYER = '@';
    private static final char CHEST = 'C';
    private static final char EMPTY = '.';
    private static final char DOOR = 'D';
    private static final char STAIRS = '=';

    public FileLevelBuilder(String filePath) throws IOException {
        this(Path.of(filePath));
    }

    public FileLevelBuilder(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        this.grid = parseGrid(lines);
        this.height = grid.length;
        this.width = grid[0].length;
        this.monsterPool = MonsterPool.getInstance();
        parseEntities();
    }

    public FileLevelBuilder(String[] levelLines) {
        List<String> lines = List.of(levelLines);
        this.grid = parseGrid(lines);
        this.height = grid.length;
        this.width = grid[0].length;
        this.monsterPool = MonsterPool.getInstance();
        parseEntities();
    }

    private char[][] parseGrid(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Empty level");
        }

        int maxWidth = 0;
        for (String line : lines) {
            if (line.length() > maxWidth) {
                maxWidth = line.length();
            }
        }

        char[][] result = new char[lines.size()][maxWidth];

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < maxWidth; x++) {
                if (x < line.length()) {
                    result[y][x] = line.charAt(x);
                } else {
                    result[y][x] = EMPTY;
                }
            }
        }

        return result;
    }

    @Override
    protected int getWidth() {
        return width;
    }

    @Override
    protected int getHeight() {
        return height;
    }

    private void parseEntities() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = grid[y][x];
                Position position = new Position(x, y);

                if (c == WALL) {
                    cachedWalls.add(new Wall(position));
                } else if (c == DOOR) {
                    cachedDoors.add(new Door(position));
                } else if (c == STAIRS) {
                    cachedStairs = new Stairs(position);
                } else if (c == CHEST) {
                    cachedChests.add(new Chest(position, "#f2a65e"));
                } else if (Character.isDigit(c)) {
                    int itemId = Character.getNumericValue(c);
                    cachedChests.add(new Chest(position, "#f2a65e", itemId));
                } else if (MonsterType.getTypeFromChar(c) != null) {
                    try {
                        Monster monster = monsterPool.acquire();
                        monster.reset(MonsterType.getTypeFromChar(c), position);
                        cachedMonsters.add(monster);
                    } catch (MonsterPoolEmptyException e) {
                        System.err.println("Monsters pool is empty at (" + x + "," + y + ")");
                    }
                }
            }
        }
    }

    @Override
    protected List<Wall> getWalls() {
        return cachedWalls;
    }

    @Override
    protected List<Monster> getMonsters() {
        return cachedMonsters;
    }

    @Override
    protected List<Chest> getChests() {
        return cachedChests;
    }

    @Override
    protected List<Door> getDoors() {
        return cachedDoors;
    }

    @Override
    protected Stairs getStairs() {
        return cachedStairs;
    }

    public Position findPlayerSpawn() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == PLAYER) {
                    return new Position(x, y);
                }
            }
        }
        // Default: center of the map
        return new Position(width / 2, height / 2);
    }
}
