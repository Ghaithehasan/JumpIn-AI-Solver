
package game;

import model.*;
import game.rules.MoveGenerator;
import game.rules.GoalChecker;

import java.util.List;
import java.util.Scanner;

public class GamePlay {

    private final Level level;
    private Node currentNode;
    private final Scanner scanner;


    public GamePlay(Level level) {
        this.level = level;
        State initialState = level.createInitialState();
        this.currentNode = new Node(null, initialState, null, 0);
        this.scanner = new Scanner(System.in);
    }



    public boolean play() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     Level " + level.getLevelId() + "                        ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        while (!GoalChecker.isFinal(currentNode.getState())) {
            printBoard();

            List<Action> possibleActions = MoveGenerator.getPossibleActions(currentNode.getState());

            if (possibleActions.isEmpty()) {
                System.out.println("\n No possible moves");
                return false;
            }

            printAvailableActions(possibleActions);

            Action selectedAction = selectAction(possibleActions);

            if (selectedAction == null) {
                return false;
            }

            State newState = selectedAction.applyMove(currentNode.getState());
            currentNode = new Node(
                    currentNode,
                    newState,
                    selectedAction,
                    currentNode.getDepth() + 1
            );

            System.out.println("\n✅ Move applied! Total moves: " + currentNode.getDepth());
            System.out.println("─────────────────────────────────────");
        }

        printBoard();
        printVictory();
        return true;
    }



    private void printBoard() {
        State state = currentNode.getState();

        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║         GAME BOARD            ║");
        System.out.println("╠═══════════════════════════════╣");

        String[][] board = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = " . ";
            }
        }

        for (Position hole : state.getHolesSet()) {
            board[hole.getY()][hole.getX()] = " ○ ";
        }

        for (Position mushroom : level.getMushrooms()) {
            board[mushroom.getY()][mushroom.getX()] = " M ";
        }

        for (Fox fox : state.getFoxes()) {
            Position pos = fox.getPosition();
            int id = fox.getId();

            if (fox.getOrientation() == Orientation.HORIZONTAL) {
                board[pos.getY()][pos.getX()] = "F" + id + "─";
                board[pos.getY()][pos.getX() + 1] = "──";
            } else {
                board[pos.getY()][pos.getX()] = "F" + id + "│";
                board[pos.getY() + 1][pos.getX()] = " │ ";
            }
        }

        for (Rabbit rabbit : state.getRabbits()) {
            Position pos = rabbit.getPosition();
            int id = rabbit.getId();

            if (state.getHolesSet().contains(pos)) {
                board[pos.getY()][pos.getX()] = "R" + id + "●";
            } else {
                board[pos.getY()][pos.getX()] = " R" + id;
            }
        }

        System.out.println("║    0   1   2   3   4          ║");
        System.out.println("║  ┌───┬───┬───┬───┬───┐        ║");

        for (int y = 0; y < 5; y++) {
            System.out.print("║" + y + " │");
            for (int x = 0; x < 5; x++) {
                System.out.print(board[y][x] + "│");
            }
            System.out.println("        ║");

            if (y < 4) {
                System.out.println("║  ├───┼───┼───┼───┼───┤        ║");
            }
        }

        System.out.println("║  └───┴───┴───┴───┴───┘        ║");
        System.out.println("╚═══════════════════════════════╝");

        System.out.println("\n Information About the game :");
        System.out.println("   R0, R1, R2 = Rabbits");
        System.out.println("   F0, F1     = Foxes");
        System.out.println("   M          = Mushroom");
        System.out.println("   ○          = Hole");
        System.out.println("   R●         = Rabbit in the hole");
    }



    private void printAvailableActions(List<Action> actions) {
        System.out.println("\n Available Actions the available moves is : " + actions.size() + " moves ");
        System.out.println("─────────────────────────────────────");

        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            System.out.printf("[%2d] %s%n", i, formatAction(action));
        }

        System.out.println("─────────────────────────────────────");
    }

    private String formatAction(Action action) {
        if (action instanceof RabbitAction) {
            RabbitAction ra = (RabbitAction) action;
            return String.format("🐰 Rabbit %d → (%d, %d)",
                    ra.getRabbit_id(),
                    ra.getNewPosition().getX(),
                    ra.getNewPosition().getY());
        } else if (action instanceof FoxAction) {
            FoxAction fa = (FoxAction) action;
            return String.format("🦊 Fox %d → (%d, %d)",
                    fa.getFox_id(),
                    fa.getNewPosition().getX(),
                    fa.getNewPosition().getY());
        }
        return action.toString();
    }



    private Action selectAction(List<Action> actions) {
        while (true) {
            System.out.print("\n➤ Enter action number, 'q' to quit: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                return null;
            }



            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice < actions.size()) {
                    return actions.get(choice);
                } else {
                    System.out.println(" Invalid choice! Please enter a number between 0 and " + (actions.size() - 1));
                }
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input! Please enter a number, 'q' to quit.");
            }
        }
    }



    private void printVictory() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║                                    ║");
        System.out.println("║        🎉 CONGRATULATIONS! 🎉      ║");
        System.out.println("║                                    ║");
        System.out.println("║     All rabbits are in holes!      ║");
        System.out.println("║                                    ║");
        System.out.println("║     Total Moves: " + String.format("%2d", currentNode.getDepth()) + "              ║");
        System.out.println("║                                    ║");
        System.out.println("╚════════════════════════════════════╝");

        System.out.println("\n Solution Path:");
        printSolutionPath();
    }

    private void printSolutionPath() {
        List<Action> path = currentNode.getPath();

        if (path.isEmpty()) {
            System.out.println("   (No moves - already solved!)");
            return;
        }

        for (int i = 0; i < path.size(); i++) {
            System.out.println("   Step " + (i + 1) + ": " + formatAction(path.get(i)));
        }
    }

}