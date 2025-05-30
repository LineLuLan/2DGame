package main;

import data.Progress;
import entity.Entity;
public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventMaster;

    int previousEventX = 1000, previousEventY = 0;
    boolean canTouchEvent = true;
    int temMap, temCol, temRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int col = 0, row = 0; int map = 0; 

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if(row == gp.maxWorldRow){
                    row = 0; 
                    map ++;
                }
            }

        }
        setDialouge();
    }
    
    public void setDialouge(){
        eventMaster.dialogues[0][0] = "You fall into a pit";

        eventMaster.dialogues[1][0] = "You drink the water.\nYou has been recoverd\n"
                                    + "(The progess has been saved)";

        eventMaster.dialogues[1][1] = "Damn, this is good water!";
    }

    public void checkEvent() {
        // Check if player is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent == true) {
            if (hit(0,23, 12, "any") == true) {
                healingPool(gp.dialogueState);
            }
            else if (hit(0,10,39,"any") == true){
                teleport(1,12,13,gp.indoor);
            }
            else if (hit(1,12,13,"any") == true){
                teleport(0,10,39,gp.outside);
            }
            else if (hit(1,12,9 ,"up")==true){
                speak(gp.npc[1][0]);
            }
            else if (hit(0,12,10,"any") == true){
                teleport(2,9,41,gp.dungeon);
            }
            else if (hit(2,9,41,"any") == true){
                teleport(0,12,10,gp.outside);
            }
            else if (hit(2,8,7,"any") == true){
                teleport(3,26,41,gp.dungeon);
            }
            else if (hit(3,26,41,"any") == true){
                teleport(2,8,7,gp.dungeon);
            }
            else if (hit(3,25,27,"any") == true){
                skeletonLord();
            }
        }

    }

    public boolean hit(int map,int col, int row, String reqDirection) {
        boolean hit = false;
        if (map == gp.currentmap){
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
               }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;  
        }
    return hit;
    }


    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "teleport";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
      
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        // eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.enteredPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            eventMaster.startDialogue(eventMaster, 1);

            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }

        // gp.gameState = gameState;
        // gp.ui.currentDialogue = "You drink the water.\nYour like has been recoverd";
        // gp.player.life = gp.player.maxLife;

        
    }
    public void teleport(int map, int col, int row, int area){

        gp.gameState = gp.transtionState;
        gp.nextArea = area;
        temMap = map;
        temCol = col;
        temRow = row;
        
        canTouchEvent = false;
        gp.playSE(13);
    }   

    public void speak(Entity entity){
        if ( gp.keyH.enteredPressed==true){
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
    public void skeletonLord(){
        if(gp.bossBattleOn == false && Progress.skeletonLordDefeated == false){
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.skeletonLord;
        }
    }
}
