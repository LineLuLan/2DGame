package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Key"; 

        down1 = setUp("objects/key", gp.tileSize, gp.tileSize);
        collision = true;
        description = "[" + name+ "]\nIt's for open treasure"; 
        price = 100;
    }
    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj,"Door");

        if(objIndex != 999){
            gp.ui.currentDialogue = "You use the " + name + " and open the door";
            gp.playSE(3);
            gp.obj[gp.currentmap][objIndex] = null;
            return true;
        }
        else{
            gp.ui.currentDialogue = "What are you doing";
            return false;
        }
    }
}
