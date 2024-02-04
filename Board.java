public class Board {

    Position[][] board;

    public Board(int rows, int columns){

        // initialize empty board

        board = new Position[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                board[row][col] = new Position(row, col);
            }
        }

        // make neighbors
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (row - 1 >= 0)
                    getPosition(row, col).setUp(getPosition(row - 1, col));
                if (row + 1 < rows)
                    getPosition(row, col).setDown(getPosition(row + 1, col));
                if (col + 1 < columns)
                    getPosition(row, col).setRight(getPosition(row, col + 1));
                if (col - 1 >= 0)
                    getPosition(row, col).setLeft(getPosition(row, col - 1));
            }
        }
    }
    public boolean isInBoard(int row, int col){
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }
    public boolean isInBoard(Position p){
        if(p==null)
            return false;
        return isInBoard(p.getRow(), p.getRow());
    }
    public Position getPosition(int row, int col){
        if (isInBoard(row, col)){
            return board[row][col];
        }
        return null;
    }
    public Piece getPieceAtPosition(Position p){
        if (isInBoard(p)){
            return getPosition(p.getRow(), p.getColumn()).getPiece();
        }
        return null;
    }

    public ConcretePiece pickUpPiece(Position p){
        p = getPosition(p.getRow(), p.getColumn());
        ConcretePiece picked_up = (ConcretePiece) getPieceAtPosition(p);
        p.setPiece(null);
        return picked_up;
    }

    public Position placePiece(ConcretePiece piece, int row, int col){
            Position board_pos = getPosition(row, col);
            if (board_pos != null) {
                board_pos.setPiece(piece);
            }
            return board_pos;
    }

    public Position placePiece(ConcretePiece piece, Position p) {
        return placePiece(piece, p.getRow(), p.getColumn());
    }
}
