package GameCode;

import GameCode.Pieces.Piece;

public class Move {
    public Piece piece;
    public Board.square startSqr;
    public Board.square endSqr;
    public boolean capture;
    public char promotePiece;
    public Move(Piece piece, Board.square startSqr, Board.square endSqr, boolean capture){
        if(startSqr == endSqr) throw new RuntimeException("Illegal move");
        this.piece = piece;
        this.startSqr = startSqr;
        this.endSqr = endSqr;
        this.capture = capture;
        this.promotePiece = ' ';
    }
    public Move(Move m, char c){
        this.piece = m.piece;
        this.startSqr = m.startSqr;
        this.endSqr = m.endSqr;
        this.capture = m.capture;
        this.promotePiece = c;
    }

    public String toString() {
        String strMove = piece.charRep + startSqr.toString() + (capture? "x":"") + endSqr.toString() + (promotePiece == ' '?"":promotePiece);
        return strMove;
    }
}
