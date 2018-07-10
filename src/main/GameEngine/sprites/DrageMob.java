package main.GameEngine.sprites;

import main.GameEngine.gameObjects.Value;
import main.GameEngine.scene.Screen;

import java.awt.*;

public class DrageMob extends Rectangle {
    public int coordinateX, coordinateY;
    public int mobSize = 52; //Size of mob image based on room blockSize value (adjust later in refactoring).
    public int mobId = Value.mobAir;
    public boolean inGame = false;

    public int moveUp = 0;
    public int moveDown = 1;
    public int moveRight = 2;
    public int moveLeft = 3;
    public int direction = moveRight; //set up the direction to begin with.
    public boolean hasUpward = false, hasDownward = false, hasLeft = false, hasRight = false;

    public int mobWalkCounter = 0;
    public int walkFrame = 0, walkSpeed = 40; //should be passed through physics

    public DrageMob() {
    }

    public void spawnMob(int mobId) {
        for(int i=0; i < Screen.room.block.length; i++) {
            if(Screen.room.block[i][0].groundId == Value.groundRoad) {
                setBounds(Screen.room.block[i][0].x, Screen.room.block[i][0].y, mobSize, mobSize);
                coordinateX = 0;
                coordinateY = i;
            }
        }

        this.mobId = mobId;
        inGame = true;
    }

    public void physics() {
        if (walkFrame >= walkSpeed) {
            if(direction == moveRight) { //switch case could be better suited?
                x += 1;
                hasRight = true;
            } else if (direction == moveUp) {
                y -= 1;
                hasUpward = true;
            } else if (direction == moveDown) {
                y += 1;
                hasDownward = true;
             } else if (direction == moveLeft) {
                x -= 1;
                hasLeft = true;
            }
            mobWalkCounter += 1;

            if(mobWalkCounter == Screen.room.blockSize) {
                if (direction == moveRight) { //switch case should be generalised on refactor...
                    coordinateX += 1;
                    hasRight = true;
                } else if (direction == moveUp) {
                    coordinateY -= 1;
                    hasUpward = true;
                } else if (direction == moveDown) {
                    coordinateY += 1;
                    hasDownward = true;
                } else if (direction == moveLeft) {
                    coordinateX -= 1;
                    hasLeft = true;
                }

                //handles sprite path logic.
                //will be generalised.
                if(!hasUpward) {
                    try {
                        if (Screen.room.block[coordinateY +1][coordinateX].groundId == Value.groundRoad) {
                            direction = moveDown;
                        }
                    } catch (Exception ex) {}
                }
                if(!hasDownward) {
                    try {
                        if (Screen.room.block[coordinateY -1][coordinateX].groundId == Value.groundRoad) {
                            direction = moveUp;
                        }
                    } catch (Exception ex) {}
                }
                if(!hasLeft) {
                    try {
                        if (Screen.room.block[coordinateY][coordinateX +1].groundId == Value.groundRoad) {
                            direction = moveRight;
                        }
                    } catch (Exception ex) {}
                }
                if(!hasRight) {
                    try {
                        if (Screen.room.block[coordinateY][coordinateX -1].groundId == Value.groundRoad) {
                            direction = moveLeft;
                        }
                    } catch (Exception ex) {}
                }

                mobWalkCounter = 0;
                hasUpward = false;
                hasDownward = false;
                hasLeft = false;
                hasRight = false;
            }

            walkFrame = 0;
        } else {
            walkFrame += 1;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(Screen.tileset_mob[mobId], x, y, width, height, null);
    }
}
