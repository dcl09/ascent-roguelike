package model.game.level;

import model.entities.*;
import model.entities.pools.MonsterPool;
import model.entities.pools.MonsterPoolEmptyException;
import model.game.Position;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/* 
    Legend:
 - '#' = Wall
 - '@' = Player spawn
 - 'M' = Monster
 - 'C' = Chest (random item)
 - '1'-'9' = Chest with specific item (1=Small Potion, 11=Sword, etc...)
 - '.' or ' ' = Empty space
 */

public class FileLevelBuilder extends LevelBuilder {

    private final char[][] grid;
    private final int width;
    private final int height;
    private final MonsterPool monsterPool;

    private static final char WALL = '#';
    private static final char PLAYER = '@';
    private static final char MONSTER = 'M';
    private static final char CHEST = 'C';
    private static final char EMPTY = '.';

    public FileLevelBuilder(String filePath) throws IOException {
        this(Path.of(filePath));
    }

    public FileLevelBuilder(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        this.grid = parseGrid(lines);
        this.height = grid.length;
        this.width = grid[0].length;
        this.monsterPool = MonsterPool.getInstance();
    }

    public FileLevelBuilder(String[] levelLines) {
        List<String> lines = List.of(levelLines);
        this.grid = parseGrid(lines);
        this.height = grid.length;
        this.width = grid[0].length;
        this.monsterPool = MonsterPool.getInstance();
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

    @Override
    protected List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == WALL) {
                    walls.add(new Wall(new Position(x, y)));
                }
            }
        }

        return walls;
    }

    @Override
    protected List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (grid[y][x] == MONSTER) {
                    try {
                        Monster monster = monsterPool.acquire();
                        monster.reset(new Position(x, y));
                        monsters.add(monster);
                    } catch (MonsterPoolEmptyException e) {
                        System.err.println("Monsters pool is empty at (" + x + "," + y + ")");
                    }
                }
            }
        }

        return monsters;
    }

    @Override
    protected List<Chest> createChests() {
        List<Chest> chests = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = grid[y][x];

                if (c == CHEST) {
                    chests.add(new Chest(new Position(x, y), "YELLOW"));
                } else if (Character.isDigit(c)) {
                    int itemId = Character.getNumericValue(c);
                    chests.add(new Chest(new Position(x, y), "YELLOW", itemId));
                }
            }
        }

        return chests;
    }

    // Find the player spawn position

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