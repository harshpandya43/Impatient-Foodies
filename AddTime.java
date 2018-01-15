import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Write a description of class AddTime here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AddTime extends Actor
{
    /**
     * Act - do whatever the AddTime wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    JFrame frame;
    FakeTimer at;
    Clock2 clk;
    public AddTime(FakeTimer at, Clock2 clk)
    {
        this.at = at;   
        this.clk = clk;
    }
    
    public void act() 
    {
     if (Greenfoot.mouseClicked(this)&&clk.flag == 1)
        {   
        Thread t = new Thread(new Runnable()
        {
        public void run(){
            Object[] options = {"Yes","No"};
            int n = JOptionPane.showOptionDialog(frame,"Add 5 minutes?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if(n==0){
            at.stepsLeft = at.stepsLeft+5;
            at.count_down_flag = 1;
            }
        }
       });
       t.start();
        }
    at.repaint();    // Add your action code here.
    }    
}
