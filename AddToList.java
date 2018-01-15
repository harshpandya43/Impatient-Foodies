import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * Write a description of class AddToList here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AddToList extends Actor
{
    /**
     * Act - do whatever the AddToList wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
     Connection conn = null;
        java.sql.Statement statement=null;
        int updateQuery = 0;
        TableTimeSorter timesorter=new TableTimeSorter();
        
        
        int trail[]=new int[]{1,3,2};
      
    public void act() 
    {
        // Add your action code here.
       if(Greenfoot.mouseClicked(this))
        {
           
            timesorter.sorter();
            
            //Display_List.lpd.updater();
            /* code before 5th Jan,2013
              try
            {
                Class.forName ("com.mysql.jdbc.Driver").newInstance ();
                System.out.println("Class Loaded");
                conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/greenfoot?useUnicode=yes", "greenfoot", "greenfoot");
                System.out.println ("Database connection established");
                statement = conn.createStatement();
                }
            
            
            catch (Exception ex)
            {
                System.out.println("SQLException: " + ex.getMessage());
            }
            
            finally
            {
                if (conn != null)
                {
                   try
                   {
                    //conn.close ();
                    //System.out.println ("Database connection terminated");
                    }
                    catch (Exception e) { /* ignore close errors  }
                }
             }
            String name = JOptionPane.showInputDialog("Enter Name ");
            String number = JOptionPane.showInputDialog("Enter Number ");
            try{String QuerySt = "INSERT INTO waiting_list(id,name,number) VALUES "+"(Null,'"+name+"',"+number+")";
                  updateQuery = statement.executeUpdate(QuerySt);
                  if (updateQuery != 0) {
                  System.out.println("table is created successfully and " + updateQuery + " row is inserted.");
                }
                }
                   catch (Exception ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
         */   
        }
    }    
}
