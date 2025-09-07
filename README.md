# CS3560-Tic-Tac-Toe

## Target Design
```
+----------------------------------------------------+
|                      Board                         |
+----------------------------------------------------+
| - grid : Mark[3][3]                                |
+----------------------------------------------------+
| + Board()                                          |
| + reset()                     : void               |
| + getCell(r:int, c:int)       : Mark               |
| + place(m: Move)              : void               |
| + isFull()                    : boolean            |
| + winner()                    : Optional<Mark>     |
+----------------------------------------------------+


+---------------------------+          +---------------------------+
|           Move            |          |           Mark            |
+---------------------------+          +---------------------------+
| - row  : int              |          | <<enum>>                  |
| - col  : int              |          | X, O, EMPTY               |
| - mark : Mark             |          +---------------------------+
+---------------------------+
| + Move(row:int, col:int, mark:Mark) |
| + row()  : int            |
| + col()  : int            |
| + mark() : Mark           |
+---------------------------+


+----------------------------------------------------+
|                     Player                         |
|                   <<abstract>>                     |
+----------------------------------------------------+
| # mark : Mark                                      |
+----------------------------------------------------+
| + Player(mark: Mark)                               |
| + getMark()             : Mark                     |
| + nextMove(b: Board)    : Move                     |
+----------------------------------------------------+
                 ^                         ^
                 |                         |
     +-----------------------+   +-----------------------+
     |      HumanPlayer      |   |    RandomAIPlayer     |
     +-----------------------+   +-----------------------+
     | - in : java.util.Scanner | | - rng : java.util.Random |
     +-----------------------+   +---------------------------+
     | + HumanPlayer(mark:Mark,  | | + RandomAIPlayer(mark:Mark) |
     |   in:Scanner)             | |                           |
     | + nextMove(b: Board):Move | | + nextMove(b: Board):Move |
     +---------------------------+ +---------------------------+


+----------------------------------------------------+
|                        Game                         |
+----------------------------------------------------+
| - board   : Board                                   |
| - current : Player                                   |
| - other   : Player                                   |
+----------------------------------------------------+
| + Game(board:Board, x:Player, o:Player)             |
| + run() : void                                      |
+----------------------------------------------------+

```
Relationships:
- Game --> Board
- Game --> Player (uses two: current/other)
- Player <|-- HumanPlayer
- Player <|-- RandomAIPlayer
- Board --> Move (consumes Move in place())
- Move --> Mark

  # CS3560 – Tic-Tac-Toe (Encapsulation & Inheritance)

This repository implements the assignment spec with clean encapsulation, polymorphic players, and JUnit 5 tests. It also includes **extensions** and **stretch extra credit**:

- **Extensions (choose ≥2)** implemented:
  1. `SmartAIPlayer` (center > corners > sides; win/block heuristics)
  2. **NxN board** with configurable **win length K**
  3. **Undo stack** using `Deque<Move>` (public `undo()` / `canUndo()`)

- **Stretch (extra credit)** implemented:
  - `MinimaxAIPlayer` with depth limit and simple evaluation (alpha–beta).

## How to run (IntelliJ)

1. Open the project as a Maven project.
2. Go to open and then open the pom.xml file.
3. Run tests: `BoardTest` and `GameTest` (8+ assertions).
4. Run `ConsoleApp` for a minimal UI:
   - Pick board size `N` and `K` (win length)
   - Choose X and O players from: Human, Random AI, Smart AI, Minimax AI.

## Design notes

- **Model (no printing):** `Board`, `Move`, `Mark`.
- **Encapsulation:** `Board` owns the grid and enforces rules via `place(Move)`; indices validated in one place; invariants documented in code.
- **Polymorphism:** `Player` is abstract (`nextMove(Board)`); concrete subtypes: `HumanPlayer`, `RandomAIPlayer`, `SmartAIPlayer`, `MinimaxAIPlayer`.
- **Game orchestration:** `Game.run()` loops: ask current player → `place(move)` → check `winner()` / draw → swap players.
- **Separation of concerns:** `ConsoleView` prints UI & reads human input. Model classes never print.

## Tests (JUnit 5)
- Row/column/diagonal/anti-diagonal wins
- Full-board draw
- Rejects out-of-bounds, wrong-turn
- Undo restores cell and turn
- NxN board with `K=3` works
- Smart AI blocks immediate win
- Minimax chooses a strong opening

## Directory layout
```
src/main/java/edu/cpp/tictactoe
  Board.java  Move.java  Mark.java
  Player.java HumanPlayer.java RandomAIPlayer.java
  SmartAIPlayer.java MinimaxAIPlayer.java
  Game.java ConsoleView.java ConsoleApp.java

src/test/java/edu/cpp/tictactoe
  BoardTest.java GameTest.java
```
