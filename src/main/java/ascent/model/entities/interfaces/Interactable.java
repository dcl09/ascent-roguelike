package ascent.model.entities.interfaces;

public interface Interactable {
    boolean canInteract();

    default void interact(Interactor interactor) {
    }

    String getInteractionMessage();
}
