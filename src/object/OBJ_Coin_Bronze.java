package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    public static final String objName = "Bronze Coin";
    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickupOnly;
        value = 1;
        name = objName;
        down1 = setUp("objects/coin_bronze", gp.tileSize, gp.tileSize);
    }

    public boolean use(Entity entity) {
        gp.playSE(1);
        gp.ui.addMessage("You got " + value + " " + name + "(s)");
        entity.coin += value;
        return true;
    }

}
