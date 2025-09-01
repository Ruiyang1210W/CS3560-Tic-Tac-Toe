# CS3560-Tic-Tac-Toe

## Target Design
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


Relationships:
- Game --> Board
- Game --> Player (uses two: current/other)
- Player <|-- HumanPlayer
- Player <|-- RandomAIPlayer
- Board --> Move (consumes Move in place())
- Move --> Mark
