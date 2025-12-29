package ascent.controller;

import ascent.Game;
import ascent.gui.ACTION;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerTest {

    @Test
    void testControllerStoresAndRetursModel() {

        Object model = new Object();
        Controller<Object> controller = new Controller<>(model) {
            @Override
            public void step(Game game, ACTION action, long time) throws IOException {
            }
        };

        assertEquals(model, controller.getModel());
    }
}
