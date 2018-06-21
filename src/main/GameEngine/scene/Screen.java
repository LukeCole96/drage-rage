package main.GameEngine.scene;

import main.GameEngine.gameUtilities.Save;

import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

public class Screen extends JPanel implements Runnable {

    public boolean gameRun = true;
    public static boolean isFirst = true;
    public static int rpsFrame = 0;
    public static int fps = 100000;
    public static int myHeight;
    public static int myWidth;
    public static Room room;
    public static Save save;
    public static String missionPath = "src/main/GameEngine/gameUtilities/levels/mission1.level";

    public static Point mse = new Point(0, 0);
    public static Image[] tileSet_ground = new Image[100];
    public static Image[] tileSet_air = new Image[100];

    public Thread thread = new Thread(this);

    public Screen() {
        setBackground(Color.BLACK);
        thread.start();
    }

    public void define() {
        room = new Room();
        save = new Save();

        for(int i=0; i<tileSet_ground.length; i++) {
            tileSet_ground[i] = new ImageIcon("src/resource/tileset_ground.jpg").getImage();
            tileSet_ground[i] = createImage(new FilteredImageSource(tileSet_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
        }
        for(int i=0; i<tileSet_air.length; i++) {
            tileSet_air[i] = new ImageIcon("/src/resource/tileSet_air.jpg").getImage();
            tileSet_air[i] = createImage(new FilteredImageSource(tileSet_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
        }
        save.loadSave(new File(missionPath));
    }

    public void paintComponent(Graphics g) {
        if(isFirst) {
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        g.clearRect(0, 0, getWidth(), getHeight());

        room.draw(g);
    }

    public void run() {
        while (gameRun) {
            repaint();

            try {
                Thread.sleep(1);
            } catch (Exception ex) {

            }

        }
    }
}