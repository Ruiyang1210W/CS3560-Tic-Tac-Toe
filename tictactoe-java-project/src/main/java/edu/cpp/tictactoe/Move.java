package edu.cpp.tictactoe;

import java.util.Objects;

public final class Move {
    private final int row;
    private final int col;
    private final Mark mark;

    public Move(int row, int col, Mark mark) {
        if (row < 0 || col < 0) throw new IllegalArgumentException("row/col must be >= 0");
        if (mark == null) throw new IllegalArgumentException("mark cannot be null");
        this.row = row;
        this.col = col;
        this.mark = mark;
    }

    public int row() { return row; }
    public int col() { return col; }
    public Mark mark() { return mark; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return row == move.row && col == move.col && mark == move.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, mark);
    }

    @Override
    public String toString() {
        return "Move{" + "row=" + row + ", col=" + col + ", mark=" + mark + '}';
    }
}
