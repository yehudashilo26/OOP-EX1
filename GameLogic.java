import java.util.ArrayList;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    PrintsStats stats;
    Stack<Move> moves_history;
    static final int VIKINGS_CHESS_BOARD_SIZE = 11;
    static final boolean FIRST = false;
    static final boolean SECOND = true;
    private int moves;
    private ConcretePlayer first, second;
    private Board board;

    public GameLogic() {
        first = new ConcretePlayer(FIRST);
        second = new ConcretePlayer(SECOND);
        board = new Board(VIKINGS_CHESS_BOARD_SIZE, VIKINGS_CHESS_BOARD_SIZE);
        setUpVikingsChessBoard();
        moves = 0;
        moves_history = new Stack<>();
        stats = new PrintsStats();
    }

    private void setUpVikingsChessBoard() {

        // create and place red pieces
        for (int i = 3; i <= 7; i++) {

            ConcretePiece piece1, piece2, piece3, piece4;

            piece1 = new Pawn(first);
            board.placePiece(piece1, i, 0);
            first.pieces.add(piece1);

            piece2 = new Pawn(first);
            board.placePiece(piece2, 0, i);
            first.pieces.add(piece2);

            piece3 = new Pawn(first);
            board.placePiece(piece3, i, VIKINGS_CHESS_BOARD_SIZE - 1);
            first.pieces.add(piece3);

            piece4 = new Pawn(first);
            board.placePiece(piece4, VIKINGS_CHESS_BOARD_SIZE - 1, i);
            first.pieces.add(piece4);
        }

        for (int[] coordinates : new int[][]{{1, 5}, {5, 1}, {9, 5}, {5, 9}}) {
            ConcretePiece pawn = new Pawn(first);
            board.placePiece(pawn, coordinates[0], coordinates[1]);
            first.pieces.add(pawn);
        }

        // create and place blue pieces
        for (int[] coordinates : new int[][]{{5, 3}, {4, 4}, {5, 4}, {6, 4}, {3, 5}, {4, 5}, {6, 5}, {7, 5}, {4, 6}, {5, 6}, {6, 6}, {5, 7}}) {
            ConcretePiece pawn = new Pawn(second);
            board.placePiece(pawn, coordinates[0], coordinates[1]);
            second.pieces.add(pawn);
        }
        // set king
        ConcretePiece king = new King(second);
        board.placePiece(king, 5, 5);
        second.pieces.add(king);

        int numA=1 , numD=1;
        for(int i=0; i<VIKINGS_CHESS_BOARD_SIZE;i++){
            for(int j=0; j<VIKINGS_CHESS_BOARD_SIZE;j++){
                ConcretePiece temp = (ConcretePiece) board.getPieceAtPosition(board.getPosition(j,i));
                if(temp!=null){
                    if(temp.getOwner().equals(first))
                        temp.setNumber(numA++);
                    if(temp.getOwner().equals(second))
                        temp.setNumber((numD++));
                }
            }
        }
    }

    @Override
    public boolean move(Position a, Position b) {
        ConcretePiece piece = (ConcretePiece) getPieceAtPosition(a);
        ConcretePlayer hisTurn = (ConcretePlayer) piece.getOwner();
        ArrayList<TakenPiece> taken = new ArrayList<>();
        Move current = new Move(a, b, null, hisTurn, moves + 1);
        if ((hisTurn.first && isSecondPlayerTurn()) || (!hisTurn.first && !isSecondPlayerTurn())) {
            if (isLegalMove(a, b)) {
                ConcretePiece movedPiece = board.pickUpPiece(a);
                Position new_pos = board.placePiece(movedPiece, b);
                if (!(movedPiece instanceof King)) {
                    if (isSecondPlayerTurn())
                        taken = take(new_pos, second, first);
                    else
                        taken = take(new_pos, first, second);
                }
                moves++;
                current.setTaken(taken);
                moves_history.push(current);
                piece.moves.add(current);
                piece.dist += current.dist();
                return true;
            }
        }
        return false;
    }


    private ArrayList<TakenPiece> take(Position p, ConcretePlayer takes, ConcretePlayer to_be_taken) {
        ArrayList<TakenPiece> taken = new ArrayList<>();

        Position right = p.getRight();
        Position left = p.getLeft();
        Position up = p.getUp();
        Position down = p.getDown();

        ConcretePiece right_piece = (ConcretePiece) getPieceAtPosition(right);
        ConcretePiece left_piece = (ConcretePiece) getPieceAtPosition(left);
        ConcretePiece up_piece = (ConcretePiece) getPieceAtPosition(up);
        ConcretePiece down_piece = (ConcretePiece) getPieceAtPosition(down);

        ConcretePiece right_right_piece = null;
        ConcretePiece left_left_piece = null;
        ConcretePiece up_up_piece = null;
        ConcretePiece down_down_piece = null;

        if (right_piece != null && right.getRight() != null)
            right_right_piece = (ConcretePiece) getPieceAtPosition(right.getRight());
        if (left_piece != null && left.getLeft() != null)
            left_left_piece = (ConcretePiece) getPieceAtPosition(left.getLeft());
        if (up_piece != null && up.getUp() != null)
            up_up_piece = (ConcretePiece) getPieceAtPosition(up.getUp());
        if (down_piece != null && down.getDown() != null)
            down_down_piece = (ConcretePiece) getPieceAtPosition(down.getDown());


        // take right
        if (board.isInBoard(right) && right_piece != null && right_piece.getOwner().equals(to_be_taken)
                && (right.getRight() == null) ||
                (right_right_piece != null && right_right_piece.getOwner().equals(takes)) && right_piece != null) {
            if (!(right.getRight() != null && right_piece.getOwner().equals(takes))) {
                if (right_piece instanceof Pawn && (right_right_piece instanceof Pawn || right_right_piece == null)) {
                    taken.add(new TakenPiece((ConcretePiece) getPieceAtPosition(right), right));
                    right.setPiece(null);
                    ((Pawn)board.getPieceAtPosition(p)).kill();
                }
            }
        }

        // take left
        if (board.isInBoard(left) && left_piece != null && left_piece.getOwner().equals(to_be_taken)
                && (left.getLeft() == null) ||
                (left_left_piece != null && left_left_piece.getOwner().equals(takes)) && left_piece != null) {
            if (!(left.getLeft() != null && left_piece.getOwner().equals(takes))) {
                if (left_piece instanceof Pawn && (left_left_piece instanceof Pawn || left_left_piece == null)) {
                    taken.add(new TakenPiece((ConcretePiece) getPieceAtPosition(left), left));
                    left.setPiece(null);
                    ((Pawn)board.getPieceAtPosition(p)).kill();
                }
            }
        }


        // take up
        if (board.isInBoard(up) && up_piece != null && up_piece.getOwner().equals(to_be_taken)
                && (up.getUp() == null) ||
                (up_up_piece != null && up_up_piece.getOwner().equals(takes)) && up_piece != null) {
            if (!(up.getUp() != null && up_piece.getOwner().equals(takes))) {
                if (up_piece instanceof Pawn && (up_up_piece instanceof Pawn || up_up_piece == null)) {
                    taken.add(new TakenPiece((ConcretePiece) getPieceAtPosition(up), up));
                    up.setPiece(null);
                    ((Pawn)board.getPieceAtPosition(p)).kill();
                }
            }
        }

        //take down
        if (!(right_right_piece instanceof King)) {
            if (board.isInBoard(down) && down_piece != null && down_piece.getOwner().equals(to_be_taken)
                    && (down.getDown() == null) ||
                    (down_down_piece != null && down_down_piece.getOwner().equals(takes)) && down_piece != null) {
                if (!(down.getDown() != null && down_piece.getOwner().equals(takes))) {
                    if (down_piece instanceof Pawn && (down_down_piece instanceof Pawn || down_down_piece == null)) {
                        taken.add(new TakenPiece((ConcretePiece) getPieceAtPosition(down), down));
                        down.setPiece(null);
                        ((Pawn)board.getPieceAtPosition(p)).kill();
                    }
                }
            }
        }
        return taken;
    }


    private boolean isLegalMove(Position a, Position b) {
        ConcretePiece a_piece = (ConcretePiece) getPieceAtPosition(a);
        if (a == null || b == null || !board.isInBoard(b) || !board.isInBoard(a) || a_piece == null || (ConcretePiece) getPieceAtPosition(b) != null)
            return false;
        if (isSecondPlayerTurn() && a_piece.getOwner().equals(first))
            return false;
        return (a_piece.getType().equals("♔") || !b.isACorner(VIKINGS_CHESS_BOARD_SIZE, VIKINGS_CHESS_BOARD_SIZE))
                && sees(a, b);
    }

    private boolean sees(Position a, Position b) {
        int a_row = a.getRow(), a_col = a.getColumn();
        int b_row = b.getRow(), b_col = b.getColumn();
        boolean coast_is_clear = true;
        coast_is_clear = !(a_col != b_col && a_row != b_row);
        // on the same row
        if (a_row == b_row && a_col != b_col) {
            for (int col = Math.min(a_col, b_col) + 1; col < Math.max(a_col, b_col); col++)
                coast_is_clear &= board.getPosition(a_row, col).getPiece() == null;
        }
        // on the same column
        if (a_col == b_col && a_row != b_row) {
            for (int row = Math.min(a_row, b_row) + 1; row < Math.max(a_row, b_row); row++)
                coast_is_clear &= board.getPosition(row, a_col).getPiece() == null;
        }
        return coast_is_clear;
    }

    @Override
    public Piece getPieceAtPosition(Position position) {
        return board.getPieceAtPosition(position);
    }

    @Override
    public Player getFirstPlayer() {
        return first;
    }

    @Override
    public Player getSecondPlayer() {
        return second;
    }

    @Override
    public boolean isGameFinished() {
        // find king
        for (int row = 0; row < VIKINGS_CHESS_BOARD_SIZE; row++) {
            for (int col = 0; col < VIKINGS_CHESS_BOARD_SIZE; col++) {
                Position current_pos = board.getPosition(row, col);
                if (board.getPieceAtPosition(current_pos) != null) {
                    ConcretePiece current_piece = (ConcretePiece) getPieceAtPosition(current_pos);
                    if (current_piece.getType().equals("♔")) {
                        boolean finish1 = false, finish2 = false;
                        if (isSecondPlayerTurn())
                            finish1 = kingIsTrapped(current_pos);
                        else
                            finish2 = current_pos.isACorner(VIKINGS_CHESS_BOARD_SIZE, VIKINGS_CHESS_BOARD_SIZE);
                        if (finish1) {
                            first.win();
                            stats.printAllStats(first, second,board,VIKINGS_CHESS_BOARD_SIZE);
                        }
                        if (finish2) {
                            second.win();
                            stats.printAllStats(second, first,board,VIKINGS_CHESS_BOARD_SIZE);
                        }
                        return (finish1 || finish2);
                    }
                }
            }
        }
        return false;
    }


    private boolean kingIsTrapped(Position kings_pos) {
        Position right = kings_pos.getRight();
        Position left = kings_pos.getLeft();
        Position up = kings_pos.getUp();
        Position down = kings_pos.getDown();

        ConcretePiece right_piece = (ConcretePiece) getPieceAtPosition(right);
        ConcretePiece left_piece = (ConcretePiece) getPieceAtPosition(left);
        ConcretePiece up_piece = (ConcretePiece) getPieceAtPosition(up);
        ConcretePiece down_piece = (ConcretePiece) getPieceAtPosition(down);

        boolean right_blocks = !board.isInBoard(right) || right_piece != null && right_piece.getOwner() == first;
        boolean left_blocks = !board.isInBoard(left) || left_piece != null && left_piece.getOwner() == first;
        boolean up_blocks = !board.isInBoard(up) || up_piece != null && up_piece.getOwner() == first;
        boolean down_blocks = !board.isInBoard(down) || down_piece != null && down_piece.getOwner() == first;

        return right_blocks && left_blocks && down_blocks && up_blocks;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return moves % 2 == 1;
    }

    @Override
    public void reset() {
//        first = new ConcretePlayer(FIRST);
//        second = new ConcretePlayer(SECOND);
        board = new Board(VIKINGS_CHESS_BOARD_SIZE, VIKINGS_CHESS_BOARD_SIZE);
        setUpVikingsChessBoard();
        moves = 0;
    }

    @Override
    public void undoLastMove() {
        if (moves_history.isEmpty())
            return;
        Move move_to_reverse = moves_history.pop();
        reverseMove(move_to_reverse);
        moves--;
    }

    private void reverseMove(Move move_to_reverse) {
        ConcretePiece piece = board.pickUpPiece(move_to_reverse.getDest());
        board.placePiece(piece, move_to_reverse.getSrc());
        piece.moves.removeLast();
        for (TakenPiece element : move_to_reverse.getTaken()) {
            board.placePiece(element.getTaken(), element.getFrom_pos());
        }
    }

    @Override
    public int getBoardSize() {
        return VIKINGS_CHESS_BOARD_SIZE;
    }
}


