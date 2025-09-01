package edu.cpp.tictactoe;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomAIPlayer extends Player {
    public RandomAIPlayer(Mark mark) { super(mark); }

    @Override
    public Move nextMove(Board board) {
        List<Move> moves = board.legalMoves(mark);
        return moves.get(ThreadLocalRandom.current().nextInt(moves.size()));
    }
}
