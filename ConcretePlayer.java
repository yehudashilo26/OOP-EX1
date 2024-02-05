import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConcretePlayer implements Player {

    ArrayList<ConcretePiece> pieces;
    int wins;
    boolean first;

    public ConcretePlayer(boolean first) {
        this.first = first;
        this.wins = 0;
        pieces = new ArrayList<>();
    }

    @Override
    public boolean isPlayerOne() {
        return first;
    }

    @Override
    public int getWins() {
        return this.wins;
    }

    public void win(){
        wins++;
    }



}