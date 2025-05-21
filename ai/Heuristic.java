package ai;

import game.*;

public interface Heuristic {
    // Computes a score for the current player of the game
    // Higher scores are better
    // Used by the Minimax AI to estimate the quality of a game state
    int score(Game game);
}
