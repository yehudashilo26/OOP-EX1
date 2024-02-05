import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Position {
    private Position right, left, up, down;
    private ConcretePiece piece;
    private int row;
    private int col;
    private List<Piece> piecesOn_pos;

    public Position getRight() {
        if (this==null)
            return null;
        return right;
    }

    public void setRight(Position right) {
        this.right = right;
    }

    public Position getLeft() {
        if (this==null)
            return null;
        return left;
    }

    public void setLeft(Position left) {
        this.left = left;
    }

    public Position getUp() {
        if (this==null)
            return null;
        return up;
    }

    public void setUp(Position up) {
        this.up = up;
    }

    public Position getDown() {
        if (this==null)
            return null;
        return down;
    }

    public void setDown(Position down) {
        this.down = down;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public ConcretePiece getPiece() {
        return piece;
    }

    public void setPiece(ConcretePiece piece) {
        this.piece = piece;
    }

    public Position(int row, int col){
        this.row = row;
        this.col = col;
        this.piece = null;
        this.piecesOn_pos = new LinkedList<>();
    }

    public boolean isACorner(int num_of_rows, int num_of_columns){
        int[] p_coordinates = {row, col};
        boolean is_corner = false;
        for (int[] corner : new int[][]{{0, 0}, {0, num_of_columns - 1},
                {num_of_rows - 1, 0}, {num_of_rows - 1, num_of_columns - 1}}) {
            is_corner |= Arrays.equals(p_coordinates, corner);
        }
        return is_corner;
    }
    public void addPiece(Piece piece1){
        piecesOn_pos.add(piece1);
    }
    public int getNumberOfStep(){
        return new HashSet<>(piecesOn_pos).size();
    }

    @Override
    public String toString() {
        return "("+row+","+col+")";
    }
}