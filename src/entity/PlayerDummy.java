package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {

    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gp) {
        super(gp);

        name = npcName;
        getImage();
    }

    public void getImage() {
        up1 = setUp("player/Line_up1", gp.tileSize, gp.tileSize);
        up2 = setUp("player/Line_up2", gp.tileSize, gp.tileSize);
        down1 = setUp("player/Line_down1", gp.tileSize, gp.tileSize);
        down2 = setUp("player/Line_down2", gp.tileSize, gp.tileSize);
        right1 = setUp("player/Line_right1", gp.tileSize, gp.tileSize);
        right2 = setUp("player/Line_right2", gp.tileSize, gp.tileSize);
        left1 = setUp("player/Line_left1", gp.tileSize, gp.tileSize);
        left2 = setUp("player/Line_left2", gp.tileSize, gp.tileSize);  
    }
}
