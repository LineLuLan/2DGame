package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity {
    GamePanel gp;

    public static final String monName = "Skeleton Lord";

    public MON_SkeletonLord(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackPower = 5;

        int size = gp.tileSize*5;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        motion1_duration = 25;
        motion2_duration = 50;

        getImage();
        getAttackImage();
    }

    
    public void getImage() {
        int i = 5;

        if (inRage == false){
            up1 = setUp("/monsters/skeletonlord_up_1", gp.tileSize*i, gp.tileSize*i);
            up2 = setUp("/monsters/skeletonlord_up_2", gp.tileSize*i, gp.tileSize*i);
            down1 = setUp("/monsters/skeletonlord_down_1", gp.tileSize*i, gp.tileSize*i);
            down2 = setUp("/monsters/skeletonlord_down_2", gp.tileSize*i, gp.tileSize*i);
            left1 = setUp("/monsters/skeletonlord_left_1", gp.tileSize*i, gp.tileSize*i);
            left2 = setUp("/monsters/skeletonlord_left_2", gp.tileSize*i, gp.tileSize*i);
            right1 = setUp("/monsters/skeletonlord_right_1", gp.tileSize*i, gp.tileSize*i);
            right2 = setUp("/monsters/skeletonlord_right_2", gp.tileSize*i, gp.tileSize*i);
        
        } 
        if (inRage == true){
            up1 = setUp("/monsters/skeletonlord_phase2_up_1", gp.tileSize*i, gp.tileSize*i);
            up2 = setUp("/monsters/skeletonlord_phase2_up_2", gp.tileSize*i, gp.tileSize*i);
            down1 = setUp("/monsters/skeletonlord_phase2_down_1", gp.tileSize*i, gp.tileSize*i);
            down2 = setUp("/monsters/skeletonlord_phase2_down_2", gp.tileSize*i, gp.tileSize*i);
            left1 = setUp("/monsters/skeletonlord_phase2_left_1", gp.tileSize*i, gp.tileSize*i);
            left2 = setUp("/monsters/skeletonlord_phase2_left_2", gp.tileSize*i, gp.tileSize*i);
            right1 = setUp("/monsters/skeletonlord_phase2_right_1", gp.tileSize*i, gp.tileSize*i);
            right2 = setUp("/monsters/skeletonlord_phase2_right_2", gp.tileSize*i, gp.tileSize*i);
        
        }
        
    }
    public void getAttackImage(){
        int i = 5;
        if (inRage == false){
            attackUp1 = setUp("monsters/skeletonlord_attack_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setUp("monsters/skeletonlord_attack_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setUp("monsters/skeletonlord_attack_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setUp("monsters/skeletonlord_attack_down_2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setUp("monsters/skeletonlord_attack_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setUp("monsters/skeletonlord_attack_left_2" , gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setUp("monsters/skeletonlord_attack_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setUp("monsters/skeletonlord_attack_right_2", gp.tileSize*i*2, gp.tileSize*i);
                
        }
        if (inRage == true){
            attackUp1 = setUp("monsters/skeletonlord_attack_phase2_up_1", gp.tileSize*i, gp.tileSize*i*2);
            attackUp2 = setUp("monsters/skeletonlord_attack_phase2_up_2", gp.tileSize*i, gp.tileSize*i*2);
            attackDown1 = setUp("monsters/skeletonlord_attack_phase2_down_1", gp.tileSize*i, gp.tileSize*i*2);
            attackDown2 = setUp("monsters/skeletonlord_attack_phase2_down_2", gp.tileSize*i, gp.tileSize*i*2);
            attackLeft1 = setUp("monsters/skeletonlord_attack_phase2_left_1", gp.tileSize*i*2, gp.tileSize*i);
            attackLeft2 = setUp("monsters/skeletonlord_attack_phase2_left_2" , gp.tileSize*i*2, gp.tileSize*i);
            attackRight1 = setUp("monsters/skeletonlord_attack_phase2_right_1", gp.tileSize*i*2, gp.tileSize*i);
            attackRight2 = setUp("monsters/skeletonlord_attack_phase2_right_2", gp.tileSize*i*2, gp.tileSize*i);

        }
    }

    public void setAction() {
        if (inRage == false && life < maxLife / 2){
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }
        if(getTileDistance(gp.player) < 10){
            moveTowardPlayer(60);
        }
        else{
        
            getRandomDirection(120);
        }
        if(attacking == false){
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize*5);
        }
    }
    
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        
    }

    @Override
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;

        // SET THE DROP RATE
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}
