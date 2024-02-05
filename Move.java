import java.util.ArrayList;

public class Move {
    private Position src;
    private Position dest;
    private ArrayList<TakenPiece> taken;
    private int move_num;
    private ConcretePlayer player;

    public Move(Position src, Position dest, ArrayList<TakenPiece> taken, ConcretePlayer player, int move_num){
        this.src = src;
        this.dest = dest;
        this.taken = taken;
        this.player = player;
        this.move_num = move_num;
    }

    public Position getSrc() {
        return src;
    }

    public void setSrc(Position src) {
        this.src = src;
    }

    public Position getDest() {
        return dest;
    }

    public void setDest(Position dest) {
        this.dest = dest;
    }

    public ArrayList<TakenPiece> getTaken() {
        return taken;
    }

    public void setTaken(ArrayList<TakenPiece> taken) {
        this.taken = taken;
    }

    public int getMove_num() {
        return move_num;
    }

    public void setMove_num(int move_num) {
        this.move_num = move_num;
    }

    public ConcretePlayer getPlayer() {
        return player;
    }

    public void setPlayer(ConcretePlayer player) {
        this.player = player;
    }
    public int dist(){
        if(src.getCol()==dest.getCol())
            return Math.abs(src.getRow()-dest.getRow());
        if (src.getRow()==dest.getRow())
            return Math.abs(src.getCol()-dest.getCol());
        return 0;
    }

    public String toString() {
        return "(" + src + ", " + dest + ")";
    }


}
