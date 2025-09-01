package edu.cpp.tictactoe;

import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ConsoleView view = new ConsoleView(sc);

        view.message("=== Tic-Tac-Toe ===");
        int n = readIntOrDefault(sc, "Enter board size N (>=3), default 3: ", 3);
        int k = readIntOrDefault(sc, "Enter win length K (>=3 and <=N), default min(N,3): ", Math.min(n,3));

        Board board = new Board(n, k);

        view.message("Choose X player: 1) Human  2) RandomAI  3) SmartAI  4) Minimax");
        int xChoice = clamp(readIntOrDefault(sc, "> ", 1), 1, 4);
        view.message("Choose O player: 1) Human  2) RandomAI  3) SmartAI  4) Minimax");
        int oChoice = clamp(readIntOrDefault(sc, "> ", 3), 1, 4);

        Player x = createPlayer(xChoice, Mark.X, sc);
        Player o = createPlayer(oChoice, Mark.O, sc);

        Game g = new Game(board, x, o, view);
        g.run();
    }

    private static int readIntOrDefault(Scanner sc, String prompt, int def) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) return def;
        try { return Integer.parseInt(line); } catch (Exception e) { return def; }
    }

    private static int clamp(int v, int lo, int hi) { return Math.max(lo, Math.min(hi, v)); }

    private static Player createPlayer(int choice, Mark mark, Scanner sc) {
        return switch (choice) {
            case 1 -> new HumanPlayer(mark, new ConsoleView(sc));
            case 2 -> new RandomAIPlayer(mark);
            case 3 -> new SmartAIPlayer(mark);
            case 4 -> new MinimaxAIPlayer(mark, 9);
            default -> new SmartAIPlayer(mark);
        };
    }
}
