package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {
    public static final String objName = "Mana Crystal";
    GamePanel gp;

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = objName;
        type = type_pickupOnly;
        value = 1;
        down1 = setUp("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setUp("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setUp("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    
    }

    public boolean  use(Entity entity) {
        gp.playSE(2);
        gp.ui.addMessage("You got " + name);
        entity.mana += value;
        return true;
    }
}
