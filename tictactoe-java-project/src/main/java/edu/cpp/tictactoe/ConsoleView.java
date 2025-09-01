package edu.cpp.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() { this(new Scanner(System.in)); }
    public ConsoleView(Scanner scanner) { this.scanner = scanner; }

    public void showBoard(Board b) {
        int n = b.getSize();
        System.out.println();
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                Mark m = b.getCell(r, c);
                char ch = m == Mark.X ? 'X' : (m == Mark.O ? 'O' : '.');
                sb.append(ch);
                if (c < n - 1) sb.append(' ');
            }
            System.out.println(sb);
        }
        System.out.println();
    }

    public void message(String s) {
        System.out.println(s);
    }

    public Move readHumanMove(Board b, Mark mark) {
        while (true) {
            try {
                System.out.print("Enter row and col for " + mark + " (0-based, e.g., '1 2'): ");
                int r = scanner.nextInt();
                int c = scanner.nextInt();
                return new Move(r, c, mark);
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clear
                message("Please enter two integers.");
            } catch (IllegalArgumentException e) {
                message("Invalid input: " + e.getMessage());
            }
        }
    }
}
