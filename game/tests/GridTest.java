package game.tests;

import game.Grid;
import game.GridImpl;
import game.PieceColour;;

public class GridTest extends Test {
    public static void main(String[] args) {
        Grid grid = new GridImpl(5);
        expect(5, grid.getSize());
        expect(PieceColour.NONE, grid.getPiece(0, 0));
        expect(PieceColour.NONE, grid.getPiece(4, 4));
        expect(PieceColour.NONE, grid.getPiece(2, 3));
        expect(".....\n.....\n.....\n.....\n.....\n", grid.toString());
        grid.setPiece(2, 1, PieceColour.WHITE);
        expect(PieceColour.WHITE, grid.getPiece(2, 1));
        expect(".....\n.....\n.W...\n.....\n.....\n", grid.toString());
        grid.setPiece(0, 0, PieceColour.BLACK);
        expect(PieceColour.BLACK, grid.getPiece(0, 0));
        expect("B....\n.....\n.W...\n.....\n.....\n", grid.toString());
        grid.setPiece(4, 4, PieceColour.WHITE);
        expect(PieceColour.WHITE, grid.getPiece(4, 4));
        expect("B....\n.....\n.W...\n.....\n....W\n", grid.toString());
        boolean caught = false;
        try {
            grid.setPiece(4, 5, PieceColour.BLACK);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        Grid copy = grid.copy();
        expect(5, copy.getSize());
        expect(PieceColour.BLACK, copy.getPiece(0, 0));
        expect(PieceColour.WHITE, copy.getPiece(4, 4));
        expect(PieceColour.NONE, copy.getPiece(2, 3));
        expect("B....\n.....\n.W...\n.....\n....W\n", copy.toString());
        copy.setPiece(1, 2, PieceColour.BLACK);
        expect(PieceColour.BLACK, copy.getPiece(1, 2));
        expect("B....\n..B..\n.W...\n.....\n....W\n", copy.toString());

        expect(PieceColour.NONE, grid.getPiece(1, 2));
        expect("B....\n.....\n.W...\n.....\n....W\n", grid.toString());
        caught = false;
        try {
            copy.getPiece(-1, 2);
        } catch (IllegalArgumentException e) {
            caught = true;
        }
        expect(true, caught);

        checkAllTestsPassed();
        

    }
}
