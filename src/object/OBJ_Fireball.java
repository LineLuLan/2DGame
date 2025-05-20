package object;


import entity.Entity;
import entity.Projectile;
import java.awt.Color;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name  ="Fireball";
        speed = 10;
        maxLife = 80;
        life  =maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = setUp("projectile/fireball_up_1", gp.tileSize, gp.tileSize);
        up2 = setUp("projectile/fireball_up_2", gp.tileSize, gp.tileSize);
        down1 = setUp("projectile/fireball_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("projectile/fireball_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("projectile/fireball_left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("projectile/fireball_left_2", gp.tileSize, gp.tileSize);
        right1 = setUp("projectile/fireball_right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("projectile/fireball_right_2", gp.tileSize, gp.tileSize);
    }

    @Override
    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    @Override
    public void subtractResource(Entity user){
        user.mana -= useCost;
    }

     @Override
    public Color getParticleColor() {
        Color color = new Color(240, 50, 0); 
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
