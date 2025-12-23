package game;
import game.rules.MoveGenerator;
import game.search.*;
import model.*;

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

//        Level level = null;
//        try {
//            level = LevelLoader.loadLevel(10);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        State state = level.createInitialState();
//        List<Action> actions = MoveGenerator.getPossibleActions(state);
//
//        for (Action a : actions) {
//            if (a instanceof RabbitAction) {
//                RabbitAction ra = (RabbitAction) a;
//                Position from = findRabbitPosition(state, ra.getRabbit_id());
//                Position to = ra.getNewPosition();
//
//                String direction = getDirection(from, to);
//                System.out.println("Rabbit " + ra.getRabbit_id() + " → " + direction);
//            } else if (a instanceof FoxAction) {
//                FoxAction fa = (FoxAction) a;
//                // نفس الشي للثعلب
//            }
//        }
//
//// Helper:



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
