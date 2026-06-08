/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */
import java.util.Random;
import javax.swing.ImageIcon;

public class Dragon extends Tile 
{
    private Direction direction;
    
    public Dragon(int x, int y) 
    {
        super(x, y, false, new ImageIcon("src/res/dragon.png").getImage());
        randomDirection();
    }
    
    
    
    public void move()
    {
        this.x += TILE_SIZE * direction.x;
        this.y += TILE_SIZE * direction.y;

    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    public boolean avoidGoal(Dragon dragon)
    {
        if(x == 18 && y == 0)
        {
            this.direction = direction.DOWN;
            return true;
        }
        return false;
    }
    
    // decide dragon's direction
    public void randomDirection()
    {
        Random random = new Random();
        int randomNum = random.nextInt(4);
        
        switch(randomNum)
        {
            case 0: 
                this.direction = Direction.UP; break;
            case 1: 
                this.direction = Direction.RIGHT; break;
            case 2: 
                this.direction = Direction.DOWN; break;
            default: 
                this.direction = Direction.LEFT;
        }
    }
    
    
    
    public Direction getDirection()
    {
        return direction;
    }
    

    
    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }
}





