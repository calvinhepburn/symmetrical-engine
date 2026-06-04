package GameCode;

public class SimpleBoard {
    int[][] sboard;
    public SimpleBoard(int[][] sb){
        sboard = sb;
    }
    public int hashCode(){
        int hc = 0;
        for(int i = 0; i <8; i++){
            for(int j = 0; j< 8; j++){
                hc += sboard[i][j]*i*j;
            }
        }
        return hc;
    }
    @Override
    public boolean equals(Object ob) {
        boolean equal = true;
        if(ob instanceof SimpleBoard){
            for(int i = 0; i <8; i++){
                for(int j = 0; j< 8; j++){
                    if(this.sboard[i][j] != ((SimpleBoard) ob).sboard[i][j]){
                        equal = false;
                        break;
                    }
                }
            }

        }
        return equal;
    }
}
