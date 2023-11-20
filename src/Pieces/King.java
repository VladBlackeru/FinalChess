package Pieces;

import Board.Move;
import Board.board;

import java.awt.image.BufferedImage;

public class King extends Piece{

    public King(Board.board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "King";

        this.sprite = sheet.getSubimage(0 * Scale, isWhite ? 0 : Scale,  Scale,Scale).getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);

    }

    public boolean isValidMovement(int col, int row){
        return Math.abs( (col - this.col) * (row - this.row) ) == 1 || (Math.abs(col -this.col) + Math.abs(row - this.row) == 1 || can_castle(col, row));
    }

    private boolean can_castle(int col, int row){
        if(this.row  == row){
            if(col == 6){
                Piece rook = board.getPieec(7, row);
                if(rook != null && rook.isFirstMove && isFirstMove){
                    return board.getPieec(5, row) == null  &&
                            board.getPieec(6, row) == null &&
                            !board.sah.isKingChecked(new Move(board, this, 5, row));
                }
            } else  if(col == 2){
                Piece rook = board.getPieec(0, row);
                if(rook != null && rook.isFirstMove && isFirstMove){
                    return board.getPieec(3, row) == null  &&
                            board.getPieec(2, row) == null &&
                            board.getPieec(1, row) == null &&
                            !board.sah.isKingChecked(new Move(board, this, 3, row));
                }
            }
        }
        return false;
    }

}
