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
}
