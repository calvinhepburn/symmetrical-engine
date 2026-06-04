package Computer;

import GameCode.Board;
import GameCode.Pieces.*;

import java.util.ArrayList;

public class PositionEvaluator {
    Board b;
    public static float getEval(Board b){
        float eval = 0;
        int colourMultiplier = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece p = b.board[i][j].piece;
                if(p != null){
                    if(p.colour){
                        colourMultiplier = 1;
                    }
                    else{
                        colourMultiplier = -1;
                    }
                    if(p instanceof Pawn){
                        eval += colourMultiplier;
                        if(2 <= p.position.getColumn() && p.position.getColumn() <= 5 && 3 <= p.position.getRow() && p.position.getRow() <= 4)
                            eval += (float) (0.2*colourMultiplier);
                        try {
                            if (b.board[p.position.getColumn() - 2][p.position.getRow() - 2].piece != null && b.board[p.position.getColumn() - 2][p.position.getRow() - 2].piece.colour == p.colour) {

                                if (b.board[p.position.getColumn() - 2][p.position.getRow() - 2].piece instanceof Pawn)
                                    eval += (float) (0.2 * colourMultiplier);
                            }
                        }catch(ArrayIndexOutOfBoundsException ignored){}
                        try {
                            if (b.board[p.position.getColumn()][p.position.getRow() - 2].piece != null && b.board[p.position.getColumn()][p.position.getRow() - 2].piece.colour == p.colour) {
                                if (b.board[p.position.getColumn()][p.position.getRow() - 2].piece instanceof Pawn)
                                    eval += (float) (0.2 * colourMultiplier);
                            }
                        }catch(ArrayIndexOutOfBoundsException ignored){}
                    }
                    else if(p instanceof Rook){
                        eval += 5*colourMultiplier;
                        eval += (float) (0.01*colourMultiplier*p.findMovesIgnoreCheck().size());
                    }
                    else if(p instanceof Bishop || p instanceof Knight){
                        eval += 3*colourMultiplier;
                        eval += (float) (0.01*colourMultiplier*p.findMovesIgnoreCheck().size());
                    }
                    else if(p instanceof Queen){
                        eval += 9*colourMultiplier;
                        eval += (float) (0.01*colourMultiplier*p.findMovesIgnoreCheck().size());
                    }
                    else if(p instanceof King){
                        //do nothing
                    }
            }
            }
        }
        return eval;
    }
}
