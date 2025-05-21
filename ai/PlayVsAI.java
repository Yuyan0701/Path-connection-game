package ai;

import game.*;
import java.util.*;

public class PlayVsAI {
    public static void main(String[] args) {
        // By default, a board of size 5 is used
        // Increasing the size of the board will make the AI slower
        Game game = new GameImpl(5);

        // Decrease the depth to make the AI faster, but less powerful
        // Increase the depth to make the AI slower, but more powerful
        // The AI gets very slow very quickly as the depth increases!
        AI ai = new Minimax(5, new MinPiecesHeuristic());

        // Change this to PieceColour.BLACK if you want to play as white
        PieceColour aiColour = PieceColour.WHITE;

        // Main loop. It is a do loop since there is always a first turn
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("The current player is " + game.currentPlayer());
            System.out.println("The game grid:");
            System.out.println(game.getGrid());
            if (game.currentPlayer() == aiColour) {
                // AI turn
                Move move = ai.getCurrentPlayerMove(game);
                System.out.println("AI move: " + move);
                game.makeMove(move);
            } else {
                // Human turn

                // First, show the moves
                System.out.println("Your turn. Please enter a move number to make a move.");
                var moves = game.getMoves().toArray();
                for (int i = 0; i < moves.length; ++i) {
                    System.out.print("Move " + (i+1) + ": ");
                    System.out.println(moves[i]);
                }
                
                // Now, get a move from the user
                try {
                    int moveId = sc.nextInt();
                    Move move = (Move) moves[moveId-1];
                    game.makeMove(move);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid move. Please enter a valid move number.");
                    continue;
                } catch (InputMismatchException e) {
                    sc.next(); // Clear the mismatched input from the scanner
                    System.out.println("Invalid move. Please enter a valid move number.");
                    continue;
                }
            }
        } while (!game.isOver());
        
        System.out.println("Game over!");
        System.out.println("The final game grid:");
        System.out.println(game.getGrid());
        if (game.winner() == aiColour)
            System.out.println("The winner is the AI!");
        else if (game.winner() != PieceColour.NONE)
            System.out.println("The winner is you!");
        else
            System.out.println("It's a draw!");
    }
    
}
