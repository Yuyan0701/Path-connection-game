package game;

// An interface defining the methods your grid class must implement
public interface Grid {
    // Returns the size of the grid
    // The grid is always a square
    // This is the length of one side of the square
    int getSize();

    // Returns the piece at the given row and column
    // Should return PieceColour.NONE if the position is empty
    // Throws IllegalArgumentException if the row or column is out of bounds
    PieceColour getPiece(int row, int col);

    // Sets the piece at the given row and column
    // Throws IllegalArgumentException if the piece is not a valid colour
    void setPiece(int row, int col, PieceColour piece);

    // Returns a copy of the grid
    // Note that this is should be a deep copy
    // Which means that the grid returned should be a new object
    // And there is no way to modify the internal state of this grid
    // by modifying the grid returned
    Grid copy();
}