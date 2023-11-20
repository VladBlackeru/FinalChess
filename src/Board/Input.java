package Board;

import Pieces.Piece;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input extends MouseAdapter {
/*
    @Override
    public void mouseClicked(MouseEvent e) {

    }*/
    private boolean turn  = true;
    board board;
    public Input(board board){
        this.board = board;
    }


    @Override
    public void mousePressed(MouseEvent e) {
            int col = e.getX() / board.tileSize;
            int row = e.getY() / board.tileSize;
            Piece pieceXY = board.getPieec(col, row);
            if(pieceXY != null && pieceXY.isWhite == turn){
                board.selectedPiece = pieceXY;
            }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(board.selectedPiece != null){
            board.selectedPiece.xPos = e.getX() - board.tileSize / 2;
            board.selectedPiece.yPos = e.getY() - board.tileSize / 2;
            board.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int col = e.getX() / board.tileSize;
        int row = e.getY() / board.tileSize;
        if(board.selectedPiece != null){
            Move move =  new Move(board, board.selectedPiece, col, row);

            if(board.isValidMove(move)){
                board.makeMove(move);
                if(turn == true)
                    turn = false;
                else
                    turn = true;
            }else{
                board.selectedPiece.xPos = board.selectedPiece.col *  board.tileSize;
                board.selectedPiece.yPos = board.selectedPiece.row *  board.tileSize;
            }

        }
        board.selectedPiece = null;
        board.repaint();
    }





}
