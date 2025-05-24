package tile_interactive;

import entity.Entity;
import java.awt.Color;
import main.GamePanel;


public class IT_DestructibleWall extends InteractiveTile {
    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
        
        down1 = setUp("tiles_interactive/destructibleWall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_pickaxe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    @Override
    public void playSE() {
        gp.playSE(20);
    }
    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }

    public Color getParticleColor() {
        Color color = new Color(65, 65, 65); // Brown color for wood
        return color;
    }


    public int getParticleSize() {
        int size = 6; // 6 pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1; // 1 pixel per frame
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20; // 20 frames
        return maxLife;
    }

    // @Override
    // public void checkDrop() {
    //     int i = new Random().nextInt(100) + 1;

    //     // SET THE DROP RATE
    //     if (i < 50) {
    //         dropItem(new OBJ_Coin_Bronze(gp));
    //     }
    //     if (i >= 50 && i < 75) {
    //         dropItem(new OBJ_Heart(gp));
    //     }
    //     if (i >= 75 && i < 100) {
    //         dropItem(new OBJ_ManaCrystal(gp));
    //     }
    // }


}
