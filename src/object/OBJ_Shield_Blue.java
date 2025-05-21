package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
    public OBJ_Shield_Blue(GamePanel gp){
        super(gp);

        type = type_shield;
        name = "Blue Shield";
        down1 = setUp("objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseValue  = 2;
        description = "[" + name+ "]\nMade by iron."; 
        price = 250; 
    }
}