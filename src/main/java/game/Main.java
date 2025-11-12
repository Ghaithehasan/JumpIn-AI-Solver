package game;

import model.Level;
import model.LevelLoader;

import java.io.IOException;
import java.util.Scanner;

public class Main {

//    public static void main(String[] args) {
//
//        try {

//            Level level = LevelLoader.loadLevel(2);
//            GamePlay game = new GamePlay(level);
//            game.play();
//        } catch (IOException e) {
//            System.err.println("خطأ: لم يتمكن من تحميل المستوى. " + e.getMessage());
//            e.printStackTrace();
//        }
//        System.out.println("Attempting to load Level 1...");
//
//        try {
//            // هنا نقوم باستدعاء الميثود السحرية
//            Level level1 = LevelLoader.loadLevel(1);
//
//            // إذا وصلنا إلى هنا، فهذا يعني أن التحميل نجح!
//            System.out.println("SUCCESS: Level 1 loaded successfully!");
//            System.out.println("---------------------------------");
//
//            // الآن، دعنا نطبع البيانات التي تم تحميلها للتحقق منها
//            System.out.println("Level ID: " + level1.getLevelId());
//            System.out.println("Number of Holes: " + level1.getHoles().size());
//            System.out.println("Holes at: " + level1.getHoles());
//
//            System.out.println("Number of Mushrooms: " + level1.getMushrooms().size());
//            System.out.println("Mushrooms at: " + level1.getMushrooms());
//
//            System.out.println("Number of Initial Rabbits: " + level1.getInitialRabbits().size());
//            System.out.println("Initial Rabbits: " + level1.getInitialRabbits());
//
//            System.out.println("Number of Initial Foxes: " + level1.getInitialFoxes().size());
//            System.out.println("Initial Foxes: " + level1.getInitialFoxes());
//
//
//        } catch (IOException e) {
//            // هذا الجزء سينفذ فقط إذا فشل تحميل الملف
//            System.err.println("ERROR: Could not load level 1.");
//            System.err.println("Reason: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int currentLevel = 1;
        int maxLevel = 10;

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║     Welcome to JUMP IN! 🐰         ║");
        System.out.println("║     10 Levels Challenge            ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println();

        while (currentLevel <= maxLevel) {
            try {
                // تحميل المستوى
                Level level = LevelLoader.loadLevel(currentLevel);
                GamePlay game = new GamePlay(level);

                // اللعب
                boolean won = game.play();

                if (won) {
                    // فاز!
                    if (currentLevel == maxLevel) {
                        // آخر مستوى!
                        printFinalVictory();
                        break;
                    } else {
                        // انتقل للمستوى التالي
                        System.out.println("\n🎉 Level " + currentLevel + " Complete!");
                        System.out.print("Press Enter to continue to Level " + (currentLevel + 1) + "...");
                        scanner.nextLine();
                        currentLevel++;
                    }
                } else {
                    // خرج من اللعبة
                    System.out.println("\n👋 You quit at Level " + currentLevel);
                    System.out.print("Do you want to continue? (y/n): ");
                    String choice = scanner.nextLine().trim().toLowerCase();

                    if (!choice.equals("y")) {
                        System.out.println("Thanks for playing! 🐰");
                        break;
                    }
                }

            } catch (IOException e) {
                System.err.println("❌ Error loading level " + currentLevel + ": " + e.getMessage());
                break;
            }
        }

        scanner.close();
    }

    private static void printFinalVictory() {
        System.out.println("\n\n");
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║                                    ║");
        System.out.println("║        🏆 CONGRATULATIONS! 🏆      ║");
        System.out.println("║                                    ║");
        System.out.println("║   You completed ALL 10 LEVELS!     ║");
        System.out.println("║                                    ║");
        System.out.println("║         YOU ARE A MASTER! 🐰       ║");
        System.out.println("║                                    ║");
        System.out.println("╚════════════════════════════════════╝");
    }

}
