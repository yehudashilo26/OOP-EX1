public abstract class ConcretePiece implements Piece {

    private String type;
    private ConcretePlayer owner;

    public ConcretePiece(ConcretePlayer owner, String type) {
        this.type = type;
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public Player getOwner() {
        return owner;
    }
}
