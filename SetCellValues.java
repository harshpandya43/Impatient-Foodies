import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.*;
import javax.swing.text.*;
import java.awt.ActiveEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetCellValues extends JPanel implements ActionListener
{
  public static JTable table;
  public static JTable teletable;
  BbtSms smsmodule=new BbtSms();
  /** variable required for db conn */
    ResultSet rs = null;
    Connection conn = null;
    java.sql.Statement statement=null;
    int updateQuery = 0;
  /** db conn variable end*/
    int total_d=0;
    int bookFlag=0;
    int bookcount=0;
    /**radio button group */
    ButtonGroup bookCustomer = new ButtonGroup(); 
    int selectedRow;
    int prevrow=-1;
    /** time conversion from 24 Hr to 12hr nd vice versa*/
    SimpleDateFormat displayFormat = new SimpleDateFormat("EEE,MMMMM d,yyyy h:mm a");
    SimpleDateFormat parseFormat = new SimpleDateFormat("EEE,MMMMM d,yyyy h:mm a");
    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar = Calendar.getInstance();
    Calendar c2=Calendar.getInstance();
    Date selectdate=calendar.getTime();
    Date CurrDate;
    JTextField Customername,Tele_Customername;
    String[] Tele_stateStrings;
    JFormattedTextField wait; 
    JFormattedTextField ID,worder,Tele_ID,PhoneNo,Tele_PhoneNo;
    JComboBox Daylist;
    Date datenow;
    JFrame frame = new JFrame("B Bhagat Tarachand : Waiting List ");  
    JPanel telebookingpanel = new JPanel();  
    JPanel newwindow=new JPanel();
    JFrame telebookingframe = new JFrame("Telebooking");
    JSpinner stateSpinner,spinner,Tele_stateSpinner; JSpinner timespinner= new JSpinner();  
    SpinnerDateModel timemodel = new SpinnerDateModel();
    boolean addressSet = false;
    final static int GAP = 10;
    public SetCellValues setcell;
    int count=0;
    int cnt=0;
    int[] waitingorder=new int[2]; /** waiting order array. size depends on no of types of seaters*/
    int i, lowestMedianValue = 6;
    int TeleMedians[] = new int[(floor.nooftables)-1];
    Date bookedTime=null, fullTimeSlot = null;
    /** col and row titles for waiting list */
        Object data[][];
        String col[] = {"ID","Customer Name","Seats","Phone no.","Wait time",""}; 
        DefaultTableModel model = new DefaultTableModel(data, col){

            @Override
            public boolean isCellEditable(int data, int col) {
                if(col==0 || col==4)
                return false; 
                else return true;
            }
        };
    
    /** col and row titles for telebooking*/
        Object teledata[][];
        String tcol[] = {"ID","Customer Name","Seats","Phone no.","Time"}; 
        DefaultTableModel tmodel = new DefaultTableModel(teledata, tcol){

            @Override
            public boolean isCellEditable(int data, int col) {
                if(col==0 || col==4)
                return false; 
                else return true;
            }
        };
    public final static long SECOND_MILLIS = 1000;
    public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
    public final static long HOUR_MILLIS = MINUTE_MILLIS*60;
    public final static long DAY_MILLIS = HOUR_MILLIS*24;
    public int availableTimeSlot = 0;
    private Table2 tableobj;
    private floor flobj;
    GraphicsConfiguration gc = frame.getGraphicsConfiguration();  
    Rectangle bounds = gc.getBounds();
    JButton button;
    
  public static void main(String[] args) {
  new SetCellValues();
  }

  public SetCellValues(){
  
    JPanel panel = new JPanel();
    JPanel telepanel = new JPanel();
    table = new JTable(model) {
      public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        repaint();
      }
    };
  teletable=new JTable(tmodel);      /**table model for telebooking*/  
/** Formatting for Waiting List Table */  
  JTableHeader header = table.getTableHeader();
  JTableHeader theader = teletable.getTableHeader();
  setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
  table.setFont(new Font("Sans Serif", Font.PLAIN, 12));
  table.setRowHeight(table.getRowHeight()+4);
  teletable.setFont(new Font("Sans Serif", Font.PLAIN, 12));
  teletable.setRowHeight(teletable.getRowHeight()+3);
  TableColumn column = null;
  /** Setting Default Width for Waiting List Columns*/
for (int i = 0; i < 5; i++) {
    column = table.getColumnModel().getColumn(i);
    if(i==0)
    column.setPreferredWidth(25);
     if(i==1)
    column.setPreferredWidth(125);
     if(i==2)
    column.setPreferredWidth(40);
     if(i==3)
    column.setPreferredWidth(90);
     if(i==4)
    column.setPreferredWidth(65);
     if(i==5)
    column.setPreferredWidth(1);
   
}
/** Seting renderer for Book column. Adding Checkbox in Book Column*/    
    TableColumn tc = table.getColumnModel().getColumn(5);     
    tc.setCellEditor(new RadioButtonEditor(new JCheckBox()));     
    tc.setCellRenderer(new RadioButtonRenderer());
    tc.setPreferredWidth(1);
/** Setting Default Width for telebooking Columns*/    
TableColumn tcolumn=null;
for (int i = 0; i < 5; i++) {
   tcolumn=teletable.getColumnModel().getColumn(i);    
    if(i==0)
        tcolumn.setPreferredWidth(20);
    if(i==1)
        tcolumn.setPreferredWidth(125);
    if(i==2)
        tcolumn.setPreferredWidth(35);
    if(i==3)
        tcolumn.setPreferredWidth(70);
    if (i==4)
        tcolumn.setPreferredWidth(160); 
}
    /** leftHalf is the panel name of the entry fields for the user to enter the values*/
        JPanel leftHalf = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                                     pref.height);
            }
        };
        /**telebook is the panel name of the entry fields for the user to enter the values*/
        JPanel telebook = new JPanel() {
            //Don't allow us to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE,
                                     pref.height);
                }
            };
        leftHalf.setLayout(new BoxLayout(leftHalf,
                                         BoxLayout.PAGE_AXIS));
        leftHalf.add(createEntryFields());
        leftHalf.add(createButtons());
        leftHalf.add(createModificationButtons());
        leftHalf.add(telebookingbuttons());
        
        telebook.setLayout(new BoxLayout(telebook,
                                         BoxLayout.PAGE_AXIS));
        telebook.add(teleModificationButtons());
        
        JScrollPane pane = new JScrollPane(table);
        JScrollPane telepane = new JScrollPane(teletable);
        
        panel.add(pane);
        panel.add(leftHalf);
        telepanel.add(telepane);
        telepanel.add(telebook);
        telepanel.add(telebookingbuttons());
        /**tabbed panes */
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Waiting List", null,
                          panel,
                          "Waiting List"); //tooltip text
        tabbedPane.addTab("Tele-Booking", null,
                          telepanel,
                          "Tele-Booking"); //tooltip text
    /**Adding all components to frame*/ 
      frame.add(tabbedPane,BorderLayout.CENTER);
      frame.setSize(500,740);
      frame.setLocation((int)(bounds.width - (bounds.width/3)), (int)(bounds.height/12));
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      //refreshTeleDatabase();
      //refreshDatabase();
      repopulateJTable();
}
/** Action Listener for Book Radio Button*/
    ActionListener bookTableActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
        selectedRow=Integer.parseInt(aButton.getText())-1;
        System.out.println("Selected: " + aButton.getText());
            for(int i=0;i<=floor.nooftables-1;i++)
              {
                floor.t[i].showAvailableTable(selectedRow,i);                           /**show available table**/
              }
              
            if(prevrow==selectedRow)                                                   /**for tick untick**/
            {   
               cnt++;
               if(cnt%2!=0)
                {
                  for(int i=0;i<=floor.nooftables-1;i++)
                  {
                    floor.t[i].hideAvailableTable(i);                          /**blue color**/
                  }
                  bookCustomer.clearSelection();
                  table.repaint();
               }
           }
           else
           {
              cnt=0;
            } 
           prevrow=selectedRow;                               /**selected row index**/
      }
    };
 
   protected JComponent createButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        button = new JButton("Add Customer");
        button.addActionListener(this);
        panel.add(button);
        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                                                GAP-5, GAP-5));
        return panel;
        
    }

    protected JComponent createModificationButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        button = new JButton("SMS");
        button.addActionListener(this);
        panel.add(button);
        button = new JButton("Delete");
        button.addActionListener(this);
        button.setActionCommand("delete");
        panel.add(button);
        button = new JButton("Clear");
        button.addActionListener(this);
        panel.add(button);
        JButton buttonup = new JButton("Move UP");
        buttonup.addActionListener(this);
        panel.add(buttonup);
        JButton buttondown = new JButton("Move Down");
        buttondown.addActionListener(this);
        panel.add(buttondown);
        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                                                GAP-5, GAP-5));
        return panel;
    }
    
    protected JComponent telebookingbuttons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton button = new JButton("Tele-booking");
        button.addActionListener(this);
        panel.add(button);
         panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                                                GAP-5, GAP-5));
        return panel;
        
    }
    
    protected JComponent teleModificationButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JButton up = new JButton("Shift Up");
        up.addActionListener(this);
        panel.add(up);
        JButton down = new JButton("Shift Down");
        down.addActionListener(this);
        panel.add(down);
        JButton Remove = new JButton("Remove");
        Remove.addActionListener(this);
        panel.add(Remove);
        //Match the SpringLayout's gap, subtracting 5 to make
        //up for the default gap FlowLayout provides.
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0,
                                                GAP-5, GAP-5));
        return panel;
    }
    protected JComponent createTelebookingFields(){
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
            "Customer Name: ",
            "Seater: ",
            "Phone no.: ",
            "Time:",
            "Select day:"
        };

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;
        /** Name entry field */
            Tele_Customername  = new JTextField();
            Tele_Customername.setColumns(20);
            fields[fieldNum++] = Tele_Customername;
        /** Seater entry field */
            Tele_stateStrings = getStateStrings();
            Tele_stateSpinner = new JSpinner(new SpinnerListModel(Tele_stateStrings));
            Tele_stateSpinner.setValue("4");
            getTextField(Tele_stateSpinner).setEditable(false); /** makes seater non editable*/
            fields[fieldNum++] = Tele_stateSpinner;
        /** Phone NO entry field */
            Tele_PhoneNo = new JFormattedTextField(
            createFormatter("##########"));
            fields[fieldNum++] = Tele_PhoneNo;
        /** time spinner entry field */      
            timemodel.setCalendarField(Calendar.MINUTE);
            timespinner.setModel(timemodel);
            timespinner.setEditor(new JSpinner.DateEditor(timespinner, "h:mm a"));
            datenow=(Date)timespinner.getValue();
            final JLabel timeLabel = new JLabel();      
            fields[fieldNum++]=timespinner;
        /** today-tomorrow entry combo box field */
            String[] daySelect = { "Today","Tomorrow"};
            Daylist = new JComboBox(daySelect);
            Daylist.setSelectedIndex(0);
            fields[fieldNum++]=Daylist;

        /** Associate label/field pairs, add everything and lay it out.*/
        for (int i = 0; i < labelStrings.length; i++) 
        {
            labels[i] = new JLabel(labelStrings[i],
                                   JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);

            //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner)fields[i]);
                //tf.setEditable(false);
                tf.addActionListener(this);
            } 
            else 
            {
                if(fields[i] instanceof JComboBox) 
                {
                    Daylist.addActionListener(this);
                }
                else
                {
                    tf = (JTextField)fields[i];
                    tf.addActionListener(this);
                }
            }  
        }
        SpringUtilities.makeCompactGrid(panel,
                                        labelStrings.length, 2,
                                        GAP, GAP, //init x,y
                                        GAP, GAP/2);//xpad, ypad
        return panel;
    }
    
    
    
    /**
     * Called when the user clicks the button or presses
     * Enter in a text field.
     */
    public void actionPerformed(ActionEvent e){
    /**waiting list table variables*/
       Integer id; String cust,state,waiting,phone;
       cust = Customername.getText();
       state = (String)stateSpinner.getValue();
       Integer st = Integer.valueOf(state); /** Seating Type*/
       phone=PhoneNo.getText();
       String empty = "";
       
       if ("Clear".equals(e.getActionCommand())) {
            addressSet = false;           
            Customername.setText("");
            for(int i=0;i<=9;i++)
                  {
                    floor.t[i].hideAvailableTable(i);                          /**blue color**/
                  }
                  bookCustomer.clearSelection();
                  table.repaint();
            //We can't just setText on the formatted text
            //field, since its value will remain set.
            PhoneNo.setText("");
            
        } else {
            addressSet = true;
        }
        
        if ("delete".equals(e.getActionCommand())) 
        {
            int RowIndex = table.getSelectedRow();                        /**selected row index */
            if(RowIndex>=0){
            System.out.println("Inside delete Customer");
            Object[] options = {"Yes","No"};
            int n = JOptionPane.showOptionDialog(frame,
            "Do you really want to delete? ",
            "Delete",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
            //int value = n.intValue();
            if (n == JOptionPane.YES_OPTION)
            {
              Delete(RowIndex);
            }
            else{
                addressSet=true;
            }
         refreshDatabase();}
      
    } 
        else {
            addressSet = true;
        }
        
        if ("Remove".equals(e.getActionCommand())) 
        {
            int RowIndex = teletable.getSelectedRow();                        /**selected row index */
            if(RowIndex>=0){
            System.out.println("Inside delete Customer");
            Object[] options = {"Yes","No"};
            int n = JOptionPane.showOptionDialog(frame,
            "Do you really want to delete? ",
            "Delete",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
            //int value = n.intValue();
            if (n == JOptionPane.YES_OPTION)
            {
              int RowNo = teletable.getRowCount();
              ((DefaultTableModel)teletable.getModel()).removeRow(RowIndex); /**remove*/
              for(int i=RowIndex;i<=RowNo-1;i++)
                {
                    if(i!=0)
                    {
                    SetteleData(i,i-1,0);
                    }
                    /*else
                    SetteleData(i,i,0); */
                }
            }
            else{
                addressSet=true;
            }
        }
        refreshTeleDatabase();
       }
        else {
            addressSet = true;
        }
        
        if("Move UP".equals(e.getActionCommand()))
       {
          int selectedRowIndex = table.getSelectedRow();
          if(selectedRowIndex!=0)
          {
              int rowAboveSelected = selectedRowIndex-1;
              Object temp[]=getSwapData(selectedRowIndex);
              setSwapData(getSwapData(rowAboveSelected),selectedRowIndex);/** for upper row*/
              setSwapData(temp,rowAboveSelected);/** selected row*/
              table.changeSelection(selectedRowIndex-1,0,false,false);
              if(((JRadioButton)table.getModel().getValueAt(selectedRow,5)).isSelected())
              {
                  ((JRadioButton)table.getModel().getValueAt(rowAboveSelected,5)).doClick();
              }
              System.out.println("Inside Move UP");
              System.out.println(selectedRowIndex);
          refreshDatabase();}
          else
          {}
       }
       
       if("Shift Up".equals(e.getActionCommand()))
       {
           int selectedRowIndex = teletable.getSelectedRow();
           if(selectedRowIndex!=0)
          {
            int rowAboveSelected = selectedRowIndex-1;
            Object temp[]=getteleSwapData(selectedRowIndex);
            setteleSwapData(getteleSwapData(rowAboveSelected),selectedRowIndex);/** for upper row*/
            setteleSwapData(temp,rowAboveSelected);/** selected row*/
            teletable.changeSelection(selectedRowIndex-1,0,false,false);
            System.out.println("Inside Move UP");
            System.out.println(selectedRowIndex);
            refreshTeleDatabase();
          }
          else
          {}
       }
       
    if("Move Down".equals(e.getActionCommand()))
       {
           int selectedRowIndex = table.getSelectedRow();
           if(selectedRowIndex != table.getRowCount()-1){
           System.out.println("Inside Move down");
           int rowBelowSelected = selectedRowIndex+1;
           Object temp[]=getSwapData(selectedRowIndex);
           setSwapData(getSwapData(rowBelowSelected),selectedRowIndex);/**for below row*/
           setSwapData(temp,rowBelowSelected);/**selected row*/
           table.changeSelection(selectedRowIndex+1,0,false,false);
           if(((JRadioButton)table.getModel().getValueAt(selectedRow,5)).isSelected())
              {
                  ((JRadioButton)table.getModel().getValueAt(rowBelowSelected,5)).doClick();
              }
        } refreshDatabase();
       }
       
       if("Shift Down".equals(e.getActionCommand()))
       {
           int selectedRowIndex = teletable.getSelectedRow();
           if(selectedRowIndex!=teletable.getRowCount()-1){
           System.out.println("Inside Move down");
           int rowBelowSelected = selectedRowIndex+1;
           Object temp[]=getteleSwapData(selectedRowIndex);
           setteleSwapData(getteleSwapData(rowBelowSelected),selectedRowIndex);/**for below row*/
           setteleSwapData(temp,rowBelowSelected);/**selected row*/
           teletable.changeSelection(selectedRowIndex+1,0,false,false);
        refreshTeleDatabase();
    }
       }
       
       if("SMS".equals(e.getActionCommand()))
       {   
           int selectedIndex=table.getSelectedRow();
           if(selectedIndex>=0 && ((String)table.getModel().getValueAt(selectedIndex,3)).length()==10)
           {
                smsmodule.sendSMS((String)table.getModel().getValueAt(selectedIndex,1),(String)table.getModel().getValueAt(selectedIndex,3),(Integer)table.getModel().getValueAt(selectedIndex,4));
                JOptionPane.showMessageDialog(frame,"The sms has been sent to the customer.");
           }
           else
           {
               JOptionPane.showMessageDialog(frame,"SMS could not be sent");
           }
    }
       
   if("Add Customer".equals(e.getActionCommand()))
   {   
      if(validateFields()<1)
        {
        /** Adding a row to the table when the user has entered all the fields*/
           count=table.getModel().getRowCount();
           model.insertRow(table.getRowCount(),new Object[]{"","",new Integer(4),"",new Integer(0),new JRadioButton(""+(count+1))});
           id=table.getModel().getRowCount();      /** ID no. is auto-incremented by counting the no. of rows of the table*/
           table.getModel().setValueAt(id,count,0);
           table.getModel().setValueAt(cust,count,1);
           table.getModel().setValueAt(st,count,2);
           table.getModel().setValueAt(phone,count,3);
           table.getModel().setValueAt(0,count,4);      
           Customername.setText("");
           PhoneNo.setValue(null);
           bookCustomer.add((JRadioButton)table.getModel().getValueAt(count,5));
           ((JRadioButton)table.getModel().getValueAt(count,5)).addActionListener(bookTableActionListener);
           insertDatabase(id,cust,st,phone);
        }
    }

     if ("Tele-booking".equals(e.getActionCommand())) {
        telebookingpanel.removeAll();
        telebookingpanel.revalidate();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
          newwindow = new JPanel() {
                //Don't allow us to stretch vertically.
                public Dimension getMaximumSize() {
                    Dimension pref = getPreferredSize();
                    return new Dimension(Integer.MAX_VALUE,
                                         pref.height);
                }
            };
            newwindow.setLayout(new BoxLayout(newwindow,
                                             BoxLayout.PAGE_AXIS));
        newwindow.add(createTelebookingFields());
        /** Styling Telebooking Window */   
            telebookingframe.add(newwindow);
            telebookingpanel.add(newwindow);
            telebookingframe.add(telebookingpanel);
            telebookingframe.setSize(400,270);
            telebookingframe.setLocation((int)(bounds.width - (bounds.width/2)), (int)(bounds.height/2));
            telebookingframe.setVisible(true);
            telebookingframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        /** Buttons field */
            JButton button = new JButton("Book Table");
            button.addActionListener(this);
            telebookingpanel.add(button);
            button = new JButton("Clear");
            button.addActionListener(this);
            button.setActionCommand("clear");
            telebookingpanel.add(button);
          
    }
    /** Clear for Telebooking */
    if ("clear".equals(e.getActionCommand())) {
            Tele_Customername.setText("");          
            Tele_PhoneNo.setValue(null);
        }
        
    if ("Book Table".equals(e.getActionCommand())) 
    {
       /**fetching the selected item from combo box*/
        /**telebooking table variables*/
           Integer tid; String tcust,tseater,tphone; Timestamp ttime;
           int tcount=0;
           tcust = Tele_Customername.getText();
           tseater = (String)Tele_stateSpinner.getValue();
           Integer s = Integer.valueOf(tseater); /** Seating Type*/
           tphone=Tele_PhoneNo.getText();
           String item=(String)Daylist.getSelectedItem();

        datenow=(Date)timespinner.getValue();
        if(item=="Today")
        { 
            calendar.setTime(datenow);
            calendar.set(Calendar.YEAR, c2.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, c2.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));
            selectdate=calendar.getTime();
        }
        if(item=="Tomorrow")
        {
            calendar.setTime(datenow);
            calendar.set(Calendar.YEAR, c2.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, c2.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, c2.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            selectdate=calendar.getTime();
        }
        Date CurrTime=c2.getTime();
        if(minutesDiff(CurrTime,selectdate)<=0 && hoursDiff(CurrTime,selectdate)<=0){
            JOptionPane.showMessageDialog(frame,"The time entered is past the current time!");
        }
/**Adding values to telebooking table*/
        else{
        bookcount=0;bookFlag=0;
        if( teletable.getRowCount()!=0)
        {
            int bookedSeater=0,timeDiff=0;
            for(int i=0;i<teletable.getRowCount();i++)
            {
              try{
                bookedTime=parseFormat.parse((String)teletable.getModel().getValueAt(i,4));
            }catch(Exception ex)
            {
                System.out.println(ex);
            }
              bookedSeater=(Integer)teletable.getModel().getValueAt(i,2);
              timeDiff=minutesDiff(selectdate,bookedTime);
              if(timeDiff==0 || timeDiff<=floor.medianWaitingTime[bookedSeater-2] || timeDiff<=floor.medianWaitingTime[s-2])
              {
                bookcount++;
                fullTimeSlot = bookedTime;
              }
            }
            if(bookcount>=floor.nooftables)
            {
                bookFlag=1;
                long t = fullTimeSlot.getTime();
                System.out.println("Full slot "+fullTimeSlot);
                Date beforeSlot = new Date(t - ((floor.medianWaitingTime[bookedSeater-2]-14) * MINUTE_MILLIS));
                Date afterSlot = new Date(t + ((getLowestSeaterMedian()-14) * MINUTE_MILLIS));
                String beforeSlotString = displayFormat.format(beforeSlot);
                String afterSlotString = displayFormat.format(afterSlot);
                JOptionPane.showMessageDialog(frame,"New table cannot be booked because reservation is full! You can book table at "+beforeSlotString+" or "+afterSlotString);
            }   
        }
        else 
        {
            bookFlag=0;
        }
        
        if(validateTeleFields()<1)
        {
        if(bookFlag==0)
            {
            /** Adding a row to the table when the user has entered all the fields*/
               tmodel.insertRow(teletable.getRowCount(),new Object[]{"","",new Integer(4),"",""});
               tcount=teletable.getModel().getRowCount()-1;
               String date=parseFormat.format(selectdate);
               tid=teletable.getModel().getRowCount();      /** ID no. is auto-incremented by counting the no. of rows of the table*/
               teletable.getModel().setValueAt(tid,tcount,0);
               teletable.getModel().setValueAt(tcust,tcount,1);
               teletable.getModel().setValueAt(s,tcount,2);
               teletable.getModel().setValueAt(tphone,tcount,3);
               teletable.getModel().setValueAt(parseFormat.format(selectdate),tcount,4);/** convert 24hr to 12 hr*/
               JOptionPane.showMessageDialog(frame,"New Table is reserved!");
               insertCustomerDatabase(tcust, tphone);
               refreshTeleDatabase();
            }   
            telebookingframe.dispose();
        }   
}
/**Comparing the current time with reserved table time n adding entries to waiting list from telebooking table*/   
    } //Book Table Ends here
}//Action Listener ends here

public void teleBookSuccess()throws Exception{
    Calendar c1=Calendar.getInstance();
    CurrDate=c1.getTime();
    int tid,count,id,st,currentWaitingTime=0;
    String tcust,tphone;
    Boolean isItTime;
    Date selectedDate;
    if(teletable.getModel().getRowCount()!=0)
    {
        for(int i=0;i<teletable.getModel().getRowCount();i++)
        {
            selectedDate=displayFormat.parse((String)teletable.getModel().getValueAt(i,4));
            if(numberofCust()==0)
            {
                currentWaitingTime=5;
            }
            else if(numberofCust()<=floor.nooftables)
            {
                currentWaitingTime=TableTimeSorter.waitingTime[numberofCust()-1];
            }
            else
            {
                currentWaitingTime=floor.medianWaitingTime[(Integer)table.getModel().getValueAt(numberofCust()+1-floor.nooftables,2)-2]+(Integer)table.getModel().getValueAt(numberofCust()+1-floor.nooftables,4);
            }
            if(minutesDiff(CurrDate,selectedDate)<=currentWaitingTime)
            {
                isItTime=true;
            }
            else
            {
                isItTime=false;
            }
            
            if(isItTime)
            {
               tid=teletable.getModel().getRowCount();
               /** here*/
            /** Adding a row to the table when the user has entered all the fields*/
               count=table.getModel().getRowCount();
               model.insertRow(table.getRowCount(),new Object[]{"","",new Integer(4),"",new Integer(0),new JRadioButton(""+(count+1))});
               /** ID no. is auto-incremented by counting the no. of rows of the table*/
               id=table.getModel().getRowCount();
               tcust=(String)teletable.getModel().getValueAt(i,1);
               st=(Integer)teletable.getModel().getValueAt(i,2);
               tphone=(String)teletable.getModel().getValueAt(i,3);
               
               table.getModel().setValueAt(id,count,0);
               table.getModel().setValueAt(tcust,count,1);
               table.getModel().setValueAt(st,count,2);
               table.getModel().setValueAt(tphone,count,3);
               table.getModel().setValueAt(0,count,4);
               bookCustomer.add((JRadioButton)table.getModel().getValueAt(count,5));
               ((JRadioButton)table.getModel().getValueAt(count,5)).addActionListener(bookTableActionListener);
               smsmodule.sendSMS(tcust,tphone,currentWaitingTime);
               ((DefaultTableModel)teletable.getModel()).removeRow(i);
               insertDatabase(id,tcust,st,tphone);
            }
        }
    }
}

 public static int minutesDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;
        if(earlierDate.getTime()/MINUTE_MILLIS>laterDate.getTime()/MINUTE_MILLIS)
        return (int)((earlierDate.getTime()/MINUTE_MILLIS) - (laterDate.getTime()/MINUTE_MILLIS));
        else
        return (int)((laterDate.getTime()/MINUTE_MILLIS) - (earlierDate.getTime()/MINUTE_MILLIS));        
    }
    
    public static int hoursDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;
        
        return (int)((laterDate.getTime()/HOUR_MILLIS) - (earlierDate.getTime()/HOUR_MILLIS));
    }
    
    public static int daysDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;
        
        return (int)((laterDate.getTime()/DAY_MILLIS) - (earlierDate.getTime()/DAY_MILLIS));
    }
    
    /** To validate the user entries in the fields*/
    public Integer validateFields() {
        
        Integer flag=0;
        String cust = Customername.getText();
        String state = (String)stateSpinner.getValue();
        String phone = PhoneNo.getText();
        String empty = "";

        if ((cust == null) || empty.equals(cust)) {
           JOptionPane.showMessageDialog(frame,
           "Enter Customer Name.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
           flag=1;
        }
        
        if ((state == null) || empty.equals(state)) {
            JOptionPane.showMessageDialog(frame,
           "Enter Seater number.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
            flag=2;
        }
        
        if(Integer.parseInt(state)<1 || Integer.parseInt(state)>12)
        {
            JOptionPane.showMessageDialog(frame,
           "Invalid Seating.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
            flag=2;
        }
           if (phone.length()!=0) 
           {
               if(phone.length()!=10){
                   JOptionPane.showMessageDialog(frame,
                   "Invaild Phone Number",
                   "Inane warning",
                   JOptionPane.WARNING_MESSAGE);
                   flag=3;
        }}
        return flag;
     } 
    
    
     public Integer validateTeleFields() {
        
        Integer flag=0;
        String tcust = Tele_Customername.getText();
        String tstate = (String)Tele_stateSpinner.getValue();
        String tphone = Tele_PhoneNo.getText();
        String empty = "";

        if ((tcust == null) || empty.equals(tcust)) {
           JOptionPane.showMessageDialog(frame,
           "Enter Customer Name.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
           flag=1;
        }
        
        if ((tstate == null) || empty.equals(tstate)) {
            JOptionPane.showMessageDialog(frame,
           "Enter Seater number.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
            flag=2;
        } 
        if ((tphone == null) || empty.equals(tphone)) {
           JOptionPane.showMessageDialog(frame,
           "Enter Phone no.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
            flag=3;
        }
        
         if(Integer.parseInt(tstate)<1 || Integer.parseInt(tstate)>12)
        {
            JOptionPane.showMessageDialog(frame,
           "Invalid Seating.",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
            flag=2;
        }
        if (!NumberValidator(tphone)){
           JOptionPane.showMessageDialog(frame,
           "Invaild Phone Number",
           "Inane warning",
           JOptionPane.WARNING_MESSAGE);
            flag=3;
        }
        return flag;
    } 
    
    //A convenience method for creating a MaskFormatter.
    
    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
    
    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {
            "Customer Name: ",
            "Seater: ",
            "Phone no.: "
        };
        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0; 
        /** Name entry field */
        Customername = new JTextField() {
                public void addNotify() {
                    super.addNotify();
                    requestFocus();
                }
            };
            Customername.setColumns(20);
            fields[fieldNum++] = Customername;
        /** Seater entry field */
            String[] stateStrings = getStateStrings();
            stateSpinner = new JSpinner(new SpinnerListModel(stateStrings));
            stateSpinner.setValue("4");
            fields[fieldNum++] = stateSpinner;
        /** Phone NO entry field */
            PhoneNo = new JFormattedTextField(
            createFormatter("##########"));
            fields[fieldNum++] = PhoneNo;
     
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                                   JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);
        //Add listeners to each field.
            JTextField tf = null;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner)fields[i]);
                tf.setEditable(false);
            } else {
                tf = (JTextField)fields[i];
            }
            tf.addActionListener(this);
        }
        SpringUtilities.makeCompactGrid(panel,
                                        labelStrings.length, 2,
                                        GAP, GAP, //init x,y
                                        GAP, GAP/2);//xpad, ypad
        return panel;
    }
   
    
    public String[] getStateStrings() {
        String[] stateStrings = {
            "1","2","3","4","5","6","7","8","9","10","11","12"
        };
        return stateStrings;    //to b formatted
    }

    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                               + spinner.getEditor().getClass()
                               + " isn't a descendant of DefaultEditor");
            return null;
        }
    }
     public String GetName(int row,int col)
  {    
      return ((String)table.getModel().getValueAt(row,col));
  }
     public void Delete(int RowIndex)
     { 
        int RowNo = table.getRowCount();
           ((DefaultTableModel)table.getModel()).removeRow(RowIndex); /**remove*/
          for(int i=RowIndex;i<=RowNo-1;i++)
            {
                if(i!=0)
                {
                SetData(i,i-1,0);
                ((JRadioButton)table.getModel().getValueAt(i-1,5)).setText(""+i);
                }
                else
                {
                    if(RowNo!=1)
                    {   
                        SetData(i,i,0);
                        ((JRadioButton)table.getModel().getValueAt(i,5)).setText(""+i);
                    }
                }
            }
            prevrow=-1;
     }
  public void SetData(Object obj, int row_index, int col_index)
  {
      table.getModel().setValueAt(obj,row_index,col_index);
  }
  public void SetteleData(Object obj, int row_index, int col_index)
 {
    teletable.getModel().setValueAt(obj,row_index,col_index);
 } 
  public int GetData(int row,int col)
  {     
      return Integer.parseInt((table.getModel().getValueAt(row,col)).toString());
  }
  public int GetTeleData(int row,int col)
  {     
      return Integer.parseInt((teletable.getModel().getValueAt(row,col)).toString());
  }
  
   public int GetWaitData(int row)
   {     
      return (Integer)(table.getModel().getValueAt(row,5));
   }
  public int numberofCust()
  {
      return table.getModel().getRowCount();
  }
  
  public void setSwapData(Object[] data,int r)
    {
        for (int c = 0; c  <  data.length; c++)
        {
            if(c!=0&&c!=5)
            {
                SetData(data[c], r, c);        /**setdata function calling*/
            }
        }
          
    }
  public void setteleSwapData(Object[] tdata,int r)
    {
        for (int c = 0; c  <  tdata.length; c++)
        {
            if(c!=5&&c!=0)
            {
                SetteleData(tdata[c], r, c);        /**setdata function calling*/
            }
        }   
    }
     
  public Object[] getSwapData(int r)
    {
        Object[] data=new Object[table.getModel().getColumnCount()];
        for (int c = 0; c  <  data.length; c++)
            {
               
                data[c]=table.getModel().getValueAt(r,c);
                 
            }
        return data;
    }
  public Object[] getteleSwapData(int r)
    {
            Object[] tdata=new Object[teletable.getModel().getColumnCount()];
            for (int c = 0; c  <  tdata.length; c++)
            {
                tdata[c]=teletable.getModel().getValueAt(r,c);
            }
           
        return tdata;
    }
  public void addRow()
  {
      model.insertRow(table.getRowCount(),new Object[]{new Integer(0),"",new Integer(4),"",new Integer(0),new Boolean(false)});
  }
  public void addteleRow()
  {
    tmodel.insertRow(teletable.getRowCount(),new Object[]{"","",new Integer(4),"",""});
  }
  
  /**
   * Updates the current values from all the rows in the TeleBooking table into the Database Table. Erases previous values.
   * Called after every new table is booked("Book Table")
   */
  public static void refreshTeleDatabase()
      {
        
            ResultSet rs = null;
            Connection conn = null;
            java.sql.Statement statement=null;
            
            System.out.println("Current no of rows "+teletable.getRowCount());
            
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
                    catch (Exception ex) 
                    { /* ignore close errors */  }
                    
                }
             }  
             
            try
            {
                int updateQuery = 0;
                String DeleteQuery = "DELETE FROM `telebooking` WHERE 1";
                System.out.println("row deleted");
                updateQuery = statement.executeUpdate(DeleteQuery);
                  if(teletable!=null&&teletable.getRowCount()!=0)
        {
                for(int i=0; i<=teletable.getRowCount()-1; i++)
                {
                    String QuerySt = "INSERT INTO `telebooking`(`ID`, `Name`, `Seater`, `Phone`, `Timestamp`) VALUES "+"("+teletable.getModel().getValueAt(i,0)+", '"+teletable.getModel().getValueAt(i,1)+"', "+teletable.getModel().getValueAt(i,2)+", '"+teletable.getModel().getValueAt(i,3)+"', '"+teletable.getModel().getValueAt(i,4)+"')";
                    updateQuery = statement.executeUpdate(QuerySt);
                    if (updateQuery != 0) 
                    {
                        System.out.println(updateQuery + " row is inserted in telebooking tABLE.");
                    }
                }
            }
        }
            
            catch (Exception ex) 
            {
                System.out.println("SQLException: " + ex.getMessage());
            }
}
public static void refreshDatabase()
      {
    
            ResultSet rs = null;
            Connection conn = null;
            java.sql.Statement statement=null;
            System.out.println("Current no of rows "+table.getRowCount());
            
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
                    catch (Exception ex) 
                    { /* ignore close errors */  }
                    
                }
             }  
             
            try
            {
                int updateQuery = 0;
                String DeleteQuery = "DELETE FROM `waiting_table` WHERE 1";
                System.out.println("row deleted in waiting table");
                updateQuery = statement.executeUpdate(DeleteQuery);
                if(table!=null&&table.getRowCount()!=0)
        {
                for(int i=0; i<=table.getRowCount()-1; i++)
                {
                    String QuerySt = "INSERT INTO `waiting_table`(`ID`, `Name`, `Seater`, `Phone_no`) VALUES "+"("+table.getModel().getValueAt(i,0)+", '"+table.getModel().getValueAt(i,1)+"', "+table.getModel().getValueAt(i,2)+", '"+table.getModel().getValueAt(i,3)+"')";
                    updateQuery = statement.executeUpdate(QuerySt);
                    if (updateQuery != 0) 
                    {
                        System.out.println(updateQuery + " row is inserted.");
                    }
                }
            }
        }
            
            catch (Exception ex) 
            {
                System.out.println("SQLException: " + ex.getMessage());
            }             
}
    /**
     * Retrieves rows stored in the TeleBooking Database table and stores them into the TeleBooking table in the front end.
     * Called once when the application is completely loaded(At the end of SetCellValues constructor)
     * Take care of the DateTime conversions
     */
    public void repopulateJTable()
    {
            tmodel.setRowCount(0);
            String dateString;
            Connection conn = null;
            java.sql.Statement st=null;            
            try
            {
                Class.forName ("com.mysql.jdbc.Driver").newInstance ();
                System.out.println("Class Loaded");
                conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/greenfoot?useUnicode=yes", "greenfoot", "greenfoot");
                System.out.println ("Retrieving from database");
                st = conn.createStatement();
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
                    catch (Exception ex) 
                    {  }
                    
                } 
             } 
             try 
             {
                 String sql = "SELECT * FROM `telebooking`";
                 ResultSet rss = st.executeQuery(sql);
                 int i=1;
                 while (rss.next()) 
                 {
                     int id = i;
                     String custName = rss.getString(2);
                     int seater = rss.getInt(3);
                     String phone = rss.getString(4);
                     String timeReg = rss.getString(5);
                     //SimpleDateFormat date = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
                     //Date regDate = date.parse(timeReg);
                     tmodel.insertRow(teletable.getRowCount(),new Object[]{"","",new Integer(4),"",""});
                     teletable.getModel().setValueAt(id,i-1,0);
                     teletable.getModel().setValueAt(custName,i-1,1);
                     teletable.getModel().setValueAt(seater,i-1,2);
                     teletable.getModel().setValueAt(phone,i-1,3);
                     teletable.getModel().setValueAt(timeReg,i-1,4);
                  i++;
                 }
                 String sqlquery = "SELECT * FROM waiting_table";
                 ResultSet rs = st.executeQuery(sqlquery);
                 int j=1;
                 while (rs.next()) 
                 {
                     int ID = j;
                     String cust = rs.getString(2);
                     int seat = rs.getInt(3);
                     String ph = rs.getString(4);
                     model.insertRow(table.getRowCount(),new Object[]{"","",new Integer(4),"",new Integer(4),new JRadioButton(""+(j))});
                     table.getModel().setValueAt(ID,j-1,0);
                     table.getModel().setValueAt(cust,j-1,1);
                     table.getModel().setValueAt(seat,j-1,2);
                     table.getModel().setValueAt(ph,j-1,3);
                     table.getModel().setValueAt(0,j-1,4);
                     bookCustomer.add((JRadioButton)table.getModel().getValueAt(j-1,5));
           ((JRadioButton)table.getModel().getValueAt(j-1,5)).addActionListener(bookTableActionListener);
                  j++;
             }
            }
             catch(Exception ex)
             {}
    
    }
   
    public void insertTeleDatabase(int tid,String tcust,int s,String tphone,String selectdate)
    {
        ResultSet rs = null;
        Connection conn = null;
            java.sql.Statement st=null;            
            try
            {
                Class.forName ("com.mysql.jdbc.Driver").newInstance ();
                System.out.println("Class Loaded");
                conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/greenfoot?useUnicode=yes", "greenfoot", "greenfoot");
                System.out.println ("Retrieving from database");
                st = conn.createStatement();
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
                    catch (Exception ex) 
                    {  }
                    
                } 
             } 
        try{
            
            String QuerySt = "INSERT INTO `telebooking`(`ID`, `Name`, `Seater`, `Phone`, `Timestamp`) VALUES "+"("+tid+",'"+tcust+"',"+s+",'"+tphone+"','"+selectdate+"')";
            updateQuery = statement.executeUpdate(QuerySt);
             if (updateQuery != 0) {
              System.out.println(updateQuery + " row is inserted in table telebooking.");
            }
        }catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }  

        
        //refreshDatabase();
    }
    
    public void insertDatabase(int id,String cust,int st,String phone)
    { ResultSet rs = null;
            Connection conn = null;
            java.sql.Statement statement=null;
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
                    catch (Exception ex) 
                    {  }
                    
                }
             } 
         try{
            String QuerySt = "INSERT INTO `waiting_table`(`ID`,`Name`, `Seater`, `Phone_no`) VALUES "+"('"+id+"','"+cust+"','"+st+"','"+phone+"')";
            updateQuery = statement.executeUpdate(QuerySt);

            String custDetails = "INSERT INTO `customer_details`(`customer_name`, `phone_number`) VALUES "+"('"+cust+"','"+phone+"')";
            updateQuery = statement.executeUpdate(custDetails);
            if (updateQuery != 0) {
              System.out.println( updateQuery + " row is inserted in customer details via waiting_table.");
            }
        }catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }  
    }
    
    public void insertCustomerDatabase(String cust, String phone)
    { ResultSet rs = null;
            Connection conn = null;
            java.sql.Statement statement=null;
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
                    catch (Exception ex) 
                    {  }
                    
                }
             } 
         try{
             String custDetails = "INSERT INTO `customer_details`(`customer_name`, `phone_number`) VALUES "+"('"+cust+"','"+phone+"')";
            updateQuery = statement.executeUpdate(custDetails);
            if (updateQuery != 0) {
              System.out.println( updateQuery + " row is inserted in customer details via telebooking");
            }
        }catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }  
    }
    
    public int getLowestSeaterMedian()
    {
        for(i=0; i<=floor.nooftables-1; i++)
        {
           String bookedTimeString = displayFormat.format(fullTimeSlot);
           String tableDateString = (String)teletable.getModel().getValueAt(i, 4);
           System.out.println(bookedTimeString);
           System.out.println(tableDateString);
           int j=0;
           if(tableDateString.equals(bookedTimeString))
           {
            TeleMedians[j] = GetTeleData(i, 2);
            j++;
           }
        }
        Arrays.sort(TeleMedians);
        for(i=0; i<=TeleMedians.length-1; i++)
        {
        if(TeleMedians[i]!=0)
            {
                lowestMedianValue = TeleMedians[i];
                break;
            }
        }
        int lowestWaitingTime = floor.medianWaitingTime[lowestMedianValue-2];
        System.out.println("lowest seater " + lowestWaitingTime);
        return lowestWaitingTime;
 }
 
    boolean NumberValidator(String sPhoneNumber)
  {
      Pattern pattern = Pattern.compile("\\d{10}");
      Matcher matcher = pattern.matcher(sPhoneNumber);
      if (matcher.matches()) {
          return true;
      }
      else
      {
          return false;
      }
  }
    
} /** SetCellValues ends here */
class RadioButtonRenderer implements TableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value,
                   boolean isSelected, boolean hasFocus, int row, int column) {
    if (value==null) return null;
    return (Component)value;
  }
}
 
class RadioButtonEditor extends    DefaultCellEditor
                        implements ItemListener {
  private JRadioButton button;
 
  public RadioButtonEditor(JCheckBox checkBox) {
    super(checkBox);
  }
 
  public Component getTableCellEditorComponent(JTable table, Object value,
                   boolean isSelected, int row, int column) {
    if (value==null) return null;
    button = (JRadioButton)value;
    button.addItemListener(this);
    return (Component)value;
  }
 
  public Object getCellEditorValue() {
    button.removeItemListener(this);
    return button;
  }
 
  public void itemStateChanged(ItemEvent e) {
    super.fireEditingStopped();
  }
}