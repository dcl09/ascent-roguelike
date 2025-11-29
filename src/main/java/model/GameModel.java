package model;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import model.entities.Player;

import java.io.IOException;


public class GameModel {
    // todo: add new private variables and initialize in constructor.
    private final Player player;

    public GameModel(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()) {
            case KeyType.ArrowUp:
                //função de movimento
                break;
                case KeyType.ArrowDown:
                    //função de movimento
                    break;
                    case KeyType.ArrowLeft:
                        //função de movimento
                        break;
                        case KeyType.ArrowRight:
                            //função de movimento
                            break;
        }
    }
}
