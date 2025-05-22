package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
    
    public GamePanel gp;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int worldX, worldY;
    public int speed;
    
    // ENTITY ATTRIBUTE
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";
    public Rectangle solidArea= new Rectangle(0, 0, 45, 45);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean collisionOn = false;
    public boolean invincible = false;
    
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    
    // SUPER OBJECT
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;
    
    // COUNTERS AND SPRITES
    public int invincibleCounter = 0;
    public int actionLockCounter = 0;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    public int shotAvailableCounter = 0;
    public int knockBackCounter = 0;

    // CHARATER ATTRIBUTE
    public int maxLife;
    public int defaultSpeed;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    //ITEMS ATTRIBUTE
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;

    //type 
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_monster = 2;
    public final int type_npc = 1;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;

 
    public Entity (GamePanel gp) {
        this.gp = gp;
    }
    public int getLeftX(){  
        return worldX + solidArea.x;
    }
    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }
    public int getTopY(){
        return worldY + solidArea.y;
    }
    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }
    public int getCol(){
        return (worldX + solidArea.x)/gp.tileSize;
    }
    public int getRow(){
        return (worldY + solidArea.y)/gp.tileSize;
    }
    public void interact(){}
    public boolean  use(Entity entity){
        return false;
    }

    public void checkDrop(){}
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj[1].length; i++){
            if (gp.obj[gp.currentmap][i] == null){
                gp.obj[gp.currentmap][i] = droppedItem;
                gp.obj[gp.currentmap][i].worldX = worldX;
                gp.obj[gp.currentmap][i].worldY = worldY;
                break;
            }
        }
    }

        public Color getParticleColor() {
        Color color = null; 
        return color;
    }

    public int getParticleSize() {
        int size = 0; // pixels
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0; // pixel per frame
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0; // frames
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {
        
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
        
    }

    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);

        // CHECK PLAYER COLLISION - FOR MONSTER ONLY
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }
    }

    public void update() {

        if(knockBack == true){
            checkCollision();

            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if( collisionOn == false){
                switch(gp.player.direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            knockBackCounter++;

            if(knockBackCounter == 10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }

        else{
            setAction();
        
            checkCollision();

        // CHECK ENTITY COLLISION
            if (collisionOn == false) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }
        

        spriteCounter++;
        if (spriteCounter > 24) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter < 45){
            shotAvailableCounter++;
        } 
    }

    public void damagePlayer(int attack){
        if (gp.player.invincible == false ) {

            gp.playSE(6);
            int damage = attack - gp.player.defense;
            if (damage < 0){
                damage = 0; 
            }
            gp.player.life -= damage;
            
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        

        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
            && worldX < gp.player.screenX + gp.player.worldX + gp.tileSize
            && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
            && worldY < gp.player.worldY + gp.player.worldY + gp.tileSize) {
        
                switch(direction) { 
                    case "up" -> {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                        }        
                    case "down" -> {
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                        } 
                    case "left" -> {
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                        }
                    case "right" -> {
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                        }
                }

                // Monster healthbar
                if (type == 2 && hpBarOn == true){
                    double oneScale = (double)gp.tileSize / maxLife;
                    double hpBarValue = oneScale*life;


                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(screenX-1, screenY - 16, gp.tileSize+2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                    hpBarCounter++;
                    if (hpBarCounter > 600){
                        hpBarCounter = 0;
                        hpBarOn = false;
                    }
                }
                
                if (invincible == true) {
                    hpBarOn = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.4F);
                }

                if (dying == true) {
                    dyingAnimation(g2);
                }
                
                g2.drawImage(image, screenX, screenY, null);
                changeAlpha(g2, 1F);
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;

        if (dyingCounter <= i) { changeAlpha(g2, 0f); }
        if (dyingCounter > i  && dyingCounter <= i*2) { changeAlpha(g2, 1f); }
        if (dyingCounter > i*2  && dyingCounter <= i*3) { changeAlpha(g2, 0f); }
        if (dyingCounter > i*3  && dyingCounter <= i*4) { changeAlpha(g2, 1f); }
        if (dyingCounter > i*4  && dyingCounter <= i*5) { changeAlpha(g2, 0f); }
        if (dyingCounter > i*5  && dyingCounter <= i*6) { changeAlpha(g2, 1f);}
        if (dyingCounter > i*6  && dyingCounter <= i*7) { changeAlpha(g2, 0f); }
        if (dyingCounter > i*7  && dyingCounter <= i*8) { changeAlpha(g2, 1f);}

        if (dyingCounter > i*8) {
            // dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    public void setAction() {}

    public void damageReaction(){}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue  = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left";break;
        }
    }
    

    public BufferedImage setUp(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("./res/"+ imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;                                    
    }


    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search() == true){
            
            // Next world X and Y
            int nextX = gp.pFinder.pathList.get(0).col*gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row*gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX){
                // up or left
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX){
                // up or right
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }
            else if (enTopY < nextY && enLeftX > nextX){
                // down or left
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX){
                // down or right
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }

            // Stop when reach goal
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;

//            if (nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }
        }
    }
    public int getDetected(Entity user, Entity target[][],String targetName){
        int index = 999;
        // Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        switch(user.direction) {
            case "up": nextWorldY = user.getTopY()-user.speed; break;    // change 1 to user.speed
            case "down": nextWorldY = user.getBottomY()+user.speed; break;    // change 1 to user.speed
            case "left": nextWorldX = user.getLeftX()-user.speed; break;    // change 1 to user.speed
            case "right": nextWorldX = user.getRightX()+user.speed; break;    // change 1 to user.speed
        }
        int col = nextWorldX/gp.tileSize;
        int row = nextWorldY/gp.tileSize;

        for (int i = 0; i < target[1].length; i ++){
            if (target[gp.currentmap][i]!= null){
                if(target[gp.currentmap][i].getCol() == col && 
                    target[gp.currentmap][i].getRow() == row &&
                    target[gp.currentmap][i].name.equals(targetName)){
                        index = i;
                        break;
                }
            }
        }
        return index;
    }

}
