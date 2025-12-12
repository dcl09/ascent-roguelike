package Ascent.model.entities.interfaces;

public interface Interactable {
    boolean canInteract();
    void interact(Interactor interactor);
    //String getInteractionMessage();
}
