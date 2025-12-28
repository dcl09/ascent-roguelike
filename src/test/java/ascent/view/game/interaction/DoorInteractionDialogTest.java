package ascent.view.game.interaction;

import ascent.gui.GUI;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class DoorInteractionDialogTest {
    @Test
    public void testReturnWhenDoorIsNull() {
        GUI gui = mock(GUI.class);
        DoorInteractionDialog dialog = new DoorInteractionDialog();
        dialog.draw(gui, null, 10, 10);
        verifyNoInteractions(gui);
    }
}
