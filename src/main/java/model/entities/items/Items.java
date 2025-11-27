package model.entities.items;

public class Items {
    protected int level = 1; // Just an idea for now, no scaling implemented yet

    Items(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
