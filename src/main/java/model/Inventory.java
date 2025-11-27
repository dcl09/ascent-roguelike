package model;
import model.items.*;

import java.util.ArrayList;

public class Inventory {
    protected ArrayList<Item> items;
    protected int maxSize;

    public Inventory(ArrayList<Item> items) {
        this.items = items;
    }

    public Inventory() {
        /* Player starts with an empty inventory? */
    }

    /* todo: check what else to add here  */

    public Item getItemAtSlot(int index) {
        return this.items.get(index);
    }

    public void changeItemAtSlot(Item item, int index) {
        this.items.set(index, item);
    }

    public boolean addItem(Item item) {
        if (this.items.size() < maxSize) {
            this.items.add(item);
            return true;
        } else return false;
    }
}
