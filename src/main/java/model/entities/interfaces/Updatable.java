package model.entities.interfaces;

// entities that update over time (monsters...)
public interface Updatable {
    void update(long deltaTime);
}
