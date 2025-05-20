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

        switch(entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                
                //Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch(entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    
                }
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision == true){
                        entity.collisionOn = true;
                    }

                    if (player == true) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    //NPC OR MONSTER
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position

                target[i].solidArea.x = target[i].worldX +target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch(entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }     
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
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

