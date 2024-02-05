import java.util.ArrayList;

public abstract class ConcretePiece implements Piece {

    ArrayList<Move> moves;
    private String type;
    private ConcretePlayer owner;
    private int number;
    int dist;

    public ConcretePiece(ConcretePlayer owner, String type) {
        moves = new ArrayList<>();
        this.type = type;
        this.owner = owner;
        dist=0;
    }

    public String getType() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }

    public void setNumber(int number){
        this.number=number;
    }
    public int getNumber(){
        return number;
    }

    public String toString() {
        if(owner.isPlayerOne())
            return "D"+number+":";
        else
            return "A"+number+":";
    }

}
