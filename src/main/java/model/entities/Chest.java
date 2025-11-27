package model.entities;
import model.entities.interfaces.Interactable;
import model.entities.interfaces.Interactor;

import java.util.ArrayList;
import java.util.List;

public class Chest implements Interactable {
    private static final char CLOSED_SYMBOL = 'C';
    private static final char OPEN_SYMBOL = 'O';

    //todo: Colocar item proveniente da Object Pool

    private boolean opened;

    @Override
    public boolean canInteract() {
        return !opened;
    }

    @Override
    public void interact(Interactor interactor) {
        if (!opened && interactor instanceof Player) {
            opened = true;
            Player player = (Player) interactor;
            // todo: Add interaction with player
        }
        // todo: Add interaction to the ui informing what item was found
    }

    public String getInteractionMessage() {
        return (opened) ? "Empty chest" : "Press E to open";
    }
}
