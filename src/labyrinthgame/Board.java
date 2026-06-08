/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Board 
{
    private final int TILE_SIZE = 40;
    private final int BOARD_SIZE = 20;
    private Tile[][] board;
    private Dragon dragon;
    private Player player;

    
    public Board(String boardText) throws IOException
    {
        board = new Tile[BOARD_SIZE][BOARD_SIZE];

        BufferedReader br = new BufferedReader(new FileReader(boardText));
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) 
        {
            int x = 0;
            for (char tileType : line.toCharArray()) {
                if (tileType == '#') 
                {
                    Image image = new ImageIcon("src/res/wall.png").getImage();
                    board[y][x] = new Tile(x * TILE_SIZE, y * TILE_SIZE, true, image);
                }
                else 
                {
                    Image image = new ImageIcon("src/res/empty.png").getImage();
                    board[y][x] = new Tile(x * TILE_SIZE, y * TILE_SIZE, false, image);
                }
                x++;
            }
            y++;
        }
    }
    

    


    public void designTile(Graphics g)
    {
        for (Tile[] x : board)
        {
            for(Tile tile : x)
            {
                tile.designTile(g);
            }
        }
    }
    

    public boolean isNextTileWallForDragon(Position position, Direction d) 
    {
        if(position.getX() + d.x == 18 && position.getY() + d.y == 0) return true;
        Tile object = board[position.getY() + d.y][position.getX() + d.x];
        return object.isWall;
    }
    
    public boolean isNextTileWallForPlayer(Position position, Direction d) 
    {
        Tile object = board[position.getY() + d.y][position.getX() + d.x];
        return object.isWall;
    }
    


    public boolean isWall(Position position) 
    {
        Tile object = board[position.getY()][position.getX()];
        return object.isWall;
    }
   
}
