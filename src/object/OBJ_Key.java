package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);
        name = "Key"; 

        down1 = setUp("objects/key", gp.tileSize, gp.tileSize);
        collision = true;
        description = "[" + name+ "]\nIt's for open treasure"; 
        price = 100;
    }
}
