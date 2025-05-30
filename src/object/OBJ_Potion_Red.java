package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    public static final String objName = "Red Potion";
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = objName;
        type = type_consumable; 
        value = 5;
        down1 = setUp("objects/potion_red", gp.tileSize, gp.tileSize);
        collision = true;
        description = "[" + name+ "]\nRestore Hp by " + value + "."; 
        price = 2;
        stackable = true;
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You drink the " + name + "!\n"
            + "Your life has been recovered by " + value +".";
            
    }
    public boolean  use(Entity entity){
        
        startDialogue(this, 0);
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}