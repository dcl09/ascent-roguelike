package ascent;

import ascent.gui.GUI;
import ascent.model.menu.GameMenu;
import ascent.state.GameMenuState;
import ascent.state.State;

import java.io.IOException;
import java.util.Stack;

public class Game {
    private final GUI gui;
    private Stack<State<?>> stateStack;

    public Game(GUI gui) {
        this.gui = gui;
        this.stateStack = new Stack<>();
    }

    public static void main(String[] args) throws IOException {
        new Game(new GUI(115, 45)).start();
    }

    public void pushState(State<?> state) {
        this.stateStack.push(state);
    }

    public void popState() {
        this.stateStack.pop();
    }

    public void start() throws IOException {
        int FPS = 60;
        int frameTime = 1000 / FPS;

        if (stateStack.isEmpty()) {
            pushState(new GameMenuState(new GameMenu(false)));
        }

        while (!stateStack.empty()) {
            long startTime = getCurrentTime();

            State<?> currState = stateStack.peek();
            currState.step(this, gui, startTime);

            long elapsedTime = getCurrentTime() - startTime;
            long sleepTime = frameTime - elapsedTime;

            try {
                if (sleepTime > 0)
                    sleepExecution(sleepTime);
            } catch (InterruptedException e) {
                // ignore the error
            }
        }
        gui.close();
    }

    protected void sleepExecution(long time) throws InterruptedException {
        Thread.sleep(time);
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
