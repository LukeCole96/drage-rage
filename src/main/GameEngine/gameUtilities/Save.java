package main.GameEngine.gameUtilities;

import main.GameEngine.scene.Screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Save {

    public void loadSave(File loadPath) {
        try {
            Scanner loadScanner = new Scanner(loadPath);
            while (loadScanner.hasNext()) {
                Screen.killsToWin = loadScanner.nextInt(); //Checks the value in the text file.
                System.out.println("kills to win = " + Screen.killsToWin);
                for (int y = 0; y < Screen.room.block.length; y++) {
                    for (int x = 0; x < Screen.room.block[0].length; x++) {
                        Screen.room.block[y][x].groundId = loadScanner.nextInt();
                    }
                }
                for (int y = 0; y < Screen.room.block.length; y++) {
                    for (int x = 0; x < Screen.room.block[0].length; x++) {
                        Screen.room.block[y][x].airId = loadScanner.nextInt();

                    }
                }
            }
            loadScanner.close();
        } catch (FileNotFoundException e) {

        }
    }
}
