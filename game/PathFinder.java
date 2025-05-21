package game;

import java.util.*;

public class PathFinder {
    private static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object obj) {
            Position other = (Position) obj;
            return row == other.row && col == other.col;
        }
    }

    private static Collection<Position> getOnRow(Grid grid, int row, PieceColour piece) {
        Collection<Position> positions = new ArrayList<>();
        for (int col = 0; col < grid.getSize(); col++)
            if (grid.getPiece(row, col) == piece)
                positions.add(new Position(row, col));
        return positions;
    }

    private static Collection<Position> getOnCol(Grid grid, int col, PieceColour piece) {
        Collection<Position> positions = new ArrayList<>();
        for (int row = 0; row < grid.getSize(); row++)
            if (grid.getPiece(row, col) == piece)
                positions.add(new Position(row, col));
        return positions;
    }

    private static boolean findPath(Grid grid, Collection<Position> starts, Collection<Position> ends) {
        Queue<Position> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[grid.getSize()][grid.getSize()];
        for (Position start : starts) {
            queue.offer(start);
            visited[start.row][start.col] = true;
        }
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            if (ends.contains(pos))
                return true;
            for (int d = 0; d < dirs.length; d++) {
                int row = pos.row + dirs[d][0];
                int col = pos.col + dirs[d][1];
                if (row < 0 || row >= grid.getSize() || col < 0 || col >= grid.getSize())
                    continue;
                if (visited[row][col])
                    continue;
                PieceColour piece = grid.getPiece(row, col);
                if (piece != grid.getPiece(pos.row, pos.col))
                    continue;
                queue.offer(new Position(row, col));
                visited[row][col] = true;
            }
        }
        return false;
    }

    // Returns true if there is a path from the top row to the bottom row
    // consisting of pieces of the given player's colour.
    public static boolean topToBottom(Grid grid, PieceColour player) {
        Collection<Position> starts = getOnRow(grid, 0, player);
        Collection<Position> ends = getOnRow(grid, grid.getSize() - 1, player);
        return findPath(grid, starts, ends);
    }


    // Returns true if there is a path from the left column to the right column
    // consisting of pieces of the given player's colour.
    public static boolean leftToRight(Grid grid, PieceColour player) {
        Collection<Position> starts = getOnCol(grid, 0, player);
        Collection<Position> ends = getOnCol(grid, grid.getSize() - 1, player);
        return findPath(grid, starts, ends);
    }
}