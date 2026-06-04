package UI.PromoPopup;

import GameCode.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromoPopup extends JFrame{
    JButton Queen;
    JButton Rook;
    JButton Bishop;
    JButton Knight;
    Move m;
    public PromoPopup(Move m){
        super("Choose Piece to Promote to:");
        this.m = m;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Queen = new JButton("Queen");
        Rook = new JButton("Rook");
        Bishop = new JButton("Bishop");
        Knight = new JButton("Knight");
        setVisible(true);
        setLayout(new FlowLayout());
        this.add(Queen);
        this.add(Rook);
        this.add(Bishop);
        this.add(Knight);
        setBounds(500, 500, 400, 100);
    }
    public void setListener(ActionListener al){
        Queen.addActionListener(al);
        Rook.addActionListener(al);
        Bishop.addActionListener(al);
        Knight.addActionListener(al);
    }
    public static void main(String[]args){
        PromoPopup pp = new PromoPopup(null);
        pp.setListener(new PromoPopupListener(pp));
    }
}
