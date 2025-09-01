package edu.cpp.tictactoe;

public class HumanPlayer extends Player {
    private final ConsoleView view;
    public HumanPlayer(Mark mark, ConsoleView view) {
        super(mark);
        this.view = view;
    }

    @Override
    public Move nextMove(Board board) {
        return view.readHumanMove(board, mark);
    }
}
