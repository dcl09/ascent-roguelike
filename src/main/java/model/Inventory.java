package model;
import model.items.*;

import java.util.ArrayList;

public class Inventory {
    protected ArrayList<Item> items;
    /* protected int size; -> this might be useless so im leaving it commented */

    public Inventory(ArrayList<Item> items) {
        this.items = items;
    }

    public Inventory() {
        /* Player starts with an empty inventory? */
    }
    
    /* todo: check what else to add here  */

    public Item getItemAtSlot(int index) {
        return items.get(index);
    }

    public void changeItemAtSlot(Item item, int index) {
        items.set(index, item);
    }

}
