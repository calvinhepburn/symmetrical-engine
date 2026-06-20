package GameCode;

import GameCode.Pieces.*;
import UI.uiSquare;

public class Board {
    public Game g;

    public Board(Game g){
        board = new square[8][8];
        for(int i = 0; i <= 7; i++){
            for(int j = 0; j <= 7; j++){
                square a = new square(j+1, i+1, !((i+j)%2==0), null);
                board[i][j] = a;
            }
        }
        this.g = g;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 7; i >= 0; i--){
            for(int j = 0; j<=7; j++){
                str.append("[" +board[j][i].toString() + "]");
            }
            str.append(String.format("%n"));
        }
        return str.toString();
    }
    public String toStringPieces(){
        StringBuilder str = new StringBuilder();
        for (int i = 7; i >= 0; i--){
            for(int j = 0; j<=7; j++){
                char piece = ' ';
                if(board[j][i].piece != null){
                    piece = board[j][i].piece.charRep;
                }
                if(board[j][i].colour){
                    str.append("[" + piece + "]");
                }
                else{
                    str.append(" " + piece + " ");
                }
            }
            str.append(String.format("%n"));
        }
        return str.toString();
    }
    public void setupGame(){
        for(int i = 0; i < 8; i++){
            board[i][1].piece = new Pawn(this, board[i][1], true);
            board[i][6].piece = new Pawn(this, board[i][6], false);
        }
        board[0][0].piece = new Rook(this, board[0][0], true);
        board[7][0].piece = new Rook(this, board[7][0], true);
        board[1][0].piece = new Knight(this, board[1][0], true);
        board[6][0].piece = new Knight(this, board[6][0], true);
        board[2][0].piece = new Bishop(this, board[2][0], true);
        board[5][0].piece = new Bishop(this, board[5][0], true);
        board[3][0].piece = new Queen(this, board[3][0], true);
        board[4][0].piece = new King(this, board[4][0], true);

        board[0][7].piece = new Rook(this, board[0][7], false);
        board[7][7].piece = new Rook(this, board[7][7], false);
        board[1][7].piece = new Knight(this, board[1][7], false);
        board[6][7].piece = new Knight(this, board[6][7], false);
        board[2][7].piece = new Bishop(this, board[2][7], false);
        board[5][7].piece = new Bishop(this, board[5][7], false);
        board[3][7].piece = new Queen(this, board[3][7], false);
        board[4][7].piece = new King(this, board[4][7], false);
    }
    public Board clone(Game g){
        Board clone = new Board(g);
        g.turnColour = this.g.turnColour;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j<8;j++){
                if(this.board[i][j].piece instanceof Pawn)
                    clone.board[i][j].piece = new Pawn(clone,clone.board[i][j], this.board[i][j].piece.colour);
                else if(this.board[i][j].piece instanceof Rook)
                    clone.board[i][j].piece = new Rook(clone,clone.board[i][j], this.board[i][j].piece.colour);
                else if(this.board[i][j].piece instanceof Bishop)
                    clone.board[i][j].piece = new Bishop(clone,clone.board[i][j], this.board[i][j].piece.colour);
                else if(this.board[i][j].piece instanceof Knight)
                    clone.board[i][j].piece = new Knight(clone,clone.board[i][j], this.board[i][j].piece.colour);
                else if(this.board[i][j].piece instanceof Queen)
                    clone.board[i][j].piece = new Queen(clone,clone.board[i][j], this.board[i][j].piece.colour);
                else if(this.board[i][j].piece instanceof King)
                    clone.board[i][j].piece = new King(clone,clone.board[i][j], this.board[i][j].piece.colour);
                if(clone.board[i][j].piece != null)
                    clone.board[i][j].piece.hasMoved = this.board[i][j].piece.hasMoved;
            }
        }
        return clone;
    }
    public class square{
        private int row;
        private int column;
        public uiSquare uis;
        private boolean colour;
        public Piece piece;
        public square(){
        }
        public square(int row, int column, boolean colour, Piece piece){
            this.row = row;
            this.column = column;
            this.colour = colour;
            this.piece = piece;
            uis = null;
        }
        public int getRow(){
            return row;
        }
        public int getColumn(){
            return column;
        }
        public boolean colour(){
            return colour;
        }
        public String toString(){
            char c = (char) (column+96);
            return String.format("%c%d", c, row);
        }
    }
    public square[][] board;
    public SimpleBoard saveBoard() {
        int[][] saveBoard = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board[i][j].piece == null)
                    saveBoard[i][j] = 0;
                else {
                     int colourMultiplier = this.board[i][j].piece.colour ? 1 : -1;
                     if (this.board[i][j].piece instanceof Pawn)
                            saveBoard[i][j] = colourMultiplier;
                        else if (this.board[i][j].piece instanceof Rook)
                            saveBoard[i][j] = 2 * colourMultiplier;
                        else if (this.board[i][j].piece instanceof Knight)
                            saveBoard[i][j] = 3 * colourMultiplier;
                        else if (this.board[i][j].piece instanceof Bishop)
                            saveBoard[i][j] = 4 * colourMultiplier;
                        else if (this.board[i][j].piece instanceof Queen)
                            saveBoard[i][j] = 5 * colourMultiplier;
                        else if (this.board[i][j].piece instanceof King)
                            saveBoard[i][j] = 6 * colourMultiplier;
                }
            }
        }
        return new SimpleBoard(saveBoard, this.g.turnColour);
    }
    }
