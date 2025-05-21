/**
 * File: GameImpl.java
 * Author: 24358018 —— Yuyan Yang
 * Description: Implementation of Game interface that handles the game logic,
 * including player turns, move validation, and win condition checking.
 */

package game;

import java.util.ArrayList;
import java.util.Collection;

// A class that implements the Game interface and manages the connection game
public class GameImpl implements Game {
    // Game state variables
    private final Grid boardGrid;
    private PieceColour activePlayer;
    private boolean isGameOver;
    private PieceColour winningPlayer;

    // Creates a new game with the specified board size
    public GameImpl(int size) {
        // Size validation
        if (size < 1) {
            throw new IllegalArgumentException("Board size must be at least 1");
        }
        
        // Initialize game components
        boardGrid = new GridImpl(size);
        activePlayer = PieceColour.WHITE;  // Convention: white goes first
        isGameOver = false;
        winningPlayer = PieceColour.NONE;
    }

    // Private constructor used for creating game copies
    private GameImpl(Grid gridCopy, PieceColour player, boolean gameOver, PieceColour winner) {
        boardGrid = gridCopy;
        activePlayer = player;
        isGameOver = gameOver;
        winningPlayer = winner;
    }

    @Override
    public boolean isOver() {
        // Return early if game is already over
        if (isGameOver) {
            return true;
        }

        // Check for a winner
        PieceColour possibleWinner = checkForWinner();
        if (possibleWinner != PieceColour.NONE) {
            isGameOver = true;
            winningPlayer = possibleWinner;
            return true;
        }

        // Check for a full board (draw scenario)
        if (isBoardFull()) {
            isGameOver = true;
            // No winner in a draw
            winningPlayer = PieceColour.NONE;
            return true;
        }

        // Game continues
        return false;
    }

    // Helper method to check if board is completely filled
    private boolean isBoardFull() {
        int boardSize = boardGrid.getSize();
        
        // Check each position
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // If any empty space found, board is not full
                if (boardGrid.getPiece(i, j) == PieceColour.NONE) {
                    return false;
                }
            }
        }
        
        // All positions filled
        return true;
    }

    // Determines if either player has created a winning path
    private PieceColour checkForWinner() {
        // Check white paths first
        boolean whiteConnectsTopBottom = PathFinder.topToBottom(boardGrid, PieceColour.WHITE);
        boolean whiteConnectsLeftRight = PathFinder.leftToRight(boardGrid, PieceColour.WHITE);
        
        if (whiteConnectsTopBottom || whiteConnectsLeftRight) {
            return PieceColour.WHITE;
        }
        
        // Then check black paths
        boolean blackConnectsTopBottom = PathFinder.topToBottom(boardGrid, PieceColour.BLACK);
        boolean blackConnectsLeftRight = PathFinder.leftToRight(boardGrid, PieceColour.BLACK);
        
        if (blackConnectsTopBottom || blackConnectsLeftRight) {
            return PieceColour.BLACK;
        }
        
        // No winner found
        return PieceColour.NONE;
    }

    @Override
    public PieceColour winner() {
        // Update game state if needed
        if (!isGameOver) {
            isOver();
        }
        
        // Return the winner (will be NONE if draw or game in progress)
        return winningPlayer;
    }

    @Override
    public PieceColour currentPlayer() {
        return activePlayer;
    }

    @Override
    public Collection<Move> getMoves() {
        // Create list for available moves
        ArrayList<Move> availableMoves = new ArrayList<>();
        
        // No moves if game is finished
        if (isGameOver) {
            return availableMoves;
        }
        
        // Find all empty positions
        int size = boardGrid.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardGrid.getPiece(i, j) == PieceColour.NONE) {
                    // Add this position as a valid move
                    availableMoves.add(new MoveImpl(i, j));
                }
            }
        }
        
        return availableMoves;
    }

    @Override
    public void makeMove(Move move) {
        // Ignore moves if game is already over
        if (isGameOver) {
            return;
        }
        
        // Extract move coordinates
        int moveRow = move.getRow();
        int moveCol = move.getCol();
        
        // Validate the move
        validateMove(moveRow, moveCol);
        
        // Place the piece
        boardGrid.setPiece(moveRow, moveCol, activePlayer);
        
        // Alternate players
        swapPlayers();
        
        // Check if this move ended the game
        isOver();
    }
    
    // Validates if a move is legal (in bounds and unoccupied)
    private void validateMove(int row, int col) {
        try {
            // Check if position is empty
            if (boardGrid.getPiece(row, col) != PieceColour.NONE) {
                throw new IllegalArgumentException("This position is already occupied");
            }
        } catch (IllegalArgumentException e) {
            // This will catch both out-of-bounds errors and occupied position errors
            throw new IllegalArgumentException("Invalid move: " + e.getMessage());
        }
    }
    
    // Changes the active player
    private void swapPlayers() {
        activePlayer = (activePlayer == PieceColour.WHITE) ? 
                       PieceColour.BLACK : PieceColour.WHITE;
    }

    @Override
    public Grid getGrid() {
        // Return a copy to prevent external modification
        return boardGrid.copy();
    }

    @Override
    public Game copy() {
        // Create a deep copy of the game
        return new GameImpl(boardGrid.copy(), activePlayer, isGameOver, winningPlayer);
    }
}