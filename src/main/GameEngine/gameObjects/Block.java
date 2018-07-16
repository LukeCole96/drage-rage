package main.GameEngine.gameObjects;

import main.GameEngine.scene.Screen;

import java.awt.*;

public class Block extends Rectangle {

    public Rectangle towerLazerDistanceSquare;
    public int towerLazerRange = 130;
    public int groundId;
    public int airId;

    public int loseTime = 100, loseFrame = 0;

    public int shotMob = -1; //for the lazer stuff
    public boolean isShooting = false;

    Value value = new Value();

    public Block(int x, int y, int width, int height, int groundId, int airId) {
        setBounds(x, y, width, height);
        towerLazerDistanceSquare = new Rectangle(x - (towerLazerRange / 2), y - (towerLazerRange / 2), width + (towerLazerRange), height + (towerLazerRange));
        this.groundId = groundId;
        this.airId = airId;
    }

    public void draw(Graphics g) {
        g.drawImage(Screen.tileSet_ground[groundId], x, y, width, height, null);

        if (airId != value.airAirBlock) {
            g.drawImage(Screen.tileSet_air[airId], x, y, width, height, null);
        }
    }

    public void fight(Graphics g) {
        if (Screen.isDebug) {
            if (airId == Value.airTowerLaser) {
                g.drawRect(towerLazerDistanceSquare.x, towerLazerDistanceSquare.y, towerLazerDistanceSquare.width, towerLazerDistanceSquare.height);
            }

            if (isShooting) {
                g.setColor(new Color(255, 255, 0));
                g.drawLine(x + (width / 2), y + (height / 2), Screen.mobs[shotMob].x + (Screen.mobs[shotMob].width / 2), Screen.mobs[shotMob].y + (Screen.mobs[shotMob].height / 2));
            }
        }
    }

    public void physics() { //check each mob that passes within the laser range, and then shoot them.
        if (shotMob != -1 && towerLazerDistanceSquare.intersects(Screen.mobs[shotMob])) {
            isShooting = true;
        } else {
            isShooting = false;
        }

        if (!isShooting) {
            if (airId == Value.airTowerLaser) {
                for (int i = 0; i < Screen.mobs.length; i++) {
                    if (Screen.mobs[i].inGame) {
                        if (towerLazerDistanceSquare.intersects(Screen.mobs[i])) {
                            isShooting = true;
                            shotMob = i;
                        }
                    }
                }

            }
        }

        if (isShooting) { //deals wqith the shooting and health taking.
            if (loseFrame >= loseTime) {
                Screen.mobs[shotMob].loseHealth(1);
                loseFrame = 0;
            } else {
                loseFrame += 1;
            }

            if (Screen.mobs[shotMob].isDead()) {
                getMoney(Screen.mobs[shotMob].mobId);
                isShooting = false;
                shotMob = -1;
            }
        }
    }

    public void getMoney(int mobID) {
        Screen.coinCount += Value.deathReward[mobID];
    }
}
