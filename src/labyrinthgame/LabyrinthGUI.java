/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;


public class LabyrinthGUI 
{
    
    private final int TILE_SIZE = 40;
    private Game game;
    private String playerName;
    private JMenu menu;
    private JFrame rankingFrame;
    private JMenuBar menuBar;
    private JFrame frame;
    private Database database;


    
    

    public LabyrinthGUI() 
    {
        frame = new JFrame("Labyrinth Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            game = new Game();
            database = game.getDatabase(); 
            updaterankingFrame();      
        } catch (SQLException ex) {
            Logger.getLogger(LabyrinthGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menu = new JMenu("Menu");

        JMenuItem highscores = new JMenuItem("Highscores");
        highscores.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                updaterankingFrame(); 
                rankingFrame.setVisible(true);
            }
        });
        menu.add(highscores);

        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                game.setLevel(1);
                game.startGame(game.getLevel());
            }
        });
        menu.add(restart);



        menuBar.add(menu);

        game.setFocusable(true);
        frame.getContentPane().add(game, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(TILE_SIZE * 20, TILE_SIZE * 21));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    

    private void updaterankingFrame(){ 
        try {
            rankingFrame = new JFrame("Highscores");
            JTable table = new JTable(database.getDataMatrix(),database.getColumnNamesArray());
            table.setEnabled(false);
            table.setRowHeight(50);
            JScrollPane sp = new JScrollPane(table);
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(40);
            columnModel.getColumn(1).setPreferredWidth(200);
            columnModel.getColumn(2).setPreferredWidth(100);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            columnModel.getColumn(0).setCellRenderer(cellRenderer);
            columnModel.getColumn(1).setCellRenderer(cellRenderer);
            columnModel.getColumn(2).setCellRenderer(cellRenderer);
            rankingFrame.add(sp);
            rankingFrame.setSize(new Dimension(600, 400));
            rankingFrame.setLocationRelativeTo(null);
        } catch (SQLException ex) {
            Logger.getLogger(LabyrinthGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

