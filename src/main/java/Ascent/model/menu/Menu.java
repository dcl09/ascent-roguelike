package Ascent.model.menu;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    protected final List<String> entries;
    protected int currEntry = 0;

    public Menu() {
        entries = new ArrayList<>();
    }

    public void nextEntry() {
        currEntry++;
        if  (currEntry > entries.size() - 1)
            currEntry = 0;
    }

    public void prevEntry() {
        currEntry--;
        if  (currEntry < 0)
            currEntry = entries.size() - 1;
    }

    public String getCurrEntry() {
        return entries.get(currEntry);
    }

    public int getNumberEntries(){
        return entries.size();
    }

    public boolean isSelected(int i){
        return currEntry == i;
    }

    public String getEntry(int i) {
        return entries.get(i);
    }
}
