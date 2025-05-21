package game;

import java.util.Collection;

// An interface defining the methods your game class must implement
// It is assumed that any implementation of this interface will have a constructor
// that takes a single parameter "size" which defines the side length of the game grid
public interface Game {
    // True if the game is over
    // The game is over when there is a winner or there are no more moves (a draw)
    boolean isOver();

    // The colour of the winner.
    // You should use the PathFinder class to determine the winner
    // Should return PieceColour.NONE if the game is not over
    // Should also return PieceColour.NONE if the game is a draw
    PieceColour winner();

    // The colour of the current player (the player who will make the next move)
    // If the game is over, the output of this method is undefined
    // That is, it does not matter what this method returns if the game is over
    PieceColour currentPlayer();

    // Gets a Collection of all valid moves for the current player
    // See https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Collection.html
    // Note that ArrayList implements Collection
    // The collection should be empty if there are no valid moves
    Collection<Move> getMoves();


    // Executes a move for the current player
    // Updates the internal game state to reflect the move
    // Changes the current player to the other colour after the move is made
    // If the game is over, the result of this method is undefined
    // That is, it does not matter what this method does if the game is over
    // Throws an IllegalArgumentException if the move is invalid
    // An invalid move is one where the position is already occupied
    // or the position is out of bounds
    void makeMove(Move move);

    // Returns a copy of the grid
    // Note that this is should be a deep copy
    // Which means that the grid returned should be a new object
    // And there is no way to modify the internal state of the game
    // by modifying the grid returned
    Grid getGrid();

    // Returns a copy of the game
    // Note that this is should be a deep copy
    // Which means that the game returned should be a new object
    // And there is no way to modify the internal state of the this game
    // by modifying the game returned
    Game copy();
}