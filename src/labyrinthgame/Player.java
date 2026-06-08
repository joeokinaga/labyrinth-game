/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */
import javax.swing.ImageIcon;


public class Player extends Tile 
{
    public Player() 
    {
        super(40, 720, false, new ImageIcon("src/res/player.png").getImage());
    }
    
    
    
    public void move(Direction d)
    {
        this.x += TILE_SIZE * d.x;
        this.y += TILE_SIZE * d.y;
    }
    
    
    
    public boolean isDead(Dragon dragon)
    {
        Position playerPosition = this.getPosition();
        Position dragonPosiiton = dragon.getPosition();
        
        if(playerPosition.samePosition(dragonPosiiton) || playerPosition.translate(Direction.UP).samePosition(dragonPosiiton) ||
           playerPosition.translate(Direction.RIGHT).samePosition(dragonPosiiton) || playerPosition.translate(Direction.DOWN).samePosition(dragonPosiiton) || playerPosition.translate(Direction.LEFT).samePosition(dragonPosiiton))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
