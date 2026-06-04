package GameCode.Pieces;

import GameCode.Board;
import GameCode.Move;

import java.util.ArrayList;

public class Queen extends Piece {

        public Queen(Board b, Board.square sqr, boolean c){
            super(b, sqr, c);
            if(c){
                charRep = 'Q';
            }
            else{
                charRep = 'q';
            }
            this.imagePath = c?"ChessPieces/whiteQueen.png":"ChessPieces/blackQueen.png";
        }
        public ArrayList<Move> findMovesIgnoreCheck() {
            ArrayList<Move> moves = new ArrayList<>();
            moves.addAll(moveInLine(1, 1));
            moves.addAll(moveInLine(1,0));
            moves.addAll(moveInLine(1,-1));
            moves.addAll(moveInLine(0,1));
            moves.addAll(moveInLine(0, -1));
            moves.addAll(moveInLine(-1,1));
            moves.addAll(moveInLine(-1,0));
            moves.addAll(moveInLine(-1,-1));
            return moves;
        }

}
