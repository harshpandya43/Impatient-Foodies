import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Defined functions to return 1 or 0 depending on the timeslot and day of the week(Weekend/Weekday)
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DayOfWeek {
    /** HourChecker returns 1 is the shift is morning and 0 otherwise
     * Uses SimpleDateFormat
     */
    public static int HourChecker()
    {
        DateFormat dateFormat = new SimpleDateFormat("kk");
        Date date = new Date();
        String formattedDateTime = dateFormat.format(date);
        int CurrentHour = Integer.parseInt(formattedDateTime);
                
        if(CurrentHour>12&&CurrentHour<16)
        {
            System.out.println("Morning Shift");
            return 1;
        }
        else
        {
            System.out.println("Evening Shift");
            return 0;
        }
    }
    /**
     * DayChecker returns 1 if the day is a weekday and 0 if it is a weekend
     * Uses Calender Class
     */
    public static int DayChecker()
    {
        Calendar ca1 = new GregorianCalendar();
        int DAY_OF_WEEK=ca1.get(Calendar.DAY_OF_WEEK);
        if(DAY_OF_WEEK==1||DAY_OF_WEEK==7)
        {   
            System.out.println("Weekend");
            return 0;
        }
        else
        {
            System.out.println("Weekday");
            return 1;
        }
    }
        
        
}