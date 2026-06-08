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
import java.awt.geom.Area;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class PlayerVision 
{
    private final int TILE_SIZE = 40;
    private final int BOARD_SIZE = 20;
    private int x; 
    private int y; 
    private Area blackTiles; 
    private Rectangle brightTiles;
    
    
    
    public void invisibleArea(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        
        blackTiles = new Area(new Rectangle(0, 0, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE));
        brightTiles = new Rectangle(x, y, 7 * TILE_SIZE, 7 * TILE_SIZE); 
        
        blackTiles.subtract(new Area(brightTiles));
        
        g2d.setColor(new Color(0, 0, 0,0));
        g2d.fill(blackTiles);
    }

    
    
    public void visibleArea(Position position)
    {
        this.x = TILE_SIZE * (position.getX() - 3); 
        this.y = TILE_SIZE * (position.getY() - 3); 
    }
}
