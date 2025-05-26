package main;

import entity.PlayerDummy;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import monster.MON_SkeletonLord;
import object.OBJ_Blue_Heart;
import object.OBJ_Door_Iron;

public class CutsceneManager {

    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;

    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
        endCredit = "Thank you for playing!\n\n"+
                    "Contributers:\n"+
                    "Leader: Tran Nam Anh _ ITDSIU23030 (Line)\n"+
                    "Member: Ngo Thi Anh Duong _ ITDSIU23005\n"+
                    "Member: Nguyen Duc Hai _ ITDSIU23006\n"+
                    "Member: Dang Minh Phat _ ITDSIU23017\n\n\n\n\n\n\n\n"+
                    "To be continued...";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch(sceneNum){
            case skeletonLord: scene_skeletonLord();break;
            case ending: scene_ending();break;
        }
    }
    public void scene_skeletonLord(){
        if(scenePhase == 0){
            gp.bossBattleOn = true;

            for(int i = 0; i < gp.obj[1].length; i ++){
                if(gp.obj[gp.currentmap][i] == null){
                    gp.obj[gp.currentmap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentmap][i].worldX = gp.tileSize*25;
                    gp.obj[gp.currentmap][i].worldY = gp.tileSize*28;
                    gp.obj[gp.currentmap][i].temp = true;
                    gp.playSE(21);
                    break;
                }
            }
            // Search a vacant slot for the dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentmap][i] == null) {
                gp.npc[gp.currentmap][i] = new PlayerDummy(gp);
                gp.npc[gp.currentmap][i].worldX = gp.player.worldX;
                gp.npc[gp.currentmap][i].worldY = gp.player.worldY;
                gp.npc[gp.currentmap][i].direction = gp.player.direction;
                break;
                }
            }

            gp.player.drawing = false;
            scenePhase++;
        }
        if(scenePhase == 1){
            gp.player.worldY -= 2;

            if (gp.player.worldY < gp.tileSize*17){
                scenePhase++;
            }
        }
        if(scenePhase == 2){
            for (int i = 0; i < gp.monster[1].length; i++) {

                if (gp.monster[gp.currentmap][i] != null &&
                    gp.monster[gp.currentmap][i].name == MON_SkeletonLord.monName) {

                        gp.monster[gp.currentmap][i].sleep = false;
                        gp.ui.npc = gp.monster[gp.currentmap][i];
                        scenePhase++;
                        break;
                }
            }
        }
        if (scenePhase == 3) {
            // The boss speaks
            gp.ui.drawDialogueScreen();
        }
        if (scenePhase == 4) {

            // Return to the player
            // Search the dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
        
                if (gp.npc[gp.currentmap][i] != null && 
                    gp.npc[gp.currentmap][i].name.equals(PlayerDummy.npcName)) {
        
                    // Restore the player position
                    gp.player.worldX = gp.npc[gp.currentmap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentmap][i].worldY;
        
                    // Delete the dummy
                    gp.npc[gp.currentmap][i] = null;
                    break;
                }
            }
        
            // Start drawing the player
            gp.player.drawing = true;
        
            // Reset
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;

            gp.stopMusic();
            gp.playMusic(22);
        }
        
    }
    public void scene_ending(){
        if(scenePhase == 0){
            gp.stopMusic();
            gp.ui.npc = new OBJ_Blue_Heart(gp);
            scenePhase ++;
        }
        if(scenePhase == 1){
            gp.ui.drawDialogueScreen();
        }
        if(scenePhase == 2){
            gp.playSE(4);
            scenePhase++;
        }
        if(scenePhase == 3){
            if(counterReached(300) == true){
                scenePhase ++;
            }
        }
        if(scenePhase == 4) {
            // The screen gets darker
            alpha += 0.005f;
            if (alpha > 1f) {
                alpha = 1f;
            }
            drawBlackBackground(alpha);
            if(alpha == 1f) {
                alpha = 0;
                scenePhase++;
            }
        }
        if (scenePhase == 5) {
            drawBlackBackground(1f);
        
            alpha += 0.005f;
            if (alpha > 1f) {
                alpha = 1f;
            }
        
            String text = "After the fierce battle with the Skeleton Lord,\n"
                        + "The Line finally found the legendary treasure.\n"
                        + "But this is not the end of his journey.\n"
                        + "The Line's adventure has just begun.";

            drawString(alpha, 38f, 200, text, 70);
            if(counterReached(600) == true){
                gp.playMusic(0);
                scenePhase++;
            }
        }
        if (scenePhase == 6) {
            drawBlackBackground(1f);
            drawString(1f, 120f, gp.screenHeight/2, "Line Adventure", 40);
        
            if (counterReached(480) == true) {
                scenePhase++;
            }
        }
        
        if (scenePhase == 7) {
            drawBlackBackground(1f);
            y = gp.screenHeight/2;
            drawString(1f,38f,y,endCredit,40);
            if (counterReached(480) == true) {
                scenePhase++;
            }
        }
        if (scenePhase == 8) {
            drawBlackBackground(1f);
        
            // Scrolling the credit
            y--;
            drawString(1f, 38f, y, endCredit, 40);

            if (y == -700){
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.titleState;
                gp.ui.titleScreenState = 0;
                gp.resetGame(true);
                gp.playSE(0);
            }
        }


    }
    public boolean counterReached (int target) {
        boolean counterReached = false;
        counter++;
        if (counter > target) {
            counterReached = true;
            counter = 0;
        }
        return counterReached;
    }
    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));
    
        for(String line : text.split("\n")) {
            int x = gp.ui.getXforCenteredText(line); 
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}

