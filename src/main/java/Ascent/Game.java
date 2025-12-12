package Ascent;

import Ascent.gui.GUI;
import Ascent.model.entities.Player;
import Ascent.model.game.floor.FileLevelBuilder;
import Ascent.state.GameState;
import Ascent.state.State;

import java.io.IOException;
import java.util.Stack;

public class Game {
    private final GUI gui;
    private Stack<State<?>> stateStack;
    private Player player;

    public Game() throws IOException {
        this.gui = new GUI(40, 20);
        this.stateStack = new Stack<>();
        // Player init moved to start() to depend on level spawn
    }

    public static void main(String[] args) throws IOException {
        new Game().start();
    }

    public void pushState(State<?> state) {
        this.stateStack.push(state);
    }

    public void popState() {
        this.stateStack.pop();
    }

    public void start() throws IOException {
        // todo: implement time in this funct & implement menus
        // stateStack.push(new StartMenuState (new StartMenu))
        // Default level file
        String defaultLevelPath = "levels/level1.txt";
        FileLevelBuilder builder = new FileLevelBuilder(defaultLevelPath);
        // Find player spawn relative to level loaded
        this.player = new Player(builder.findPlayerSpawn());

        stateStack.push(new GameState(builder.createFloor(player)));
        while (!stateStack.empty()) {
            State<?> currState = stateStack.peek();
            currState.step(this, gui);
        }
        gui.close();
    }
}
