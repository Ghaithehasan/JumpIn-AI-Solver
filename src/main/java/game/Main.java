package game;
import game.search.SearchAlgorithms;
import model.Action;
import model.Level;
import model.LevelLoader;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int the_number_of_level = 10;
//        System.out.println("╔════════════════════════════════════╗");
//        System.out.println("║     Welcome to JUMP IN             ║");
//        System.out.println("║     Level " + the_number_of_level + "                       ║");
//        System.out.println("╚════════════════════════════════════╝");
//        System.out.println();
//
//        try {
//            Level level = LevelLoader.loadLevel(the_number_of_level);
//            GamePlay game = new GamePlay(level);
//            game.play();
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());;
//        }





        System.out.println("=======================================================================");
        System.out.println("=======================================================================");





        try {
            Level level = LevelLoader.loadLevel(the_number_of_level);
//            List<Action> solution = SearchAlgorithms.BFS(level);
            List<Action> solution = SearchAlgorithms.DFS(level);


            if (solution != null) {
                System.out.println("--- the answer is Found ---");
                int the_number_of_steps=0;
                for (Action action : solution) {
                    System.out.println(action);
                    the_number_of_steps++;
                }

                System.out.println("the number of steps is : " + the_number_of_steps);
            } else {
                System.out.println("didnt Found The Answer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
