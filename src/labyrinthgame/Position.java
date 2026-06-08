/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */
public class Position
{
    private int x;
    private int y;

    public Position(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }
    
    public Position translate(Direction d)
    {
        return new Position(d.x + x, d.y + y);
    }
    
    
    public boolean samePosition(Position other)
    {
        return this.x == other.x && this.y == other.y;
    }
    
    public int getX()
    {
        return x;
    }
        
    public int getY()
    {
        return y;
    }
}

