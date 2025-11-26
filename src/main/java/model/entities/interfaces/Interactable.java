package model.entities.interfaces;

public interface Interactable {
    void interactWith(Interactable target);
    boolean canInteract();
}
