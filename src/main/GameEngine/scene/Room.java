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
                block[y][x] = new Block(((Screen.myWidth/2) - (worldWidth * blockSize/2) + (x * blockSize)),
                        y * blockSize, blockSize, blockSize, value.groundGrassBlock, value.airAirBlock);
            }
        }
    }

    public void physics() {

    }

    public void draw(Graphics g) {
        for(int y=0; y < block.length; y++) {
            for(int x=0; x < block[0].length; x++) {
                block[y][x].draw(g);
            }
        }
    }
}
