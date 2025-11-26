package model.entities.interfaces;

public interface Interactor {
    void interactWith(Interactable target);
    boolean canInteract();
}
