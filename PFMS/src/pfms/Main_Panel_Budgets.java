/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;

import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author HOUSE OF PRAYER
 */


public class Main_Panel_Budgets extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    int data;
    String  data1;
    PreparedStatement pst;
    String name = null,userName = null,sex =null;
    String sessionUsername=null;
    int sessionId;
    int updateNo;
     Color color=new Color(100,113,116);
    
    public Main_Panel_Budgets(int data, String data1) {
        this.data=data;
        this.data1=data1;
        this.getData_main();
        
        
        
        setUndecorated(true);
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
       
        
   
        
        grossAmountTextBox.setText(calGross());
        
        checkActive();
        getDisplay();
        checkActive();
        display();
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
                
                }    
    
    public Main_Panel_Budgets(int data) {
        this.data=data;
        System.out.println(data+"incoming data");
        this.getData();
        
        
        
        setUndecorated(true);
        
        
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
        
        
       grossAmountTextBox.setText(calGross());

        checkActive();
        getDisplay();
        checkActive();
        display();
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
                
                
    }
    public void checkActive(){
        LocalDate startDate;
    LocalDate endDate;
    int targetAmount;
    int balanceAmount;
    int number;
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try(Connection con=Sql.getConnection()){
        String sql="SELECT * FROM expense_budget_pfms where Id= ? and Username =? and Active =?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setString(3, "Yes");
        try(ResultSet result=pst.executeQuery()){
            while(result.next()){
                number=result.getInt("Number");
                
                System.out.println(result.getString("Date"));
                startDate= LocalDate.parse(result.getString("Date"), formatter1);
                System.out.println("is  here");
                LocalDate thisdate= LocalDate.now();
                endDate=LocalDate.parse(result.getString("End_Date"), formatter1);
           if (thisdate.isBefore(endDate)) {
               sql="UPDATE expense_budget_pfms set Target=?, Active=? where Number=? and Id=? and Username=?";
               pst=con.prepareStatement(sql);
              
               targetAmount=result.getInt("Amount");
               balanceAmount=result.getInt("Balance");
               
               System.out.println(result.getInt("Amount")+"target");
               System.out.println(result.getInt("Balance")+"Balance");
               if(targetAmount-balanceAmount==0||targetAmount-balanceAmount<0){
                   pst.setString(1, "Yes");
               }
               else{
                   pst.setString(1, "No");
               }
               pst.setString(2, "Yes");
               pst.setInt(3, number);
               pst.setInt(4, sessionId);
               pst.setString(5, sessionUsername);
               pst.execute();
          
            }
           else{
               sql="UPDATE expense_budget_pfms set Target=?, Active=? where Number=? and Id=? and Username=?";
               pst=con.prepareStatement(sql);
              
               targetAmount=result.getInt("Amount");
               balanceAmount=result.getInt("Balance");
               System.out.println(result.getInt("Amount")+"target");
               System.out.println(result.getInt("Balance")+"Balance");
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
                   
           }
        }
        }
        
        
        
    }   catch (SQLException ex) {
        System.out.println(ex);
            Logger.getLogger(Main_Panel_Goal.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        
        
    }
    
    public void getDisplay(){    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = null;
    String category;
    String cate = null;
    
        
    
    try(Connection con=Sql.getConnection()){
        String statement;
        String sql="SELECT * FROM expense_budget_pfms where Id= ? and Username =? and Active =?";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setString(3, "Yes");
        try(ResultSet results=pst.executeQuery()){
            while(results.next()){
                    
        date= LocalDate.parse(results.getString("Date"), formatter1);
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
   
    
         category=results.getString("category");
        
         if(category.equals("EXPENSES")){
             
             
             int balances=0;
             statement="SELECt * from expense_pfms where Username=? and Id=?";
             pst=con.prepareStatement(statement);
             pst.setString(1, sessionUsername);
             pst.setInt(2, sessionId);
             try(ResultSet result=pst.executeQuery()){
                 int i= 0;
                 while(result.next()){
                  
                  System.out.println(i);
                  System.out.println(result.getString("Number"));
                  System.out.println(result.getString("Category"));

              while(date.getMonthValue()!=nextMonth){
                
                     date=date.plusDays(1);
                if(date.getMonthValue()==nextMonth){
                        break;
                                        }
                else{
                    try{
                    java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                               sqlDate=formatter.format(getdate);
                               System.out.println(sqlDate);
                               System.out.println(date.toString());
                    if(sqlDate.equals(date.toString())){
                        System.out.println(result.getString("Amount"));
                        System.out.println(sqlDate);
                        balances+=result.getInt("Amount");
                        
                    }
                    }
                    catch (ParseException ex) {
                              System.out.println(ex);
                          }
                }
                 }
              i++;
              date= LocalDate.parse(results.getString("Date"), formatter1);
              date=date.minusDays(1);
              
             }
                 statement="UPDATE expense_budget_pfms set Balance =? where Username=? and Id=? and Number=?";
                 pst=con.prepareStatement(statement);
                 pst.setInt(1, balances);
                 pst.setString(2, sessionUsername);
                 pst.setInt(3, sessionId);
                 pst.setInt(4, results.getInt("Number"));
                 pst.execute();
                 
                         
             } catch (SQLException ex) {
                 System.out.println(ex);
                Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         else{
             
              switch(category){
             case "BILLS & UTILITIES":
                 cate="Bills & Utilities";
                 break;
             case  "BUSINESS":
                 cate="Business";
                 break;
             case "EDUCATION":
                 cate="Education";
                 break;
             case "TRAVEL":
                 cate="Travel";
                 break;
             case "FAMILY":
                 cate="Family";
                 break;
             case "FEES & CHARGES":
                 cate="Fees & Charges";
                 break;
             case "TRANSPORTATION":
                 cate="Transportation";
                 break;     
             case "FRIENDS & LOVER":
                 cate="Friends & Lover";
                 break;
             case "GIFTS & DONATIONS":
                 cate="Gifts & Donations";
                 break;
             case "HEALTH & FITNESS":
                 cate="Health & Fitness";
                 break;
             case "INSURANCES":
                 cate="Insurances";
                 break;
             case "FOOD & BEVERAGE":
                 cate="Food & Beverage";
                 break;
             case "OTHER EXPENSES":
                 cate="Other Expenses";
                 break;
             case "SHOPPING":
                 cate="Shopping";
                 break;
             case "INVESTMENT":
                 cate="Investment";
                 break;
             case "ENTERTAINMENT":
                 cate="Entertainment";
                 break;    
  
         }         
             
             int balances=0;
             statement="SELECt * from expense_pfms where Username=? and Id=? and Category=?";
             pst=con.prepareStatement(statement);
             pst.setString(1, sessionUsername);
             pst.setInt(2, sessionId);
             pst.setString(3, cate);
             try(ResultSet result=pst.executeQuery()){
                 int i= 0;
                 while(result.next()){
                  
                  System.out.println(i);
                  System.out.println(result.getString("Number"));
                  System.out.println(result.getString("Category"));

              while(date.getMonthValue()!=nextMonth){
                
                     date=date.plusDays(1);
                if(date.getMonthValue()==nextMonth){
                        break;
                                        }
                else{
                    try{
                    java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                               sqlDate=formatter.format(getdate);
                               System.out.println(sqlDate);
                               System.out.println(date.toString());
                    if(sqlDate.equals(date.toString())){
                        System.out.println(result.getString("Amount"));
                        System.out.println(sqlDate);
                        balances+=result.getInt("Amount");
                        
                    }
                    }
                    catch (ParseException ex) {
                              System.out.println(ex);
                          }
                }
                 }
              i++;
              date= LocalDate.parse(results.getString("Date"), formatter1);
              date=date.minusDays(1);
              
             }
                 statement="UPDATE expense_budget_pfms set Balance =? where Username=? and Id=? and Number=?";
                 pst=con.prepareStatement(statement);
                 pst.setInt(1, balances);
                 pst.setString(2, sessionUsername);
                 pst.setInt(3, sessionId);
                 pst.setInt(4, results.getInt("Number"));
                 pst.execute();
                 
                         
             } catch (SQLException ex) {
                 System.out.println(ex);
                Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         

                
            }
        }
        }
        
    
        catch (SQLException ex) {
            Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void display(){
       
        percentbar.setStringPainted(true);
        UIManager.put("percentbar.background", Color.GREEN);
        UIManager.put("percentbar.foreground", Color.BLUE);
        UIManager.put("percentbar.selectionBackground", Color.RED);
        UIManager.put("percentbar.selectionForeground", Color.GREEN);
        
        DefaultTableModel model = (DefaultTableModel) otherBudgetTable.getModel();
        DefaultTableModel model1 = (DefaultTableModel) topBudgetTable.getModel();
        int rowCount = model.getRowCount();

             for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
            }
              rowCount = model1.getRowCount();

             for (int i = rowCount - 1; i >= 0; i--) {
            model1.removeRow(i);
            }
        int noOfBudgets, noactiveBudget, complitedBudget;
       otherBudgetTable.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 10));
       topBudgetTable.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 10));
        try(Connection con=Sql.getConnection()){
            noOfBudgets=0;
            noactiveBudget=0;
            complitedBudget=0;
            String sql="SELECT * from expense_budget_pfms where Id=? and Username=?";
            pst=con.prepareStatement(sql);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            try(ResultSet result=pst.executeQuery()){
                int i=1;
                while(result.next()){
                   noOfBudgets++;
                   if(result.getString("Active").equals("Yes")){
                       noactiveBudget++;
                   }
                   if(result.getString("Target").equals("Yes")){
                    complitedBudget++;
                }
                   
                           
                           model.addRow(new Object[]{i, result.getString("Category"), result.getString("Amount"), result.getShort("Balance"), result.getString("Date"),
                     result.getString("End_Date"),result.getString("Active"),result.getString("Duration") });
                           i++;
                }
                numberOfBugets.setText(String.valueOf(noOfBudgets));
                activeBudget.setText(String.valueOf(noactiveBudget));
                completedBudget.setText(String.valueOf(complitedBudget));
                
            }
            sql="SELECT MAX(Amount) FROM expense_budget_pfms WHERE Id=? and Username=? and Active=?";
            pst=con.prepareStatement(sql);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            pst.setString(3, "Yes");
            try(ResultSet result1=pst.executeQuery()){
                if(result1.next()){
                    sql="SELECT * FROM expense_budget_pfms WHERE Id=? and Username=? and Active=? and Amount=?";
                    pst=con.prepareStatement(sql);
                    pst.setInt(1, sessionId);
                    pst.setString(2, sessionUsername);
                    pst.setString(3, "Yes");
                    pst.setString(4, result1.getString("MAX(Amount)"));
                    try(ResultSet result2=pst.executeQuery()){
                if(result2.next()){
                     percentbar.setMaximum(result2.getInt("Amount"));
                     percentbar.setValue(result2.getInt("Balance"));
                     if(result2.getString("Active")==null){
                         
                     }
                     else{
                     model1.addRow(new Object[]{"1", result2.getString("Category"), result2.getString("Amount"), result2.getShort("Balance"), result2.getString("Date"),
                     result2.getString("End_Date"),result2.getString("Active"),result2.getString("Duration") });
                     }
                }
                }
                }
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
            Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
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
        backgroundPanel = new RoundedPanel(50);
        mainPanel = new RoundedPanel(50);
        uiDate = new javax.swing.JLabel();
        uiTime = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
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
        expenceDisplayPanel = new RoundedPanel(50);
        jLabel2 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        activeBudget = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        topBudgetTable = new javax.swing.JTable();
        completedBudget = new javax.swing.JLabel();
        numberOfBugets = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        otherBudgetTable = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        updateButon = new javax.swing.JLabel();
        deleteButton = new javax.swing.JLabel();
        percentbar = new javax.swing.JProgressBar();
        javax.swing.JPanel addBudgetPanel = new RoundedPanel(30);
        jPanel4 = new RoundedPanel(15);
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tagTextBox = new javax.swing.JTextField();
        budgetTextBox = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        catCombo = new javax.swing.JComboBox<>();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel30 = new javax.swing.JLabel();
        exitButton = new javax.swing.JLabel();
        backButton = new javax.swing.JLabel();

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

        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Avater.png"))); // NOI18N
        userIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userIconMouseClicked(evt);
            }
        });
        mainPanel.add(userIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, -1));

        alertText.setFont(new java.awt.Font("Roboto Light", 0, 10)); // NOI18N
        alertText.setForeground(new java.awt.Color(255, 51, 0));
        alertText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainPanel.add(alertText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 250, -1));

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel13.setText("INCOME");
        mainPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, -1, -1));

        jLabel18.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel18.setText("EXPENSE");
        mainPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        mainExpense.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mainExpense.setText("VALUE");
        mainPanel.add(mainExpense, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        mainIncome.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mainIncome.setText("VALUE");
        mainPanel.add(mainIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

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

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-goalsNormal.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

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
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("BUDGETS");

        jLabel17.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(100, 113, 116));
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

        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 580, 320, 50));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-budgetsTitle.png"))); // NOI18N
        mainPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel20.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel20.setText("EXPENSE BUDGETS");
        mainPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        expenceDisplayPanel.setBackground(new java.awt.Color(255, 255, 255));
        expenceDisplayPanel.setForeground(new java.awt.Color(255, 255, 255));
        expenceDisplayPanel.setOpaque(false);

        jLabel2.setText("NUMBER OF BUGETS");

        jLabel38.setText("TOP BUDGET");

        jLabel34.setText("ACTIVE");

        activeBudget.setForeground(new java.awt.Color(255, 0, 51));
        activeBudget.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        activeBudget.setText("VALUE");

        jLabel42.setText("OTHER BUDGETS");

        topBudgetTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        topBudgetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Category", "Amount", "Balance", "Date", "End Date", "Active", "Duration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        topBudgetTable.setShowHorizontalLines(false);
        topBudgetTable.setShowVerticalLines(false);
        jScrollPane2.setViewportView(topBudgetTable);
        if (topBudgetTable.getColumnModel().getColumnCount() > 0) {
            topBudgetTable.getColumnModel().getColumn(0).setMaxWidth(10);
            topBudgetTable.getColumnModel().getColumn(2).setMaxWidth(60);
            topBudgetTable.getColumnModel().getColumn(3).setMaxWidth(60);
            topBudgetTable.getColumnModel().getColumn(4).setMaxWidth(60);
            topBudgetTable.getColumnModel().getColumn(5).setMaxWidth(60);
            topBudgetTable.getColumnModel().getColumn(6).setMaxWidth(50);
        }

        completedBudget.setForeground(new java.awt.Color(51, 255, 51));
        completedBudget.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completedBudget.setText("VALUE");

        numberOfBugets.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfBugets.setText("VALUE");

        otherBudgetTable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        otherBudgetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Category", "Amount", "Balance", "Date", "End Date", "Active", "Duration"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        otherBudgetTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        otherBudgetTable.setShowHorizontalLines(false);
        otherBudgetTable.setShowVerticalLines(false);
        jScrollPane3.setViewportView(otherBudgetTable);
        if (otherBudgetTable.getColumnModel().getColumnCount() > 0) {
            otherBudgetTable.getColumnModel().getColumn(0).setMaxWidth(10);
            otherBudgetTable.getColumnModel().getColumn(1).setResizable(false);
            otherBudgetTable.getColumnModel().getColumn(2).setMaxWidth(60);
            otherBudgetTable.getColumnModel().getColumn(3).setMaxWidth(60);
            otherBudgetTable.getColumnModel().getColumn(4).setMaxWidth(60);
            otherBudgetTable.getColumnModel().getColumn(5).setMaxWidth(60);
            otherBudgetTable.getColumnModel().getColumn(6).setMaxWidth(50);
            otherBudgetTable.getColumnModel().getColumn(7).setMaxWidth(60);
        }

        jLabel36.setText("COMPLETED");

        updateButon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        updateButon.setText("UPDATE");
        updateButon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButonMouseClicked(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        deleteButton.setText("DELETE");
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout expenceDisplayPanelLayout = new javax.swing.GroupLayout(expenceDisplayPanel);
        expenceDisplayPanel.setLayout(expenceDisplayPanelLayout);
        expenceDisplayPanelLayout.setHorizontalGroup(
            expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expenceDisplayPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(updateButon)
                .addGap(21, 21, 21)
                .addComponent(deleteButton)
                .addGap(57, 57, 57))
            .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel42))
                    .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel38)
                        .addGap(18, 18, 18)
                        .addComponent(percentbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfBugets, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(activeBudget)
                            .addComponent(jLabel34))
                        .addGap(43, 43, 43)
                        .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(completedBudget, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        expenceDisplayPanelLayout.setVerticalGroup(
            expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expenceDisplayPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel34)
                    .addComponent(jLabel36))
                .addGap(16, 16, 16)
                .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberOfBugets)
                    .addComponent(activeBudget)
                    .addComponent(completedBudget))
                .addGap(35, 35, 35)
                .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(percentbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel42)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(expenceDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateButon)
                    .addComponent(deleteButton))
                .addContainerGap())
        );

        mainPanel.add(expenceDisplayPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 490, 440));

        addBudgetPanel.setBackground(new java.awt.Color(235, 239, 229));
        addBudgetPanel.setOpaque(false);

        jPanel4.setBackground(new java.awt.Color(255, 153, 0));
        jPanel4.setOpaque(false);

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("ADD BUDGETS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel26.setText("TAG NAME");

        tagTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        budgetTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel27.setText("BUDGETED AMOUNT  ");

        jLabel29.setText("CATEGORY");

        catCombo.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        catCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EXPENSES", "BILLS & UTILITIES", "BUSINESS", "EDUCATION", "TRAVEL", "FAMILY", "FEES & CHARGES", "TRANSPORTATION", "FRIENDS & LOVER", "GIFTS & DONATIONS", "HEALTH & FITNESS", "INSURANCES", "FOOD & BEVERAGE", "OTHER EXPENSES", "SHOPPING", "INVESTMENT", "ENTERTAINMENT", " " }));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("THIS WEEK");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("THIS MONTH");

        jLabel30.setBackground(new java.awt.Color(0, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("ADD BUDGET");
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
                .addGap(12, 12, 12)
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel27)
                    .addComponent(budgetTextBox)
                    .addComponent(jLabel29)
                    .addComponent(catCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tagTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addGroup(addBudgetPanelLayout.createSequentialGroup()
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton1))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addBudgetPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        addBudgetPanelLayout.setVerticalGroup(
            addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addBudgetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(budgetTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(catCombo)
                .addGap(18, 18, 18)
                .addGroup(addBudgetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(27, 27, 27)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        mainPanel.add(addBudgetPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, 260, 360));

        exitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.png"))); // NOI18N
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, 40, 30));

        backButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Back.png"))); // NOI18N
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });
        mainPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, -1, -1));

        backgroundPanel.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 810, 645));
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

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        // TODO add your handling code 
        if(jLabel30.getText().equals("MODIFY")){
              System.out.println("I did this");
            String duration;
        try(Connection con=Sql.getConnection()){
            ButtonModel thismonth = jRadioButton2.getModel();
            ButtonModel thisweek = jRadioButton1.getModel();
            if(buttonGroup1.getSelection().equals(thismonth)){
                duration="Month";
            }
            else{
                duration="week";
            }
            String sql="UPDATE expense_budget_pfms set Category=? , Amount=? ,Duration=?, Tag=? where Number=? and Id=? and Username=? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, (catCombo.getSelectedItem()).toString());
            pst.setString(2,  budgetTextBox.getText());
            pst.setString(3, duration);
            pst.setString(4, tagTextBox.getText());
            pst.setInt(5, updateNo);
            pst.setInt(6, sessionId);
            pst.setString(7, sessionUsername);
            pst.execute();
            jLabel30.setText("ADD BUDGET");
            jLabel24.setText("ADD BUDGETS");
            tagTextBox.setText("");
            budgetTextBox.setText("");
            display();
            thisweek.setEnabled(true);
            thismonth.setEnabled(true);
        }   catch (SQLException ex) {
                Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        
        
        
        }
        
        else{
            System.out.println("I did else");
        LocalDate thisdate= LocalDate.now();
        LocalDate date=thisdate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        ButtonModel thismonth = jRadioButton2.getModel();
        ButtonModel thisweek = jRadioButton1.getModel();
        
        System.out.println(sessionUsername+"bug");
        String duration;
        String category;
        String target,active;
        System.out.println(catCombo.getSelectedItem()+"hi");
        
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
       
        
        if (budgetTextBox.getText().equals("")){
            JOptionPane.showMessageDialog(null, "empty fields");
        }
        else{
            
            
            
          
      
         LocalDate today= LocalDate.now();
         try(Connection con=Sql.getConnection()){
             
             try{
                 int amount=Integer.parseInt(budgetTextBox.getText());
           
             
             
             String sql;


                 sql="INSERT into expense_budget_pfms (Id,Username,Category,Amount,Date,Duration,Tag,Target,Active,End_Date)"
                         + "values(?,?,?,?,?,?,?,?,?,?)";
        
             target="No";
             active="Yes";
             pst=con.prepareStatement(sql);
             pst.setInt(1, sessionId);
             pst.setString(2, sessionUsername);
             pst.setString(3, (catCombo.getSelectedItem()).toString());
             pst.setString(4, budgetTextBox.getText());
             pst.setString(5, today.toString());
             pst.setString(6, duration);
             pst.setString(7, tagTextBox.getText());
             pst.setString(8, target);
             pst.setString(9, active);
             pst.setString(10, date.toString());
             pst.execute();
               }  catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(null, "Please check Inputs");
            }
             
         }  catch (SQLException ex) {
             System.out.println(ex);
               // Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            display();
        }
        }
    }//GEN-LAST:event_jLabel30MouseClicked

    private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseClicked
        // TODO add your handling code here
        int row,column,column1;
        row=otherBudgetTable.getSelectedRow();
        System.out.println(row);
        column=1;
        column1=2;
        System.out.println(otherBudgetTable.getValueAt(row, column));
        System.out.println(otherBudgetTable.getValueAt(row, column1));
       if(row==-1){
           
       }
       else{
           System.out.println(otherBudgetTable.getValueAt(row, column));
        System.out.println(otherBudgetTable.getValueAt(row, column1));
        try(Connection con=Sql.getConnection()){
            String sql="Delete from expense_budget_pfms where Category=? and Amount=? and Id=? and Username=? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, otherBudgetTable.getValueAt(row, column).toString());
            pst.setString(2, otherBudgetTable.getValueAt(row, column1).toString());
            pst.setInt(3, sessionId);
            pst.setString(4, sessionUsername);
            pst.execute();
            display();
        }   catch (SQLException ex) {
            System.out.println(ex);
                Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
           
        
    }//GEN-LAST:event_deleteButtonMouseClicked

    private void updateButonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButonMouseClicked
        // TODO add your handling code here:
           int row,column,column1;
            ButtonModel thismonth = jRadioButton2.getModel();
            ButtonModel thisweek = jRadioButton1.getModel();
        row=otherBudgetTable.getSelectedRow();
        System.out.println(row);
        column=1;
        column1=2;
        if(row==-1){}
                  else{
            jLabel24.setText("MODIFY BUDGET");
            jLabel30.setText("MODIFY");
           System.out.println(otherBudgetTable.getValueAt(row, column));
        System.out.println(otherBudgetTable.getValueAt(row, column1));
        try(Connection con=Sql.getConnection()){
            String sql="select * from expense_budget_pfms where Category=? and Amount=? and Id=? and Username=? ";
            pst=con.prepareStatement(sql);
            pst.setString(1, otherBudgetTable.getValueAt(row, column).toString());
            pst.setString(2, otherBudgetTable.getValueAt(row, column1).toString());
            pst.setInt(3, sessionId);
            pst.setString(4, sessionUsername);
            try (ResultSet result=pst.executeQuery()){
                if( result.next()){
                    tagTextBox.setText(result.getString("Tag"));
                    budgetTextBox.setText(result.getString("Amount"));
                    int index = 0;
                    switch (result.getString("Category")){

                            case "EXPENSES":
                                index=0;
                                break;
                            case "BILLS & UTILITIES":
                                index=1;
                                break;  
                            case "BUSINESS":
                                index=2;
                                break;    
                            case "EDUCATION":
                                index=3;
                                break;     
                            case "TRAVEL":
                                index=4;
                                break;     
                            case "FAMILY":
                                index=5;
                                break;     
                            case "FEES & CHARGES":
                                index=6;
                                break; 
                            case "TRANSPORTATION":
                                index=7;
                                break;     
                            case "FRIENDS & LOVER":
                                index=8;
                                break;     
                            case "GIFTS & DONATIONS":
                                index=9;
                                break;     
                            case "HEALTH & FITNESS":
                                index=10;
                                break; 
                            case "INSURANCES":
                                index=11;
                                break;     
                            case "FOOD & BEVERAGE":
                                index=12;
                                break; 
                            case "OTHER EXPENSES":
                                index=13;
                                break;     
                                
                            case "SHOPPING":
                                index=14;
                                break; 
                            case "INVESTMENT":
                                index=15;
                                break;     
                            case "ENTERTAINMENT":
                                index=16;
                                break;     
                                
                    }
                    catCombo.setSelectedIndex(index);
                    if (result.getString("Duration").equals("Month")){
                        thismonth.setSelected(true);
                    }
                    else{
                        thisweek.setSelected(true);
                    }
                    updateNo=result.getInt("Number");
                }
                thismonth.setEnabled(false);
                thisweek.setEnabled(false);
            }
            display();
        }   catch (SQLException ex) {
            System.out.println(ex);
                Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       }
    }//GEN-LAST:event_updateButonMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        new Main_Panel(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Goal(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        jLabel17.setForeground(Color.white);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        jLabel17.setForeground(color);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
        jLabel12.setForeground(Color.white);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        // TODO add your handling code here:
        jLabel12.setForeground(color);
    }//GEN-LAST:event_jLabel1MouseExited

    private void reportJlableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseClicked
        // TODO add your handling code here:
        new Main_Panel_Report(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_reportJlableMouseClicked

    private void reportJlableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseEntered
        // TODO add your handling code here:
        reportLable.setForeground(Color.white);
    }//GEN-LAST:event_reportJlableMouseEntered

    private void reportJlableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseExited
        // TODO add your handling code here:
        reportLable.setForeground(color);
    }//GEN-LAST:event_reportJlableMouseExited

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Search(sessionId, sessionUsername).setVisible(true);
        this.dispose();
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
            Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(Main_Panel_Budgets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Budgets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Budgets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Budgets.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Panel_Budgets(0).setVisible(true);
                         
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeBudget;
    private javax.swing.JLabel addButton;
    private javax.swing.JLabel alertText;
    private javax.swing.JLabel backButton;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JTextField budgetTextBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> catCombo;
    private javax.swing.JLabel completedBudget;
    private javax.swing.JLabel comsumptionMonth;
    private javax.swing.JLabel consumptionWeek;
    private javax.swing.JLabel deleteButton;
    private javax.swing.JLabel exitButton;
    private javax.swing.JPanel expenceDisplayPanel;
    private javax.swing.JLabel grossAmountTextBox;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JLabel logOutText;
    private javax.swing.JLabel mainExpense;
    private javax.swing.JLabel mainIncome;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel numberOfBugets;
    private javax.swing.JTable otherBudgetTable;
    private javax.swing.JProgressBar percentbar;
    private javax.swing.JLabel reportJlable;
    private javax.swing.JLabel reportLable;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel sidePanelName;
    private javax.swing.JLabel sidePanelSex;
    private javax.swing.JLabel sidePanelUsername;
    private javax.swing.JTextField tagTextBox;
    private javax.swing.JTable topBudgetTable;
    private javax.swing.JLabel uiDate;
    private javax.swing.JLabel uiTime;
    private javax.swing.JLabel updateButon;
    private javax.swing.JLabel userIcon;
    // End of variables declaration//GEN-END:variables
}
