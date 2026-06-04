package GameCode.Pieces;

import GameCode.Board;
import GameCode.Game;
import GameCode.Move;

import javax.print.DocFlavor;
import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;

public abstract class Piece {
    public boolean hasMoved;
    public char charRep;
    public boolean colour;
    public Game g;
    public Board b;
    public Board.square position;
    public String imagePath;
    public abstract ArrayList<Move> findMovesIgnoreCheck();
    public ArrayList<Move> findMovesWithCheck(){
        ArrayList<Move> moves = this.findMovesIgnoreCheck();
        ArrayList<Move> moves2 = new ArrayList<>();
        for(Move m: moves){
            if(g.testMove(m)){
                moves2.add(m);
            }
        }
        return moves2;
    }

    public Piece(Board b,Board.square sqr, boolean c) {
        this.b = b;
        g = b.g;
        position = sqr;
        sqr.piece = this;
        this.colour = c;
        if (c)
            g.whitePieces.add(this);
        else
            g.blackPieces.add(this);
        hasMoved = false;
    }
    public ArrayList<Move> moveInLine(int i, int j){
        ArrayList<Move> moves = new ArrayList<>();
        boolean run = true;
        Board.square lastSquare = position;
        while(run){
            try {
                int nextRow = lastSquare.getRow() - 1 + i;
                int nextCol = lastSquare.getColumn() -1 + j;
                lastSquare = b.board[nextCol][nextRow];
                Move m = null;
                if (lastSquare.piece == null) {
                    m = new Move(this, position, lastSquare, false);
                } else if (lastSquare.piece.colour != this.colour) {
                    m = new Move(this, position, lastSquare, true);
                    run = false;
                } else {
                    run = false;
                }
                if(m != null)
                    moves.add(m);
            }catch (RuntimeException re){
                run = false;
            }
        }
        return moves;
    }

}
