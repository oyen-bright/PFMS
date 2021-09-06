/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;

import java.awt.Color;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOUSE OF PRAYER
 */


public class Main_Panel_Goal extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    
    int data;
    String  data1;
    PreparedStatement pst;
    String name = null,userName = null,sex =null;
    String sessionUsername=null;
    int sessionId;
     Color color=new Color(100,113,116);
    
    public Main_Panel_Goal(int data, String data1) {
        this.data=data;
        this.data1=data1;
        this.getData_main();
        
        
        
        setUndecorated(true);
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
        
        
        grossAmountTextBox.setText(calGross());
        
        
        
      
        sidePanel.setVisible(true);
        
        uiDate.setText((java.time.LocalDate.now()).toString());
        uiTime.setText(format.format(java.time.LocalTime.now()));
        
        uiDate.setForeground(Color.white);
        uiTime.setForeground(Color.white);
        
        sidePanelName.setText(name);
        sidePanelUsername.setText(userName);
        sidePanelSex.setText(sex);
        
        sidePanelName.setForeground(Color.white);
        sidePanelUsername.setForeground(Color.white);
        sidePanelSex.setForeground(Color.white);
        
          refreshData();
          checkActiveGoals();
                
                }    
    
    public Main_Panel_Goal(int data) {
        this.data=data;
        System.out.println(data+"incoming data");
        this.getData();
        
        
        
        setUndecorated(true);
        
        
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
       
                grossAmountTextBox.setText(calGross());

        
        
        sidePanel.setVisible(true);
        
        uiDate.setText((java.time.LocalDate.now()).toString());
        uiTime.setText(format.format(java.time.LocalTime.now()));
        
        uiDate.setForeground(Color.white);
        uiTime.setForeground(Color.white);
        
        sidePanelName.setText(name);
        sidePanelUsername.setText(userName);
        sidePanelSex.setText(sex);
        
        sidePanelName.setForeground(Color.white);
        sidePanelUsername.setForeground(Color.white);
        sidePanelSex.setForeground(Color.white);
        
       // jTable1.getUI().setPreferredSize(new Dimension(10, 0));
       //jTable1.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));
        
        refreshData();   
        checkActiveGoals();
        
                }
    
    public void getData(){
        
        System.out.println(data);
   
        try (Connection con=Sql.getConnection()){
            String sql="SELECT * from user_account_log_info WHERE Id = ?";
            pst=con.prepareStatement(sql);
            pst.setInt(1, data);
                try(ResultSet result=pst.executeQuery()){
                    if(result.next()){
                        System.out.println(result.getString("Username"));
                        
                        sessionUsername=result.getString("Username");
                        System.out.println("usernameExist"+"meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        
                        
                       
                    }
                    else{
                         System.out.println("User Account Information not found");
                    }
                }
                 sql="SELECT * from user_account WHERE Username = ?";
                        pst=con.prepareStatement(sql);
                        
                        System.out.println(sessionUsername);
                        pst.setString(1, sessionUsername);
                        try(ResultSet result1=pst.executeQuery()){
                        if(result1.next()){
                            System.out.println("User Account Information found");
                            name=result1.getString("Name");
                            sessionId=result1.getInt("Id");
                            System.out.println("this is the real session Id"+sessionId);
                            userName=result1.getString("Username");
                            if("M".equals(result1.getString("Sex"))){
                               sex="Male";
                            }else{
                              sex="FEMALE";
                          }
                            
                        }
                        else{
                            System.out.println("User log in information not found");
                        }
                        }

        } catch (SQLException ex) {
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
   
    }
        public void getData_main(){
        
        System.out.println(data);
   
        try (Connection con=Sql.getConnection()){
            
             String  sql="SELECT * from user_account WHERE Id = ? and Username = ?";
                        pst=con.prepareStatement(sql);
                        pst.setInt(1, data);
                        pst.setString(2, data1);
                        try(ResultSet result1=pst.executeQuery()){
                        if(result1.next()){
                            System.out.println("User Account Information found");
                            name=result1.getString("Name");
                            sessionId=result1.getInt("Id");
                            sessionUsername=result1.getString("Username");
                            System.out.println("this is the real session Id"+sessionId);
                            userName=result1.getString("Username");
                            if("M".equals(result1.getString("Sex"))){
                               sex="Male";
                            }else{
                              sex="FEMALE";
                          }
                           System.out.println("suvess");
                        }
                        else{
                            System.out.println("User log in information not found");
                        }
                        }

        } catch (SQLException ex) {
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
   
    }
                                              
    
public String calGross(){
    int grossAmount;
    int totallIncomeAmount = 0;
    int totallConsumptionAmount = 0;
    String sql="SELECT SUM(Amount) from income_pfms where Id=? and Username = ?";
    try(Connection con=Sql.getConnection()){
       PreparedStatement pst=con.prepareStatement(sql);
       pst.setInt(1, sessionId);
       pst.setString(2, sessionUsername);
       try(ResultSet result=pst.executeQuery()){
           while(result.next()){
               totallIncomeAmount=result.getInt("SUM(Amount)");
               mainIncome.setText("$ "+result.getString("SUM(Amount)"));
               System.out.println(totallIncomeAmount);    } }
            
     sql="SELECT SUM(Amount) from expense_pfms where Id=? and Username = ?";
       pst=con.prepareStatement(sql);
       pst.setInt(1, sessionId);
       pst.setString(2, sessionUsername);
       try(ResultSet result=pst.executeQuery()){
           while(result.next()){
               totallConsumptionAmount=result.getInt("SUM(Amount)");
               mainExpense.setText("$ "+result.getString("SUM(Amount)"));
               System.out.println(totallConsumptionAmount);    } }
        
    } 
    catch (SQLException ex) {
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    grossAmount=totallIncomeAmount-totallConsumptionAmount;
    if(grossAmount<0){
        grossAmountTextBox.setForeground(Color.red);
    }
    else{
         grossAmountTextBox.setForeground(Color.green);
    }


   
   return Integer.toString(grossAmount);
}
    
    public void refreshData(){
   // String mainEcpense;
   
   
   
   
    int thisId;
    int targetAmount;
    String duration;
    int incomeFromDate = 0;
    int expenseFromDate = 0;
    int balance;
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = null;
    try(Connection con=Sql.getConnection()){
        String sql="SELECT * FROM income_goal_pfms where Id= ? and Username =? and Active =?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setString(3, "Yes");
        try(ResultSet results=pst.executeQuery()){
            if(results.next()){
        duration=results.getString("Duration");
        targetAmount=results.getInt("Target");
        date= LocalDate.parse(results.getString("Date"), formatter1);
         thisId=results.getInt("Number");
        
        
                
                
                
        LocalDate thisdate= LocalDate.now();
       
        String sqlDate;
        System.out.println(thisdate+"today");
        int nextMonth=thisdate.getMonthValue();
        System.out.println(nextMonth+"nextMonth");
        nextMonth++;
         if(nextMonth ==13){
        nextMonth=1;
    }
        System.out.println(nextMonth+"nextMonth");
        thisdate=thisdate.plusDays(1);
        System.out.println(thisdate+"plusone");
        //LocalDate date=thisdate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(date+"date");
        date=date.minusDays(1);
        System.out.println(date+"dateminus ones");
        while(date.getMonthValue()!=nextMonth){
        date=date.plusDays(1);
    if(date.getMonthValue()==nextMonth){;
        break;
    }
    else{
         sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ?";
         pst=con.prepareStatement(sql);
         pst.setInt(1, sessionId);
         pst.setString(2, sessionUsername);
        try(ResultSet results1=pst.executeQuery()){
            while (results1.next()){
                try{
                    java.util.Date getdate= new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(results1.getString("Date"));
                     sqlDate=formatter.format(getdate);
                              
                               if (sqlDate.equals(date.toString())){
                                   incomeFromDate+=results1.getInt("Amount");
                                   System.out.println("sqldate"+sqlDate+"--"+date.toString());
                               }
                }
                catch(ParseException ex){
                    System.out.println("errror herer");
                    System.out.println(ex);
                }
            }
        }
         
        System.out.println(incomeFromDate+"INCOME FROM DATE");
        
          sql="SELECT Amount, Date from expense_pfms WHERE Id = ? and Username = ?";
         pst=con.prepareStatement(sql);
         pst.setInt(1, sessionId);
         pst.setString(2, sessionUsername);
        try(ResultSet results2=pst.executeQuery()){
            while (results2.next()){
                try{
                    java.util.Date getdate= new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(results2.getString("Date"));
                     sqlDate=formatter.format(getdate);
                              
                               if (sqlDate.equals(date.toString())){
                                   expenseFromDate+=results2.getInt("Amount");
                               }
                }
                catch(ParseException ex){
                    System.out.println(ex);
                }
            }
        }
        System.out.println(expenseFromDate+"EXPENSE FROM DATE");
        
       
        
        
    }
      System.out.println("was here");  
    }
        targetAmountBox.setText(String.valueOf(targetAmount));
        incomeFromDateBox.setText(String.valueOf(incomeFromDate));
        expenseFromDateBox.setText(String.valueOf(expenseFromDate));
        balance=incomeFromDate-targetAmount;
        
        sql="UPDATE income_goal_pfms SET Balance = ? Where Id = ? and Username=? and Number=? " ;
        pst=con.prepareStatement(sql);
        pst.setInt(1, balance);
        pst.setInt(2, sessionId);
        pst.setString(3, sessionUsername);
        pst.setInt(4, thisId);     
        pst.executeUpdate();
        
        DefaultTableModel model=(DefaultTableModel) historyTable.getModel();
           int rowCount = model.getRowCount();
           for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
            }
           
        sql="SELECT  Goal_Name, Target, Balance, Duration, Date, End_Date, Hit_Target, End_Date From income_goal_pfms where Id=? and Username=?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        try(ResultSet result=pst.executeQuery()){
        int i=0;
            while(result.next()){
              i++;
              model.addRow(new Object[]{i,result.getString("Goal_Name"), result.getString("Target"), result.getString("Balance"),result.getString("Duration"), result.getString("Date")
               , result.getString("End_Date"), result.getString("Hit_Target")
              });
               
            }
        }
        
        
        durationBox2.setText(duration);
        balanceAmountBox.setText(String.valueOf(balance));
        progressBar.setMaximum(targetAmount);
        progressBar.setMinimum(0);
        int getProgress=balance;
        progressBar.setStringPainted(true);
        
        UIManager.put("ProgressBar.background", Color.GREEN);
        UIManager.put("ProgressBar.foreground", Color.BLUE);
        UIManager.put("ProgressBar.selectionBackground", Color.RED);
        UIManager.put("ProgressBar.selectionForeground", Color.GREEN);
        progressBar.setBackground(Color.red);
        progressBar.setForeground(Color.green);
         System.out.println("was here too");  
      
            progressBar.setValue(getProgress);
        

    }
      else{
                
               
               
            }
        }
    }
        catch (SQLException ex) {
        System.out.println(ex);
            //Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
}

public void checkActiveGoals(){
    LocalDate startDate;
    LocalDate endDate;
    int targetAmount;
    int balanceAmount;
    int number;
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try(Connection con=Sql.getConnection()){
        String sql="SELECT * FROM income_goal_pfms where Id= ? and Username =? and Active =?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setString(3, "Yes");
        try(ResultSet result=pst.executeQuery()){
            if(result.next()){
                number=result.getInt("Number");
                 System.out.println("was here mind");  
                startDate= LocalDate.parse(result.getString("Date"), formatter1);
                endDate=LocalDate.parse(result.getString("End_Date"), formatter1);
                LocalDate thisdate= LocalDate.now();
           if (thisdate.isBefore(endDate)) {
              
          
            }
           else{
               System.out.println("hggggggggggggggggggggggggg");
               sql="UPDATE income_goal_pfms set Hit_Target=?, Active=? where Number=? and Id=? and Username=?";
               pst=con.prepareStatement(sql);
              
               targetAmount=result.getInt("Target");
               balanceAmount=result.getInt("Balance");
               if(targetAmount-balanceAmount==0||targetAmount-balanceAmount<0){
                   pst.setString(1, "Yes");
               }
               else{
                   pst.setString(1, "No");
               }
               pst.setString(2, "No");
               pst.setInt(3, number);
               pst.setInt(4, sessionId);
               pst.setString(5, sessionUsername);
               pst.execute();
                System.out.println("was here minde"); 
                refreshData();
           }
        }
        }
        
        
        
    }   catch (SQLException ex) {
        System.out.println(ex);
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexButtonGroup = new javax.swing.ButtonGroup();
        sidePanel = new RoundedPanel(50);
        logOutIcon = new javax.swing.JLabel();
        logOutText = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sidePanelName = new javax.swing.JLabel();
        sidePanelUsername = new javax.swing.JLabel();
        sidePanelSex = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        incomeWeek = new javax.swing.JLabel();
        incomeMonth = new javax.swing.JLabel();
        comsumptionMonth = new javax.swing.JLabel();
        consumptionWeek = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        grossAmountTextBox = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        addButton = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel32 = new javax.swing.JLabel();
        backgroundPanel = new RoundedPanel(50);
        mainPanel = new RoundedPanel(50);
        uiDate = new javax.swing.JLabel();
        uiTime = new javax.swing.JLabel();
        exitButton = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        backButton = new javax.swing.JLabel();
        alertText = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mainExpense = new javax.swing.JLabel();
        mainIncome = new javax.swing.JLabel();
        jPanel1 = new RoundedPanel(30);
        jLabel1 = new javax.swing.JLabel();
        reportJlable = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        reportLable = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new RoundedPanel(50);
        goalModifyButton = new javax.swing.JLabel();
        expenseFromDateBox = new javax.swing.JLabel();
        durationBox2 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        balanceAmountBox = new javax.swing.JLabel();
        incomeFromDateBox = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        targetAmountBox = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        addBudgetPanel = new RoundedPanel(30);
        jPanel4 = new RoundedPanel(15);
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        goalNameTextBox = new javax.swing.JTextField();
        targetAmountTextBox = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel30 = new javax.swing.JLabel();
        durationBox = new javax.swing.JLabel();

        sidePanel.setBackground(new java.awt.Color(92, 84, 107));
        sidePanel.setForeground(new java.awt.Color(255, 246, 233));
        sidePanel.setToolTipText("");
        sidePanel.setOpaque(false);
        sidePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logOutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        logOutIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutIconMouseClicked(evt);
            }
        });
        sidePanel.add(logOutIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 595, -1, 23));

        logOutText.setBackground(new java.awt.Color(255, 255, 255));
        logOutText.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        logOutText.setForeground(new java.awt.Color(255, 255, 255));
        logOutText.setText("LOG OUT");
        logOutText.setToolTipText("");
        logOutText.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutTextMouseClicked(evt);
            }
        });
        sidePanel.add(logOutText, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 595, -1, 23));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/uers.png"))); // NOI18N
        sidePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 67, -1, -1));

        sidePanelName.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        sidePanelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelName.setText("Name ");
        sidePanel.add(sidePanelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        sidePanelUsername.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        sidePanelUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelUsername.setText("username");
        sidePanel.add(sidePanelUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        sidePanelSex.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        sidePanelSex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelSex.setText("Sex");
        sidePanel.add(sidePanelSex, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("|");
        jLabel9.setToolTipText("");
        sidePanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 16, 17));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 154, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/income.png"))); // NOI18N
        sidePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 154, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/outcome.png"))); // NOI18N
        jLabel7.setAutoscrolls(true);
        sidePanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, -1, -1));

        jLabel8.setForeground(new java.awt.Color(255, 154, 0));
        jLabel8.setText("INCOMING");
        sidePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel10.setForeground(new java.awt.Color(255, 154, 0));
        jLabel10.setText("CONSUMPTION");
        sidePanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, -1, -1));

        incomeWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        incomeWeek.setText("INCOME");
        sidePanel.add(incomeWeek, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 100, -1));

        incomeMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        incomeMonth.setText("INCOME");
        sidePanel.add(incomeMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 100, -1));

        comsumptionMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comsumptionMonth.setText("CONSUMPTION");
        sidePanel.add(comsumptionMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, 120, -1));

        consumptionWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        consumptionWeek.setText("CONSUMPTION");
        sidePanel.add(consumptionWeek, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 120, -1));

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 154, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MONTH");
        sidePanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 210, 20));

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 154, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("WEEK");
        sidePanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 210, -1));

        grossAmountTextBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        grossAmountTextBox.setText("GROSS");
        sidePanel.add(grossAmountTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 210, 20));
        sidePanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 190, -1));

        addButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addButton.setText("ADD");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        sidePanel.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 210, 20));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sidePanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 510, 180, 100));

        sidePanel.getAccessibleContext().setAccessibleName("");

        jLabel32.setText("jLabel32");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(900, 50));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        backgroundPanel.setBackground(new java.awt.Color(255, 153, 0));
        backgroundPanel.setOpaque(false);
        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setOpaque(false);
        mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainPanelMouseClicked(evt);
            }
        });
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uiDate.setText("Date");
        mainPanel.add(uiDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        uiTime.setText("Time");
        mainPanel.add(uiTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        exitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.png"))); // NOI18N
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 40, 30));

        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Avater.png"))); // NOI18N
        userIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userIconMouseClicked(evt);
            }
        });
        mainPanel.add(userIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, -1));

        backButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Back.png"))); // NOI18N
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });
        mainPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 50, -1, -1));

        alertText.setFont(new java.awt.Font("Roboto Light", 0, 10)); // NOI18N
        alertText.setForeground(new java.awt.Color(255, 51, 0));
        alertText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainPanel.add(alertText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 250, -1));

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel13.setText("INCOME");
        mainPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, -1, -1));

        jLabel18.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel18.setText("EXPENCE");
        mainPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, -1, -1));

        mainExpense.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mainExpense.setText("VALUE");
        mainPanel.add(mainExpense, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, -1));

        mainIncome.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mainIncome.setText("VALUE");
        mainPanel.add(mainIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setOpaque(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-dashboardNormal.png"))); // NOI18N
        jLabel1.setText("  ");
        jLabel1.setToolTipText("");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setIconTextGap(6);
        jLabel1.setInheritsPopupMenu(false);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        reportJlable.setBackground(new java.awt.Color(255, 255, 102));
        reportJlable.setForeground(new java.awt.Color(255, 102, 255));
        reportJlable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon-reportsNormal2.png"))); // NOI18N
        reportJlable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportJlableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reportJlableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reportJlableMouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-budgetsNormal.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-goalsNormal.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-search-dark.png"))); // NOI18N
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(100, 113, 116));
        jLabel12.setText("DASHBORD");

        reportLable.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        reportLable.setForeground(new java.awt.Color(100, 113, 116));
        reportLable.setText("REPORT");

        jLabel14.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(100, 113, 116));
        jLabel14.setText("BUDGETS");

        jLabel17.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("GOALS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(jLabel1))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(reportJlable)
                    .addComponent(reportLable))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel14)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel4)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reportJlable)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(reportLable)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)))
        );

        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 580, 320, 50));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-goalsTitle.png"))); // NOI18N
        mainPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel20.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel20.setText("INCOME GOALS");
        mainPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(63, 183, 255));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        goalModifyButton.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        goalModifyButton.setText("MODIFY");
        goalModifyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goalModifyButtonMouseClicked(evt);
            }
        });
        jPanel2.add(goalModifyButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 260, 240, -1));

        expenseFromDateBox.setFont(new java.awt.Font("Roboto Medium", 0, 20)); // NOI18N
        expenseFromDateBox.setForeground(new java.awt.Color(255, 0, 0));
        expenseFromDateBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        expenseFromDateBox.setText("AMOUNT");
        jPanel2.add(expenseFromDateBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 90, 140, -1));

        durationBox2.setFont(new java.awt.Font("Roboto Medium", 0, 8)); // NOI18N
        durationBox2.setText("DURATIONS");
        jPanel2.add(durationBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, -1, 20));
        jPanel2.add(progressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 250, 20));

        historyTable.setFont(new java.awt.Font("Roboto Light", 0, 11)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Name", "Target", "Balance", "Duration", "Start Date", "End_Date", "Hit Target"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        historyTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        historyTable.setFocusable(false);
        historyTable.setRowSelectionAllowed(false);
        historyTable.setSelectionBackground(new java.awt.Color(255, 153, 0));
        historyTable.setShowGrid(false);
        jScrollPane2.setViewportView(historyTable);
        if (historyTable.getColumnModel().getColumnCount() > 0) {
            historyTable.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 350, 590, 90));

        jLabel36.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel36.setText("HISTORY");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, -1, -1));

        balanceAmountBox.setFont(new java.awt.Font("Roboto Medium", 0, 20)); // NOI18N
        balanceAmountBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        balanceAmountBox.setText("AMOUNT");
        jPanel2.add(balanceAmountBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 160, 240, -1));

        incomeFromDateBox.setFont(new java.awt.Font("Roboto Medium", 0, 20)); // NOI18N
        incomeFromDateBox.setForeground(new java.awt.Color(0, 204, 0));
        incomeFromDateBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        incomeFromDateBox.setText("AMOUNT");
        jPanel2.add(incomeFromDateBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 90, 130, -1));

        jLabel39.setBackground(new java.awt.Color(235, 239, 229));
        jLabel39.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel39.setText("TARGET AMOUNT");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, 20));

        jLabel28.setFont(new java.awt.Font("Roboto Medium", 0, 8)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("PROGRESS");
        jPanel2.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 240, 10));

        jLabel29.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel29.setText("INCOME SINCE START DATE");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, 20));

        targetAmountBox.setFont(new java.awt.Font("Roboto Medium", 0, 20)); // NOI18N
        targetAmountBox.setForeground(new java.awt.Color(212, 175, 55));
        targetAmountBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        targetAmountBox.setText("AMOUNT");
        jPanel2.add(targetAmountBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(175, 30, 90, -1));

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("ADD");
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 404, -1, -1));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 270, 10));

        jLabel33.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel33.setText("EXPENCES SINCE START DATE");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, -1, 20));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 329, 270, -1));

        addBudgetPanel.setBackground(new java.awt.Color(235, 239, 229));
        addBudgetPanel.setOpaque(false);

        jPanel4.setBackground(new java.awt.Color(255, 153, 0));
        jPanel4.setOpaque(false);

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("ADD INCOME GOAL");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel26.setText("GOAL NAME");

        goalNameTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        targetAmountTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel27.setText("TARGET AMOUNT  ");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("THIS WEEK");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("THIS MONTH");

        jLabel30.setBackground(new java.awt.Color(0, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("ADD GOAL");
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout addBudgetPanelLayout = new javax.swing.GroupLayout(addBudgetPanel);
        addBudgetPanel.setLayout(addBudgetPanelLayout);
        addBudgetPanelLayout.setHorizontalGroup(
            addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBudgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addBudgetPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(goalNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(jLabel27)
                                .addComponent(targetAmountTextBox)
                                .addGroup(addBudgetPanelLayout.createSequentialGroup()
                                    .addComponent(jRadioButton2)
                                    .addGap(18, 18, 18)
                                    .addComponent(jRadioButton1))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addBudgetPanelLayout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(26, 26, 26))
                    .addGroup(addBudgetPanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        addBudgetPanelLayout.setVerticalGroup(
            addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBudgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addGap(14, 14, 14)
                .addComponent(goalNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(targetAmountTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(18, 18, 18)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.add(addBudgetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, 310));

        durationBox.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        durationBox.setText("DURATION :");
        jPanel2.add(durationBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, -1, 20));

        mainPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 750, 450));

        backgroundPanel.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 770, 645));
        mainPanel.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        // TODO add your handling code here:
        userLogOut();
        dispose();
    }//GEN-LAST:event_exitButtonMouseClicked

    private void backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backButtonMouseClicked
        // TODO add your handling code here:
         new Main_Panel(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonMouseClicked

    private void mainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseClicked
        // TODO add your handling code here:
        if(sidePanel.isVisible()){
            sidePanel.setVisible(false);
               uiDate.setForeground(Color.black);
         uiTime.setForeground(Color.black);
        }
    }//GEN-LAST:event_mainPanelMouseClicked

    private void userIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userIconMouseClicked
        // TODO add your handling code here:
        if(sidePanel.isVisible()){
            sidePanel.setVisible(false);
            uiDate.setForeground(Color.black);
         uiTime.setForeground(Color.black);
        }
        else{
         uiDate.setForeground(Color.white);
         uiTime.setForeground(Color.white);
          sidePanel.setVisible(true);
        }
        
    }//GEN-LAST:event_userIconMouseClicked

    private void logOutTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutTextMouseClicked
        // TODO add your handling code here:
        userLogOut();
    }//GEN-LAST:event_logOutTextMouseClicked

    private void logOutIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logOutIconMouseClicked
        // TODO add your handling code here:
        userLogOut();
    }//GEN-LAST:event_logOutIconMouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        // TODO add your handling code here:
        new Add_Panel(sessionId).setVisible(true);
        this.dispose();
            
    }//GEN-LAST:event_addButtonMouseClicked

    private void reportJlableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseEntered
        // TODO add your handling code here:
       reportLable.setForeground(Color.white);
    }//GEN-LAST:event_reportJlableMouseEntered

    private void reportJlableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseExited
        // TODO add your handling code here:
        reportLable.setForeground(color);
    }//GEN-LAST:event_reportJlableMouseExited

    private void goalModifyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_goalModifyButtonMouseClicked
        // TODO add your handling code here:
        try(Connection con=Sql.getConnection()){
        String sql="SELECT * FROM income_goal_pfms where Id= ? and Username =? and Active =?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setString(3, "Yes");
        try(ResultSet results=pst.executeQuery()){
            if(results.next()){
                jLabel24.setText("MODIFY INCOME GOAL");
                jLabel30.setText("MODIFY GOAL");
                goalNameTextBox.setText(results.getString("Goal_Name"));
                targetAmountTextBox.setText(results.getString("Target"));
                if(results.getString("Duration").equals("Month")){
                    jRadioButton2.setSelected(true);
                }
                else{
                    jRadioButton1.setSelected(true);
                }
                jRadioButton2.setEnabled(false);
                jRadioButton1.setEnabled(false);
            }
            else{
                JOptionPane.showMessageDialog(null, "Please Add A Goal first");
            }
        } 
        
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }//GEN-LAST:event_goalModifyButtonMouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        // TODO add your handling code here
        if(jLabel30.getText().equals("MODIFY GOAL")){

           
           
           
           try(Connection con= Sql.getConnection()){
          
            String sql="UPDATE income_goal_pfms SET Goal_Name = ?, Target=? Where Id = ? and Username=? and Active=? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, goalNameTextBox.getText());
            pst.setString(2, targetAmountTextBox.getText());
            pst.setInt(3, sessionId);
            pst.setString(4, sessionUsername);
            pst.setString(5, "Yes");
            pst.executeUpdate();  
            
            jLabel24.setText("ADD INCOME GOAL");
            jLabel30.setText("ADD GOAL");
            
             goalNameTextBox.setText("");
             targetAmountTextBox.setText("");
             
            
                
             refreshData();
             
             jRadioButton2.setEnabled(false);
                jRadioButton1.setEnabled(false);
            
        }   catch (SQLException ex) {
            System.out.println(ex);
                Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
        else{
        
        
        
        try(Connection con= Sql.getConnection()){
        String sql="SELECT * FROM income_goal_pfms where Id= ? and Username =? and Active =?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setString(3, "Yes");
        try(ResultSet results=pst.executeQuery()){
            if(results.next()){
           JOptionPane.showMessageDialog(null, "Goal for the month/week alredy set");
              goalModifyButton.setVisible(true);
             targetAmountTextBox.setText("");
             goalNameTextBox.setText("");
            }
     
            else{
        
        LocalDate thisdate= LocalDate.now();
        LocalDate date=thisdate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ButtonModel thismonth = jRadioButton2.getModel();
        ButtonModel thisweek = jRadioButton1.getModel();
        String duration;
        String hitTarget,active;
        
        
         if(buttonGroup1.getSelection().equals(thismonth)){
                duration="Month";
            }
            else{
                duration="week";
            }
         
// get end date for month or week   
        if (duration.equals("week")){
           System.out.println("week");
           date= LocalDate.now();
           date= thisdate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1L);
           date=date.plusDays(1);
           String endofweek=date.plusWeeks(1).toString();
           System.out.println("start date"+date);
           System.out.println("end date"+endofweek);
           while( !date.toString().equals(endofweek)){
               date=date.plusDays(1);
           }
        }
        else{
             System.out.println("Month");
        int nextMonth=thisdate.getMonthValue();
        nextMonth++;
            if(nextMonth ==13){
        nextMonth=1;
       }
       
        date=date.minusDays(1);
         while(date.getMonthValue()!=nextMonth){
        date=date.plusDays(1);
             if(date.getMonthValue()==nextMonth){;
             break;
               }
         }
         date=date.minusDays(1);
         
        }
// get end date for month or week           
    

        
        if (targetAmountTextBox.getText().equals("") || goalNameTextBox.getText().equals("")){
            
        }
        else{
            
            try{
                int amount=Integer.parseInt(targetAmountTextBox.getText());
            
           
         LocalDate today= LocalDate.now();
           
            sql="INSERT into income_goal_pfms (Id,Username,Target,Date,Duration,Goal_Name,Hit_Target,Active,End_Date)"
                         + "values(?,?,?,?,?,?,?,?,?)";
             
             hitTarget="No";
             active="Yes";
             pst=con.prepareStatement(sql);
             pst.setInt(1, sessionId);
             pst.setString(2, sessionUsername);
             pst.setString(3, targetAmountTextBox.getText());
             pst.setString(4, today.toString());
             pst.setString(5, duration);
             pst.setString(6, goalNameTextBox.getText());
             pst.setString(7, hitTarget);
             pst.setString(8, active);
             pst.setString(9, date.toString());
             pst.execute();

             goalModifyButton.setVisible(true);
             targetAmountTextBox.setText("");
             goalNameTextBox.setText("");
             refreshData();
        }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Please check inputs");
            }
        }
            } } 
        }catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
        } 
        jRadioButton2.enable();
             jRadioButton1.enable();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Budgets(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        new Main_Panel(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void reportJlableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseClicked
        // TODO add your handling code here:
        new Main_Panel_Report(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_reportJlableMouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        jLabel14.setForeground(Color.white);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
        jLabel14.setForeground(color);
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
        jLabel12.setForeground(Color.white);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        // TODO add your handling code here:
        jLabel12.setForeground(color);
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
  new Main_Panel_Search(sessionId, sessionUsername).setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel11MouseClicked
    private void userLogOut (){
        try(Connection con= Sql.getConnection()){
            LocalDateTime now=LocalDateTime.now();
            String sql="UPDATE user_account_log_info SET logOut_Info = ? Where Id = ? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, now.atZone(ZoneId.of("GMT")).format(DateTimeFormatter.RFC_1123_DATE_TIME));
            pst.setInt(2, data);
            pst.executeUpdate();
            new Account_LogIn().setVisible(true);
            this.dispose();
            
        } catch (SQLException ex) {
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }
  
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Goal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Goal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Goal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Goal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Panel_Goal(0).setVisible(true);
                         
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addBudgetPanel;
    private javax.swing.JLabel addButton;
    private javax.swing.JLabel alertText;
    private javax.swing.JLabel backButton;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel balanceAmountBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel comsumptionMonth;
    private javax.swing.JLabel consumptionWeek;
    private javax.swing.JLabel durationBox;
    private javax.swing.JLabel durationBox2;
    private javax.swing.JLabel exitButton;
    private javax.swing.JLabel expenseFromDateBox;
    private javax.swing.JLabel goalModifyButton;
    private javax.swing.JTextField goalNameTextBox;
    private javax.swing.JLabel grossAmountTextBox;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel incomeFromDateBox;
    private javax.swing.JLabel incomeMonth;
    private javax.swing.JLabel incomeWeek;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JLabel logOutText;
    private javax.swing.JLabel mainExpense;
    private javax.swing.JLabel mainIncome;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel reportJlable;
    private javax.swing.JLabel reportLable;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel sidePanelName;
    private javax.swing.JLabel sidePanelSex;
    private javax.swing.JLabel sidePanelUsername;
    private javax.swing.JLabel targetAmountBox;
    private javax.swing.JTextField targetAmountTextBox;
    private javax.swing.JLabel uiDate;
    private javax.swing.JLabel uiTime;
    private javax.swing.JLabel userIcon;
    // End of variables declaration//GEN-END:variables
}
