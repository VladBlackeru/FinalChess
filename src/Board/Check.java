package Board;

import Pieces.Piece;

public class Check {
    board board;

    public Check(board board){
        this.board = board;
    }


    public boolean isKingChecked(Move move){
        Piece king = board.find_king(move.piece.isWhite);
        assert king != null;
        int king_col = king.col;
        int king_row = king.row;
        if(board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            king_col = move.newCol;
            king_row = move.newRow;
        }


        return  hitByRook(move.newCol, move.newRow, king, king_col, king_row, 0, 1) ||    /// up
                hitByRook(move.newCol, move.newRow, king, king_col, king_row, 1, 0) ||
                hitByRook(move.newCol, move.newRow, king, king_col, king_row, 0, -1) ||  // down
                hitByRook(move.newCol, move.newRow, king, king_col, king_row, -1, 0) ||

                hitByBishop(move.newCol, move.newRow, king, king_col, king_row,-1, -1) || //stanga sus
                hitByBishop(move.newCol, move.newRow, king, king_col, king_row, 1, -1) || // dreapta sus
                hitByBishop(move.newCol, move.newRow, king, king_col, king_row, 1, 1) || //dreapa jos
                hitByBishop(move.newCol, move.newRow, king, king_col, king_row, -1, 1) || // stanga jos

                hitByKnight(move.newCol, move.newRow, king, king_col, king_row) ||

                hitByPawn(move.newCol, move.newRow, king, king_col, king_row) ||

                hitByKing(king, king_col, king_row);
    }

    private boolean hitByRook(int col, int row, Piece king, int king_col, int king_row, int col_value, int row_value){
        for(int i = 1; i < 8 ; i++){
            if(king_col + (i * col_value) == col && king_row + (i * row_value) == row) {
                break;
            }

            Piece piece = board.getPieec(king_col + (i * col_value),king_row + (i * row_value));

            if(piece != null && piece != board.selectedPiece){
                if(!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen") ))
                    return true;
                else
                    break;
            }

        }
        return false;
    }


    private boolean hitByBishop(int col, int row, Piece king, int king_col, int king_row, int col_value, int row_value){
        for(int i = 1; i < 8 ; i++){
            if(king_col - (i * col_value) == col && king_row - (i * row_value) == row)
                break;

            Piece piece = board.getPieec(king_col - (i * col_value),king_row - (i * row_value));

            if(piece != null && piece != board.selectedPiece){
                if(!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen") ))
                    return true;
                else
                    break;
            }

        }
        return false;
    }

    private boolean hitByKnight(int col, int row, Piece king, int king_col, int king_row){
        return check_knight(board.getPieec(king_col - 1, king_row - 2), king, col, row) ||
               check_knight(board.getPieec(king_col + 1, king_row - 2), king, col, row) ||
                check_knight(board.getPieec(king_col + 2, king_row - 1), king, col, row) ||
                check_knight(board.getPieec(king_col + 2, king_row + 1), king, col, row) ||
                check_knight(board.getPieec(king_col + 1, king_row + 2), king, col, row) ||
                check_knight(board.getPieec(king_col - 1, king_row + 2), king, col, row) ||
                check_knight(board.getPieec(king_col - 2, king_row + 1), king, col, row) ||
                check_knight(board.getPieec(king_col - 2, king_row - 1), king, col, row);

    }

    private boolean check_knight(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }


    private boolean hitByKing(Piece king, int king_col, int king_row){
        return  check_king(board.getPieec(king_col - 1, king_row - 1), king) ||
                check_king(board.getPieec(king_col + 1, king_row - 1), king) ||
                check_king(board.getPieec(king_col, king_row - 1), king) ||
                check_king(board.getPieec(king_col - 1, king_row), king) ||
                check_king(board.getPieec(king_col + 1, king_row), king) ||
                check_king(board.getPieec(king_col - 1, king_row + 1), king) ||
                check_king(board.getPieec(king_col + 1, king_row + 1), king) ||
                check_king(board.getPieec(king_col, king_row + 1), king);

    }

    private boolean check_king(Piece p, Piece k){
            return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }


    private boolean hitByPawn(int col, int row, Piece king, int king_col, int king_row){
        int colorVal = king.isWhite ? -1  : 1;
        return check_Pawn(board.getPieec(king_col + 1, king_row + colorVal),king, col, row ) ||
                check_Pawn(board.getPieec(king_col - 1, king_row + colorVal),king, col, row );
    }

    private boolean check_Pawn(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) &&  p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }


}
