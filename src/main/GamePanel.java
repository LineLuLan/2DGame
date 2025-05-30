package main;

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

    //Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels 

    // FPS
    int FPS = 60;

    // SYSTEM 
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    public SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);

    Map map = new Map(this);

    public UI ui = new UI(this);
    // chuyen max map len day thì mới ko lỗi ở phần player and object
    public final int maxMap = 10;

    //PLAYER AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
//    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gamveOverState = 6;
    public final int transtionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;
    public final int cutsceneState = 11;

    //Others
    public boolean bossBattleOn = false;

    //AREA
    public int currentAre;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;

    //SOUND
    Sound music = new Sound();
    Sound se = new Sound();

    //World Setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public int currentmap = 0;

    // FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    public boolean fullScreenOn = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();

        gameState = titleState;
        currentAre = outside;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn == true) {
            setFullScreen(); // Full screen mode
        }
    }

    public void resetGame(boolean restart) {
        stopMusic();
        currentAre = outside;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPosition();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setMonster();
        aSetter.setNPC();

        if (restart == true) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }

    public void setFullScreen() {

        // SET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(App.window);

        // GET FULL SCREEN DIMENSIONS
        screenWidth2 = App.window.getWidth();
        screenHeight2 = App.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                // repaint();
                drawToTempScreen(); // draw everthing into buffered image
                drawToScreen(); // draw buffered image to the screen
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentmap][i] != null) {
                    npc[currentmap][i].update();
                }
            }

            // Monster
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentmap][i] != null) {
                    if (monster[currentmap][i].alive == true && monster[currentmap][i].dying == false) {
                        monster[currentmap][i].update();
                    }
                    if (monster[currentmap][i].alive == false) {
                        monster[currentmap][i].checkDrop();
                        monster[currentmap][i] = null;
                    }
                    // monster[i].update();
                }
            }

            //PROJECTILE
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentmap][i] != null) {
                    // Gán vào biến để dễ đọc và hiệu quả hơn
                    if (projectile[currentmap][i].alive == true) {
                        projectile[currentmap][i].update(); // Trong Projectile.update(), alive có thể bị set thành false
                    }
                    // Sau khi update, kiểm tra lại trạng thái alive của projectile
                    // vì p.update() có thể đã thay đổi p.alive
                    if (projectile[currentmap][i].alive == false) {
                        projectile[currentmap][i] = null;
                        // QUAN TRỌNG: Giảm i để không bỏ sót phần tử tiếp theo
                    }
                }
            }

            // PARTICLE
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentmap][i] != null) {
                    iTile[currentmap][i].update();
                }
            }

            eManager.update();

        }

        if (gameState == pauseState) {
            // do nothing
        }

    }

    public void drawToTempScreen() {
        //DEBUG
        long drawStart = 0;
        if (keyH.showDebugText == true) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        } //Map screen
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2);
        } // OTHER
        else {
            //TILE
            tileM.draw(g2);

            // INTERACTIVE TILE
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentmap][i] != null) {
                    iTile[currentmap][i].draw(g2);
                }
            }

            // EMPTY THE LIST
            entityList.clear();

            // ALL ENTITIES
            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentmap][i] != null) {
                    entityList.add(npc[currentmap][i]);
                }
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentmap][i] != null) {
                    entityList.add(obj[currentmap][i]);
                }
            }

            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentmap][i] != null) {
                    entityList.add(monster[currentmap][i]);
                }
            }
            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentmap][i] != null) {
                    entityList.add(projectile[currentmap][i]);
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // ENVIRONMENT
            eManager.draw(g2);

            //Mini Map
            map.drawMiniMap(g2);
            //CUTSCENCE
            csManager.draw(g2);
            // UI
            ui.draw(g2);
        }

        //Debug
        if (keyH.showDebugText == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 400;
            int lineHeight = 20;

            g2.drawString("WorldX" + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col" + (player.worldX - player.solidAreaDefaultX) / tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row" + (player.worldY - player.solidAreaDefaultY) / tileSize, x, y);
            y += lineHeight;
            g2.drawString("Draw Time" + passed, x, y);
            y += lineHeight;

            g2.drawString("God Mod: " + keyH.godModeOn, x, y);

        }
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();

    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void changeArea() {
        if (nextArea != currentAre) {
            stopMusic();
            if (nextArea == outside) {
                playMusic(0);
            }
            if (nextArea == indoor) {
                playMusic(18);
            }
            if (nextArea == dungeon) {
                playMusic(19);
            }
            aSetter.setNPC();
        }

        currentAre = nextArea;
        aSetter.setMonster();
    }

    public void removeTempEntity() {
        for (int mapNum = 0; mapNum < maxMap; mapNum++) {
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
                    obj[mapNum][i] = null;

                }
            }
        }
    }
}
