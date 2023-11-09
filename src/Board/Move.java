package Board;

import Pieces.Piece;

public class Move {
    int oldCol;
    int oldRow;
    int newCol;
    int newRow;

    Piece piece;
    Piece capture;
    public Move(board board,Piece piece, int newCol, int newRow){
        this.oldCol = piece.col;
        this.oldRow = piece.row;
        this.newCol = newCol ;
        this.newRow  = newRow ;

        this.piece = piece;
        this.capture = board.getPieec(newCol, newRow);
    }
}
