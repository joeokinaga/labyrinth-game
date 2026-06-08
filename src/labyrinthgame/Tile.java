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


public class Tile
{
    protected final int TILE_SIZE = 40;
    protected int x;
    protected int y;
    protected Image image;
    protected boolean isWall;

    public Tile(int x, int y, boolean isWall, Image image)
    {
        this.x = x;
        this.y = y;
        this.image = image;
        this.isWall = isWall;
    }
    
    
    public void designTile(Graphics g)
    {
        g.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
    }

    
    public Position getPosition()
    {
        return new Position(x / TILE_SIZE, y / TILE_SIZE);
    }

}

