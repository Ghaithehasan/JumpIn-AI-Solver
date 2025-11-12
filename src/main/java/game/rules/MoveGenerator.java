//package game.rules;
//
//import model.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MoveGenerator {
//
////
////    public static List<Action> getPossibleActions(State state) {
////        List<Action> allActions = new ArrayList<>();
////
////        // أضف كل حركات الأرانب الممكنة
////        allActions.addAll(generateRabbitActions(state));
////
////        // أضف كل حركات الثعالب الممكنة
////        allActions.addAll(generateFoxActions(state));
////
////        return allActions;
////    }
////
////     private static List<Action> generateRabbitActions(State state)
////     {
////
////
////     }
////     private static List<Action> generateFoxActions(State state)
////     {
////
////     }
////
////    private static boolean isFoxMoveValid(Fox fox, Position newHeadPosition, State state) {
////        // سنستخدم isInsideBoard و isPositionOccupied هنا
////
////
////    }
////
////
////
////    private static List<Position> getPotentialFoxDestinations(Fox fox) {
////        List<Position> potentialHeadDestinations = new ArrayList<>();
////        Position firstCell = fox.getPosition();
////        if (fox.getOrientation()==Orientation.HORIZONTAL)
////        {
////            potentialHeadDestinations.add(new Position(firstCell.getX() , firstCell.getY()+1));
////            potentialHeadDestinations.add(new Position(firstCell.getX() , firstCell.getY()-1));
////
////        }
////        else
////        {
////            potentialHeadDestinations.add(new Position(firstCell.getX() +1 , firstCell.getY()));
////            potentialHeadDestinations.add(new Position(firstCell.getX() -1, firstCell.getY()));
////        }
////
////        return potentialHeadDestinations;
////    }
////
////
////    private static boolean isPositionOccupied(Position position, State state) {
////
////        List<Rabbit> rabbits = state.getRabbits();
////        List<Position> mushrooms = state.getLevel().getMushrooms();
////        List<Fox> foxes = state.getFoxes();
////
////        for(Rabbit rabbit : rabbits)
////        {
////            if(rabbit.getPosition().equals(position))
////            {
////                return true;
////            }
////        }
////
////        for(Position mushroom : mushrooms)
////        {
////            if(mushroom.equals(position))
////            {
////                return true;
////            }
////        }
////
////        for (Fox fox : foxes)
////        {
////
////           Position firstCell = fox.getPosition();
////           Position secondCell = getSecondCell(firstCell , fox.getOrientation());
////            if (firstCell.equals(position) || secondCell.equals(position)) {
////                return true;
////            }
////
////        }
////
////
////        return false;
////    }
////
////
////    private static Position getSecondCell(Position pos, Orientation orientation) {
////        if (orientation == Orientation.HORIZONTAL) {
////            return new Position(pos.getX() + 1, pos.getY());
////        } else {
////            return new Position(pos.getX(), pos.getY() + 1);
////        }
////
////    }
////
////    private static boolean isInsideBoard(Position position) {
////        if(position.getX()<0 || position.getY()<0)
////            return false;
////        if(position.getX()>4 || position.getY()>4)
////            return false;
////
////
////        return true;
////    }
////
//
//
//        public static List<Action> getPossibleActions(State state) {
//            List<Action> allActions = new ArrayList<>();
//
//            // 1. أضف كل حركات الأرانب الممكنة
//            allActions.addAll(generateRabbitActions(state));
//
//            // 2. أضف كل حركات الثعالب الممكنة
//            allActions.addAll(generateFoxActions(state));
//
//            return allActions;
//        }
//
//        /**
//         * تولد كل حركات القفز الممكنة لكل أرنب في الحالة الحالية.
//         * @param state الحالة الحالية للعبة.
//         * @return قائمة بـ RabbitActions الممكنة.
//         */
//        private static List<Action> generateRabbitActions(State state) {
//            List<Action> actions = new ArrayList<>();
//            // الاتجاهات الأربعة المحتملة: يمين، يسار، أعلى، أسفل {dx, dy}
//            int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
//
//            for (Rabbit rabbit : state.getRabbits()) {
//                Position currentPos = rabbit.getPosition();
//
//                for (int[] dir : directions) {
//                    // الخلية المجاورة التي يجب القفز فوقها
//                    Position jumpOverPos = new Position(currentPos.getX() + dir[0], currentPos.getY() + dir[1]);
//
//                    // الشرط الأول: يجب أن تكون الخلية المجاورة داخل اللوحة ومشغولة (للقفز فوقها)
//                    if (isInsideBoard(jumpOverPos) && isPositionOccupied(jumpOverPos, state)) {
//                        // الخلية التي سيهبط عليها الأرنب
//                        Position landingPos = new Position(jumpOverPos.getX() + dir[0], jumpOverPos.getY() + dir[1]);
//
//                        // الشرط الثاني: يجب أن تكون خلية الهبوط داخل اللوحة وفارغة
//                        if (isInsideBoard(landingPos) && !isPositionOccupied(landingPos, state)) {
//                            actions.add(new RabbitAction(rabbit.getId(), landingPos));
//                        }
//                    }
//                }
//            }
//            return actions;
//        }
//
//        /**
//         * تولد كل حركات الانزلاق الممكنة لكل ثعلب في الحالة الحالية.
//         * @param state الحالة الحالية للعبة.
//         * @return قائمة بـ FoxActions الممكنة.
//         */
//        private static List<Action> generateFoxActions(State state) {
//            List<Action> actions = new ArrayList<>();
//
//            for (Fox fox : state.getFoxes()) {
//                // تحديد اتجاهات الحركة بناءً على اتجاه الثعلب
//                int[][] directions;
//                if (fox.getOrientation() == Orientation.HORIZONTAL) {
//                    directions = new int[][]{{0, -1}, {0, 1}}; // يسار، يمين
//                } else { // VERTICAL
//                    directions = new int[][]{{-1, 0}, {1, 0}}; // أعلى، أسفل
//                }
//
//                for (int[] dir : directions) {
//                    int step = 1;
//                    // استمر في الانزلاق خطوة بخطوة
//                    while (true) {
//                        // حساب الموقع الجديد لرأس الثعلب
//                        Position newHead = new Position(fox.getPosition().getX() + dir[0] * step, fox.getPosition().getY() + dir[1] * step);
//                        // حساب الموقع الجديد لذيل الثعلب بناءً على الرأس الجديد
//                        Position newTail = getSecondCell(newHead, fox.getOrientation());
//
//                        // الشرط الأول: يجب أن يكون كل جزء من الثعلب داخل اللوحة
//                        if (!isInsideBoard(newHead) || !isInsideBoard(newTail)) {
//                            break; // خرج من اللوحة، توقف عن الانزلاق في هذا الاتجاه
//                        }
//
//                        // الشرط الثاني: يجب أن يكون الموقع الجديد خالياً من أي قطع أخرى
//                        if (isPositionOccupied(newHead, state) || isPositionOccupied(newTail, state)) {
//                            break; // اصطدم بحاجز، توقف. آخر موقع كان هو الموقع الصحيح.
//                        }
//
//                        // إذا تحقق الشرطان، فهذه حركة صالحة. أضفها.
//                        actions.add(new FoxAction(fox.getId(), newHead));
//                        step++; // حاول الانزلاق خطوة إضافية
//                    }
//                }
//            }
//            return actions;
//        }
//
//
//        // --- دوال مساعدة (Helper Methods) ---
//
//        /**
//         * تتحقق مما إذا كانت الخلية المشغولة بقطعة (أرنب، فطر، أو ثعلب).
//         * @param position الموقع المراد فحصه.
//         * @param state الحالة الحالية للعبة.
//         * @return true إذا كانت الخلية مشغولة، false إذا كانت فارغة.
//         */
//        private static boolean isPositionOccupied(Position position, State state) {
//            // التحقق من الأرانب
//            for (Rabbit rabbit : state.getRabbits()) {
//                if (rabbit.getPosition().equals(position)) return true;
//            }
//
//            // التحقق من الفطر
//            for (Position mushroom : state.getLevel().getMushrooms()) {
//                if (mushroom.equals(position)) return true;
//            }
//
//            // التحقق من الثعالب (يجب فحص الخليتين)
//            for (Fox fox : state.getFoxes()) {
//                Position head = fox.getPosition();
//                Position tail = getSecondCell(head, fox.getOrientation());
//                if (head.equals(position) || tail.equals(position)) {
//                    return true;
//                }
//            }
//
//            return false;
//        }
//
//        /**
//         * تحسب موقع الخلية الثانية للثعلب بناءً على موضع الرأس واتجاهه.
//         * @param headPosition موقع رأس الثعلب (الخلية المرجعية).
//         * @param orientation اتجاه الثعلب.
//         * @return موقع ذيل الثعلب.
//         */
//        private static Position getSecondCell(Position headPosition, Orientation orientation) {
//            if (orientation == Orientation.HORIZONTAL) {
//                return new Position(headPosition.getX() + 1, headPosition.getY());
//            } else { // VERTICAL
//                return new Position(headPosition.getX(), headPosition.getY() + 1);
//            }
//        }
//
//        /**
//         * تتحقق مما إذا كان الموقع داخل حدود لوحة 5x5.
//         * @param position الموقع المراد فحصه.
//         * @return true إذا كان الموقع داخل اللوحة.
//         */
//        private static boolean isInsideBoard(Position position) {
//            // اللوحة 5x5، لذا المؤشرات الصحيحة هي من 0 إلى 4
//            return position.getX() >= 0 && position.getX() < 5 &&
//                    position.getY() >= 0 && position.getY() < 5;
//        }
//    }


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





































//▪ Generate Next Sates()
//▪ Print State()
//▪ Get possible action
//
// s()


