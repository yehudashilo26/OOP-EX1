public class Pawn extends ConcretePiece{
    private int kils;
    public Pawn(ConcretePlayer owner) {
        super(owner, "♟");
    }
    public void kill(){
        kils++;
    }
    public int getKils(){return kils;}
}
