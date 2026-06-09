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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel
{
    
    private final int TILE_SIZE = 40;
    private final JPanel screen = this;
    private final Database database; 
    private Player player;
    private Dragon dragon;
    private Board board;
    private int currLevel = 1;
    private PlayerVision playerVision;
    private String playerName;
    private Timer dragonMove;
    private int dragonMoveSpeed;
    private Timer frameTimer;
    private Timer timer;
    private JFrame frame;
    private Direction direction;
    


    public Game() throws SQLException
    {
        playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);        
        if (playerName == null || playerName.trim().isEmpty()) 
        {
            playerName = "Player";
        }
        
        frame = new JFrame("Labyrinth - " + playerName);
  
        
        startGame(currLevel);

        this.addKeyListener(new CharacterMovement());
        frameTimer = new Timer(1, new NewFrameListener());
        frameTimer.start();
        this.dragonMoveSpeed = 900;
        dragonMove = new Timer(dragonMoveSpeed, new dragonMoveListener());
        dragonMove.start();
        database = new Database(); 
    }
    
    


    

    
    public void startGame(int currLevel)
    {
        
        frame = new JFrame("Labyrinth - " + playerName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            board = new Board("src/res/levels/level" + currLevel + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        this.player = new Player();
        this.playerVision = new PlayerVision();
        playerVision.visibleArea(player.getPosition());
        
        do
        {
            Random random = new Random();
            int x = random.nextInt(18);
            int y = random.nextInt(18);
            dragon = new Dragon(TILE_SIZE * x, TILE_SIZE * y);
            
        } while(board.isWall(dragon.getPosition()) || player.isDead(dragon));
        
    }
    

    public boolean isGoal()
    {
        Position playerPosition = player.getPosition();
        return playerPosition.getX() == 18 && playerPosition.getY() == 0;
    }


    
    @Override
    protected void paintComponent(Graphics g) 
    {
        board.designTile(g);
        player.designTile(g);
        dragon.designTile(g);
        playerVision.invisibleArea(g);

    }

    
    class dragonMoveListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            if(board.isNextTileWallForDragon(dragon.getPosition(), dragon.getDirection()))
            {
                do
                {
                    dragon.randomDirection();
                }while(board.isNextTileWallForDragon(dragon.getPosition(),dragon.getDirection()));
            }
            
            dragon.move();
            if(dragon.getX() == 0 && dragon.getY() == 720)
            {
                dragon.move();
            }
        }
    }
    

    class NewFrameListener implements ActionListener 
    {

        private LabyrinthGUI gui;

        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            repaint();
            if(player.isDead(dragon))
            {
                database.insertData(playerName, currLevel); 
                int option = JOptionPane.showConfirmDialog(screen, "You couldn't escape... Start again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.OK_OPTION) 
                {
                    currLevel = 1;
                    startGame(currLevel);
                }
                else System.exit(0);
                if (option == JOptionPane.YES_OPTION)
                {
                    currLevel = 1;
                    startGame(currLevel);
                } 
                else 
                {
                    System.exit(0);
                }
            }
            else if(isGoal())
            {
                currLevel ++;
                dragonMove.stop(); 

                if(currLevel > 10)
                {
                    currLevel--;
                    JOptionPane.showMessageDialog(screen, "You escaped. You won!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    database.insertData(playerName, currLevel);
                    int option = JOptionPane.showConfirmDialog(screen, "Play again?" , "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                    if (option == JOptionPane.OK_OPTION)
                    {
                        currLevel = 1;
                        startGame(currLevel);
                        dragonMoveSpeed = 900 - (85 * currLevel);
                        dragonMove = new Timer(dragonMoveSpeed, new dragonMoveListener());
                        dragonMove.start();
                        startGame(currLevel);
                    }
                    else System.exit(0);
                }
                else 
                {
                    dragonMoveSpeed = 900 - (85 * currLevel);
                    dragonMove = new Timer(dragonMoveSpeed, new dragonMoveListener());
                    dragonMove.start();
                    startGame(currLevel);
                }
            }
            repaint();
        }
    }
    

    
    class CharacterMovement extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent key) 
        {
            int kc = key.getKeyCode();
            Position position = player.getPosition();
            switch (kc)
            {
                case KeyEvent.VK_W: 
                {
                    if (!board.isNextTileWallForPlayer(position, Direction.UP))
                    {
                        player.move(Direction.UP);
                    }
                    break;
                }
                case KeyEvent.VK_D:
                {
                    if (!board.isNextTileWallForPlayer(position, Direction.RIGHT))
                    {
                        player.move(Direction.RIGHT);
                    }
                    break;
                }
                case KeyEvent.VK_S: 
                {
                    if (!board.isNextTileWallForPlayer(position, Direction.DOWN)) 
                    {
                        player.move(Direction.DOWN);
                    }
                    break;
                }
                case KeyEvent.VK_A: 
                {
                    if (!board.isNextTileWallForPlayer(position, Direction.LEFT)) 
                    {
                        player.move(Direction.LEFT);
                    }
                    break;
                }
            }
            
            playerVision.visibleArea(player.getPosition());
           
        }
    }
    
    public Database getDatabase()
    {
        return database;
    }
    
    public int getLevel()
    {
        return currLevel;
    }
    
    public void setLevel(int level)
    {
        this.currLevel = level;
    }
}