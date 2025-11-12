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

    // ==================== Rabbit Actions ====================

    private static List<Action> generateRabbitActions(State state) {
        List<Action> actions = new ArrayList<>();

        for (Rabbit rabbit : state.getRabbits()) {
            actions.addAll(generateRabbitJumps(rabbit, state));
        }

        return actions;
    }

    /**
     * يولد كل القفزات الممكنة للأرنب
     */
    private static List<Action> generateRabbitJumps(Rabbit rabbit, State state) {
        List<Action> jumps = new ArrayList<>();
        Position pos = rabbit.getPosition();

        // الاتجاهات الأربعة
        int[][] directions = {
                {0, -1},   // UP
                {0, +1},   // DOWN
                {-1, 0},   // LEFT
                {+1, 0}    // RIGHT
        };

        for (int[] dir : directions) {
            // جرب القفز بهالاتجاه
            jumps.addAll(generateJumpsInDirection(pos, dir[0], dir[1], rabbit.getId(), state));
        }

        return jumps;
    }

    /**
     * يولد قفزات باتجاه معين (يقدر يقفز فوق قطع متتالية)
     */
    private static List<Action> generateJumpsInDirection(Position start, int dx, int dy, int rabbitId, State state) {
        List<Action> jumps = new ArrayList<>();

        // الخلية الأولى لازم تكون فيها قطعة
        Position firstCell = new Position(start.getX() + dx, start.getY() + dy);

        if (!isInBounds(firstCell) || !isOccupied(firstCell, state)) {
            return jumps;  // ما في قطعة نقفز فوقها
        }

        // ابدأ القفز - جرب كل المسافات الممكنة
        int distance = 2;  // أول قفزة = خليتين

        while (distance <= 10) {  // max معقول (5x5 board)
            Position target = new Position(
                    start.getX() + dx * distance,
                    start.getY() + dy * distance
            );

            // هل الهدف جوا الشبكة؟
            if (!isInBounds(target)) {
                break;  // خرجنا من الشبكة
            }

            // هل الهدف فاضي أو جحر؟
            if (isOccupiedByPiece(target, state)) {
                // مشغول بقطعة → كمل القفز فوقها
                distance++;
                continue;
            }

            // ✅ الهدف فاضي! هاي قفزة صحيحة
            jumps.add(new RabbitAction(rabbitId, target));

            // وقف - ما بنقدر نقفز أبعد من هون
            break;
        }

        return jumps;
    }

    // ==================== Fox Actions ====================

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

        // الثعلب يتحرك بس باتجاه orientation
        if (ori == Orientation.HORIZONTAL) {
            moves.addAll(generateFoxSlide(fox, +1, 0, state));  // RIGHT
            moves.addAll(generateFoxSlide(fox, -1, 0, state));  // LEFT
        } else {
            moves.addAll(generateFoxSlide(fox, 0, +1, state));  // DOWN
            moves.addAll(generateFoxSlide(fox, 0, -1, state));  // UP
        }

        return moves;
    }

    private static List<Action> generateFoxSlide(Fox fox, int dx, int dy, State state) {
        List<Action> moves = new ArrayList<>();
        Position currentPos = fox.getPosition();

        // جرب الانزلاق خطوة خطوة
        for (int step = 1; step <= 4; step++) {
            Position newPos = new Position(
                    currentPos.getX() + dx * step,
                    currentPos.getY() + dy * step
            );

            if (isValidFoxMove(fox, newPos, state)) {
                moves.add(new FoxAction(fox.getId(), newPos));
            } else {
                break;  // صادف عائق
            }
        }

        return moves;
    }

    private static boolean isValidFoxMove(Fox fox, Position newPos, State state) {
        Position second = getFoxSecondCell(newPos, fox.getOrientation());

        if (!isInBounds(newPos) || !isInBounds(second)) {
            return false;
        }

        if (isOccupiedByOtherThanFox(newPos, fox.getId(), state) ||
                isOccupiedByOtherThanFox(second, fox.getId(), state)) {
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

    // ==================== Helper Methods ====================

    private static boolean isInBounds(Position pos) {
        return pos.getX() >= 0 && pos.getX() < 5 &&
                pos.getY() >= 0 && pos.getY() < 5;
    }

    /**
     * هل الموقع مشغول بأي قطعة؟
     */
    private static boolean isOccupied(Position pos, State state) {
        // أرانب
        for (Rabbit r : state.getRabbits()) {
            if (r.getPosition().equals(pos)) {
                return true;
            }
        }

        // ثعالب
        for (Fox f : state.getFoxes()) {
            if (foxOccupies(f, pos)) {
                return true;
            }
        }

        // فطر
        for (Position m : state.getLevel().getMushrooms()) {
            if (m.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    /**
     * هل الموقع مشغول بقطعة (مش جحر)؟
     */
    private static boolean isOccupiedByPiece(Position pos, State state) {
        // أرانب
        for (Rabbit r : state.getRabbits()) {
            if (r.getPosition().equals(pos)) {
                return true;
            }
        }

        // ثعالب
        for (Fox f : state.getFoxes()) {
            if (foxOccupies(f, pos)) {
                return true;
            }
        }

        // فطر
        for (Position m : state.getLevel().getMushrooms()) {
            if (m.equals(pos)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isOccupiedByOtherThanFox(Position pos, int foxId, State state) {
        // أرانب
        for (Rabbit r : state.getRabbits()) {
            if (r.getPosition().equals(pos)) {
                return true;
            }
        }

        // ثعالب أخرى
        for (Fox f : state.getFoxes()) {
            if (f.getId() == foxId) continue;
            if (foxOccupies(f, pos)) {
                return true;
            }
        }

        // فطر
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