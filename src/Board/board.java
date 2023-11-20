package Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Pieces.*;

public class board extends JPanel {
    public int tileSize = 85;
    int col = 8;
    int row = 8;
    ArrayList<Piece> pieceList = new ArrayList<>();
    public Piece selectedPiece;
    public int enPassantTile = -1;

    public Check sah =  new Check(this);

    Input input = new Input(this);

    public Piece getPieec(int col, int row){
        for(Piece piece : pieceList)
            if(piece.col == col && piece.row == row)
                return piece;
        return null;
    }

    public void makeMove(Move move){

        if(move.piece.name.equals("Pawn")){
            movePawn(move);
        }else if(move.piece.name.equals("King")) {
            move_King(move);
        }
            move.piece.col = move.newCol;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newCol * tileSize;
            move.piece.yPos = move.newRow * tileSize;
            move.piece.isFirstMove = false;

            capture(move.capture);

    }

    private void move_King(Move move){
            if(Math.abs(move.piece.col - move.newCol) == 2){
                Piece rook;
                if(move.piece.col < move.newCol){
                    rook = getPieec(7, move.piece.row);
                    rook.col = 5;
                }else{
                    rook = getPieec(0, move.piece.row);
                    rook.col = 3;
                }

                rook.xPos = rook.col * tileSize;
            }
    }

    private void movePawn(Move move) {
        /// enpassant
        int colorIndex = move.piece.isWhite ? 1: -1;

        if(getTileNum(move.newCol, move.newRow) == enPassantTile){
            move.capture = getPieec(move.newCol,move.newRow + colorIndex);
        }
        if(Math.abs(move.piece.row - move.newRow) == 2){
            enPassantTile = getTileNum(move.newCol, move.newRow + colorIndex);
        }else{
            enPassantTile = -1;
        }

        ///promote
        colorIndex = move.piece.isWhite? 0: 7;
        if(move.newRow == colorIndex) {
            promotePawn(move);
        }

    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece){
        pieceList.remove(piece);
    }


    public boolean isValidMove(Move move){

        if(sameTeam(move.piece, move.capture)){
            return false;
        }

        if(!move.piece.isValidMovement(move.newCol, move.newRow))
            return false;

        if(move.piece.MoveCollidesWithPiece(move.newCol, move.newRow)){
            return false;
        }

        if(sah.isKingChecked(move)){
            return false;
        }

        return true;
    }

    public boolean sameTeam(Piece p1, Piece p2){
        if (p1 == null || p2 == null)
            return false;
        return p1.isWhite == p2.isWhite;
    }

    Piece find_king(boolean isWhite){
        for(Piece piece : pieceList){
            if(isWhite ==  piece.isWhite && piece.name.equals("King")){
                return piece;
            }
        }
        return null;
    }


    public int getTileNum(int col, int row){
        return row * this.row + col;
    }

    public void AddPiece(){
        pieceList.add(new Rook(this, 0, 0, false));
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Queen(this, 3, 0, false));
        pieceList.add(new King(this, 4, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Rook(this, 7, 0, false));
        pieceList.add(new Pawn(this, 0, 1, false));
        pieceList.add(new Pawn(this, 1, 1, false));
        pieceList.add(new Pawn(this, 2, 1, false));
        pieceList.add(new Pawn(this, 3, 1, false));
        pieceList.add(new Pawn(this, 4, 1, false));
        pieceList.add(new Pawn(this, 5, 1, false));
        pieceList.add(new Pawn(this, 6, 1, false));
        pieceList.add(new Pawn(this, 7, 1, false));


        pieceList.add(new Rook(this, 0, 7, true));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        pieceList.add(new Rook(this, 7, 7, true));
        pieceList.add(new Pawn(this, 0, 6, true));
        pieceList.add(new Pawn(this, 1, 6, true));
        pieceList.add(new Pawn(this, 2, 6, true));
        pieceList.add(new Pawn(this, 3, 6, true));
        pieceList.add(new Pawn(this, 4, 6, true));
        pieceList.add(new Pawn(this, 5, 6, true));
        pieceList.add(new Pawn(this, 6, 6, true));
        pieceList.add(new Pawn(this, 7, 6, true));

    }

    public board(){
        this.setPreferredSize(new Dimension(col *tileSize, row * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        AddPiece();
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        ///afis board
        for(int i = 0; i < row;i++)
            for(int j = 0; j < col; j++) {
                g2d.setColor((i + j) % 2 == 0 ? Color.WHITE : new Color(56, 4, 4));
                g2d.fillRect(j * tileSize,i * tileSize, tileSize, tileSize);
            }
        ///highlight possible move
        if(selectedPiece != null) {
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++) {
                    if (isValidMove(new Move(this, selectedPiece, j, i))) {

                        g2d.setColor(new Color(138, 231, 145));
                        g2d.fillRect(j * tileSize , i * tileSize , tileSize , tileSize );
                    }
                }
        }
        /// paint the fokin pisis
        for(Piece piece : pieceList)
            piece.Paint(g2d);
    }

}
