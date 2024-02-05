public class TakenPiece {
    private ConcretePiece taken;
    private Position from_pos;

    public TakenPiece(ConcretePiece taken, Position from){
        this.taken = taken;
        this.from_pos = from;
    }

    public ConcretePiece getTaken() {
        return taken;
    }

    public void setTaken(ConcretePiece taken) {
        this.taken = taken;
    }

    public Position getFrom_pos() {
        return from_pos;
    }

    public void setFrom_pos(Position from_pos) {
        this.from_pos = from_pos;
    }
}
