/**
 * File: GameTest.java
 * Author: 24358018 —— Yuyan Yang
 * Description: Test class for GameImpl that verifies all aspects of the game
 * implementation including construction, moves, win conditions, and edge cases.
 */
package game.tests;

import java.util.Collection;
import game.*;

// Test class for GameImpl.
// Tests all aspects of the Game interface implementation.
public class GameTest {
    private static int passedTests = 0;
    private static int totalTests = 0;
    
    // Helper method to test a condition and print result
    private static void test(boolean condition, String message) {
        totalTests++;
        
        if (condition) {
            System.out.println("[PASS] " + message);
            passedTests++;
        } else {
            System.out.println("[FAIL] " + message);
        }
    }
    
    public static void main(String[] args) {
        // ============= CONSTRUCTOR TESTS =============
        System.out.println("\n=== Testing Constructor ===");
        
        // Test zero size constructor
        boolean exceptionThrown = false;
        try {
            new GameImpl(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, "Constructor rejected zero size");
        
        // Test negative size constructor
        exceptionThrown = false;
        try {
            new GameImpl(-5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, "Constructor rejected negative size");
        
        // Test valid constructor
        exceptionThrown = false;
        try {
            new GameImpl(5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(!exceptionThrown, "Constructor accepted valid size");
        
        // ============= INITIAL STATE TESTS =============
        System.out.println("\n=== Testing Initial Game State ===");
        
        // Small game tests (1x1)
        Game smallGame = new GameImpl(1);
        test(!smallGame.isOver(), "1x1 game is not over at start");
        test(smallGame.winner() == PieceColour.NONE, "1x1 game has no winner at start");
        
        // Medium game tests (5x5)
        Game game = new GameImpl(5);
        test(!game.isOver(), "5x5 game is not over at start");
        test(game.winner() == PieceColour.NONE, "5x5 game has no winner at start");
        test(game.currentPlayer() == PieceColour.WHITE, "WHITE is first player");
        
        // ============= MOVE TESTS =============
        System.out.println("\n=== Testing Game Moves ===");
        
        // Test initial moves count
        Collection<Move> moves = game.getMoves();
        test(moves.size() == 25, "5x5 game has 25 moves at start (got " + moves.size() + ")");
        
        // Make a move and test state changes
        Move firstMove = new MoveImpl(2, 2);
        game.makeMove(firstMove);
        
        test(game.currentPlayer() == PieceColour.BLACK, "Player switched to BLACK after WHITE's move");
        
        Grid grid = game.getGrid();
        test(grid.getPiece(2, 2) == PieceColour.WHITE, "Piece at (2,2) is WHITE after move");
        
        // Test remaining moves count
        moves = game.getMoves();
        test(moves.size() == 24, "5x5 game has 24 moves after first move (got " + moves.size() + ")");
        
        // ============= INVALID MOVE TESTS =============
        System.out.println("\n=== Testing Invalid Moves ===");
        
        // Test move to occupied position
        exceptionThrown = false;
        try {
            game.makeMove(firstMove); // Same position as before
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, "Exception thrown for move to occupied position");
        
        // Test out-of-bounds move
        exceptionThrown = false;
        try {
            game.makeMove(new MoveImpl(5, 5));
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, "Exception thrown for out-of-bounds move");
        
        // ============= NEGATIVE COORDINATES TEST =============
        System.out.println("\n=== Testing Negative Coordinates ===");
        
        // Test negative row
        exceptionThrown = false;
        try {
            game.makeMove(new MoveImpl(-1, 0));
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, "Exception thrown for negative row");
        
        // Test negative column
        exceptionThrown = false;
        try {
            game.makeMove(new MoveImpl(0, -1));
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, "Exception thrown for negative column");
        
        // ============= COPY TESTS =============
        System.out.println("\n=== Testing Game Copy ===");
        
        // Test copying game state
        Game gameCopy = game.copy();
        test(gameCopy.currentPlayer() == PieceColour.BLACK, "Copy has same current player (BLACK)");
        
        Grid copyGrid = gameCopy.getGrid();
        test(copyGrid.getPiece(2, 2) == PieceColour.WHITE, "Copy grid has WHITE at (2,2)");
        
        // Test independence of copies
        gameCopy.makeMove(new MoveImpl(1, 1));
        grid = game.getGrid();
        copyGrid = gameCopy.getGrid();
        
        test(grid.getPiece(1, 1) == PieceColour.NONE, "Original unchanged when copy is modified");
        test(copyGrid.getPiece(1, 1) == PieceColour.BLACK, "Copy has BLACK at (1,1) after move");
        
        // ============= WIN CONDITION TESTS =============
        System.out.println("\n=== Testing Win Conditions ===");
        
        // Test WHITE win (top to bottom)
        game = new GameImpl(3);
        game.makeMove(new MoveImpl(0, 0)); // WHITE
        game.makeMove(new MoveImpl(0, 1)); // BLACK
        game.makeMove(new MoveImpl(1, 0)); // WHITE
        game.makeMove(new MoveImpl(1, 1)); // BLACK
        game.makeMove(new MoveImpl(2, 0)); // WHITE connects top to bottom
        
        test(game.isOver(), "Game ends when WHITE connects top to bottom");
        test(game.winner() == PieceColour.WHITE, "WHITE is winner with top-bottom path");
        test(game.getMoves().size() == 0, "No moves available after game ends");
        
        // Test moves after game is over
        PieceColour oldWinner = game.winner();
        game.makeMove(new MoveImpl(0, 2));
        test(game.winner() == oldWinner, "Winner unchanged after move in finished game");
        
        // Test WHITE win (left to right)
        game = new GameImpl(3);
        game.makeMove(new MoveImpl(1, 0)); // WHITE
        game.makeMove(new MoveImpl(0, 0)); // BLACK
        game.makeMove(new MoveImpl(1, 1)); // WHITE
        game.makeMove(new MoveImpl(0, 1)); // BLACK
        game.makeMove(new MoveImpl(1, 2)); // WHITE connects left to right
        
        test(game.isOver(), "Game ends when WHITE connects left to right");
        test(game.winner() == PieceColour.WHITE, "WHITE is winner with left-right path");
        
        // Test BLACK win (top to bottom)
        game = new GameImpl(3);
        game.makeMove(new MoveImpl(0, 1)); // WHITE
        game.makeMove(new MoveImpl(0, 0)); // BLACK
        game.makeMove(new MoveImpl(1, 1)); // WHITE
        game.makeMove(new MoveImpl(1, 0)); // BLACK
        game.makeMove(new MoveImpl(2, 2)); // WHITE
        game.makeMove(new MoveImpl(2, 0)); // BLACK connects top to bottom
        
        test(game.isOver(), "Game ends when BLACK connects top to bottom");
        test(game.winner() == PieceColour.BLACK, "BLACK is winner with top-bottom path");
        
        // Test BLACK win (left to right)
        game = new GameImpl(3);
        game.makeMove(new MoveImpl(0, 0)); // WHITE
        game.makeMove(new MoveImpl(1, 0)); // BLACK
        game.makeMove(new MoveImpl(0, 1)); // WHITE
        game.makeMove(new MoveImpl(1, 1)); // BLACK
        game.makeMove(new MoveImpl(2, 2)); // WHITE
        game.makeMove(new MoveImpl(1, 2)); // BLACK connects left to right
        
        test(game.isOver(), "Game ends when BLACK connects left to right");
        test(game.winner() == PieceColour.BLACK, "BLACK is winner with left-right path");
        
        // ============= 2x2 BOARD TESTS =============
        System.out.println("\n=== Testing 2x2 Board Configurations ===");
        
        // Test 2x2 draw configuration
        Game game2x2a = new GameImpl(2);
        game2x2a.makeMove(new MoveImpl(0, 0)); // WHITE
        game2x2a.makeMove(new MoveImpl(0, 1)); // BLACK
        game2x2a.makeMove(new MoveImpl(1, 1)); // WHITE
        game2x2a.makeMove(new MoveImpl(1, 0)); // BLACK - creates pattern W B / B W
        
        test(game2x2a.isOver(), "2x2 game ends when board is full");
        test(game2x2a.winner() == PieceColour.NONE, "2x2 with diagonal pattern should be a draw");
        
        // Test 2x2 WHITE win (top row)
        Game game2x2b = new GameImpl(2);
        game2x2b.makeMove(new MoveImpl(0, 0)); // WHITE
        game2x2b.makeMove(new MoveImpl(1, 0)); // BLACK
        game2x2b.makeMove(new MoveImpl(0, 1)); // WHITE - creates pattern W W / B ?
        
        test(game2x2b.isOver(), "2x2 game over after WHITE connects top row");
        test(game2x2b.winner() == PieceColour.WHITE, "WHITE should win with top row connected");
        
        // Test 2x2 game with one move left
        Game game2x2c = new GameImpl(2);
        game2x2c.makeMove(new MoveImpl(0, 0)); // WHITE
        game2x2c.makeMove(new MoveImpl(1, 0)); // BLACK
        game2x2c.makeMove(new MoveImpl(1, 1)); // WHITE
        
        test(!game2x2c.isOver(), "2x2 game with one empty space should not be over");
        test(game2x2c.getMoves().size() == 1, "2x2 game with 3 moves should have 1 move left");
        
        // Complete the game
        game2x2c.makeMove(new MoveImpl(0, 1)); // BLACK
        test(game2x2c.isOver(), "2x2 game should be over after filling board");
        test(game2x2c.winner() == PieceColour.NONE, "Game should be a draw when no player has a path");
        
        // ============= RESULTS =============
        System.out.println("\n=== TEST RESULTS ===");
        System.out.println("Passed: " + passedTests + "/" + totalTests + " tests");
        
        if (passedTests == totalTests) {
            System.out.println("ALL TESTS PASSED!");
        } else {
            System.out.println("SOME TESTS FAILED!");
        }
    }
}