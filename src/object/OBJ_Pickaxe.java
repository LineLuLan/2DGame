package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {
    public static  final String objName = "Pickaxe";
    public OBJ_Pickaxe(GamePanel gp){
        super(gp);

        type = type_pickaxe;
        name = objName;
        down1 = setUp("objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue  = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name+ "]\nA bit rusty but\ncan cut dig wall."; 
        price = 5;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 45;
        
    }
}
