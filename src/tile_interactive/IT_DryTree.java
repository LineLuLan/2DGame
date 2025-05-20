package tile_interactive;

import entity.Entity;
import java.awt.Color;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {
    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
        
        down1 = setUp("tiles_interactive/drytree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 4;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    @Override
    public void playSE() {
        gp.playSE(11);
    }
    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }

    @Override
    public Color getParticleColor() {
        Color color = new Color(139, 69, 19); // Brown color for wood
        return color;
    }

    
    @Override
    public int getParticleSize() {
        int size = 6; // 6 pixels
        return size;
    }

    @Override
    public int getParticleSpeed() {
        int speed = 1; // 1 pixel per frame
        return speed;
    }

    @Override
    public int getParticleMaxLife() {
        int maxLife = 20; // 20 frames
        return maxLife;
    }


}
