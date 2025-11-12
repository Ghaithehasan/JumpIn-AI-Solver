package game;
import model.Level;
import model.LevelLoader;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        int levelNumber = 8;

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     Welcome to JUMP IN             ║");
        System.out.println("║     Level " + levelNumber + "                        ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        try {
            Level level = LevelLoader.loadLevel(levelNumber);
            GamePlay game = new GamePlay(level);
            game.play();

        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

}
