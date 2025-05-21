package ai;

import game.*;

public interface AI {
    // Estimates the best move for the current player
    Move getCurrentPlayerMove(Game game);
}