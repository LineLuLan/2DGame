package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    GamePanel gp;
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Heart";
        type = type_pickupOnly;
        value = 2;
        
        down1 = setUp("objects/heart_full", gp.tileSize, gp.tileSize);

        image = setUp("objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setUp("objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setUp("objects/heart_blank", gp.tileSize, gp.tileSize);
    }

    @Override
    public boolean use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("You got " + value + " " + name + "(s)");
        entity.life += value;
        return true;
    }
}
