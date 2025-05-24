package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Lantern;
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
    public boolean lightUpdated = false;


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

    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
  
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 10; //The more strength she has, the more damage he gives
        dexterity = 1; //The more dexterity she has, the less damage she receives
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Fireball(gp);
        // projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); //The total defense value is decided by dexterity and shield
    
        getImage();
        getAttackImage();
        setItems();
        getGuardImage();
        setDialogue();
    }

    public void setDefaultPosition(){
        gp.currentmap = 0;

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;

    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Lantern(gp));
        

    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense(){
        return defense = strength * currentShield.defenseValue;
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
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
    public void getSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 =image;
        right1 = image;
        right2 = image;
        left1 = image;
        left2 = image;
    }
    public void getAttackImage() {

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
        if (currentWeapon.type == type_pickaxe){
            attackUp1 = setUp("player/Line_pick_up1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setUp("player/Line_pick_up2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setUp("player/Line_pick_down1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setUp("player/Line_pick_down2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setUp("player/Line_pick_left1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setUp("player/Line_pick_left2" , gp.tileSize*2, gp.tileSize);
            attackRight1 = setUp("player/Line_pick_right1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setUp("player/Line_pick_right2", gp.tileSize*2, gp.tileSize);
        }
    }
    public void getGuardImage(){
        guardUp = setUp("player/Line_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setUp("player/Line_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setUp("player/Line_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setUp("player/Line_guard_right", gp.tileSize, gp.tileSize);
    }
    @Override
    public void update() {
        if(knockBack == true){
            collisionOn = false;
            gp.cChecker.checkTile(this);

            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            if(collisionOn == true){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if( collisionOn == false){
                switch(knockBackDirection){
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
        else if (attacking) {
            attacking();
        }
        else if (keyH.spacePressed == true){
            guarding = true;
            guardCounter++;
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

                    // DECREASE DURABILITY
                    currentWeapon.durability --;
                }

                attackCanceled = false;
                gp.keyH.enteredPressed = false;
                guarding = false;
                guardCounter = 0;
        
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
                guarding = false;
                guardCounter = 0;
            }

            if (gp.keyH.shotKeyPressed == true && projectile.alive == false 
                && shotAvailableCounter == 40 && projectile.haveResource(this) == true) {

                //SET DEFAULT COORDINATE, DIRECTION AND UESR
                projectile.set(worldX, worldY, direction, true, this);

                //SUBTRACT PLAYER'S MANA
                projectile.subtractResource(this);

                //Check Vacancy
                for(int i = 0; i < gp.projectile[1].length; i ++){
                    if (gp.projectile[gp.currentmap][i] == null){
                        gp.projectile[gp.currentmap][i] = projectile;
                        break;
                    }
                }

                shotAvailableCounter =0;

                gp.playSE(10);

            }
        
            // INVINCIBLE TIMER
            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    transparent = false;
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
        if (keyH.godModeOn == false){
            if (life <= 0){
                gp.gameState = gp.gamveOverState;
                gp.ui.commandNum = -1;
                gp.stopMusic();
                gp.playSE(12);
            }
        } 
    }
    
    

    public void pickUpObject(int i) {
        if (i != 999) {
            //PICKUP ONLY ITEMS
            if(gp.obj[gp.currentmap][i].type == type_pickupOnly){
                gp.obj[gp.currentmap][i].use(this);
                gp.obj[gp.currentmap][i] = null;   
            }
            //OBSTACLE
            else if (gp.obj[gp.currentmap][i].type == type_obstacle) {
                if(keyH.enteredPressed == true){
                    attackCanceled = true;
                    gp.obj[gp.currentmap][i].interact();
                }
            }

            //INVENTORY ITEMS
            else {
                String text ;
                if (canObtainItem(gp.obj[gp.currentmap][i]) == true){
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
        if (i != 999) {
            if (gp.keyH.enteredPressed == true){

                attackCanceled = true;
                gp.npc[gp.currentmap][i].speak();
            }
            gp.npc[gp.currentmap][i].move(direction);
            //            gp.keyH.enteredPressed = false;
        }

        
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[gp.currentmap][i].dying == false) {
                gp.playSE(6);
                int damage = gp.monster[gp.currentmap][i].attack - defense;
                if (damage < 1){
                    damage = 1; 
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }         
        }
    }

    public void damageMonster(int i,Entity attacker, int attack,int knockBackPower) {
        if (i != 999) {
            if (gp.monster[gp.currentmap][i].invincible == false) {

                gp.playSE(5);

                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentmap][i],attacker,knockBackPower);
                }

                if(gp.monster[gp.currentmap][i].offBalance == true){
                    attack *=5;
                }
                
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

    public void setDialogue(){
        dialogues[0][0] = "You are level " + level + " now!\n"
                + "You feel stronger!";
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

    public void damageProJectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentmap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
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
//            dialogues[0][0] = "You are level" + level + " now!/n"
//            + "You feel stronger!";
            setDialogue();;
            startDialogue(this, 0);
        }
    }

    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe){
                currentWeapon = selectedItem; 
                attack = getAttack();
                getAttackImage();
            }   
            if (selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){
                
                if (currentLight == selectedItem){
                    currentLight = null;
                } else {
                    currentLight = selectedItem;
                }

                lightUpdated = true;
            }

            if (selectedItem.type == type_consumable){
                if(selectedItem.use(this) == true){
                    if (selectedItem.amount > 1){
                        selectedItem.amount --;
                    }
                    else{
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInventory(String itemName){
        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i ++){
            if (inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        Entity newItem = gp.eGenerator.getObject((item.name));
        //check if stackable
        if(newItem.stackable == true){
            int index = searchItemInventory(newItem.name);

            if (index != 999){
                inventory.get(index).amount ++;
                canObtain = true;
            }
            else { // NEW ITEM SO NEED TO CHECK VACANCY
                if (inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else{ // NOT STACKABLE SO CHECK VANCANCY
            if (inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }
        return canObtain;
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
                if(guarding == true){
                    image = guardUp;
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
                if(guarding == true){
                    image = guardDown;
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
                if(guarding == true){
                    image = guardLeft;
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
                if(guarding == true){
                    image = guardRight;
                }
            }
        }
        
        if (transparent == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }



        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


}
