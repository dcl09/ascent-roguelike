package model.entities.interfaces;

public interface Interactable {
    void interact(Interactor interactor);
    boolean canInteract();
    String getInteractionMessage();
}
