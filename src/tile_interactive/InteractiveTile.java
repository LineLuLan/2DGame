package tile_interactive;

import entity.Entity;
import java.awt.Graphics2D;
import main.GamePanel;

public class InteractiveTile extends Entity{
    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {    
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        return isCorrectItem;
    }

    public void playSE(){}

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }



    public void update() {
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 20) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        

        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
            && worldX < gp.player.screenX + gp.player.worldX + gp.tileSize
            && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
            && worldY < gp.player.worldY + gp.player.worldY + gp.tileSize) {
                
            g2.drawImage(down1, screenX, screenY, null);
        }
    }

}
