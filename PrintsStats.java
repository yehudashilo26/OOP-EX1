import java.util.Comparator;
import java.util.*;
import java.util.function.Function;


public class PrintsStats {
    private boolean winner;
    public void printAllStats(ConcretePlayer winner, ConcretePlayer loser, Board b1, int size) {
        this.winner=winner.isPlayerOne();
        printPartA(winner,loser);
        printasterisks();
        printPartB(winner,loser);
        printasterisks();
        printPartC(winner,loser);
        printasterisks();
        printPartD(b1,size);
        printasterisks();
    }

    private void printasterisks(){
        for (int i =0;i<75;i++)
            System.out.print("*");
        System.out.println();
    }
    private void printPartA(ConcretePlayer winner, ConcretePlayer loser)
    {
        winner.pieces.sort(byNumOfMoves);
        loser.pieces.sort(byNumOfMoves);
        Iterator<ConcretePiece> itwinner = winner.pieces.iterator();
        while (itwinner.hasNext()) {
            ConcretePiece temp = itwinner.next();
            if (!temp.moves.isEmpty()) {
                System.out.print(temp);
                System.out.print("[");
                Iterator<Move> itpieces = temp.moves.iterator();
                Move m = itpieces.next();
                System.out.print(m.getSrc());
                System.out.print(m.getDest());
                while (itpieces.hasNext())
                    System.out.print(itpieces.next().getDest());
                System.out.println("]");
            }
        }
        Iterator<ConcretePiece> itloser = loser.pieces.iterator();
        while (itloser.hasNext()) {
            ConcretePiece temp = itloser.next();
            if (!temp.moves.isEmpty()) {
                System.out.print(temp);
                System.out.print("[");
                Iterator<Move> itpieces = temp.moves.iterator();
                Move m = itpieces.next();
                System.out.print(m.getSrc());
                System.out.print(m.getDest());
                while (itpieces.hasNext())
                    System.out.print(itpieces.next().getDest());
                System.out.println("]");
            }
        }

    }
    private void printPartB(ConcretePlayer winner, ConcretePlayer loser){
        ArrayList<ConcretePiece> allPiecesWithoutKing =(ArrayList<ConcretePiece>) winner.pieces.clone();
        allPiecesWithoutKing.addAll(loser.pieces);
        // put out the king that can't kill
        for(int i=0;i<allPiecesWithoutKing.size();i++){
            ConcretePiece temp= allPiecesWithoutKing.get(i);
            if(temp.getType().equals("♔"))
                allPiecesWithoutKing.remove(temp);
        }
        allPiecesWithoutKing.sort(bykills);
        Iterator<ConcretePiece> itwinner = allPiecesWithoutKing.iterator();
        while (itwinner.hasNext()) {
            ConcretePiece temp = itwinner.next();
            if (((Pawn)(temp)).getKils()>0) {
                System.out.print(temp);
                System.out.println(((Pawn)temp).getKils()+"kills");
            }
        }
    }
    private void printPartC(ConcretePlayer winner, ConcretePlayer loser){
        ArrayList<ConcretePiece> allPieces =(ArrayList<ConcretePiece>) winner.pieces.clone();
        allPieces.addAll(loser.pieces);
        allPieces.sort(byDist);
        Iterator<ConcretePiece> itwinner = allPieces.iterator();
        while (itwinner.hasNext()) {
            ConcretePiece temp = itwinner.next();
            if (temp.dist>0) {
                System.out.print(temp);
                System.out.println(+temp.dist+"squares");
            }
        }
    }
    private void printPartD(Board b1 ,int size){
        ArrayList<Position> positions =new ArrayList<>();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
            {
                positions.add(b1.getPosition(j,i));
            }
        }
        positions.sort(byPos);
        Iterator<Position> itwinner = positions.iterator();
        while (itwinner.hasNext()) {
            Position temp = itwinner.next();
            if (temp.getNumberOfStep()>1) {
                System.out.print(temp);
                System.out.println(+temp.getNumberOfStep()+"pieces");
            }
        }

    }

    //copmartor to part A
    Comparator<ConcretePiece> byNumOfMoves = (ConcretePiece piece1, ConcretePiece piece2)
            -> {
        int hist = Integer.compare(piece1.moves.size(),piece2.moves.size());
        if(hist!=0)
            return hist;
        return Integer.compare(piece1.getNumber(),piece2.getNumber());
    };
    //comprator to part B
    Comparator<ConcretePiece> bykills =  (ConcretePiece piece1, ConcretePiece piece2) ->{
        int kill = Integer.compare(((Pawn)piece1).getKils(),((Pawn)piece2).getKils());
        if(kill!=0)
            return -kill;
        int byNumber= Integer.compare(piece1.getNumber(),piece2.getNumber());
        if(byNumber!=0)
            return byNumber;
        int byWinner = Boolean.compare(piece1.getOwner().isPlayerOne(),piece2.getOwner().isPlayerOne());
        if(winner)
            return byNumber;
        else
            return -byWinner;
    };
    Comparator<ConcretePiece> byDist =  (ConcretePiece piece1, ConcretePiece piece2) ->{
        int dist = Integer.compare(piece1.dist,piece2.dist);
        if(dist!=0)
            return -dist;
        int byNumber= Integer.compare(piece1.getNumber(),piece2.getNumber());
        if(byNumber!=0)
            return byNumber;
        int byWinner = Boolean.compare(piece1.getOwner().isPlayerOne(),piece2.getOwner().isPlayerOne());
        if(winner)
            return byNumber;
        else
            return -byWinner;
    };
    Comparator<Position> byPos =  (Position pos1, Position pos2) ->{
        int numStep = Integer.compare(pos1.getNumberOfStep(),pos2.getNumberOfStep());
        if(numStep!=0)
            return -numStep;
        int byX= Integer.compare(pos1.getRow(),pos2.getRow());
        if(byX!=0)
            return byX;
        return Integer.compare(pos1.getCol(),pos2.getCol());
    };

}
