package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {
    public static final String objName = "Door";
    public OBJ_Door(GamePanel gp) {
        super(gp);
        
        type = type_obstacle;
        name = objName;
        down1 = setUp("objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDialogue();
    }
    public void setDialogue(){
        dialogues[0][0] = "You need a key to open this!";
    }

    public void interact(){
        startDialogue(this, 0);
        
    }
    
    

}
