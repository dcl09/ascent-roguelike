package Ascent.model.game;
public interface IPathFinder {
    Position findNextStep(Position start, Position target);
}
