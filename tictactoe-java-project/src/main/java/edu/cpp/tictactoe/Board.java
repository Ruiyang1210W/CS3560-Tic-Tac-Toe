package edu.cpp.tictactoe;

import java.util.*;

 class Board {
    private final int size;
    private final int winLength;
    private final Mark[][] grid;
    private Mark nextMark = Mark.X; // X starts
    private final Deque<Move> history = new ArrayDeque<>();

    public Board() {
        this(3, 3);
    }

    public Board(int size) {
        this(size, Math.min(size, 3));
    }

    public Board(int size, int winLength) {
        if (size < 3) throw new IllegalArgumentException("size must be >= 3");
        if (winLength < 3 || winLength > size) throw new IllegalArgumentException("winLength must be in [3, size]");
        this.size = size;
        this.winLength = winLength;
        this.grid = new Mark[size][size];
        reset();
    }

    public int getSize() { return size; }
    public int getWinLength() { return winLength; }
    public Mark getNextMark() { return nextMark; }

    public void reset() {
        for (int r = 0; r < size; r++) {
            Arrays.fill(grid[r], Mark.EMPTY);
        }
        nextMark = Mark.X;
        history.clear();
    }

    public Mark getCell(int r, int c) {
        checkBounds(r,c);
        return grid[r][c];
    }

    public boolean isFull() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (grid[r][c] == Mark.EMPTY) return false;
            }
        }
        return true;
    }

    private void checkBounds(int r, int c) {
        if (r < 0 || r >= size || c < 0 || c >= size)
            throw new IllegalArgumentException("out of bounds");
    }

    public void place(Move mv) {
        Objects.requireNonNull(mv);
        checkBounds(mv.row(), mv.col());
        if (mv.mark() == Mark.EMPTY) throw new IllegalArgumentException("mark cannot be EMPTY");
        if (grid[mv.row()][mv.col()] != Mark.EMPTY) throw new IllegalArgumentException("cell not empty");
        if (mv.mark() != nextMark) throw new IllegalArgumentException("not " + mv.mark() + "'s turn");
        grid[mv.row()][mv.col()] = mv.mark();
        history.push(mv);
        nextMark = nextMark.other();
    }

    public boolean canUndo() { return !history.isEmpty(); }

    public void undo() {
        if (!canUndo()) throw new IllegalStateException("no moves to undo");
        Move last = history.pop();
        grid[last.row()][last.col()] = Mark.EMPTY;
        nextMark = last.mark(); // it's now the same player's turn again
    }

    public Optional<Mark> winner() {
        // scan entire grid and check streaks
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Mark m = grid[r][c];
                if (m == Mark.EMPTY) continue;
                if (hasStreakFrom(r, c, 1, 0, m) ||
                    hasStreakFrom(r, c, 0, 1, m) ||
                    hasStreakFrom(r, c, 1, 1, m) ||
                    hasStreakFrom(r, c, 1, -1, m)) {
                    return Optional.of(m);
                }
            }
        }
        return Optional.empty();
    }

    private boolean hasStreakFrom(int r, int c, int dr, int dc, Mark m) {
        for (int k = 1; k < winLength; k++) {
            int rr = r + dr * k;
            int cc = c + dc * k;
            if (rr < 0 || cc < 0 || rr >= size || cc >= size) return false;
            if (grid[rr][cc] != m) return false;
        }
        return true;
    }

    public List<Move> legalMoves(Mark mark) {
        List<Move> out = new ArrayList<>();
        if (mark == null || mark == Mark.EMPTY) return out;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (grid[r][c] == Mark.EMPTY) {
                    out.add(new Move(r, c, mark));
                }
            }
        }
        return out;
    }
}
