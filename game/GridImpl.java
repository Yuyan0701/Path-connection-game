/**
 * File: GridImpl.java
 * Author: Yuyan Yang
 * Description: Implementation of Grid interface representing a square game board,
 * storing the positions of black and white pieces.
 */

package game;

public class GridImpl implements Grid {
    private int size;
    private PieceColour[][] gridData;

    // Creates a new grid with specified dimensions
    public GridImpl(int size) {
        // Validate size parameter
        if (size <= 0) {
            throw new IllegalArgumentException("Grid size must be positive");
        }
        
        // Initialize grid
        this.size = size;
        this.gridData = new PieceColour[size][size];
        
        // Set all positions to empty initially
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                gridData[row][col] = PieceColour.NONE;
            }
        }
    }

    // Returns the size of this grid (number of rows/columns)
    @Override
    public int getSize() {
        return this.size;
    }

    // Gets the piece at the specified position
    @Override
    public PieceColour getPiece(int row, int col) {
        // Make sure position is valid
        validatePosition(row, col);
        
        // Return the piece at that position
        return gridData[row][col];
    }

    // Places a piece at the specified position
    @Override
    public void setPiece(int row, int col, PieceColour piece) {
        // Validate position and piece
        validatePosition(row, col);
        
        if (piece == null) {
            throw new IllegalArgumentException("Cannot place null piece");
        }
        
        // Set the piece
        gridData[row][col] = piece;
    }

    // Helper method to validate position coordinates
    private void validatePosition(int row, int col) {
        // Check row bounds
        if (row < 0 || row >= size) {
            throw new IllegalArgumentException("Row index " + row + " is out of bounds");
        }
        
        // Check column bounds
        if (col < 0 || col >= size) {
            throw new IllegalArgumentException("Column index " + col + " is out of bounds");
        }
    }

    // Creates a deep copy of this grid
    @Override
    public Grid copy() {
        // Create new grid with same size
        GridImpl copiedGrid = new GridImpl(this.size);
        
        // Copy all pieces
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                copiedGrid.gridData[row][col] = this.gridData[row][col];
            }
        }
        
        return copiedGrid;
    }

    // Generates a string representation of the grid
    @Override
    public String toString() {
        // Use StringBuilder for efficiency
        StringBuilder representation = new StringBuilder();
        
        // Build string row by row
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Use switch to determine character for this piece
                switch (gridData[row][col]) {
                    case WHITE:
                        representation.append('W');
                        break;
                    case BLACK:
                        representation.append('B');
                        break;
                    case NONE:
                        representation.append('.');
                        break;
                }
            }
            
            // Add newline after each row
            representation.append('\n');
        }
        
        return representation.toString();
    }
}