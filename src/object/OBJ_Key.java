package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    public static final String objName = "Key";
    GamePanel gp;
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName; 

        down1 = setUp("objects/key", gp.tileSize, gp.tileSize);
        collision = true;
        description = "[" + name+ "]\nIt's for open treasure"; 
        price = 2;
        stackable = true;
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] =  "You use the " + name + " and open the door";

        dialogues[1][0] = "What are you doing";
    }
    public boolean use(Entity entity){

        int objIndex = getDetected(entity, gp.obj,"Door");

        if(objIndex != 999){
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentmap][objIndex] = null;
            return true;
        }
        else{
            startDialogue(this, 1);
            return false;
        }
    }
}
