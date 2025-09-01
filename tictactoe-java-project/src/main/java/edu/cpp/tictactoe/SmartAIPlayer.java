package edu.cpp.tictactoe;

import java.util.*;

public class SmartAIPlayer extends Player {
    public SmartAIPlayer(Mark mark) { super(mark); }

    @Override
    public Move nextMove(Board board) {
        // 1. win now
        Move m = findImmediateWin(board, mark);
        if (m != null) return m;
        // 2. block
        m = findImmediateWin(board, mark.other());
        if (m != null) return new Move(m.row(), m.col(), mark);
        // 3. priority list
        int n = board.getSize();
        int mid = n / 2;
        // center
        if (n % 2 == 1 && board.getCell(mid, mid) == Mark.EMPTY) return new Move(mid, mid, mark);
        // corners
        int[][] corners = {{0,0},{0,n-1},{n-1,0},{n-1,n-1}};
        for (int[] rc : corners) {
            if (board.getCell(rc[0], rc[1]) == Mark.EMPTY) return new Move(rc[0], rc[1], mark);
        }
        // sides
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if ((r==0||r==n-1||c==0||c==n-1) && board.getCell(r,c)==Mark.EMPTY) {
                    return new Move(r,c,mark);
                }
            }
        }
        // anything
        return board.legalMoves(mark).get(0);
    }

    private Move findImmediateWin(Board board, Mark m) {
        for (Move mv : board.legalMoves(m)) {
            try {
                board.place(mv);
                boolean win = board.winner().isPresent();
                board.undo();
                if (win) return mv;
            } catch (Exception ignored) {
                // ignore - legalMoves should make them valid
            }
        }
        return null;
    }
}
