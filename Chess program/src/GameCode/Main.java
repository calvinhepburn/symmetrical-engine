package GameCode;

import GameCode.Pieces.King;
import GameCode.Pieces.Pawn;
import GameCode.Pieces.Piece;
import GameCode.Pieces.Rook;

public class Main {
    public static void main(String[] args) {
        Game g = new Game();
        Board b = new Board(g);
        Piece rook1 = new Rook(b, b.board[0][0], true);
        Piece rook2 = new Rook(b, b.board[7][0], true);
        Piece rook3 = new Rook(b, b.board[5][7], false);
        Piece king1 = new King(b, b.board[3][0], true);
        Piece pawn1 = new Pawn(b, b.board[7][4], true);
        Piece pawn2 = new Pawn(b, b.board[6][6], false);
        System.out.print(b.toStringPieces());
        System.out.println(pawn1.findMovesWithCheck());
        System.out.println(pawn2.findMovesWithCheck());
        g.executeMove(pawn2.findMovesWithCheck().get(1), b);
        System.out.println(pawn1.findMovesWithCheck());
        System.out.print(b.toStringPieces());
    }
}