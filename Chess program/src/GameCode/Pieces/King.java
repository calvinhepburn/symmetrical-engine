package GameCode.Pieces;

import GameCode.Board;
import GameCode.Move;

import java.util.ArrayList;
public class King extends Piece {
    boolean check = false;
    public King(Board b, Board.square sqr, boolean c){
        super(b, sqr, c);
        if(c){
            charRep = 'K';
        }
        else{
            charRep = 'k';
        }
        this.imagePath = c?"ChessPieces/whiteKing.png":"ChessPieces/blackKing.png";
    }

    public ArrayList<Move> findMovesIgnoreCheck() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    Board.square startSqr = position;
                    Board.square endSqr = b.board[this.position.getColumn() + i - 1][this.position.getRow() + j - 1];
                    Move m = null;
                    if (endSqr.piece == null) {
                        boolean legal = true;
                        m = new Move(this, startSqr, endSqr, false);
                    } else if (endSqr.piece.colour != this.colour) {
                        m = new Move(this, startSqr, endSqr, true);
                    }
                    if(m != null){
                        moves.add(m);
                    }
                } catch (RuntimeException re) {
                    //ignore
                }
            }
        }
        return moves;
    }
    public ArrayList<Move> findMovesWithCheck(){
        ArrayList<Move> moves = super.findMovesWithCheck();
        if (!this.hasMoved && !this.check) {
            int row = this.position.getRow() - 1;
            int column = this.position.getColumn() - 1;
            try {
                if (!b.board[0][row].piece.hasMoved) {
                    boolean blocked = false;
                    for (int i = column-1; i > 0; i--) {
                        if (!(b.board[i][row].piece == null)) {
                            blocked = true;
                            break;
                        }
                    }
                    if (!blocked) {
                        Board.square rSquare = b.board[column - 1][row];
                        Board.square KSquare = b.board[column - 2][row];
                        boolean legal = true;
                        for (Move m : g.findAllMovesIgnoreCheck(!colour)) {
                            if (m.endSqr == rSquare || m.endSqr == KSquare) {
                                legal = false;
                                break;
                            }
                        }
                        if (legal) {
                            moves.add(new Move(this, position, KSquare, false));
                        }
                    }
                }
            }catch (RuntimeException re){
                //do nothing
            }
            try {
                if (!b.board[7][row].piece.hasMoved) {
                    boolean blocked = false;
                    for (int i = column+1; i < 7; i++) {
                        if (!(b.board[i][row].piece == null)) {
                            blocked = true;
                            break;
                        }
                    }
                    if (!blocked) {
                        Board.square rSquare = b.board[column + 1][row];
                        Board.square KSquare = b.board[column + 2][row];
                        boolean legal = true;
                        for (Move m : g.findAllMovesIgnoreCheck(!colour)) {
                            if (m.endSqr == rSquare || m.endSqr == KSquare) {
                                legal = false;
                                break;
                            }
                        }
                        if (legal) {
                            moves.add(new Move(this, position, KSquare, false));
                        }
                    }
                }
            }catch (RuntimeException re){
                //do nothing
            }
        }
        return moves;
    }
}
