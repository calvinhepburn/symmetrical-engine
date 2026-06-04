package UI;

import Computer.Decider;
import GameCode.Move;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class autoMoveListener implements ActionListener {
    Chessboard cb;
    public autoMoveListener(Chessboard cb){
        super();
        this.cb = cb;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Decider d = new Decider();
        Move m = d.decide(cb.b);
        Move m2 = new Move(cb.b.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1].piece, cb.b.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1], cb.b.board[m.endSqr.getColumn()-1][m.endSqr.getRow()-1], m.capture);
        cb.g.playMove(m2, cb.b);
        cb.reload();

    }
}
