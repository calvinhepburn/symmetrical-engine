package UI.PromoPopup;

import GameCode.Move;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class PromoPopupListener implements ActionListener {
    PromoPopup pp;

    public PromoPopupListener(PromoPopup pp) {
        super();
        this.pp = pp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pp.m.promotePiece = e.toString().charAt(0);
        pp.m.piece.g.playMove(new Move(pp.m, e.getActionCommand().charAt(0)), pp.m.piece.b);
        System.out.println(e.getActionCommand());
        pp.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        pp.dispatchEvent(new WindowEvent(pp, WindowEvent.WINDOW_CLOSING));
        pp.m.endSqr.uis.cb.reload();
        System.out.println(pp.m.piece.b.toStringPieces());

    }
}
