package main.GameEngine.gameUtilities.controls;

import main.GameEngine.gameObjects.Value;
import main.GameEngine.scene.Screen;

import java.awt.*;

public class Store {

    public static int shopWidth = 8;
    public static int buttonSize = 52;
    public static int cellSpace = 2;
    public static int awayFromRoom = 3;
    public static int iconSize = 20;
    public static int iconSpace = 6;
    public static int iconTextY = 15;
    public static int itemIn = 4; //how big the item border is
    public static int heldID = -1;
    public static int realID = 0; //used to solve the issue regarding the coin transfer for towers.
    public static int[] buttonID = {Value.airTowerLaser, Value.airAirBlock, Value.airAirBlock, Value.airAirBlock, Value.airAirBlock, Value.airAirBlock,
            Value.airAirBlock, Value.airTrashCan}; //the button menu drag and drop stuff (this is place holded).
    public static int[] buttonPrice = {10, 0, 0, 0, 0, 0, 0, 0}; //place held


    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;

    public boolean holdsItem = false;

    public Store() {
        define();
    }

    public void click(int mouseButton) { //handles drag * drop features
        if(mouseButton == 1) {
            for(int i=0; i < button.length; i++) {
                if(button[i].contains(Screen.mse)) {
                    if(buttonID[i] != Value.airAirBlock) {
                        if (buttonID[i] == Value.airTrashCan) { //delete the held item.
                            holdsItem = false;
                        } else {
                            heldID = buttonID[i];
                            realID = i;
                            holdsItem = true;
                        }
                    }
                }
            }
        }
        if(holdsItem) { //purchasing
            if(Screen.coinCount >= buttonPrice[realID]) {
                for(int i=0; i < Screen.room.block.length; i++) {
                    for (int y = 0; y < Screen.room.block[0].length; y++) {
                        if (Screen.room.block[i][y].contains(Screen.mse)) {
                            if (Screen.room.block[i][y].groundId != Value.groundRoad && Screen.room.block[i][y].airId == Value.airAirBlock) {
                                Screen.room.block[i][y].airId = heldID;
                                Screen.coinCount -= buttonPrice[realID];
                            }
                        }
                    }
                }
            }
        }
    }

    public void define() {
        for (int i = 0; i < button.length; i++) {
            button[i] = new Rectangle((Screen.myWidth/2) - ((shopWidth *(buttonSize + cellSpace))/2) +
                    ((buttonSize + cellSpace)*i), (Screen.room.block[Screen.room.worldHeight -1][0].y) + Screen.room.blockSize + cellSpace + awayFromRoom, buttonSize, buttonSize);
        }
        buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
        buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y + button[0].height - (iconSize + 5), iconSize, iconSize);

    }

    public void draw(Graphics g) {
        for (int i = 0; i < button.length; i++) {
            if (button[i].contains(Screen.mse)) {
                g.setColor(new Color(255, 255, 255, 150));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }
            g.drawImage(Screen.tileSet_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
            if (buttonID[i] != Value.airAirBlock) {
                g.drawImage(Screen.tileSet_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - (itemIn * 2), button[i].height - (itemIn * 2), null); //draw icons for menu.
            }
            if (buttonPrice[i] > 0) { //draw the price label
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("£" + buttonPrice[i], button[i].x + itemIn, button[i].y + itemIn + 10); //change the y coordinate when redrawing labels for multiple towers.
            }
        }

        g.drawImage(Screen.tileSet_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(Screen.tileSet_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconTextY);
        g.drawString("" + Screen.coinCount, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);

        if(holdsItem) { //creates the item pick up and drag.
            g.drawImage(Screen.tileSet_air[heldID], Screen.mse.x - ((button[0]. width - (itemIn*2))/2) + itemIn, Screen.mse.y - ((button[0]. width - (itemIn*2))/2) + itemIn, button[0].width - (itemIn*2), button[0].height - (itemIn*2), null);
        }
//        g.drawString(coinCountLabel, x, y);

    }
}
