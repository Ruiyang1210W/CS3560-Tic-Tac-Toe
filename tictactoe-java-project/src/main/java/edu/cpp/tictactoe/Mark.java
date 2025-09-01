package edu.cpp.tictactoe;

public enum Mark {
    X, O, EMPTY;

    public Mark other() {
        return this == X ? O : (this == O ? X : EMPTY);
    }
}
