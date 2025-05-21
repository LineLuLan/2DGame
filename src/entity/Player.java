package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int standCounter = 0;
    public boolean moving = false;
    public int pixelCounter = 0;
    public boolean attackCanceled = false;

    


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(); 
        solidArea.x = 8;
        solidArea.y = 20;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 18;
        solidArea.height = 18;

        //attackArea.width = 36;
        //attackArea.height = 36;    

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
  
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; //The more strength she has, the more damage he gives
        dexterity = 1; //The more dexterity she has, the less damage she receives
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        // projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); //The total defense value is decided by dexterity and shield
    }

    public void setDefaultPosition(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = strength * currentShield.defenseValue;
    }

    public void getPlayerImage() {

        up1 = setUp("player/Line_up1", gp.tileSize, gp.tileSize);
        up2 = setUp("player/Line_up2", gp.tileSize, gp.tileSize);
        down1 = setUp("player/Line_down1", gp.tileSize, gp.tileSize);
        down2 = setUp("player/Line_down2", gp.tileSize, gp.tileSize);
        right1 = setUp("player/Line_right1", gp.tileSize, gp.tileSize);
        right2 = setUp("player/Line_right2", gp.tileSize, gp.tileSize);
        left1 = setUp("player/Line_left1", gp.tileSize, gp.tileSize);
        left2 = setUp("player/Line_left2", gp.tileSize, gp.tileSize);  

    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == type_sword ){
            attackUp1 = setUp("player/Line_attack_up1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("player/Line_attack_up2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("player/Line_attack_down1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("player/Line_attack_down2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setUp("player/Line_attack_left1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("player/Line_attack_left2" , gp.tileSize*2, gp.tileSize);
            attackRight1 = setUp("player/Line_attack_right1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("player/Line_attack_right2", gp.tileSize*2, gp.tileSize);
        }
        if (currentWeapon.type == type_axe){
            attackUp1 = setUp("player/Line_axe_up1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("player/Line_axe_up2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("player/Line_axe_down1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("player/Line_axe_down2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setUp("player/Line_axe_left1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("player/Line_axe_left2" , gp.tileSize*2, gp.tileSize);
            attackRight1 = setUp("player/Line_axe_right1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("player/Line_axe_right2", gp.tileSize*2, gp.tileSize);
        }
    }

    @Override
    public void update() {
    
        if (attacking) {
            attacking();
        } else {
            if (keyH.upPressed == true|| keyH.downPressed == true|| keyH.leftPressed == true|| keyH.rightPressed == true|| keyH.enteredPressed == true) {
                if (keyH.upPressed) direction = "up";
                if (keyH.downPressed) direction = "down";
                if (keyH.leftPressed) direction = "left";
                if (keyH.rightPressed) direction = "right";
        
                // COLLISION CHECK
                collisionOn = false;
                gp.cChecker.checkTile(this);

                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                contactMonster(monsterIndex);

                // CHECK INTERACTIVE TILE
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

                gp.eHandler.checkEvent();
        
                // MOVE IF NO COLLISION
                if (!collisionOn) {
                    switch(direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                if (keyH.enteredPressed == true && attackCanceled == false){
                    gp.playSE(7);
                    attacking = true;
                    spriteCounter = 0;
                }

                attackCanceled = false;
                gp.keyH.enteredPressed = false;
        
                // ANIMATION
                spriteCounter++;
                if (spriteCounter > 10) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            } else {
                standCounter++;
                if (standCounter > 20) {
                    spriteNum = 1;
                    standCounter = 0;
                }
            }

            if (gp.keyH.shotKeyPressed == true && projectile.alive == false 
                && shotAvailableCounter == 40 && projectile.haveResource(this) == true) {

                //SET DEFAULT COORDINATE, DIRECTION AND UESR
                projectile.set(worldX, worldY, direction, true, this);

                //SUBTRACT PLAYER'S MANA
                projectile.subtractResource(this);

                //ADD IT TO THE LIST
                gp.projectileList.add(projectile);

                shotAvailableCounter =0;

                gp.playSE(10);

            }
        
            // INVINCIBLE TIMER
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    
        if (shotAvailableCounter < 40){
            shotAvailableCounter ++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }  
        if (life <= 0){
            gp.gameState = gp.gamveOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(12);
        }

    }
    
    public void attacking() {
        moving = false;
        spriteCounter++;
        
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }

        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            
            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch(direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            //PICKUP ONLY ITEMS
            if(gp.obj[gp.currentmap][i].type == type_pickupOnly){
                gp.obj[gp.currentmap][i].use(this);
                gp.obj[gp.currentmap][i] = null;   
            }

            //INVENTORY ITEMS
            else {
                String text ;
                if (inventory.size() != maxInventorySize ){
                    inventory.add(gp.obj[gp.currentmap][i]);
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentmap][i].name + "!";
                }
                else {
                    text = "You can not carry anymore";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentmap][i] = null;
                }
            
        }
    }

    public void interactNPC(int i) {
        if (gp.keyH.enteredPressed == true) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentmap][i].speak();
            }
//            gp.keyH.enteredPressed = false;
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[gp.currentmap][i].dying == false) {
                gp.playSE(6);
                int damage = gp.monster[gp.currentmap][i].attack - defense;
                if (damage < 0){
                    damage = 0; 
                }
                life -= damage;
                invincible = true;
            }         
        }
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[gp.currentmap][i].invincible == false) {

                gp.playSE(5);
                
                int damage = attack -gp.monster[gp.currentmap][i].defense;
                if (damage < 0){
                    damage = 0; 
                }
                gp.monster[gp.currentmap][i].life -= damage;
                gp.monster[gp.currentmap][i].invincible = true;
                gp.monster[gp.currentmap][i].damageReaction();
                gp.ui.addMessage(damage + " damage!");

                if (gp.monster[gp.currentmap][i].life <= 0) {
                    gp.monster[gp.currentmap][i].dying = true;
                    gp.ui.addMessage("Kill the " + gp.monster[gp.currentmap][i].name + "!");
                    gp.ui.addMessage("+ " + gp.monster[gp.currentmap][i].exp + " exp !");
                    exp += gp.monster[gp.currentmap][i].exp;
                    checkLeverUp();
                }
            } 
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[gp.currentmap][i].destructible == true 
            && gp.iTile[gp.currentmap][i].isCorrectItem(this) == true
            && gp.iTile[gp.currentmap][i].invincible == false) {

                gp.iTile[gp.currentmap][i].playSE();
                gp.iTile[gp.currentmap][i].life--;
                gp.iTile[gp.currentmap][i].invincible = true;

                // Generate particles
                generateParticle(gp.iTile[gp.currentmap][i], gp.iTile[gp.currentmap][i]);

                if (gp.iTile[gp.currentmap][i].life <= 0) {
                    gp.iTile[gp.currentmap][i] = gp.iTile[gp.currentmap][i].getDestroyedForm();
                    
                }
                

        } 
    }

    public void checkLeverUp(){
        if (exp >= nextLevelExp ){
            level ++;
            nextLevelExp = nextLevelExp *2;
            maxLife += 2;
            life +=2;
            strength ++;
            dexterity ++;
            projectile.attack +=1;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n"
                + "You feel stronger!";
        }
    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe ){
                currentWeapon = selectedItem; 
                attack = getAttack();
                getPlayerAttackImage();
            }   
            if (selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        switch(direction) {
            case "up" -> {
                if (attacking == false) {
                    if (spriteNum == 1) { image = up1; }
                    if (spriteNum == 2) { image = up2; }
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) { image = attackUp1; }
                    if (spriteNum == 2) { image = attackUp2; }
                }
            }
            case "down" -> {
                if (attacking == false) {
                    if (spriteNum == 1) { image = down1; }
                    if (spriteNum == 2) { image = down2; }
                }
                if (attacking == true) {
                    if (spriteNum == 1) { image = attackDown1; }
                    if (spriteNum == 2) { image = attackDown2; }
                }
            }
            case "left" -> {
                if (attacking == false) {
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                }
                if (attacking == true) {
                    tempScreenX =  screenX - gp.tileSize;
                    if (spriteNum == 1) { image = attackLeft1; }
                    if (spriteNum == 2) { image = attackLeft2; }
                }
            }
            case "right" -> {
                if (attacking == false) {
                    if (spriteNum == 1) { image = right1; }
                    if (spriteNum == 2) { image = right2; }
                }
                if (attacking == true) {
                    if (spriteNum == 1) { image = attackRight1; }
                    if (spriteNum == 2) { image = attackRight2; }
                }
            }
        }
        
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }



        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
