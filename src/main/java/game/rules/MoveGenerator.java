//package game.rules;
//
//import model.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MoveGenerator {
//
////    createBoardSnapshot
//    private static final int BOARD_SIZE=5;
//    private static final char MUSHROOM = 'M';
//    private static final char HOLE = 'O';
//    private static final char EMPTY = '.';
//    private static final char FOX = 'F';
//    private static final char RABBIT = 'R';
//
//
//    private static char[][] createBoardSnapshot(State state) {
//        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
//        for (int y = 0; y < BOARD_SIZE; y++) {
//            for (int x = 0; x < BOARD_SIZE; x++) {
//                board[y][x] = EMPTY;
//            }
//        }
//
//        // وضع الفطر والحفر (عناصر ثابتة)
//        for (Position pos : state.getLevel().getMushrooms()) {
//            board[pos.getY()][pos.getX()] = MUSHROOM;
//        }
//        for (Position pos : state.getLevel().getHoles()) {
//            board[pos.getY()][pos.getX()] = HOLE;
//        }
//
//        // وضع الثعالب (تستخدم مربعين)
//        for (Fox fox : state.getFoxes()) {
//            Position secondCell = getFoxSecondCell(fox.getPosition(), fox.getOrientation());
//            board[fox.getPosition().getY()][fox.getPosition().getX()] = FOX;
//            board[secondCell.getY()][secondCell.getX()] = FOX;
//        }
//
//        // وضع الأرانب (تستخدم مربع واحد)
//        for (Rabbit rabbit : state.getRabbits()) {
//            board[ rabbit.getPosition().getY()][ rabbit.getPosition().getX()] = RABBIT;
//        }
//        return board;
//    }
//
//        public static List<Action> getPossibleActions(State state) {
//        char[][] board = createBoardSnapshot(state);
//        List<Action> actions = new ArrayList<>();
//        actions.addAll(generateRabbitActions(state , board));
//        actions.addAll(generateFoxActions(state , board));
//        return actions;
//    }
//
//
//
//    private static List<Action> generateRabbitActions(State state , char[][] board) {
//        List<Action> actions = new ArrayList<>();
//
//        for (Rabbit rabbit : state.getRabbits()) {
//            actions.addAll(generateRabbitJumps(rabbit, state , board));
//        }
//
//        return actions;
//    }
//
//    private static List<Action> generateRabbitJumps(Rabbit rabbit, State state , char[][] board) {
//        List<Action> jumps = new ArrayList<>();
//        Position pos = rabbit.getPosition();
//
//
//        int[][] directions = {
//                {0, -1},   // up
//                {0, +1},   // down
//                {-1, 0},   // left
//                {+1, 0}    // right
//        };
//
//        for (int[] dir : directions) {
//            jumps.addAll(generateJumpsInDirection(pos, dir[0], dir[1], rabbit.getId(), state , board));
//        }
//
//        return jumps;
//    }
//
//
//    private static List<Action> generateJumpsInDirection(Position start, int dx, int dy, int rabbitId, State state , char[][] board) {
//        List<Action> jumps = new ArrayList<>();
//
//        Position firstCell = new Position(start.getX() + dx, start.getY() + dy);
//
//        if (!isInBounds(firstCell) || !isOccupied(firstCell , board)) {
//            return jumps;
//        }
//
//        int distance = 2;
//
//        while (distance <= 10) { // 5x5
//            Position target = new Position(
//                    start.getX() + dx * distance,
//                    start.getY() + dy * distance
//            );
//
//            if (!isInBounds(target)) {
//                break;
//            }
//
//            if (isOccupied(target , board)) {
//                distance++;
//                continue;
//            }
//
//            jumps.add(new RabbitAction(rabbitId, target));
//
//            break;
//        }
//
//        return jumps;
//    }
//
//    private static List<Action> generateFoxActions(State state, char[][] board) {
//        List<Action> actions = new ArrayList<>();
//
//        for (Fox fox : state.getFoxes()) {
//            actions.addAll(generateFoxMoves(fox, state, board));
//        }
//
//        return actions;
//    }
//
//    private static List<Action> generateFoxMoves(Fox fox, State state, char[][] board) {
//        List<Action> moves = new ArrayList<>();
//        Orientation ori = fox.getOrientation();
//
//        if (ori == Orientation.HORIZONTAL) {
//            moves.addAll(generateFoxStep(fox, +1, 0, state, board));  // RIGHT
//            moves.addAll(generateFoxStep(fox, -1, 0, state, board));  // LEFT
//        } else {
//            moves.addAll(generateFoxStep(fox, 0, +1, state, board));  // DOWN
//            moves.addAll(generateFoxStep(fox, 0, -1, state, board));  // UP
//        }
//
//        return moves;
//    }
//
//    private static List<Action> generateFoxStep(Fox fox, int dx, int dy, State state, char[][] board) {
//        List<Action> moves = new ArrayList<>();
//        Position currentHead = fox.getPosition();
//        Position currentTail = getFoxSecondCell(currentHead, fox.getOrientation());
//
//        // حفظ القيم الأصلية
//        char savedHead = board[currentHead.getY()][currentHead.getX()];
//        char savedTail = board[currentTail.getY()][currentTail.getX()];
//
//        try {
//            // امسح الثعلب مؤقتاً
//            board[currentHead.getY()][currentHead.getX()] = EMPTY;
//            board[currentTail.getY()][currentTail.getX()] = EMPTY;
//
//            // احسب الموقع الجديد
//            Position newHead = new Position(
//                    currentHead.getX() + dx,
//                    currentHead.getY() + dy
//            );
//            Position newTail = getFoxSecondCell(newHead, fox.getOrientation());
//
//            // تحقق بسيط ومباشر
//            if (isInBounds(newHead) && isInBounds(newTail) &&
//                    board[newHead.getY()][newHead.getX()] == EMPTY &&
//                    board[newTail.getY()][newTail.getX()] == EMPTY) {
//
//                moves.add(new FoxAction(fox.getId(), newHead));
//            }
//
//        } finally {
//            // أرجع الثعلب (دايماً يتنفذ)
//            board[currentHead.getY()][currentHead.getX()] = savedHead;
//            board[currentTail.getY()][currentTail.getX()] = savedTail;
//        }
//
//        return moves;
//    }
//
//    private static boolean isValidFoxMove(Fox fox, Position newPos, char[][] board) {
//        // احصل على المواقع القديمة للثعلب
//        Position oldHead = fox.getPosition();
//        Position oldTail = getFoxSecondCell(oldHead, fox.getOrientation());
//
//        // احصل على المواقع الجديدة للثعلب
//        Position newHead = newPos;
//        Position newTail = getFoxSecondCell(newHead, fox.getOrientation());
//
//        if (!isInBounds(newHead) || !isInBounds(newTail)) {
//            return false;
//        }
//
//        // التحقق مما إذا كان الرأس الجديد في مكان خالٍ
//        // (إما فارغ في اللقطة، أو كان يشغله ذيل الثعلب القديم)
//        boolean isHeadFree = board[newHead.getY()][newHead.getX()] == EMPTY || newHead.equals(oldTail);
//
//        // التحقق مما إذا كان الذيل الجديد في مكان خالٍ
//        // (إما فارغ في اللقطة، أو كان يشغله رأس الثعلب القديم)
//        boolean isTailFree = board[newTail.getY()][newTail.getX()] == EMPTY || newTail.equals(oldHead);
//
//        return isHeadFree && isTailFree;
//    }
//
//    private static Position getFoxSecondCell(Position pos, Orientation ori) {
//        if (ori == Orientation.HORIZONTAL) {
//            return new Position(pos.getX() + 1, pos.getY());
//        } else {
//            return new Position(pos.getX(), pos.getY() + 1);
//        }
//    }
//
//
//    private static boolean isInBounds(Position pos) {
//        return pos.getX() >= 0 && pos.getX() < 5 &&
//                pos.getY() >= 0 && pos.getY() < 5;
//    }
//
//    private static boolean isOccupied(Position pos, char[][] board) {
//        return board[pos.getY()][pos.getX()] != EMPTY;
//    }
//
//
//
//    private static boolean foxOccupies(Fox fox, Position pos) {
//        Position head = fox.getPosition();
//        Position tail = getFoxSecondCell(head, fox.getOrientation());
//        return head.equals(pos) || tail.equals(pos);
//    }
//}
//







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
            moves.addAll(generateFoxStep(fox, +1, 0, state));  // right
            moves.addAll(generateFoxStep(fox, -1, 0, state));  // left
        } else {
            moves.addAll(generateFoxStep(fox, 0, +1, state));  // down
            moves.addAll(generateFoxStep(fox, 0, -1, state));  // upp
        }

        return moves;
    }

    private static List<Action> generateFoxStep(Fox fox, int dx, int dy, State state) {
        List<Action> moves = new ArrayList<>();
        Position currentPos = fox.getPosition();

        Position newPos = new Position(
                currentPos.getX() + dx ,
                currentPos.getY() + dy
        );

        if (isValidFoxMove(fox, newPos, state)) {
            moves.add(new FoxAction(fox.getId(), newPos));
        }
        return moves;

        //        for (int step = 1; step <= 4; step++) {
//            Position newPos = new Position(
//                    currentPos.getX() + dx * step,
//                    currentPos.getY() + dy * step
//            );
//
//            if (isValidFoxMove(fox, newPos, state)) {
//                moves.add(new FoxAction(fox.getId(), newPos));
//            } else {
//                break;
//            }
//        }
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
