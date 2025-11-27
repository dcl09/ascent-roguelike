package model.entities;
import model.entities.interfaces.Interactable;
import model.entities.interfaces.Interactor;

import java.util.ArrayList;
import java.util.List;

public class Chest implements Interactable {
    private static final char CLOSED_SYMBOL = 'C';
    private static final char OPEN_SYMBOL = 'O';

    //todo: Implementar as list de items

    private boolean opened;


    @Override
    public void interact(Interactor interactor) {
        // todo: Implementar interação com o Player
    }

    @Override
    public boolean canInteract() {
        return !opened;
    }

    @Override
    public String getInteractionMessage() {
        // todo: Ver se é necessário
        return "";
    }
}
