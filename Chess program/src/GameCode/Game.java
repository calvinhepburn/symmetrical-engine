package GameCode;

import GameCode.Pieces.*;
import UI.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Game {
public ArrayList<Move> gameMoves;
public HashMap<SimpleBoard, Integer> gamePositions;
public ArrayList<Piece> blackPieces;
public ArrayList<Piece> whitePieces;
public boolean turnColour;
public boolean GameOver;
public int fiftyMove;
public Game(){
    gameMoves = new ArrayList<>();
    whitePieces = new ArrayList<>();
    blackPieces = new ArrayList<>();
    turnColour = true;
    gamePositions = new HashMap<SimpleBoard, Integer>();

    fiftyMove = 0;
}
public void executeMoveTheory(Move m, Board b){
    Piece p = m.piece;
    Piece captured = null;
    m.startSqr.piece = null;
    p.hasMoved = true;
    if(m.capture){
        captured = m.endSqr.piece;
        if(captured == null && p instanceof Pawn){
            captured = b.board[m.endSqr.getColumn()-1][m.startSqr.getRow()-1].piece;
            b.board[m.endSqr.getColumn()-1][m.startSqr.getRow()-1].piece = null;
        }
        if(m.piece.colour)
            this.blackPieces.remove(captured);
        else
            this.whitePieces.remove(captured);
        m.endSqr.piece = p;
        p.position = m.endSqr;
    }
    else if(p instanceof King && Math.abs(m.startSqr.getColumn() - m.endSqr.getColumn()) == 2){
        m.endSqr.piece = p;
        p.position = m.endSqr;
        if((m.startSqr.getColumn() - m.endSqr.getColumn()) < 0){
            Piece rook1 = b.board[7][m.endSqr.getRow()-1].piece;
            b.board[7][m.endSqr.getRow()-1].piece = null;
            b.board[5][m.endSqr.getRow()-1].piece =rook1;
            rook1.position = b.board[5][m.endSqr.getRow()-1];
        }
        else{
            Piece rook1 = b.board[0][m.endSqr.getRow()-1].piece;
            b.board[0][m.endSqr.getRow()-1].piece = null;
            b.board[3][m.endSqr.getRow()-1].piece =rook1;
            rook1.position = b.board[3][m.endSqr.getRow()-1];
        }
    }
    else {
        m.endSqr.piece = p;
        p.position = m.endSqr;
    }

    if(m.promotePiece != ' ') {
        m.startSqr.piece = null;
        if(m.piece.colour)
            this.whitePieces.remove(m.piece);
        else
            this.blackPieces.remove(m.piece);
        m.endSqr.piece = null;
        switch (m.promotePiece) {
            case 'B':
                p = new Bishop(b, m.endSqr, p.colour);
                break;
            case 'K':
                m.promotePiece = 'N';
                p = new Knight(b, m.endSqr, p.colour);
                break;
            case 'R':
                p = new Rook(b, m.endSqr, p.colour);
                break;
            default:
                p = new Queen(b, m.endSqr, p.colour);
                break;
        }
        m.endSqr.piece = p;
        p.position = m.endSqr;
    }
    gameMoves.add(m);
}
public String playMove(Move m, Board b){
    executeMove(m, b);
    String s1 = recordMove(m, b);
    String s2 = checkGameEnd(b);
    turnColour = !turnColour;
    if(s1 != null)
        return s1;
    else return s2;
}
public void executeMove(Move m, Board b){
    Piece p = m.piece;
    Piece captured = null;
    m.startSqr.piece = null;
    if(m.capture){
        captured = m.endSqr.piece;
            if(captured == null && p instanceof Pawn){
                captured = b.board[m.endSqr.getColumn()-1][m.startSqr.getRow()-1].piece;
                b.board[m.endSqr.getColumn()-1][m.startSqr.getRow()-1].piece = null;
            }
        if(m.piece.colour)
            this.blackPieces.remove(captured);
        else
            this.whitePieces.remove(captured);
        m.endSqr.piece = p;
        p.position = m.endSqr;
    }
    else if(p instanceof King && Math.abs(m.startSqr.getColumn() - m.endSqr.getColumn()) == 2){
        m.endSqr.piece = p;
        p.position = m.endSqr;
        if((m.startSqr.getColumn() - m.endSqr.getColumn()) < 0){
            Piece rook1 = b.board[7][m.endSqr.getRow()-1].piece;
            b.board[7][m.endSqr.getRow()-1].piece = null;
            b.board[5][m.endSqr.getRow()-1].piece =rook1;
            rook1.position = b.board[5][m.endSqr.getRow()-1];
            rook1.hasMoved = true;
        }
        else{
            Piece rook1 = b.board[0][m.endSqr.getRow()-1].piece;
            b.board[0][m.endSqr.getRow()-1].piece = null;
            b.board[3][m.endSqr.getRow()-1].piece =rook1;
            rook1.position = b.board[3][m.endSqr.getRow()-1];
            rook1.hasMoved = true;
        }
    }
    else {
        m.endSqr.piece = p;
        p.position = m.endSqr;
    }

    if(m.promotePiece != ' ') {
        m.startSqr.piece = null;
        if(m.piece.colour)
            this.whitePieces.remove(m.piece);
        else
            this.blackPieces.remove(m.piece);
        m.endSqr.piece = null;
        switch (m.promotePiece) {
            case 'B':
                p = new Bishop(b, m.endSqr, p.colour);
                break;
            case 'K':
                m.promotePiece = 'N';
                p = new Knight(b, m.endSqr, p.colour);
                break;
            case 'R':
                p = new Rook(b, m.endSqr, p.colour);
                break;
            default:
                p = new Queen(b, m.endSqr, p.colour);
                break;
        }
        m.endSqr.piece = p;
        p.position = m.endSqr;
    }
}
public String recordMove(Move m, Board b){
    m.piece.hasMoved = true;
    gameMoves.add(m);
    if(!gamePositions.containsKey(b.saveBoard()))
        gamePositions.put(b.saveBoard(), 1);
    else {
        int prevReps = gamePositions.get(b.saveBoard());
        gamePositions.put(b.saveBoard(), prevReps + 1);
        if (gamePositions.get(b.saveBoard()) >= 3) {
            //draw
            gameOver("3 move repetition", b);
            return "draw";
        }
    }
    if(!m.capture && !(m.piece instanceof Pawn)) {
        fiftyMove++;
        if (fiftyMove >= 50) {
            gameOver("50 move repetition", b);
            return "draw";
        }
    }
    else
        fiftyMove = 0;
    return null;
}
public String checkGameEnd(Board b){
    ArrayList<Move> moves1= b.g.findAllMovesIgnoreCheck(!turnColour);
    ArrayList<Move> moves2 = new ArrayList<>();
    for(Move m1: moves1){
        if(testMove(m1)){
            moves2.add(m1);
            break;
        }
    }
    if(moves2.isEmpty()){
        boolean mate = false;
        for(Move m: findAllMoves(turnColour)){
            if(m.capture && m.endSqr.piece instanceof King){
                mate = true;
                break;
            }
        }if(mate){
            gameOver("checkmate", whitePieces.get(0).b);
            return "checkmate";
        }
        else {
            gameOver("stalemate", whitePieces.get(0).b);
            return "stalemate";
        }
    }
    return null;
}
public void gameOver(String message, Board b){
    GameOver = true;
    JLabel endMessage = new JLabel("Game Over: " + message);
    endMessage.setFont(endMessage.getFont().deriveFont(36f));
    if(b.board[1][1].uis != null) {
        b.board[1][1].uis.cb.add(endMessage, BorderLayout.SOUTH);
        b.board[1][1].uis.cb.pack();
    }
}
public ArrayList<Move> findAllMoves(boolean c){
    ArrayList<Move> moves = new ArrayList<>();
    if (c) {
        for(Piece p: whitePieces){
            moves.addAll(p.findMovesWithCheck());
        }
    }
    else{
        for(Piece p: blackPieces){
            moves.addAll(p.findMovesWithCheck());
        }
    }
    return moves;
}
public ArrayList<Move> findAllMovesIgnoreCheck(boolean c){
    ArrayList<Move> moves = new ArrayList<>();
    if (c) {
        for(Piece p: whitePieces){
            moves.addAll(p.findMovesIgnoreCheck());
        }
    }
    else{
        for(Piece p: blackPieces){
            moves.addAll(p.findMovesIgnoreCheck());
        }
    }
    return moves;
}
    public boolean testMove(Move m){
        Game cloneGame = new Game();
        Board cloneBoard = m.piece.b.clone(cloneGame);
        cloneGame.gameMoves = m.piece.g.gameMoves;
        Piece thisKing = null;
        Move m2 = new Move(cloneBoard.board[m.piece.position.getColumn()-1][m.piece.position.getRow()-1].piece, cloneBoard.board[m.startSqr.getColumn()-1][m.startSqr.getRow()-1], cloneBoard.board[m.endSqr.getColumn()-1][m.endSqr.getRow()-1], m.capture);
        cloneGame.executeMove(m2, cloneBoard);
        Board.square kingSquare;
        if(m.piece instanceof King){
            kingSquare = m.endSqr;
        }
        else {
            if (m.piece.colour) {
                for (Piece p : cloneGame.whitePieces) {
                    if (p instanceof King) {
                        thisKing = p;
                        break;
                    }
                }
            } else {
                for (Piece p : cloneGame.blackPieces) {
                    if (p instanceof King) {
                        thisKing = p;
                        break;
                    }
                }
            }
            if(thisKing != null)
                kingSquare = thisKing.position;
            else
                return false;
        }
        ArrayList<Move> nextMoves = cloneGame.findAllMovesIgnoreCheck(!m.piece.colour);
        for(Move m3: nextMoves){
            if(m3.endSqr.getRow() == kingSquare.getRow() && m3.endSqr.getColumn() == kingSquare.getColumn() && m3.capture)
                return false;
        }
        return true;

    }

}
