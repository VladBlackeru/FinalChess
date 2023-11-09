package Pieces;

import Board.board;

import java.awt.image.BufferedImage;

public class Rook extends Piece{

    public Rook(Board.board board, int col, int row, boolean isWhite) {
        super(board);
        this.col = col;
        this.row = row;
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Rook";

        this.sprite = sheet.getSubimage(4 * Scale, isWhite ? 0 : Scale,  Scale,Scale).getScaledInstance(board.tileSize,board.tileSize, BufferedImage.SCALE_SMOOTH);

    }
    public boolean isValidMovement(int col, int row){
        return this.col == col || this.row == row;
    }

    public boolean MoveCollidesWithPiece(int col, int row){
        //left
        if(this.col > col){
            for(int c = this.col - 1; c > col; c--){
                if(board.getPieec(c, this.row ) != null)
                    return true;
            }
        }

        //right
        if(this.col < col){
            for(int c = this.col + 1; c < col; c++){
                if(board.getPieec(c, this.row ) != null)
                    return true;
            }
        }
     //up
        if(this.row > row){
            for(int c = this.row - 1; c > row; c--){
                if(board.getPieec(this.col, c) != null)
                    return true;
            }
        }

        //down
        if(this.row < row){
            for(int c = this.row + 1; c < row; c++){
                if(board.getPieec(this.col, c ) != null)
                    return true;
            }
        }

        return false;
    }

}
