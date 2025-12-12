package Ascent;

import Ascent.gui.GUI;
import Ascent.model.entities.Player;
import Ascent.model.game.Position;
import Ascent.model.game.floor.BaseplateBuilder;
import Ascent.model.game.floor.Floor;
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
        this.player = new Player(new Position(20, 10));
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

        /* some "inspiration" from hero-solid for time functions */

        int FPS = 60; // for now, 10 fps feels sluggish and unnecessary, it slows the game down for no reason (computations are fast, for now)
        int frameTime = 1000 / FPS;

        // stateStack.push(new StartMenuState (new StartMenu))
        stateStack.push(new GameState(new BaseplateBuilder(40, 20, 4, 1).createFloor(player)));
        while (!stateStack.empty()) {
            /* get time at the start of the loop, do the step, get the end time and the difference is the elapsed time */
            long startTime = System.currentTimeMillis();

            State<?> currState = stateStack.peek();
            currState.step(this, gui);

            long elapsedTime = System.currentTimeMillis() - startTime;

            /* sleepTime is just the leftover time for the current iteration */
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0) Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // throw new RuntimeException(e); -> this is irrelevant, if time is 0 (or less for some quantum reason) we just don't sleep and ignore the error
            }
        }
        gui.close();
    }
}

