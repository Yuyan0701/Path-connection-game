package game;

public interface Move {
    // Returns the row of the move
    // Rows are numbered from 0 to size - 1
    int getRow();

    // Returns the column of the move
    // Columns are numbered from 0 to size - 1
    int getCol();
}
