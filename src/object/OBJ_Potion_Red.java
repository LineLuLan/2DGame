package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;
        name = "Red Potion";
        type = type_consumable; 
        value = 5;
        down1 = setUp("objects/potion_red", gp.tileSize, gp.tileSize);
        collision = true;
        description = "[" + name+ "]\nRestore Hp by " + value + "."; 
        price = 25;
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
            + "Your life has been recovered by " + value +".";
        entity.life += value;
        gp.playSE(2);
    }
}