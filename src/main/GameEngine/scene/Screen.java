package main.GameEngine.scene;

import main.GameEngine.gameObjects.Value;
import main.GameEngine.gameUtilities.Save;
import main.GameEngine.gameUtilities.controls.KeyHandle;
import main.GameEngine.gameUtilities.controls.Store;
import main.GameEngine.sprites.DrageMob;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

public class Screen extends JPanel implements Runnable {

    public boolean gameRun = true;
    public static boolean isFirst = true;
    public static boolean isDebug = true;
    public static int rpsFrame = 0;
    public static int fps = 100000;
    public static int myHeight;
    public static int myWidth;
    public static Room room;
    public static Save save;
    public static Store store;
    public static String missionPath = "src/main/GameEngine/gameUtilities/levels/mission1.level";

    public static DrageMob[] mobs = new DrageMob[100];

    public static int coinCount = 10;
    public static int health = 100;
    public int spawnTime = 2400, spawnFrame = 0; //for spawner class....


    public static Point mse = new Point(0, 0);
    public static Image[] tileSet_ground = new Image[100];
    public static Image[] tileSet_air = new Image[100];
    public static Image[] tileSet_res = new Image[100];
    public static Image[] tileset_mob = new Image[100];

    public Thread thread = new Thread(this);

    public Screen(Frame frame) {
        frame.addMouseListener(new KeyHandle()); //frame extends listeners, to listen intoo user input - mouseover.
        frame.addMouseMotionListener(new KeyHandle());
        thread.start();
    }

    public void define() {
        room = new Room();
        save = new Save();
        store = new Store();

        for (int i = 0; i < tileSet_ground.length; i++) {
            tileSet_ground[i] = new ImageIcon("src/resource/tileset_ground.png").getImage();
            tileSet_ground[i] = createImage(new FilteredImageSource(tileSet_ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }
        for (int i = 0; i < tileSet_air.length; i++) {
            tileSet_air[i] = new ImageIcon("src/resource/tileset_air.png").getImage();
            tileSet_air[i] = createImage(new FilteredImageSource(tileSet_air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }

        tileSet_res[0] = new ImageIcon("src/resource/cell.png").getImage();
        tileSet_res[1] = new ImageIcon("src/resource/health.png").getImage();
        tileSet_res[2] = new ImageIcon("src/resource/coin.png").getImage();

        tileset_mob[0] = new ImageIcon("src/resource/drage_mob.png").getImage();

        save.loadSave(new File(missionPath));

        for (int i = 0; i < mobs.length; i++) { //spawn mobs after resources are loaded.
            mobs[i] = new DrageMob();
        }
    }

    public void paintComponent(Graphics g) {
        if(isFirst) {
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        g.setColor(new Color(70, 70, 70)); //red green blue
        g.fillRect(0, 0, getWidth(), getHeight()); //background rect
        g.setColor(new Color(0,0,0));
        g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y + room.blockSize); //draw left border line
        g.drawLine(room.block[0][room.worldWidth-1].x + room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y + room.blockSize); //draw right border line
        g.drawLine(
                room.block[0][0].x,
                room.block[room.worldHeight-1][0].y + room.blockSize,
                room.block[0][room.worldWidth-1].x + room.blockSize,
                room.block[room.worldHeight-1][0].y + room.blockSize); //draw the bottom line

        room.draw(g); //draw the game room

        for(int i=0; i < mobs.length; i++) {
            if(mobs[i].inGame) {
                mobs[i].draw(g);
            }
        }
        store.draw(g); //draw the buttons

        if(health < 1) {
            g.setColor(new Color(240, 20, 20));
            g.fillRect(0,0,myWidth, myHeight); //create interface

            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            g.drawString("Game Over", 10, 20);
        }
    }


    //spawner should be moved to its own class in refactor.

    public void mobSpawner() {
        if(spawnFrame >= spawnTime) {
            for(int i=0; i < mobs.length; i++) {
                if(!mobs[i].inGame) {
                    mobs[i].spawnMob(Value.mobGreen);
                    break;

                }
            }
            spawnFrame = 0;
        } else {
            spawnFrame += 1;
        }
    }

    public void run() {
        while (gameRun) {
            if(!isFirst && health > 0) {
                room.physics();
                mobSpawner();

                for(int i=0; i < mobs.length; i++) {
                    if(mobs[i].inGame) {
                        mobs[i].physics();
                    }
                }
            }
            repaint();

            try {
                Thread.sleep(1);
            } catch (Exception ex) {

            }

        }
    }
}
