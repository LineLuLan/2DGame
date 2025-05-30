package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public static final String objName = "Blue Shield";
    public OBJ_Shield_Blue(GamePanel gp){
        super(gp);

        type = type_shield;
        name = objName;
        down1 = setUp("objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue  = 3;
        description = "[" + name+ "]\nMade by iron."; 
        price = 5; 
    }
}