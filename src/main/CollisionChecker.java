package main;

import entity.Entity;
import java.awt.Rectangle;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY +  entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        // Use a tamporal direction when it's being knockback
        String direction = entity.direction;
        if(entity.knockBack == true){
            direction = entity.knockBackDirection;
        }

        switch(direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentmap][entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        // Use a tamporal direction when it's being knockback
        String direction = entity.direction;
        if(entity.knockBack == true){
            direction = entity.knockBackDirection;
        }

        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentmap][i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                //Get the object's solid area position
                gp.obj[gp.currentmap][i].solidArea.x = gp.obj[gp.currentmap][i].worldX + gp.obj[gp.currentmap][i].solidArea.x;
                gp.obj[gp.currentmap][i].solidArea.y = gp.obj[gp.currentmap][i].worldY + gp.obj[gp.currentmap][i].solidArea.y;

                switch(direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    
                }
                if (entity.solidArea.intersects(gp.obj[gp.currentmap][i].solidArea)) {
                    if (gp.obj[gp.currentmap][i].collision == true){
                        entity.collisionOn = true;
                    }

                    if (player == true) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[gp.currentmap][i].solidArea.x = gp.obj[gp.currentmap][i].solidAreaDefaultX;
                gp.obj[gp.currentmap][i].solidArea.y = gp.obj[gp.currentmap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;
        // Use a tamporal direction when it's being knockback
        String direction = entity.direction;
        if(entity.knockBack == true){
            direction = entity.knockBackDirection;
        }

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentmap][i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position

                target[gp.currentmap][i].solidArea.x = target[gp.currentmap][i].worldX +target[gp.currentmap][i].solidArea.x;
                target[gp.currentmap][i].solidArea.y = target[gp.currentmap][i].worldY + target[gp.currentmap][i].solidArea.y;

                switch(direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
                if (entity.solidArea.intersects(target[gp.currentmap][i].solidArea)) {
                    if (target[gp.currentmap][i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }     
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentmap][i].solidArea.x = target[gp.currentmap][i].solidAreaDefaultX;
                target[gp.currentmap][i].solidArea.y = target[gp.currentmap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    // public boolean checkPlayer(Entity entity) {
        
    //     boolean contactPlayer = false;

    //     // Get entity's solid area position
    //     entity.solidArea.x = entity.worldX + entity.solidArea.x;
    //     entity.solidArea.y = entity.worldY + entity.solidArea.y;

    //     //Get the object's solid area position
    //     gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
    //     gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

    //     switch(entity.direction) {
    //         case "up" -> entity.solidArea.y -= entity.speed;
    //         case "down" -> entity.solidArea.y += entity.speed;
    //         case "left" -> entity.solidArea.x -= entity.speed;
    //         case "right" -> entity.solidArea.x += entity.speed;
    //         }
    //     if (entity.solidArea.intersects(gp.player.solidArea)) {
    //         entity.collisionOn = true;
    //         contactPlayer = true;
    //     }
        
    //     entity.solidArea.x = entity.solidAreaDefaultX;
    //     entity.solidArea.y = entity.solidAreaDefaultY;
    //     gp.player.solidArea.x = gp.player.solidAreaDefaultX;
    //     gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        
    //     return contactPlayer;
        
    // }

    public boolean checkPlayer(Entity entity) {
        boolean contactPlayer = false;

        // Tạo một bản sao của solidArea của entity để kiểm tra, không làm thay đổi solidArea gốc
        Rectangle entityCheckArea = new Rectangle();
        entityCheckArea.x = entity.worldX + entity.solidArea.x; // Sử dụng offset gốc
        entityCheckArea.y = entity.worldY + entity.solidArea.y; // Sử dụng offset gốc
        entityCheckArea.width = entity.solidArea.width;
        entityCheckArea.height = entity.solidArea.height;

        // Tạo một bản sao của solidArea của player để kiểm tra
        Rectangle playerCheckArea = new Rectangle();
        playerCheckArea.x = gp.player.worldX + gp.player.solidArea.x; // Sử dụng offset gốc
        playerCheckArea.y = gp.player.worldY + gp.player.solidArea.y; // Sử dụng offset gốc
        playerCheckArea.width = gp.player.solidArea.width;
        playerCheckArea.height = gp.player.solidArea.height;


        // Dự đoán vị trí tiếp theo của entityCheckArea
        switch (entity.direction) {
            case "up"    -> entityCheckArea.y -= entity.speed;
            case "down"  -> entityCheckArea.y += entity.speed;
            case "left"  -> entityCheckArea.x -= entity.speed;
            case "right" -> entityCheckArea.x += entity.speed;
        }

        if (entityCheckArea.intersects(playerCheckArea)) {
            // CHỈ đặt collisionOn cho entity đang được kiểm tra, KHÔNG phải cho player
            entity.collisionOn = true;
            contactPlayer = true;
        }
        // Không cần reset solidArea.x/y của entity hay player về solidAreaDefaultX/Y
        // vì chúng ta không thay đổi các giá trị offset gốc của chúng.

        return contactPlayer;
    }
}

