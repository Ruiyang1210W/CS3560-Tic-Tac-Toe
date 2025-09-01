package edu.cpp.tictactoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void smartAIBlocksImmediateWin() {
        Board b = new Board();
        // X at (0,0) and (0,1). O to move should block at (0,2).
        b.place(new Move(0,0, Mark.X));
        b.place(new Move(1,1, Mark.O));
        b.place(new Move(0,1, Mark.X));
        SmartAIPlayer o = new SmartAIPlayer(Mark.O);
        Move mv = o.nextMove(b);
        assertEquals(0, mv.row());
        assertEquals(2, mv.col());
        assertEquals(Mark.O, mv.mark());
    }

    @Test
    void minimaxPlaysCornerOrCenterAtStart() {
        Board b = new Board();
        MinimaxAIPlayer x = new MinimaxAIPlayer(Mark.X, 9);
        Move mv = x.nextMove(b);
        assertNotNull(mv);
        // For empty board acceptable first moves are center or corners
        boolean ok = (mv.row()==1 && mv.col()==1) ||
                (mv.row()==0 && mv.col()==0) ||
                (mv.row()==0 && mv.col()==2) ||
                (mv.row()==2 && mv.col()==0) ||
                (mv.row()==2 && mv.col()==2);
        assertTrue(ok);
    }
}
