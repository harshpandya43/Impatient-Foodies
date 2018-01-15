import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;
import java.util.*;
import java.io.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * Write a description of class floor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class floor extends World
{
    public static long initialTime = System.currentTimeMillis();
    public static Table2 t[]=new Table2[12];
    public static AddTime PlusFive[] = new AddTime[12];
    public static FakeTimer tableTimeLeft[]=new FakeTimer[12];
    public static final int noofsixseaters=1;
    public static final int nooffourseaters=2;
    public static int medianTimeforFour=0, nooftables;
    public static int medianTimeforSix=0;  
    public static int[] medianWaitingTime = new int[12];
    int NoPersons[] = new int[10];
    String persons[] = new String[10];
    /**
     * Constructor for objects of class floor.
     * 
     */

    public static int DayChecker()
    {
        Calendar ca1 = new GregorianCalendar();
        int DAY_OF_WEEK=ca1.get(Calendar.DAY_OF_WEEK);
        if(DAY_OF_WEEK==1)
        {   
            nooftables=12;
            System.out.println("Sunday Tables = "+12);
            return 0;
        }
        else
        {
            nooftables=10;
            System.out.println("Not a sunday");
            return 1;
        }
    }
    
    public floor()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(835, 550, 1);
        /** Median walues stored in varibles to reduce queries*/
        int sundayCheck = DayChecker();
        medianTimeforFour=seater(4);
        medianTimeforSix=seater(6);
        for(int i=0;i<=10;i++)
        {
            medianWaitingTime[i]=seater(i+1);
        }
        
        logo l=new logo();
        addObject(l,100,500);
        Entrance ent = new Entrance();
        addObject(ent, 300, 550);
                
        Clock2 c1=new Clock2();
        addObject(c1,150,20);
        tableTimeLeft[0]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[0],150,35);
        t[0]=new Table2(4,c1,tableTimeLeft[0],1);
        PlusFive[0] = new AddTime(tableTimeLeft[0], c1);
        addObject(t[0],150,80);
        addObject(PlusFive[0],150,130); //For Adding 5 minutes
        
        Clock2 c2=new Clock2();
        addObject(c2,280,20);
        tableTimeLeft[1]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[1],280,35);
        t[1]=new Table2(4,c2,tableTimeLeft[1],2);
        PlusFive[1] = new AddTime(tableTimeLeft[1], c2);
        addObject(t[1],280,80);
        addObject(PlusFive[1],280,130);
        
        Clock2 c3=new Clock2();
        addObject(c3,410,20);
        tableTimeLeft[2]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[2],410,35);
        t[2]=new Table2(4,c3,tableTimeLeft[2],3);
        PlusFive[2] = new AddTime(tableTimeLeft[2], c3);
        addObject(t[2],410,80);
        addObject(PlusFive[2],410,130); //For Adding 5 minutes
        
        Clock2 c4=new Clock2();
        addObject(c4,540,20);
        tableTimeLeft[3]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[3],540,35);
        t[3]=new Table2(4,c4,tableTimeLeft[3], 4);
        PlusFive[3] = new AddTime(tableTimeLeft[3], c4);
        addObject(t[3],540,80);
        addObject(PlusFive[3],540,130);
        
        Clock2 c5=new Clock2();
        addObject(c5,670,20);
        tableTimeLeft[4]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[4],670,35);
        t[4]=new Table2(4,c5,tableTimeLeft[4], 5);
        PlusFive[4] = new AddTime(tableTimeLeft[4], c5);
        addObject(t[4],670,80);
        addObject(PlusFive[4],670,130);
        
        Clock2 c6=new Clock2();
        addObject(c6,790,180);
        tableTimeLeft[5]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[5],790,200);
        t[5]=new Table2(4,c6,tableTimeLeft[5], 6);
        PlusFive[5] = new AddTime(tableTimeLeft[5], c6);
        addObject(t[5],690,190);
        addObject(PlusFive[5],630,190);
        
        Clock2 c7=new Clock2();
        addObject(c7,790,270);
        tableTimeLeft[6]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[6],790,290);
        t[6]=new Table2(4,c7,tableTimeLeft[6], 7);
        PlusFive[6] = new AddTime(tableTimeLeft[6], c7);
        addObject(t[6],690,280);
        addObject(PlusFive[6],630,280);
        
        Clock2 c8=new Clock2();
        addObject(c8,790,360);
        tableTimeLeft[7]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[7],790,380);
        t[7]=new Table2(4,c8,tableTimeLeft[7], 8);
        PlusFive[7] = new AddTime(tableTimeLeft[7], c8);
        addObject(t[7],690,370);
        addObject(PlusFive[7],630,370);
        
        Clock2 c9=new Clock2();
        addObject(c9,790,450);
        tableTimeLeft[8]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[8],790,470);
        t[8]=new Table2(4,c9,tableTimeLeft[8], 9);
        PlusFive[8] = new AddTime(tableTimeLeft[8], c9);
        addObject(t[8],690,460);
        addObject(PlusFive[8],630,460);
        
        Clock2 c10=new Clock2();
        addObject(c10,65,270);
        tableTimeLeft[9]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[9],65,290);
        t[9]=new Table2(4,c10,tableTimeLeft[9], 10);
        PlusFive[9] = new AddTime(tableTimeLeft[9], c10);
        addObject(t[9],150,280);
        addObject(PlusFive[9],210,280);

    if(sundayCheck == 0)
        {
        Clock2 c11=new Clock2();
        addObject(c11,350,220);
        tableTimeLeft[10]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[10],350,235);
        t[10]=new Table2(4,c11,tableTimeLeft[10],11);
        PlusFive[10] = new AddTime(tableTimeLeft[10], c11);
        addObject(t[10],350,280);
        addObject(PlusFive[10],350,330); //For Adding 5 minutes
        
        Clock2 c12=new Clock2();
        addObject(c12,490,220);
        tableTimeLeft[11]=new FakeTimer(medianTimeforFour);
        addObject(tableTimeLeft[11],490,235);
        t[11]=new Table2(4,c12,tableTimeLeft[11], 12);
        PlusFive[11] = new AddTime(tableTimeLeft[11], c4);
        addObject(t[11],490,280);
        addObject(PlusFive[11],490,330);
      }
      
        Display_List dlp=new Display_List();
        addObject(dlp,500,500);
        Greenfoot.start();
