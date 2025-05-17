package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {
    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = col * gp.tileSize;
        this.worldY = row * gp.tileSize;
        
        down1 = setUp("tiles_interactive/drytree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 4;
    }

    @Override
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        if (entity.currentWeapon.type == type_axe) {
            isCorrectItem = true;
        }
        return isCorrectItem;
    }

    @Override
    public void playSE() {
        gp.playSE(11);
    }
    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
        return tile;
    }
}
