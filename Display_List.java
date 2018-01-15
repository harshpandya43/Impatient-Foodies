import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.*;
import java.util.*;
import java.io.*;
/**
 * Calls Waiting List object
 * Sorts Waiting Time
 * Calls teleBookingSuccess 
 * Updates Waiting Time for Customers in waiting list
 * 
 * @author WRK 
 * @version Feb 14,2013
 */
public class Display_List extends Actor
{
    /**
     * 
     */
    java.util.Date today=new java.util.Date();
    static SetCellValues setcalle;
    int seater=0,waitingOrder=0,currentWaitingTime=0,waitingOrderforFour=0,waitingOrderforSix=0,lastCustID=0;
    int flag_disp=0,i;
    ArrayList<Integer> waitingArrayList = new ArrayList<Integer>();
    public void act()
    {
        // Add your action code here.
        
        GreenfootImage imge=new GreenfootImage(200,200);
        
        if(flag_disp==0)
        {
        setImage(imge);
        flag_disp=1;
        setcalle= new SetCellValues();
        }
           waitingArrayList.clear(); 
           TableTimeSorter.sorter();
           i=0;
            for(i=0;i<setcalle.numberofCust();i++)
        {
                       seater=setcalle.GetData(i,2);
                       
                       waitingOrder=(int)Math.floor(i/(floor.nooftables));
                       
                       if(i<(floor.nooftables))
                       {
                            currentWaitingTime = TableTimeSorter.waitingTime[i%(floor.nooftables)];
                            setcalle.SetData(currentWaitingTime,i,4);
                       }
                       else
                       {
                           currentWaitingTime = setcalle.GetData(i-(floor.nooftables),4)+floor.medianWaitingTime[setcalle.GetData(i-(floor.nooftables),2)-2];
                           if(currentWaitingTime>90) /** Upper Constraint for Waiting Time displayed in Waiting List*/
                           {
                               waitingArrayList.add(90);
                           }
                           else
                           {
                               waitingArrayList.add(currentWaitingTime);
                           }
                       }
        }
        Collections.sort(waitingArrayList);
        for(int i=0;i<waitingArrayList.size();i++)
        {
            setcalle.SetData(waitingArrayList.get(i),i+(floor.nooftables),4);   
        }
        try
        {   
            setcalle.teleBookSuccess();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
