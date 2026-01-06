package game;
import game.rules.MoveGenerator;
import game.search.*;
import model.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int the_number_of_level = 10;

        System.out.println("=======================================================================");
        System.out.println("=======================================================================");

        try {
            Level level = LevelLoader.loadLevel(the_number_of_level);
            SearchStrategy searchStrategy = new UCSStrategy();
            List<Action> solution = SearchAlgorithms.solve(level , searchStrategy);

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
