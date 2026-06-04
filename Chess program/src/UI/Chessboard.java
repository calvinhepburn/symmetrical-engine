package UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import GameCode.*;
import GameCode.Pieces.King;
import GameCode.Pieces.Pawn;
import GameCode.Pieces.Piece;

public class Chessboard extends JFrame {
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 80;
    public Game g;
    public Board b;
    Piece selectedPiece;
    uiSquare[][] squareArray;



    public Chessboard() {
        squareArray = new uiSquare[8][8];
        g = new Game();
        b = new Board(g);
        b.setupGame();
        g.gamePositions.put(b.saveBoard(), 1);
        setTitle("Chessboard");
        this.getContentPane().setSize(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel chessboardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        chessboardPanel.setPreferredSize(new Dimension(BOARD_SIZE*CELL_SIZE, BOARD_SIZE*CELL_SIZE));
        add(chessboardPanel);

        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col <= 7; col++) {
                uiSquare uisq = new uiSquare(b, this, col, row);
                chessboardPanel.add(uisq);
                squareArray[col][row] = uisq;
            }
        }
        pack();
        cpuMove();
        setLocation(100, 100);
        setVisible(true);
    }
    public void reload(){
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col <= 7; col++) {
                squareArray[col][row].setPieceIcon();
                squareArray[col][row].moveable.setVisibleVar(false);
                selectedPiece = null;
            }
        }
    }
    public boolean testMove(Move m){
        Game cloneGame = new Game();
        Board cloneBoard = b.clone(cloneGame);
        Piece thisKing = null;
        Move m2 = new Move(cloneBoard.board[m.piece.position.getColumn()-1][m.piece.position.getRow()-1].piece, cloneBoard.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1], cloneBoard.board[m.endSqr.getColumn()-1][m.endSqr.getRow()-1], m.capture);
        cloneGame.executeMove(m2, cloneBoard);

        if(m.piece.colour){
            for(Piece p: cloneGame.whitePieces){
                if(p instanceof King) {
                    thisKing = p;
                    break;
                }
            }
        }
        else{
            for(Piece p: cloneGame.blackPieces){
                if(p instanceof King) {
                    thisKing = p;
                    break;
                }
            }
        }
        ArrayList<Move> nextMoves = cloneGame.findAllMovesIgnoreCheck(!m.piece.colour);
        for(Move m3: nextMoves){
            if(m3.endSqr.getRow() == thisKing.position.getRow() && m3.endSqr.getColumn() == thisKing.position.getColumn() && m3.capture)
                return false;
        }
        return true;

    }
    public void cpuMove(){
        JButton autoMove = new JButton("Auto-Move");
        autoMove.addActionListener(new autoMoveListener(this));
        autoMove.setFont(autoMove.getFont().deriveFont(36f));
        this.add(autoMove, BorderLayout.SOUTH);
        this.pack();
    }
    public static void main(String[] args) {
        new Chessboard();
    }
    }
