# Path Connection Game

A Java implementation of a two-player connection game where players take turns placing pieces on a grid, with the goal of creating a continuous path from top to bottom or left to right. Features include an AI opponent using the Minimax algorithm with alpha-beta pruning.

## Game Rules

- The game is played on a square grid (typically 5×5)
- Two players use black and white pieces
- White moves first, and players alternate turns
- On each turn, a player places one piece of their color on any empty square
- A player wins by creating a 4-connected path (moving only up/down/left/right):
  - From the top row to the bottom row
  - OR from the leftmost column to the rightmost column
- The game ends in a draw if the board is filled with no winner

## Project Structure

- `game` package: Core game interfaces and implementations
  - `Move.java` & `MoveImpl.java`: Represents a move on the board
  - `Grid.java` & `GridImpl.java`: Represents the game board
  - `Game.java` & `GameImpl.java`: Manages game logic and state
  - `PathFinder.java`: Utility for detecting winning paths
  - `PieceColour.java`: Enum for piece colors (WHITE, BLACK, NONE)
  
- `ai` package: AI implementation using Minimax algorithm
  - `AI.java` & `Minimax.java`: AI interface and implementation
  - `Heuristic.java` & `MinPiecesHeuristic.java`: Evaluation function
  - `PlayVsAI.java`: Main program for playing against the AI

## How to Run

Compile the project:
```bash
javac -d bin src/game/*.java src/game/tests/*.java src/ai/*.java
```

## Run the tests:
```bash
java -cp bin game.tests.MoveTest
java -cp bin game.tests.GridTest
java -cp bin game.tests.GameTest
```

## Play against the AI:
```bash
java -cp bin ai.PlayVsAI
```

## Playing Against the AI

- The AI plays as WHITE and moves first
- The current board state will be displayed after each move
- When it's your turn (BLACK), you'll see a list of available moves
- Enter the number of the move you want to make
- The game continues until someone wins or the board is full (draw)

## Project Features

- Object-oriented design with interfaces and implementations
- Deep copy functionality for game state
- AI using Minimax algorithm with alpha-beta pruning
- Path finding using breadth-first search
- Comprehensive test suite

