package model.items;

public class Item {
    //protected int level = 1; // Just an idea for now, no scaling implemented yet
    protected final String name;
    protected final Integer id; // For making choosing a random item easier

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
