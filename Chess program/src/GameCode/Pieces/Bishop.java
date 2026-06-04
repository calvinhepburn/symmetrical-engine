package GameCode.Pieces;

import GameCode.Board;
import GameCode.Move;


import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(Board b, Board.square sqr, boolean c){
        super(b, sqr, c);
        if(c){
            charRep = 'B';
        }
        else{
            charRep = 'b';
        }
        this.imagePath = c?"ChessPieces/whiteBishop.png":"ChessPieces/blackBishop.png";
    }
    public ArrayList<Move> findMovesIgnoreCheck() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(moveInLine(1, 1));
        moves.addAll(moveInLine(-1,1));
        moves.addAll(moveInLine(1,-1));
        moves.addAll(moveInLine(-1,-1));
        return moves;
    }

}
