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
        if(m != null){ //m already references the live board, so play it directly
            cb.g.playMove(m, cb.b);
            cb.reload();
        }
    }
}
