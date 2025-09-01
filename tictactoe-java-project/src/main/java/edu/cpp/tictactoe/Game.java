package edu.cpp.tictactoe;

import java.util.Optional;

public class Game {
    private final Board board;
    private final Player x;
    private final Player o;
    private final ConsoleView view;

    public Game(Board board, Player x, Player o, ConsoleView view) {
        this.board = board;
        this.x = x;
        this.o = o;
        this.view = view;
    }

    public Optional<Mark> run() {
        Player current = board.getNextMark() == Mark.X ? x : o;
        while (true) {
            if (view != null) view.showBoard(board);
            if (board.winner().isPresent()) break;
            if (board.isFull()) break;

            Move mv = current.nextMove(board);
            try {
                board.place(mv);
                current = (current == x) ? o : x;
            } catch (IllegalArgumentException ex) {
                if (view != null) view.message("Invalid move: " + ex.getMessage());
            }
        }
        if (view != null) {
            view.showBoard(board);
            var w = board.winner();
            if (w.isPresent()) view.message("Winner: " + w.get());
            else view.message("Draw!");
        }
        return board.winner();
    }
}
