package GameCode.Pieces;

import GameCode.Board;
import GameCode.Move;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Board b, Board.square sqr, boolean c) {
        super(b, sqr, c);
        if(c){
            charRep = 'N';
        }
        else{
            charRep = 'n';
        }
        this.imagePath = c?"ChessPieces/whiteKnight.png":"ChessPieces/blackKnight.png";
    }

    public ArrayList<Move> findMovesIgnoreCheck() {
        ArrayList<Move> moves = new ArrayList<>();
        int[] doubleMoves = {-2, 2};
        int[] singleMoves = {-1, 1};
        for(int i: doubleMoves){
            for(int j: singleMoves){
                try{
                    Board.square startSqr = position;
                    int nextRow = startSqr.getRow() - 1 + i;
                    int nextCol = startSqr.getColumn() -1 + j;
                    Board.square endSqr = b.board[nextCol][nextRow];
                    Move m = null;
                    if(endSqr.piece == null){
                        m = new Move(this, startSqr, endSqr,false);
                    }
                    else if(endSqr.piece.colour != this.colour){
                        m = new Move(this, startSqr, endSqr,true);
                    }
                    if(m != null)
                        moves.add(m);
                }catch(RuntimeException re){
                    //ignore
                }
            }
        }
        for(int j: doubleMoves){
            for(int i: singleMoves){
                try{
                    Board.square startSqr = position;
                    int nextRow = startSqr.getRow() - 1 + i;
                    int nextCol = startSqr.getColumn() -1 + j;
                    Board.square endSqr = b.board[nextCol][nextRow];
                    if(endSqr.piece == null){
                        moves.add(new Move(this, startSqr, endSqr,false));
                    }
                    else if(endSqr.piece.colour != this.colour){
                        moves.add(new Move(this, startSqr, endSqr,true));
                    }
                }catch(RuntimeException re){
                    //ignore
                }
            }
        }
        return moves;
    }
}
