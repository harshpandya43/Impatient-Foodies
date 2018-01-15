import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class FakeTimer  extends Actor
{
    public int stepsLeft = 0,rest_d;
    public int count_down_flag=0;
    long TimerStart = System.currentTimeMillis();
    public FakeTimer(int startValue)
    {
        //stepsLeft = startValue;

        setImage(new GreenfootImage(120, 20));
        setLocation(1,1);
        rest_d=stepsLeft;
        repaint();
    }
    public void act(){
       if(count_down_flag==1&&(System.currentTimeMillis() > TimerStart + 60000))
       {
           getImage().drawString("Time Left : " + stepsLeft+" mins", 0, 60);
           timerStart();
           TimerStart = System.currentTimeMillis();
       }  
    }
    
    public void timerStart()
    {
        stepsLeft--;
        repaint();
        
        if(stepsLeft <= 0)
        {
            count_down_flag=0;
            //Greenfoot.stop();
        }
    }
    
    public void setTime(int time)
    {
        stepsLeft = time;
    }

    public void repaint()
    {
        
        getImage().clear();
        getImage().drawString("Time Left: " + stepsLeft+" mins", 0, 20);
        
    }
    public void setFlagcd(int v)
   {
    
       if(v==5)
       {count_down_flag=v;
        stepsLeft=rest_d;
        repaint();
       }
       else
       count_down_flag=v;
    }
}