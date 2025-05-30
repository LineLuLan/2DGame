package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class Particle extends Entity {
    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed,
                    int maxLife ,int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd; 
        this.speed = speed;

        life = maxLife;
        int offset = (gp.tileSize/2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset; 
    }

    public void set(int worldX, int worldY, String direction, Entity generator, int life, int speed) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.generator = generator;
        this.life = life;
        this.speed = speed;
    }

    public void update() {
        worldX += xd * speed;
        worldY += yd * speed; 
        life--;

        if (life < maxLife / 3) {
            yd++;
        }

        if (life <= 0) {
            alive = false;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }

}
