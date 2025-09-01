package edu.cpp.tictactoe;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void rowWinDetected() {
        Board b = new Board(3,3);
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(1,0, Mark.O));
        b.place(new Move(0,1, Mark.X));
        b.place(new Move(1,1, Mark.O));
        b.place(new Move(0,2, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test
    void colWinDetected() {
        Board b = new Board(3,3);
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(0,1, Mark.O));
        b.place(new Move(1,0, Mark.X));
        b.place(new Move(1,1, Mark.O));
        b.place(new Move(2,0, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test
    void diagWinDetected() {
        Board b = new Board(3,3);
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(0,1, Mark.O));
        b.place(new Move(1,1, Mark.X));
        b.place(new Move(2,1, Mark.O));
        b.place(new Move(2,2, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test
    void antiDiagWinDetected() {
        Board b = new Board(3,3);
        b.place(new Move(0,2, Mark.X));
        b.place(new Move(0,0, Mark.O));
        b.place(new Move(1,1, Mark.X));
        b.place(new Move(2,1, Mark.O));
        b.place(new Move(2,0, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }

    @Test
    void rejectsOutOfBounds() {
        Board b = new Board();
        assertThrows(IllegalArgumentException.class, () -> b.place(new Move(5,5, Mark.X)));
    }

    @Test
    void rejectsWrongTurn() {
        Board b = new Board();
        b.place(new Move(0,0, Mark.X));
        assertThrows(IllegalArgumentException.class, () -> b.place(new Move(1,1, Mark.X)));
    }

    @Test
    void drawDetectedOnFullBoard() {
        Board b = new Board();
        // X O X
        // X X O
        // O X O
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(0,1, Mark.O));
        b.place(new Move(0,2, Mark.X));
        b.place(new Move(1,2, Mark.O));
        b.place(new Move(1,0, Mark.X));
        b.place(new Move(2,0, Mark.O));
        b.place(new Move(1,1, Mark.X));
        b.place(new Move(2,2, Mark.O));
        b.place(new Move(2,1, Mark.X));
        assertTrue(b.isFull());
        assertEquals(Optional.empty(), b.winner());
    }

    @Test
    void undoWorksAndRestoresTurn() {
        Board b = new Board();
        assertEquals(Mark.X, b.getNextMark());
        b.place(new Move(0,0, Mark.X));
        assertEquals(Mark.O, b.getNextMark());
        b.undo();
        assertEquals(Mark.X, b.getNextMark());
        assertEquals(Mark.EMPTY, b.getCell(0,0));
    }

    @Test
    void nxnBoardWithK3Works() {
        Board b = new Board(4,3);
        // X X X in row 2 (index 1)
        b.place(new Move(1,0, Mark.X));
        b.place(new Move(0,0, Mark.O));
        b.place(new Move(1,1, Mark.X));
        b.place(new Move(0,1, Mark.O));
        b.place(new Move(1,2, Mark.X));
        assertEquals(Optional.of(Mark.X), b.winner());
    }
}
