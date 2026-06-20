package GameCode;

import java.util.Arrays;

public class SimpleBoard {
    int[][] sboard;
    boolean turn;
    public SimpleBoard(int[][] sb, boolean turn){
        sboard = sb;
        this.turn = turn;
    }
    @Override
    public int hashCode(){
        return 31 * Arrays.deepHashCode(sboard) + (turn ? 1 : 0);
    }
    @Override
    public boolean equals(Object ob) {
        if (this == ob) return true;
        if (!(ob instanceof SimpleBoard)) return false;
        SimpleBoard other = (SimpleBoard) ob;
        return turn == other.turn && Arrays.deepEquals(sboard, other.sboard);
    }
}
