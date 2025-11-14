package game;
import model.Level;
import model.LevelLoader;
import java.io.IOException;
public class Main {

    public static void main(String[] args) {
        int the_number_of_level = 3;
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     Welcome to JUMP IN             ║");
        System.out.println("║     Level " + the_number_of_level + "                    ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        try {
            Level level = LevelLoader.loadLevel(the_number_of_level);
            GamePlay game = new GamePlay(level);
            game.play();

        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }


    }

}
