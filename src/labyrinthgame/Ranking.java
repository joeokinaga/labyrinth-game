/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthgame;

/**
 *
 * @author okinagajoe
 */

public class Ranking implements Comparable<Ranking> 
{
    private final String name; 
    private final int level;   

    public Ranking(String name, int level)
    {
        this.name = name;
        this.level = level;
    }

    public String getName()
    {
        return name;
    }

    public int getLevel() 
    {
        return level;
    }

    
    @Override
    public int compareTo(Ranking r) 
    {
        int levelDiff = - Integer.compare(this.level, r.level); 
        if (levelDiff != 0) 
        {
            return levelDiff;
        }
        return this.name.compareTo(r.name); 
    }
}
