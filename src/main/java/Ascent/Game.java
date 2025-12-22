package Ascent;

import Ascent.gui.GUI;
import Ascent.model.menu.StartMenu;
import Ascent.state.StartMenuState;
import Ascent.state.State;

import java.io.IOException;
import java.util.Stack;

public class Game {
    private final GUI gui;
    private Stack<State<?>> stateStack;

    public Game() throws IOException {
        this.gui = new GUI(115, 45);
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
        int FPS = 30;
        int frameTime = 1000 / FPS;

        stateStack.push(new StartMenuState(new StartMenu()));

        while (!stateStack.empty()) {
            long startTime = System.currentTimeMillis();

            State<?> currState = stateStack.peek();
            currState.step(this, gui, startTime);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // ignore the error
            }
        }
        gui.close();
    }
}
