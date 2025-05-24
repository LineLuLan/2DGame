package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity{
    public static final String objName = "Latern";
    public OBJ_Lantern (GamePanel gp){
        super(gp);

        type = type_light;
        name = objName;
        down1 = setUp("objects/Lantern", gp.tileSize, gp.tileSize);
        description = "[Lantern]\nIlluminates your\nsurroundings";
        price = 200;
        lightRadius = 250;
        
    }
}
