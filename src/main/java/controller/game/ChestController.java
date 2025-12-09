package controller.game;

import gui.ACTION;
import model.entities.Chest;
import model.entities.Monster;
import model.game.Position;
import model.game.floor.Floor;

public class ChestController extends GameController{
    //Todo: Add game & floor check for chest
    public ChestController(Floor floor){ super(floor); }

    public void step(Game game, ACTION action/*, long time*/){
        Position checkForChest = getModel().getPlayer().facing();
        //if getModel().isChest(checkForChest)
            for (Chest chest : getModel().getChests()) {
                if (chest.getPosition().equals(checkForChest)) {
                    if (chest.canInteract()) {
                        getModel().getPlayer().interactWith(chest);
                    }
                    break;
                }
            }
    }
}

