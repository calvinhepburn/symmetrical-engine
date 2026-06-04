package GameCode.Pieces;

import GameCode.Board;
import GameCode.Move;

import java.util.ArrayList;

public class Rook extends Piece {
    public boolean hasMoved;
    public Rook(Board b, Board.square sqr, boolean c) {
        super(b, sqr, c);
        if(c){
            charRep = 'R';
        }
        else{
            charRep = 'r';
        }
        this.imagePath = c?"ChessPieces/whiteRook.png":"ChessPieces/blackRook.png";
    }
        public ArrayList<Move> findMovesIgnoreCheck(){
            ArrayList<Move> moves = new ArrayList<>();
            moves.addAll(moveInLine(1, 0));
            moves.addAll(moveInLine(-1,0));
            moves.addAll(moveInLine(0,1));
            moves.addAll(moveInLine(0,-1));
            return moves;
        }
}
