package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Blue_Heart extends Entity {

    GamePanel gp;
    public static final String objName = "Blue Heart";

    public OBJ_Blue_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        down1 = setUp("/objects/blueheart",gp.tileSize,gp.tileSize);

        setDialogues();
    }

    public void setDialogues() {
        dialogues[0][0] = "You pick up a beautiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
    }
    public boolean use(Entity entity){
        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}