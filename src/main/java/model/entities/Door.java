package model.entities;

public class Door {
    private static final char CLOSED_SYMBOL = 'X';
    private static final char OPEN_SYMBOL = '0';

    private boolean isOpen;

    public Door(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close(){
        this.isOpen = false;
    }

    public void open(){
        this.isOpen = true;
    }
}
