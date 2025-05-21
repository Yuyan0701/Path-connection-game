/**
 * File: MoveImpl.java
 * Author: 24358018 —— Yuyan Yang
 * Description: Implementation of Move interface representing a move at a specific position
 * on the game board with row and column coordinates.
 */
package game;

// Implementation of Move interface that stores a position on the board
public class MoveImpl implements Move {
    // Row and column position
    private final int rowPosition;
    private final int columnPosition;
    
    // Creates a new move at the given coordinates
    public MoveImpl(int row, int col) {
        rowPosition = row;
        columnPosition = col;
    }
    
    // Returns the row coordinate
    @Override
    public int getRow() {
        return rowPosition;
    }
    
    // Returns the column coordinate
    @Override
    public int getCol() {
        return columnPosition;
    }
    
    // Returns string "(row,col)" representing this move
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(rowPosition);
        result.append(",");
        result.append(columnPosition);
        result.append(")");
        return result.toString();
    }
}