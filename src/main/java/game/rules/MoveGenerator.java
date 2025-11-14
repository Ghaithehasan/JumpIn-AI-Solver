package game.rules;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
    public static List<Action> getPossibleActions(State state) {
        List<Action> actions = new ArrayList<>();
        actions.addAll(generateRabbitActions(state));
        actions.addAll(generateFoxActions(state));
        return actions;
    }

    private static List<Action> generateRabbitActions(State state) {
        List<Action> actions = new ArrayList<>();

        for (Rabbit rabbit : state.getRabbits()) {
            actions.addAll(generateRabbitJumps(rabbit, state));
        }

        return actions;
    }

    private static List<Action> generateRabbitJumps(Rabbit rabbit, State state) {
        List<Action> jumps = new ArrayList<>();
        Position pos = rabbit.getPosition();


        int[][] directions = {
                {0, -1},   // up
                {0, +1},   // down
                {-1, 0},   // left
                {+1, 0}    // right
        };

        for (int[] dir : directions) {
            jumps.addAll(generateJumpsInDirection(pos, dir[0], dir[1], rabbit.getId(), state));
        }

        return jumps;
    }


    private static List<Action> generateJumpsInDirection(Position start, int dx, int dy, int rabbitId, State state) {
        List<Action> jumps = new ArrayList<>();

        Position firstCell = new Position(start.getX() + dx, start.getY() + dy);

        if (!isInBounds(firstCell) || !isOccupied(firstCell, state)) {
            return jumps;
        }

        int distance = 2;

        while (distance <= 10) { // 5x5
            Position target = new Position(
                    start.getX() + dx * distance,
                    start.getY() + dy * distance
            );

            if (!isInBounds(target)) {
                break;
            }

            if (isOccupiedByPiece(target, state)) {
                distance++;
                continue;
            }

            jumps.add(new RabbitAction(rabbitId, target));

            break;
        }

        return jumps;
    }


    private static List<Action> generateFoxActions(State state) {
        List<Action> actions = new ArrayList<>();

        for (Fox fox : state.getFoxes()) {
            actions.addAll(generateFoxMoves(fox, state));
        }

        return actions;
    }

    private static List<Action> generateFoxMoves(Fox fox, State state) {
        List<Action> moves = new ArrayList<>();
        Orientation ori = fox.getOrientation();


        if (ori == Orientation.HORIZONTAL) {
            moves.addAll(generateFoxSlide(fox, +1, 0, state));  // right
            moves.addAll(generateFoxSlide(fox, -1, 0, state));  // left
        } else {
            moves.addAll(generateFoxSlide(fox, 0, +1, state));  // down
            moves.addAll(generateFoxSlide(fox, 0, -1, state));  // upp
        }

        return moves;
    }

    private static List<Action> generateFoxSlide(Fox fox, int dx, int dy, State state) {
        List<Action> moves = new ArrayList<>();
        Position currentPos = fox.getPosition();


        for (int step = 1; step <= 4; step++) {
            Position newPos = new Position(
                    currentPos.getX() + dx * step,
                    currentPos.getY() + dy * step
            );

            if (isValidFoxMove(fox, newPos, state)) {
                moves.add(new FoxAction(fox.getId(), newPos));
            } else {
                break;
            }
        }

        return moves;
    }

    private static boolean isValidFoxMove(Fox fox, Position newPos, State state) {
        Position second = getFoxSecondCell(newPos, fox.getOrientation());

        if (!isInBounds(newPos) || !isInBounds(second)) {
            return false;
        }

        if ( isOccupiedByOtherThanFox(newPos, fox.getId(), state) || isOccupiedByOtherThanFox(second, fox.getId(), state) ) {
            return false;
        }

        return true;
    }

    private static Position getFoxSecondCell(Position pos, Orientation ori) {
        if (ori == Orientation.HORIZONTAL) {
            return new Position(pos.getX() + 1, pos.getY());
        } else {
            return new Position(pos.getX(), pos.getY() + 1);
        }
    }


    private static boolean isInBounds(Position pos) {
        return pos.getX() >= 0 && pos.getX() < 5 &&
                pos.getY() >= 0 && pos.getY() < 5;
    }

    private static boolean isOccupied(Position pos, State state) {
        for (Rabbit r : state.getRabbits()) {
            if (r.getPosition().equals(pos)) {
                return true;
            }
        }

        for (Fox f : state.getFoxes()) {
            if (foxOccupies(f, pos)) {
                return true;
            }
        }

        for (Position m : state.getLevel().getMushrooms()) {
            if (m.equals(pos)) {
                return true;
            }
        }

        return false;
    }


    private static boolean isOccupiedByPiece(Position pos, State state) {
        for (Rabbit r : state.getRabbits()) {
            if (r.getPosition().equals(pos)) {
                return true;
            }
        }

        for (Fox f : state.getFoxes()) {
            if (foxOccupies(f, pos)) {
                return true;
            }
        }

        for (Position m : state.getLevel().getMushrooms()) {
            if (m.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isOccupiedByOtherThanFox(Position pos, int foxId, State state) {
        for (Rabbit r : state.getRabbits()) {
            if (r.getPosition().equals(pos)) {
                return true;
            }
        }

        for (Fox f : state.getFoxes()) {
            if (f.getId() == foxId) continue;
            if (foxOccupies(f, pos)) {
                return true;
            }
        }

        for (Position m : state.getLevel().getMushrooms()) {
            if (m.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    private static boolean foxOccupies(Fox fox, Position pos) {
        Position head = fox.getPosition();
        Position tail = getFoxSecondCell(head, fox.getOrientation());
        return head.equals(pos) || tail.equals(pos);
    }
}