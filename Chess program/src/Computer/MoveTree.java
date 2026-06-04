package Computer;

import GameCode.Board;
import GameCode.Game;
import GameCode.Move;

import java.util.LinkedList;

import static Computer.PositionEvaluator.getEval;

public class MoveTree {
    MoveTreeNode head;
    MoveTree(Board b){
        head = new MoveTreeNode(null, b, null);
    }


    class MoveTreeNode{
        Board b;
        LinkedList<MoveTreeNode> children;
        MoveTreeNode parent;
        boolean turnColour;
        float eval;
        float MaxChildEval;
        float MinChildEval;
        MoveTreeNode favChild;
        Move move;
        int level;
        MoveTreeNode(Move m, Board b2, MoveTreeNode mtn){
            children = new LinkedList<MoveTreeNode>();
            MinChildEval = 100;
            MaxChildEval = -100;
            if(mtn != null) {
                parent = mtn;
                b = parent.b.clone(new Game());
                turnColour = !mtn.turnColour;
                Move m2 = new Move(b.board[m.piece.position.getColumn()-1][m.piece.position.getRow()-1].piece, b.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1], b.board[m.endSqr.getColumn()-1][m.endSqr.getRow()-1], m.capture);
                String message = b.g.playMove(m2, b);
                parent.children.add(this);
                move = m2;
                if (message != null) {
                    if (message.equals("draw"))
                        eval = 0;
                    if (message.equals("checkmate"))
                        eval = 200 * (turnColour ? 1 : -1);
                } else {
                    eval = getEval(this.b);
                }
                level = parent.level + 1;
                if (eval <= parent.MinChildEval) {
                    parent.MinChildEval = eval;
                    if(turnColour)
                        parent.favChild = this;
                }
                if (eval >= parent.MaxChildEval) {
                    parent.MaxChildEval = eval;
                    if(!turnColour)
                        parent.favChild = this;
                }
                if(parent.favChild == null)
                    parent.favChild = this;
            }
            else{
                Board tempb = b2.clone(new Game());
                this.b = tempb;
                turnColour = b.g.turnColour;
                parent = null;
                move = null;
                eval = 0;
                level = 0;
            }
        }
    }
}
