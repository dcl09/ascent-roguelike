package Ascent.model.entities.interfaces;

public interface Interactable {
    default boolean canInteract() {
        return true;
    }

    default void interact(Interactor interactor) {
    }

    String getInteractionMessage();
}
