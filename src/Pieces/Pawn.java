package Pieces;

import Board.board;

import java.awt.image.BufferedImage;

public class Pawn extends Piece{

    public Pawn(Board.board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Pawn";

        this.sprite = sheet.getSubimage(5 * Scale, isWhite ? 0 : Scale,  Scale,Scale).getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);

    }
    public boolean isValidMovement(int col, int row){
        int colorIndex = isWhite? 1 : -1;
    ///1
        if(this.col == col && this.row - colorIndex == row  && board.getPieec(col,row) == null)
            return true;

        //2
        if(isFirstMove && this.col == col && this.row - colorIndex*2 == row  && board.getPieec(col,row + colorIndex) == null)
            return true;

        ///take

        if(col == this.col - 1 && row == this.row - colorIndex && board.getPieec(col, row) != null)
                return true;
        if(col == this.col + 1 && row == this.row - colorIndex && board.getPieec(col, row) != null)
                return true;

        ///en passant left
        if(board.getTileNum(col,row) == board.enPassantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPieec(col, row+colorIndex) != null)
            return true;
        if(board.getTileNum(col,row) == board.enPassantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPieec(col, row+colorIndex) != null)
            return true;


        return false;
    }
}
