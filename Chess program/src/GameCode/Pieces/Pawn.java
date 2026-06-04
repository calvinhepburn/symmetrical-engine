package GameCode.Pieces;

import GameCode.Board;
import GameCode.Move;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(Board b, Board.square sqr, boolean c){
        super(b, sqr, c);
        if(c){
            charRep = 'P';
        }
        else{
            charRep = 'p';
        }
            this.imagePath = c ? "ChessPieces/whitePawn.png":"ChessPieces/blackPawn.png";
    }

    public ArrayList<Move> findMovesIgnoreCheck() {
        ArrayList<Move> moves = new ArrayList<>();
        moves.addAll(moveForward());
        moves.addAll(capture());
        return moves;
    }
    private ArrayList<Move> moveForward(){
        int direction = -1;
        int startRow = 7;
        if(this.colour){
            direction = 1;
            startRow = 2;
        }
        ArrayList<Move> moves = new ArrayList<>();
        try{
            Board.square startSqr = position;
            int nextRow = startSqr.getRow() -1 +(direction);
            int nextCol = startSqr.getColumn() -1;
            Board.square endSqr = b.board[nextCol][nextRow];
            Move m = null;
            if(endSqr.piece == null){
                if(endSqr.getRow() == 8){
                    moves.addAll(promotion(new Move(this, startSqr, endSqr, false)));
                }
                else {
                    moves.add(new Move(this, startSqr, endSqr, false));
                    }
            }
        }catch(RuntimeException re){
            System.out.println(re);
        }
        if(position.getRow() == startRow && !moves.isEmpty()){
            try{
                Board.square startSqr = position;
                int nextRow = startSqr.getRow() -1 +(2*direction);
                int nextCol = startSqr.getColumn() -1;
                Board.square endSqr = b.board[nextCol][nextRow];
                Move m = null;
                if(endSqr.piece == null){
                     moves.add(new Move(this, startSqr, endSqr, false));

                }
            }catch(RuntimeException re){
                //ignore
            }
        }
        return moves;
    }
    private ArrayList<Move> capture() {
        int direction = -1;
        if (this.colour) {
            direction = 1;
        }
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = -1; i < 2; i += 2) {
            try {
                Board.square startSqr = position;
                int nextRow = startSqr.getRow() - 1 + (direction);
                int nextCol = startSqr.getColumn() - 1 + i;
                Board.square endSqr = b.board[nextCol][nextRow];
                Move lm = b.g.gameMoves.get(b.g.gameMoves.size()-1);
            if (endSqr.piece != null && endSqr.piece.colour != this.colour) {
                    if(endSqr.getRow() == 8){
                        moves.addAll(promotion(new Move(this, startSqr, endSqr, true)));
                    }
                    else {
                        moves.add(new Move(this, startSqr, endSqr, true));
                    }
                }
            else if(lm.endSqr == b.board[nextCol][position.getRow()-1] && lm.startSqr == b.board[nextCol][nextRow + direction] && lm.piece instanceof Pawn){
                    moves.add(new Move(this, startSqr, endSqr, true));
                }
            } catch (RuntimeException re) {
                //ignore
            }
        }
        return moves;
    }
    private ArrayList<Move> promotion(Move m){
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move(m, 'B'));
        moves.add(new Move(m, 'N'));
        moves.add(new Move(m, 'R'));
        moves.add(new Move(m, 'Q'));
        return moves;
    }
}
