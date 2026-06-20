package Computer;

import GameCode.Board;
import GameCode.Game;
import GameCode.Move;
import GameCode.Pieces.King;
import GameCode.Pieces.Piece;

import java.util.ArrayList;

import static Computer.PositionEvaluator.getEval;

public class Decider {
    //eval is from white's perspective, so white maximises and black minimises
    private static final int DEFAULT_DEPTH = 3;
    private static final float INF = 1_000_000f;
    private static final float MATE = 100_000f;

    private final int depth;

    public Decider() {
        this(DEFAULT_DEPTH);
    }
    public Decider(int depth) {
        this.depth = depth;
    }

    public static void main(String[] args){
        Game g = new Game();
        Board b = new Board(g);
        b.setupGame();
        System.out.println(new Decider().decide(b));
    }

    //best move for the side to move on b, or null if there are none
    public Move decide(Board b){
        boolean side = b.g.turnColour;
        ArrayList<Move> moves = b.g.findAllMoves(side);
        Move best = null;
        float bestVal = side ? -INF : INF;
        for (Move m : moves) {
            float val = minimax(playOnClone(b, m), depth - 1, -INF, INF);
            if (best == null || (side ? val > bestVal : val < bestVal)) {
                bestVal = val;
                best = m;
            }
        }
        return best;
    }

    private float minimax(Board b, int depth, float alpha, float beta){
        if (depth == 0)
            return getEval(b);

        boolean side = b.g.turnColour;
        ArrayList<Move> moves = b.g.findAllMoves(side);
        if (moves.isEmpty()) {
            if (inCheck(b, side))
                return side ? -(MATE + depth) : (MATE + depth); //side to move is mated
            return 0f; //stalemate
        }

        if (side) { //white maximises
            float best = -INF;
            for (Move m : moves) {
                best = Math.max(best, minimax(playOnClone(b, m), depth - 1, alpha, beta));
                alpha = Math.max(alpha, best);
                if (alpha >= beta) break;
            }
            return best;
        } else { //black minimises
            float best = INF;
            for (Move m : moves) {
                best = Math.min(best, minimax(playOnClone(b, m), depth - 1, alpha, beta));
                beta = Math.min(beta, best);
                if (alpha >= beta) break;
            }
            return best;
        }
    }

    //clone b, re-map move m onto the clone, play it, and return the clone
    private Board playOnClone(Board b, Move m){
        Board c = b.clone(new Game());
        Move mc = new Move(
            c.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1].piece,
            c.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1],
            c.board[m.endSqr.getColumn()-1][m.endSqr.getRow()-1],
            m.capture);
        mc.promotePiece = m.promotePiece;
        c.g.playMove(mc, c);
        return c;
    }

    private boolean inCheck(Board b, boolean side){
        for (Piece p : side ? b.g.whitePieces : b.g.blackPieces)
            if (p instanceof King)
                return ((King) p).isInCheck();
        return false;
    }
}
