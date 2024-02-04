public class ConcretePlayer implements Player {

    int wins;
    boolean first;

    public ConcretePlayer(boolean first) {
        this.first = first;
        this.wins = 0;
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