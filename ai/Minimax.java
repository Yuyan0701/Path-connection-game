package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import game.*;

// An implementation of the Minimax algorithm
public class Minimax implements AI {
    private int maxDepth;
    private Heuristic heuristic;


    // maxDepth is the number of moves ahead to look
    // heuristic is used to estimate the quality of a non-terminal game state
    public Minimax(int maxDepth, Heuristic heuristic) {
        if (maxDepth < 1) throw new IllegalArgumentException("maxDepth must be at least 1");
        this.maxDepth = maxDepth;
        this.heuristic = heuristic;
    }

    // Returns the list of moves in a random order
    private ArrayList<Move> getMoves(Game game) {
        var moves = new ArrayList<>(game.getMoves());
        Collections.shuffle(moves, new Random());
        return moves;
    }

    // Returns the score of the best move for the current player
    // Uses minimax with alpha-beta pruning
    private long minimax(Game game, int depth, long alpha, long beta, PieceColour player) {
        if (game.isOver() || depth == 0) {
            if (game.winner() == player) return Integer.MAX_VALUE;
            else if (game.winner() != PieceColour.NONE) return Integer.MIN_VALUE;
            return heuristic.score(game);
        }

        var moves = getMoves(game);

        if (game.currentPlayer() == player) {
            long bestScore = Integer.MIN_VALUE;
            for (Move move : moves) {
                Game newGame = game.copy();
                newGame.makeMove(move);
                long score = minimax(newGame, depth - 1, alpha, beta, player);
                bestScore = Math.max(bestScore, score);
                if (bestScore >= beta) return bestScore;
                alpha = Math.max(alpha, score);
            }
            return bestScore;
        } else {
            long bestScore = Integer.MAX_VALUE;
            for (Move move : moves) {
                Game newGame = game.copy();
                newGame.makeMove(move);
                long score = minimax(newGame, depth - 1, alpha, beta, player);
                bestScore = Math.min(bestScore, score);
                if (bestScore <= alpha) return bestScore;
                beta = Math.min(beta, score);
            }
            return bestScore;
        }
    }

    @Override
    public Move getCurrentPlayerMove(Game game) {
        var moves = getMoves(game);
        Move bestMove = moves.get(0);
        long bestScore = Integer.MIN_VALUE, alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
        for (Move move : moves) {
            Game newGame = game.copy();
            newGame.makeMove(move);
            long score = minimax(newGame, maxDepth-1, alpha, beta, game.currentPlayer());
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
            alpha = Math.max(alpha, score);
        }
        return bestMove;
    }
    
}
