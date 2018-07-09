package main.GameEngine.sprites;

import main.GameEngine.gameObjects.Value;
import main.GameEngine.scene.Screen;

import java.awt.*;

public class DrageMob extends Rectangle {
    public int mobSize = 52; //Size of mob image based on room blockSize value (adjust later in refactoring).
    public int mobId = Value.mobAir;
    public boolean inGame = false;

    public DrageMob() {
    }

    public void spawnMob(int mobId) {
        for(int i=0; i < Screen.room.block.length; i++) {
            if(Screen.room.block[i][0].groundId == Value.groundRoad) {
                setBounds(Screen.room.block[i][0].x, Screen.room.block[i][0].y, mobSize, mobSize);
            }
        }

        this.mobId = mobId;
        inGame = true;
    }

    public void physics() {

    }

    public void draw(Graphics g) {
        g.drawImage(Screen.tileset_mob[mobId], x, y, width, height, null);
    }
}
