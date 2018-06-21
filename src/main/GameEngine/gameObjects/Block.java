package main.GameEngine.gameObjects;

import main.GameEngine.scene.Screen;

import java.awt.*;

public class Block extends Rectangle {
    public int groundId;
    public int airId;

    Value value = new Value();

    public Block(int x, int y, int width, int height, int groundId, int airId){
        setBounds(x, y, width, height);
        this.groundId = groundId;
        this.airId = airId;
    }

    public void draw(Graphics g) {
        g.drawImage(Screen.tileSet_ground[groundId], x, y, width, height, null);

        if(airId != value.airAirBlock) {
            g.drawImage(Screen.tileSet_air[airId], x, y, width, height, null);
        }
    }
}
