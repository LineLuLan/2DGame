package object;

import entity.Entity;
import entity.Projectile;
import java.awt.Color;
import main.GamePanel;

public class OBJ_Rock extends Projectile {
    public static final String objName = "Rock";
    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name  =objName;
        speed = 8;
        maxLife = 80;
        life  = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setUp("projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }

    @Override
    public boolean haveResource(Entity user) {
        boolean haveResource = false;

        if (user.type == type_monster) { 
            return true; 
        }
        if (user.ammo >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    @Override
    public void subtractResource(Entity user){
        if (user.type == type_monster){
            return;
        }

        user.ammo -= useCost;
    }

     @Override
    public Color getParticleColor() {
        Color color = new Color(40, 50, 0); 
        return color;
    }

    
    @Override
    public int getParticleSize() {
        int size = 10; // 6 pixels
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
