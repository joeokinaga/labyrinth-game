/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database
{
    private final int TOP_TEN = 10; 
    private Statement st;
    private PreparedStatement insertStatement;
    private PreparedStatement deleteStatement;
    private Connection connection;


    public Database() throws SQLException 
    {    
        String dbURL = "jdbc:mysql://localhost:3306/Protech3";
        try {
            connection = DriverManager.getConnection(dbURL, "root", "Jookinaga0921");
            st = connection.createStatement();
        } catch (SQLException ex) {
            throw new SQLException("Failed to connect to the database: " + ex.getMessage());
        }
        initTable();
        prepareStatements();
    }


    
    private void initTable() throws SQLException
    {
        st.execute("CREATE TABLE IF NOT EXISTS labyrinth_ranking (" +
                     "NAME VARCHAR(50) NOT NULL, " +
                     "LEVEL INT NOT NULL)");
    }


    
    private void prepareStatements() throws SQLException 
    {
        String insertQuery = "INSERT INTO labyrinth_ranking (NAME, LEVEL) VALUES (?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);

        String deleteQuery = "DELETE FROM labyrinth_ranking WHERE LEVEL = ?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }


    public ArrayList<Ranking> getHighScores() throws SQLException 
    {
        String query = "SELECT * FROM labyrinth_ranking ORDER BY LEVEL DESC LIMIT " + TOP_TEN;
        ArrayList<Ranking> highScores = new ArrayList<>();
        ResultSet results = st.executeQuery(query);
        while (results.next()) 
        {
            String name = results.getString("NAME");
            int level = results.getInt("LEVEL");
            highScores.add(new Ranking(name, level));
        }
        return highScores;
    }


    
    public void insertData(String name, int level) 
    {
        try {
            ArrayList<Ranking> highScores = getHighScores();
            if (highScores.size() < TOP_TEN) {
                insertScore(name, level);
            } else {
                int leastLevel = highScores.get(highScores.size() - 1).getLevel();
                if (level > leastLevel) {
                    deleteScores(leastLevel); 
                    insertScore(name, level); 
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void insertScore(String name, int level)
    {
        try {
            insertStatement.setString(1, name);
            insertStatement.setInt(2, level);
            insertStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void deleteScores(int level) throws SQLException
    {
        deleteStatement.setInt(1, level);
        deleteStatement.executeUpdate();
    }


    
    
    public String[][] getDataMatrix () throws SQLException
    {
        String[][] columnNames = new String[10][4];
        ArrayList<Ranking> highscores = getHighScores();
        int cnt = 0;
        for(Ranking hs : highscores)
        {
            columnNames[cnt][0] = String.valueOf(cnt+1);
            columnNames[cnt][1] = hs.getName();
            columnNames[cnt][2] = String.valueOf(hs.getLevel());
            cnt++;
        }
        for(;cnt < 10; cnt++)
        {
            columnNames[cnt][0] = String.valueOf(cnt+1);
            columnNames[cnt][1] = "";
            columnNames[cnt][2] = "";
            columnNames[cnt][3] = "";
        }
        return columnNames;
    }
    
    public String[] getColumnNamesArray ()
    {
        String[] columnNames = {"rank", "Name", "clear board"};
        return columnNames;
    }
}
