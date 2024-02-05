public class King extends ConcretePiece{
    public King(ConcretePlayer owner) {
        super(owner, "♔");
    }

    @Override
    public String toString() {
        return "K7:";
    }
}
