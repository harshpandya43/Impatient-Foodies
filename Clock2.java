import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Calendar;
import java.awt.Font;
import java.awt.Color;

/**
 * A clock. Displays the time.
 * 
 * @author Michael Berry (mjrb4)
 * @version 06/12/08
 */
public class Clock2 extends Actor
{

    long TimeStart = System.currentTimeMillis();
    public int flag;
    private int counterr = 0,counter_sup=0;
    /**
     * When the clock is added to the world, just update it
     * (same as we do in the act method.)
     */
  
    /**
     * Execute repeatedly.
     */
    public void act() 
    {
       if (flag==1)
       {
        updateTime();
        draw();
       }
       //Greenfoot.delay(600);
       
    }
    
    /**
     * Update the hours, minutes and seconds fields.
     */
    private void updateTime()
    {
        if((System.currentTimeMillis() > TimeStart + 60000))
        {
        counterr++;
        TimeStart = System.currentTimeMillis();
    }
    }
    
    /**
     * Put a 0 before the number if it's less than 10.
     */
    private String getStr(int val)
    {
        String str = null;
        if(val<10) {
            str = "0"+val;
        }
        else {
            str = ""+val;
        }
        return str;
    }
    
    /**
     * Put a reasonable font on the image and draw it to the world.
     */
    void draw()
    {
        GreenfootImage image = new GreenfootImage(120,20);
        image.drawString("Time Taken : "+counterr, 10, 10);
        setImage(image);
    }
    
    void setFlag(int v)
    {
        flag=v;
    }
    void counter_reset()
    {
        counterr=0;
    }
    int return_curr_value()
    {
        return counterr;
    }
}
