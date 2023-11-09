package Pieces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import Board.board;
public class Piece {
    public int col,row;
    public int xPos, yPos;

    public boolean isWhite;
    public String name;
    public int value;
    public boolean isFirstMove = true;

    BufferedImage sheet;
    {
        try{
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("piese.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected int Scale = sheet.getWidth()/6;
    Image sprite;
    board board;
    public Piece(board board){
        this.board = board;
    }

    public void Paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos,null);
    }

    public boolean isValidMovement(int col, int row){
        return true;
    }
    public boolean MoveCollidesWithPiece(int col, int row){
        return false;
    }

}
