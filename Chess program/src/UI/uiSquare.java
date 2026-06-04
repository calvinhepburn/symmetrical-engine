package UI;

import javax.swing.*;
import GameCode.*;
import GameCode.Pieces.Pawn;
import GameCode.Pieces.Piece;
import UI.PromoPopup.PromoPopup;
import UI.PromoPopup.PromoPopupListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;

import static javax.swing.text.StyleConstants.setIcon;

public class uiSquare extends JLayeredPane {
    public Board b;
    public Board.square sq;
    public Chessboard cb;
    public Moveable moveable;
    public piecePanel piecePanel;
    public JLabel pieceIcon;
    public uiSquare(Board b, Chessboard cb, int i, int j){
        this.b = b;
        sq = b.board[i][j];
        this.cb = cb;

        sq.uis = this;

        setBackground(sq.colour()? Color.WHITE : Color.BLACK);
        JPanel panel1 = new JPanel();
        panel1.setOpaque(true);
        panel1.setBackground(sq.colour()? Color.WHITE : Color.BLACK);;
        panel1.setBounds(0, 0, 80, 80);
        JLabel coodinate = new JLabel(sq.toString());
        coodinate.setForeground(Color.LIGHT_GRAY);
        panel1.add(coodinate);
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 63));

        piecePanel = new piecePanel(this);
        piecePanel.setOpaque(false);
        piecePanel.setBounds(0, 0, 80, 80);
        piecePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        piecePanel.addMouseListener(new PieceListener(piecePanel));
        ImageIcon pieceImage = null;
        pieceIcon = null;

        moveable = new Moveable(sq);
        this.add(moveable, JLayeredPane.DRAG_LAYER);
        moveable.addMouseListener(moveable);

        setPieceIcon();
        this.add(piecePanel, JLayeredPane.DRAG_LAYER);

        this.add(panel1, JLayeredPane.DEFAULT_LAYER);
    }
    public void setPieceIcon(){
        ImageIcon pieceImage = null;
        try {
            piecePanel.remove(pieceIcon);
        }catch(NullPointerException npe){}
        pieceIcon = null;
        piecePanel.setVisible(false);
        if(sq.piece != null){
            pieceImage = new ImageIcon(new ImageIcon(sq.piece.imagePath).getImage().getScaledInstance(44, 66, Image.SCALE_DEFAULT));
            pieceIcon = new JLabel(pieceImage);
            piecePanel.add(pieceIcon);
            piecePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
            piecePanel.setVisible(true);
        }

    }
    public static void main(String[]args){
        JFrame chessSquare = new JFrame();
        chessSquare.setBounds(0,0,100,140);
        chessSquare.add(new uiSquare(new Board(new Game()), new Chessboard(), 0, 0));
        chessSquare.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chessSquare.setVisible(true);
    }
    public class piecePanel extends JPanel{
        uiSquare uis;
        piecePanel(uiSquare uis){
            super();
            this.uis = uis;
        }
    }
    public class Moveable extends JPanel implements MouseListener{
        boolean visible = false;
        Board.square sq;
        Moveable(Board.square sq){
            super();
            this.sq = sq;
            this.setBounds(0, 0, 80, 80);
            this.setBackground(new Color(0, 255, 0, 100));
            this.setVisible(false);
        }

        public void setVisibleVar(boolean visible) {
            this.visible = visible;
            this.setVisible(visible);
            if(visible)
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(visible){
                for(Move m2: cb.selectedPiece.findMovesWithCheck()){
                    if((m2.piece instanceof Pawn && m2.endSqr.getRow() == 8 || m2.piece instanceof Pawn && m2.endSqr.getRow() == 1) && m2.endSqr.getRow() == sq.getRow() && m2.endSqr.getColumn() == sq.getColumn()) {
                        PromoPopup pp = new PromoPopup(m2);
                        pp.setListener(new PromoPopupListener(pp));
                        break;
                    }
                    else if(m2.endSqr.getRow() == sq.getRow() && m2.endSqr.getColumn() == sq.getColumn()) {
                        b.g.playMove(m2, b);
                    }
                }
                cb.reload();
                System.out.println(b.toStringPieces());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public class PieceListener implements MouseListener {
        piecePanel p;
        PieceListener(piecePanel p){
            this.p = p;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(p.uis.sq.piece.colour == p.uis.b.g.turnColour && !p.uis.b.g.GameOver) {
                cb.selectedPiece = p.uis.sq.piece;
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++) {
                        //b.board[i][j].uis.moveable.setVisible(false);
                        b.board[i][j].uis.moveable.setVisibleVar(false);
                    }
                ArrayList<Move> moves = p.uis.sq.piece.findMovesWithCheck();
                System.out.println(moves);
                for (int i = 0; i < moves.size(); i++) {
                    moves.get(i).endSqr.uis.moveable.setVisible(true);
                    moves.get(i).endSqr.uis.moveable.setVisibleVar(true);
                }
            }
            else{
                //do nothing
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
