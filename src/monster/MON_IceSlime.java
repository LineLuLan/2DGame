package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_IceRock;
import object.OBJ_ManaCrystal;

public class MON_IceSlime extends Entity{
    GamePanel gp;

    public MON_IceSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Ice Slime";
        defaultSpeed = 2;
        speed = defaultSpeed + 1;
        maxLife = 30;
        life = maxLife;
        attack = 5;
        defense = 3;
        exp = 7;
        projectile = new OBJ_IceRock(gp);

        solidArea.x  = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    
    public void getImage() {
        up1 = setUp("/monsters/Ice_Slime_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/monsters/Ice_Slime_2", gp.tileSize, gp.tileSize);

        down1 = setUp("/monsters/Ice_Slime_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/monsters/Ice_Slime_2", gp.tileSize, gp.tileSize);

        left1 = setUp("/monsters/Ice_Slime_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/monsters/Ice_Slime_2", gp.tileSize, gp.tileSize);

        right1 = setUp("/monsters/Ice_Slime_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/monsters/Ice_Slime_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {

        if(onPath == true){
            // Check if it stops chasing
            checkStopChasingOrNot(gp.player, 10, 100);
            // Search the directiono to go 
            
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
            //Check if it shoots a projectile
            checkShootOrNot(200, 30);
        }
        else{
            //check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);
            //get a random direction
            getRandomDirection(120);
        }
    }
    
    @Override
    public void damageReaction(){
        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true;
        
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
