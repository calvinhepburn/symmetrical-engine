package Computer;

import GameCode.Board;
import GameCode.Game;
import GameCode.Move;


public class Decider {
    public MoveTree decisionTree;
    public static void main(String[]args){
        Game g = new Game();
        Board b = new Board(g);
        b.setupGame();
        Decider d = new Decider();
        d.decide(b);
    }
    public Move decide(Board b){
        decisionTree = new MoveTree(b);
        decisionTree.head.turnColour = b.g.turnColour;
        TreeSetUpRecursion(decisionTree.head, 3);
        System.out.println(decisionTree.head.favChild.move);
        return decisionTree.head.favChild.move;
    }
    public void TreeSetUpRecursion(MoveTree.MoveTreeNode mtn, int levelCap){
        if(mtn.level < levelCap){
            for(Move m: mtn.b.g.findAllMoves(mtn.turnColour)){
                Board cloneb = mtn.b.clone(new Game());
                MoveTree.MoveTreeNode nextMove = decisionTree.new MoveTreeNode(m, cloneb, mtn);
                mtn.children.add(nextMove);
                TreeSetUpRecursion(nextMove, levelCap);
            }
        }
    }
}
