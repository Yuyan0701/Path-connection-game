package game.tests;

import game.Move;
import game.MoveImpl;

public class MoveTest extends Test {
    public static void main(String[] args) {
        Move move = new MoveImpl(1, 2);
        expect(1, move.getRow());
        expect(2, move.getCol());
        expect("(1,2)", move.toString());

        move = new MoveImpl(1, 0);
        expect(1, move.getRow());
        expect(0, move.getCol());
        expect("(1,0)", move.toString());

        move = new MoveImpl(0, 0);
        expect(0, move.getRow());
        expect(0, move.getCol());
        expect("(0,0)", move.toString());

        move = new MoveImpl(4, 4);
        expect(4, move.getRow());
        expect(4, move.getCol());
        expect("(4,4)", move.toString());

        checkAllTestsPassed();
    }
}
