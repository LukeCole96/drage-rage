package main.GameEngine.scene;

import main.GameEngine.gameObjects.Block;
import main.GameEngine.gameObjects.Value;

import java.awt.*;

public class Room {

    public int worldWidth = 12;
    public int worldHeight = 9;
    public int blockSize = 52;
    public Block[][] block;

    Value value = new Value();

    public Room() {
        define();
    }

    public void define() {
        block = new Block[worldHeight][worldWidth];

        for(int y=0; y < block.length; y++) {
            for(int x=0; x < block[0].length; x++) {
                block[y][x] = new Block((Screen.myWidth/2) - ((worldWidth * blockSize)/2) + (x * blockSize),
                        y * blockSize, blockSize, blockSize, value.groundGrassBlock, value.airAirBlock);
            }
        }
    }

    public void physics() {
        for (int i = 0; i < block.length; i++) {
            for (int y = 0; y < block[0].length; y++) {
                block[i][y].physics();
            }
        }
    }

    public void draw(Graphics g) {
        for(int y=0; y < block.length; y++) { //render the tiles in the screen. This is the main drawing component for the paths etc.
            for(int x=0; x < block[0].length; x++) {
                block[y][x].draw(g);
            }
        }

        for(int y=0; y < block.length; y++) { // create the fighting.
            for(int x=0; x < block[0].length; x++) {
                block[y][x].fight(g);
            }
        }
    }
}
