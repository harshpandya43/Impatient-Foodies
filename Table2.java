import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;
/**
 * Write a description of class table here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Table2 extends Actor
{
    private int value = 0;
    private int target = 0;
    private String text,persons="";
    private Clock2 cl;
    int stringLength, tablesize = 0;
    static int RowNo=0;
    FakeTimer f;
    int n;
    final int tbl_n;
    JFrame frame;
    Connection conn = null;
    java.sql.Statement statement=null;
    int updateQuery = 0;
    Table2(int size, Clock2 c,FakeTimer f, int n)
    {
        this.tbl_n=n;
        if(n==1)
        {
            setImage("1.png");
        }
        if(n==2)
        {
            setImage("2.png");
        }
        if(n==3)
        {
            setImage("3.png");
        }
        if(n==4)
        {
            setImage("4.png");
        }
        if(n==5)
        {
            setImage("5.png");
        }
        if(n==6)
        {
            setImage("6.png");
        }
        if(n==7)
        {
            setImage("7.png");
        }
        if(n==8)
        {
            setImage("8.png");
        }
        if(n==9)
        {
            setImage("9.png");
        }if(n==10)
        {
            setImage("10.png");
        }
        if(n==11)
        {
            setImage("11.png");
        }
        if(n==12)
        {
            setImage("12.png");
        }
        tablesize=size;
        cl=c;
        this.f=f;
        
    }
    /**
     * Act - do whatever the table wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this))
        {
            System.out.println(tbl_n);
            updateImage();
     
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
                    catch (Exception e) { /* ignore close errors */ }
                }
             }
      }
    }
    
   public void updateImage()
   {
       Thread t = new Thread(new Runnable()
        { 
        public void run(){
             GreenfootImage image = getImage();
        if(image.getTransparency()==255)
        {
         persons = JOptionPane.showInputDialog(null, "Enter number of people: ", "BBT", 1);
         if(persons!=null){
         tablesize = Integer.parseInt(persons);
         System.out.println("No of seats for "+ this + " is "+ tablesize);
         if(tablesize>=2&&tablesize<=12)
         {
             image.setTransparency(115);
             cl.setFlag(1);
             cl.act();
             f.setFlagcd(1);
             f.setTime(floor.medianWaitingTime[tablesize-2]);
         }
         else
         {
         JOptionPane.showOptionDialog(null, "Try again and enter seats between 1 and 12", "Attention",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, null, "Ok");
         }
       }
    }
     else if(image.getTransparency()==225)
         {   
             tablesize= Display_List.setcalle.GetData(RowNo,2);
             String name= Display_List.setcalle.GetName(RowNo,1);
             if(tablesize>=2&&tablesize<=12)
         {
             Object[] options = {"Yes","No"};
             n = JOptionPane.showOptionDialog(frame,"Do you want to book table for " +name+" for "+tablesize+" person","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
             if(n==0){
             setImage(getN()+".png");
             getImage().setTransparency(140);
             cl.setFlag(1);
             cl.act();
             f.setFlagcd(1);
             f.setTime(floor.medianWaitingTime[tablesize-2]);
             for(int i=0;i<=floor.nooftables-1;i++)
             {
             floor.t[i].hideAvailableTable(i);
            }   
            Display_List.setcalle.Delete(RowNo);
            
         }
        }
         
         else
         {
         JOptionPane.showOptionDialog(null, "Try again and enter seats between 1 and 12", "Attention",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, null, "Ok");
         }
      }
       
        else
        {Object[] options = {"Yes","No"};
           n = JOptionPane.showOptionDialog(frame,"Table Vacated?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
        if(n==0)
        {   
            try{String QuerySt = "INSERT INTO table_details(id,seat_capacity,duration,timestamp,day_flag,shift_flag) VALUES "+"(Null,"+tablesize+","+cl.return_curr_value()+",12314, "+DayOfWeek.DayChecker()+","+DayOfWeek.HourChecker()+")";
                  updateQuery = statement.executeUpdate(QuerySt);
                  if (updateQuery != 0) {
                  System.out.println("table is created successfully and " + updateQuery + " row is inserted.");
                }
                }
                   catch (Exception ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            image.setTransparency(255);
            cl.setFlag(0);
            cl.counter_reset();
            cl.draw();
            cl.act();
            f.setFlagcd(5);
        }
       
    
        }
        }
       });
        t.start();       
    }
    public void showAvailableTable(int Rowno,int i)
   {
       i = i+1;
     RowNo=Rowno;
     GreenfootImage image = this.getImage();
     if(image.getTransparency()==255)
     {
     this.setImage("r"+i+".png"); 
     this.getImage().setTransparency(225);
     }
   }  
    public void hideAvailableTable(int i)
    {
        i = i + 1;
        GreenfootImage image = this.getImage();
        if(image.getTransparency()==225)
        { 
            this.setImage(i+".png"); 
            this.getImage().setTransparency(255);
        }
    }

    int getN()
    {
        return this.tbl_n;
    }
    
     

}