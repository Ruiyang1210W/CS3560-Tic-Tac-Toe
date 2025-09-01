package edu.cpp.tictactoe;

import java.util.List;

public class MinimaxAIPlayer extends Player {
    private final int maxDepth;

    public MinimaxAIPlayer(Mark mark, int maxDepth) {
        super(mark);
        if (maxDepth < 1) throw new IllegalArgumentException("maxDepth must be >= 1");
        this.maxDepth = maxDepth;
    }

    @Override
    public Move nextMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move mv : board.legalMoves(mark)) {
            board.place(mv);
            int score = minimax(board, maxDepth - 1, false, mark, mark.other(), Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undo();
            if (score > bestScore) {
                bestScore = score;
                bestMove = mv;
            }
        }
        return bestMove != null ? bestMove : board.legalMoves(mark).get(0);
    }

    private int minimax(Board board, int depth, boolean maximizing, Mark me, Mark opp, int alpha, int beta) {
        var win = board.winner();
        if (win.isPresent()) {
            if (win.get() == me) return 1000 + depth; // faster wins better
            else return -1000 - depth;
        }
        if (board.isFull() || depth == 0) {
            return evaluate(board, me, opp);
        }
        if (maximizing) {
            int best = Integer.MIN_VALUE;
            for (Move mv : board.legalMoves(me)) {
                board.place(mv);
                best = Math.max(best, minimax(board, depth - 1, false, me, opp, alpha, beta));
                board.undo();
                alpha = Math.max(alpha, best);
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Move mv : board.legalMoves(opp)) {
                board.place(mv);
                best = Math.min(best, minimax(board, depth - 1, true, me, opp, alpha, beta));
                board.undo();
                beta = Math.min(beta, best);
                if (beta <= alpha) break;
            }
            return best;
        }
    }

    private int evaluate(Board board, Mark me, Mark opp) {
        // heuristic: count potential lines for me - opponent
        return linesScore(board, me) - linesScore(board, opp);
    }

    private int linesScore(Board board, Mark m) {
        int n = board.getSize();
        int k = board.getWinLength();
        int score = 0;
        // rows
        for (int r = 0; r < n; r++) {
            score += segmentScore(board, m, r,0, 0,1, k);
        }
        // cols
        for (int c = 0; c < n; c++) {
            score += segmentScore(board, m, 0,c, 1,0, k);
        }
        // diags (top-left to bottom-right)
        for (int r = 0; r <= n - k; r++) {
            for (int c = 0; c <= n - k; c++) {
                score += segmentScore(board, m, r,c, 1,1, k);
            }
        }
        // anti-diags
        for (int r = 0; r <= n - k; r++) {
            for (int c = k - 1; c < n; c++) {
                score += segmentScore(board, m, r,c, 1,-1, k);
            }
        }
        return score;
    }

    private int segmentScore(Board board, Mark m, int r, int c, int dr, int dc, int k) {
        int n = board.getSize();
        int endR = r + dr * (k - 1);
        int endC = c + dc * (k - 1);
        if (endR < 0 || endC < 0 || endR >= n || endC >= n) return 0;
        int theirs = 0, mine = 0;
        for (int i = 0; i < k; i++) {
            Mark cell = board.getCell(r + dr * i, c + dc * i);
            if (cell == m) mine++;
            else if (cell != Mark.EMPTY) theirs++;
        }
        if (theirs == 0) {
            return switch (mine) {
                case 0 -> 1;
                case 1 -> 5;
                case 2 -> 25;
                default -> 125;
            };
        }
        return 0;
    }
}