/*        AddToList atl=new AddToList();
        addObject(atl,545,580); */
    }
/** Calculates approximate time from the passed parameter using Median value of Normal Distribution*/    
static public int seater(int seater)
    { 
        int noofvalues = 0; //calculate number of values in database
        ResultSet rs = null;
        int count=0;
        Connection conn = null;
        java.sql.Statement statement=null;
        int updateQuery = 0;
        String name;
        ArrayList<Double> MArray = new ArrayList<Double>();
        int seat,time,timestamp;
            try
            {
                Class.forName ("com.mysql.jdbc.Driver").newInstance ();
                conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/greenfoot?useUnicode=yes", "greenfoot", "greenfoot");
                System.out.println ("Database connection established");
                statement = conn.createStatement();
            }
            catch (Exception ex)
            {
                System.out.println("SQLException: " + ex.getMessage());
            }finally
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
            try{
                String Query = "SELECT * from table_details WHERE `day_flag`='"+DayOfWeek.DayChecker()+"' AND `shift_flag`='"+DayOfWeek.HourChecker()+"'";
                rs = statement.executeQuery(Query);
                while(rs.next()) {
                    name = rs.getString("id");
                    seat = rs.getInt("seat_capacity");
                    timestamp=rs.getInt("timestamp");
                    if(seat==seater) /** Check for values used for Prediction*/
                    {   
                        noofvalues++;
                        MArray.add(rs.getDouble("duration"));
                    }
                }   
            }catch (Exception ex) {
                    System.out.println("SQLException: " + ex.getMessage());
            }
            Collections.sort(MArray);
            double ttime;
            int middle = (int)Math.floor(noofvalues/2);
            if(middle==0)
            {
                time=60; /** This a sample value and is needed from the restaurant owner */
            }
            else
            {
                if(noofvalues%2 == 1)
                {
                    ttime = MArray.get(middle);
                    time = (int)ttime;    
                }
                else
                {
                    ttime = (MArray.get(middle-1) + MArray.get(middle)) / 2.0;
                    time = (int)ttime;    
                }
            }
        return time;
  }
}