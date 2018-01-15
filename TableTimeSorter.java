/**
 * Write a description of class TableTimeSorter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class TableTimeSorter  
{

    static int[] waitingTime=new int[floor.nooftables];
    
    
    TableTimeSorter(){
     System.out.println("Table Time Sorter Loaded");
    }
    int temp;
    static void sorter()
    {
                for(int i=0;i<floor.nooftables;i++)
                {
                    if(floor.tableTimeLeft[i].count_down_flag!=1)
                        {
                            waitingTime[i]=0;
                        }
                    else
                        {
                            waitingTime[i]=floor.tableTimeLeft[i].stepsLeft;
                        }
                    
                }
        Arrays.sort(waitingTime);
    }
}