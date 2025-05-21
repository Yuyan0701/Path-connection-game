package ai;
import game.*;
import java.util.*;

// Computes the minimum number of pieces needed to win
// assuming the other player never places anything.
// This is a safe lower bound on closeness to winning.
public class MinPiecesHeuristic implements Heuristic {

    private class Position {
        int row, col, distance;

        Position(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }

    // Uses Dijkstra's algorithm to find the shortest path
    // Locations with the same colour as ours have weight 0
    // Empty locations have weight 1
    // Locations with the other colour are impassable
    private int dijkstra(Grid grid, PieceColour piece, int startr, int startc, int endr, int endc) {
        PriorityQueue<Position> queue = new PriorityQueue<>(new Comparator<Position>() {
            @Override
            public int compare(Position a, Position b) {
                return a.distance - b.distance;
            }
        });
        boolean[][] visited = new boolean[grid.getSize()][grid.getSize()];
        for (int i = 0; i < grid.getSize(); ++i) {
            if (startr != -1)
                queue.offer(new Position(startr, i, grid.getPiece(startr, i) == piece ? 0 : 1));
            if (startc != -1)
                queue.offer(new Position(i, startc, grid.getPiece(i, startc) == piece ? 0 : 1));
        }
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            if (visited[pos.row][pos.col])
                continue;
            visited[pos.row][pos.col] = true;
            if (pos.row == endr || pos.col == endc)
                return pos.distance;
            for (int d = 0; d < dirs.length; d++) {
                int row = pos.row + dirs[d][0];
                int col = pos.col + dirs[d][1];
                if (row < 0 || row >= grid.getSize() || col < 0 || col >= grid.getSize())
                    continue;
                if (visited[row][col])
                    continue;
                if (grid.getPiece(pos.row, pos.col) == piece) {
                    queue.offer(new Position(row, col, pos.distance));
                } else if (grid.getPiece(pos.row, pos.col) == PieceColour.NONE) {
                    queue.offer(new Position(row, col, pos.distance + 1));
                }
            }
        }
        return grid.getSize() * grid.getSize();
    }

    @Override
    public int score(Game game) {
        var grid = game.getGrid();
        var player = game.currentPlayer();
        return -Math.min(dijkstra(grid, player, 0, -1, grid.getSize() - 1, -1),
                dijkstra(grid, player, -1, 0, -1, grid.getSize() - 1));
    }

}
