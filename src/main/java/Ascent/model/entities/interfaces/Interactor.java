package Ascent.model.entities.interfaces;

public interface Interactor {
    boolean canInteract();
    void interactWith(Interactable target);
}
