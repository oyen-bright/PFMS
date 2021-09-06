/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOUSE OF PRAYER
 */


public class Main_Panel extends javax.swing.JFrame {

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
    
    public Main_Panel(int data, String data1) {
        this.data=data;
        this.data1=data1;
        this.getData_main();
        
        
        
        setUndecorated(true);
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
        String sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
        incomeWeek.setText(totallPriceWeek(sql));
        
        sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
        incomeMonth.setText(totallPriceMonth(sql));
        
        sql="SELECT Amount, Date from expense_pfms WHERE Id = ? and Username = ? ";
        consumptionWeek.setText(totallPriceWeek(sql));
        
        sql="SELECT Amount, Date from expense_pfms WHERE Id = ? and Username = ? ";
        comsumptionMonth.setText(totallPriceMonth(sql));
        
       // int incomeGross=Integer.parseInt(incomeWeek.getText())+Integer.parseInt(incomeMonth.getText());
       // int consumptionGross=Integer.parseInt(comsumptionMonth.getText())+Integer.parseInt(consumptionWeek.getText());
        
   
        
        grossAmountTextBox.setText("$"+calGross());
        mainDisplay();
        getTopExpense();
        
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
    
    public Main_Panel(int data) {
        this.data=data;
        this.getData();
        
        
        
        setUndecorated(true);
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
        String sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
       incomeWeek.setText(totallPriceWeek(sql));
        
        sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
       incomeMonth.setText(totallPriceMonth(sql));
        
        sql="SELECT Amount, Date from expense_pfms WHERE Id = ? and Username = ? ";
       consumptionWeek.setText(totallPriceWeek(sql));
        
        sql="SELECT Amount, Date from expense_pfms WHERE Id = ? and Username = ? ";
       comsumptionMonth.setText(totallPriceMonth(sql));
        
       // int incomeGross=Integer.parseInt(incomeWeek.getText())+Integer.parseInt(incomeMonth.getText());
        //int consumptionGross=Integer.parseInt(comsumptionMonth.getText())+Integer.parseInt(consumptionWeek.getText());
        
                grossAmountTextBox.setText("$ "+calGross());

        mainDisplay();
        getTopExpense();
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
                        System.out.println("usernameExist");
                        
                        
                       
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
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
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
                            
                        }
                        else{
                            System.out.println("User log in information not found");
                        }
                        }

        } catch (SQLException ex) {
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
   
    }
        
public String totallPriceWeek(String sql){
        int weekPrice = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String sqlDate;

        LocalDate thisdate= LocalDate.now();
        LocalDate date= LocalDate.now();


        date= thisdate.with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1L);
        date=date.plusDays(1);

        String endofweek=date.plusWeeks(1).toString();
        System.out.println("start date"+date);
        System.out.println("end date"+endofweek);


        System.out.println("third date"+date);
        int i=1;
        
        while( !date.toString().equals(endofweek)){
   
      //  System.out.println(i+": "+date);
        
        try(Connection con=Sql.getConnection()){
            pst=con.prepareStatement(sql);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            ResultSet results=pst.executeQuery();
            
            while(results.next()){
                java.util.Date getdate; 
                try {
                    getdate = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(results.getString("Date"));
                    sqlDate=formatter.format(getdate);
                    
                               if (sqlDate.equals(date.toString())){
                                   weekPrice+=results.getInt("Amount");
                                 //  System.out.println(weekPrice);
                                 //  System.out.println(results.getLong("Id"));
                                     }
                
                                  //  System.out.println(date);
                                 //   System.out.println(sqlDate);
                                    }
                catch (ParseException ex) {
                    System.out.println(ex);
                }
            }
           
            con.close();
        }
        
        catch(SQLException ex){
            System.out.println(ex);
        } 
         i++;
         date=date.plusDays(1);
    }
  return String.valueOf(weekPrice);
 }


public String totallPriceMonth(String sql){
    LocalDate thisdate= LocalDate.now();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String sqlDate;
    int monthPrice = 0;
    
    System.out.println(thisdate);
    int nextMonth=thisdate.getMonthValue();
    nextMonth++;
    if(nextMonth ==13){
        nextMonth=1;
    }
    thisdate=thisdate.plusDays(1);
    LocalDate date=thisdate.with(TemporalAdjusters.firstDayOfMonth());
    date=date.minusDays(1);
   
      try(Connection con=Sql.getConnection()){
    while(date.getMonthValue()!=nextMonth){
    date=date.plusDays(1);
    
    if(date.getMonthValue()==nextMonth){;
        break;
    }
    else{
        System.out.println("hi"+date);
 
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
           pst.setString(2, sessionUsername);
        try(ResultSet results=pst.executeQuery()){
            while (results.next()){
                 try { 
                              
                              java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(results.getString("Date")); 
                               sqlDate=formatter.format(getdate);
                              
                               if (sqlDate.equals(date.toString())){
                                   monthPrice+=results.getInt("Amount");
                                  // System.out.println(monthPrice);
                                 // System.out.println(results.getLong("expenditure_name"));
                                   
                               }
                          } catch (ParseException ex) {
                              System.out.println(ex);
                          }
               
            }
        }
        catch(SQLException ex){
        System.out.println(ex);
    }
     
   
   // System.out.println(date);
    }
}
     con.close();
      }
    catch(SQLException ex){
        System.out.println(ex);
    }
    return  String.valueOf(monthPrice);
           
   }
public void mainDisplay(){
     DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
      int rowCount = model.getRowCount();

             for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
            }
             String in_out;
    try(Connection con=Sql.getConnection()){
        String sql="(SELECT * from expense_pfms where Id= ? and Username=?) union (SELECT * from income_pfms where Id=? and Username=?) order by amount desc";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        pst.setInt(3, sessionId);
        pst.setString(4, sessionUsername);
        try(ResultSet result=pst.executeQuery()){
            while(result.next()){
                if(result.getString("Category").equals("Gifts") || result.getString("Category").equals("Other Incomes") ||
                   result.getString("Category").equals("Interest Money") || result.getString("Category").equals("Loan Income") ||     
                   result.getString("Category").equals("Salary") || result.getString("Category").equals("Selling Income") ||     
                    result.getString("Category").equals("Award")  
                                    ){
                    
                    in_out="+";
                }
                else{
                    in_out="-";
                }
                model.addRow(new Object[]{result.getString("Date"),in_out, "$ "+result.getString("Amount"), result.getString("Category"), result.getString("Location"),
            });
        }    
        }
     con.close();
    }   catch (SQLException ex) {
        System.out.println(ex);
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
    int noOfBudgets, noactiveBudget, complitedBudget;
      // otherBudgetTable.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 10));
        try(Connection con=Sql.getConnection()){
            noOfBudgets=0;
            noactiveBudget=0;
            complitedBudget=0;
            String sql="SELECT * from expense_budget_pfms where Id=? and Username=?";
            pst=con.prepareStatement(sql);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            try(ResultSet result=pst.executeQuery()){
                while(result.next()){
                   noOfBudgets++;
                   if(result.getString("Active").equals("Yes")){
                       noactiveBudget++;
                   }
                   if(result.getString("Target").equals("Yes")){
                    complitedBudget++;
                }
                   
                           
                           //model.addRow(new Object[]{noOfBudgets, result.getString("Category"), result.getString("Amount"), result.getShort("Balance"), result.getString("Date"),
                           //result.getString("Active")});
                }
                numberOfBugets.setText(String.valueOf(noOfBudgets));
                activeBudget.setText(String.valueOf(noactiveBudget));
                completedBudget.setText(String.valueOf(complitedBudget));
                
            }
            con.close();
}       catch (SQLException ex) {
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try(Connection con=Sql.getConnection()){
            String sql="SELECT * from income_goal_pfms where Id= ? and Username= ?";
            pst=con.prepareStatement(sql);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            try(ResultSet result=pst.executeQuery()){
                if(result.next()){
                    targetamountvalue.setText("$ "+result.getString("Target"));
                    durationvalue.setText(result.getString("Duration"));
                }
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void getTopExpense(){
    
    try(Connection con=Sql.getConnection()){
        String sql="SELECT * from expense_pfms where Id=? and Username=? order by amount desc";
        pst=con.prepareStatement(sql);
        pst.setInt(1, sessionId);
        pst.setString(2, sessionUsername);
        
        try(ResultSet result=pst.executeQuery()){
            int i=1;
            int total=0;
            while(result.next()){
                System.out.println(result.getString("Category"));
                   if(i==4){
                    break;
                }
                   else if(i==1){  
                        tag1.setText(result.getString("Category"));
                        tagxEpense1.setText("$"+result.getString("Amount"));
                        total+=Integer.parseInt(result.getString("Amount"));
                        i++;
                   }  
                   else if (i==2){
                        tag2.setText(result.getString("Category"));
                        tagxEpense2.setText("$"+result.getString("Amount"));
                        total+=Integer.parseInt(result.getString("Amount"));

                           i++;
                   }
                   else if (i==3){
                       
                   
                        tag3.setText(result.getString("Category"));
                        tagxEpense3.setText("$"+result.getString("Amount"));
                        total+=Integer.parseInt(result.getString("Amount"));

                        i++;
                    }   
                    
                }
               jLabel25.setText("$"+String.valueOf(total));
            }
        
        
    }   catch (SQLException ex) {
        System.out.println(ex);
        Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
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
        backgroundPanel = new RoundedPanel(50);
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
        sidePanelUsername1 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        mainPanel = new RoundedPanel(50);
        uiDate = new javax.swing.JLabel();
        uiTime = new javax.swing.JLabel();
        exitButton = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        backButton = new javax.swing.JLabel();
        alertText = new javax.swing.JLabel();
        timePeriodBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mainExpense = new javax.swing.JLabel();
        mainIncome = new javax.swing.JLabel();
        mainBudget = new javax.swing.JLabel();
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
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel2 = new RoundedPanel(30);
        jLabel20 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        completedBudget = new javax.swing.JLabel();
        activeBudget = new javax.swing.JLabel();
        numberOfBugets = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        targetamountvalue = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        durationvalue = new javax.swing.JLabel();
        jPanel3 = new RoundedPanel(30);
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        tag1 = new javax.swing.JLabel();
        tag2 = new javax.swing.JLabel();
        tag3 = new javax.swing.JLabel();
        tagxEpense1 = new javax.swing.JLabel();
        tagxEpense2 = new javax.swing.JLabel();
        tagxEpense3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(900, 50));
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        backgroundPanel.setBackground(new java.awt.Color(255, 153, 0));
        backgroundPanel.setOpaque(false);
        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidePanel.setBackground(new java.awt.Color(92, 84, 107));
        sidePanel.setForeground(new java.awt.Color(255, 246, 233));
        sidePanel.setToolTipText("");
        sidePanel.setOpaque(false);

        logOutIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        logOutIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logOutIconMouseClicked(evt);
            }
        });

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

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/uers.png"))); // NOI18N

        sidePanelName.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        sidePanelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelName.setText("Name ");

        sidePanelUsername.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        sidePanelUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelUsername.setText("username");

        sidePanelSex.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        sidePanelSex.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelSex.setText("Sex");

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("|");
        jLabel9.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 154, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/income.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 154, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/outcome.png"))); // NOI18N
        jLabel7.setAutoscrolls(true);

        jLabel8.setForeground(new java.awt.Color(255, 154, 0));
        jLabel8.setText("INCOMING");

        jLabel10.setForeground(new java.awt.Color(255, 154, 0));
        jLabel10.setText("CONSUMPTION");

        incomeWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        incomeWeek.setText("INCOME");

        incomeMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        incomeMonth.setText("INCOME");

        comsumptionMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        comsumptionMonth.setText("CONSUMPTION");

        consumptionWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        consumptionWeek.setText("CONSUMPTION");

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 154, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("MONTH");

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 154, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("WEEK");

        grossAmountTextBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        grossAmountTextBox.setText("GROSS");

        addButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addButton.setText("ADD");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });

        sidePanelUsername1.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        sidePanelUsername1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidePanelUsername1.setText("EDIT ACCOUNT");
        sidePanelUsername1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidePanelUsername1MouseClicked(evt);
            }
        });

        jLabel33.setText("HELP");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(sidePanelName)
                    .addComponent(jLabel5))
                .addGap(0, 0, 0)
                .addComponent(sidePanelSex))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(sidePanelUsername)
                .addGap(0, 0, 0)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel33))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sidePanelUsername1)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addComponent(logOutIcon)
                        .addGap(6, 6, 6)
                        .addComponent(logOutText))))
            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(grossAmountTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addComponent(incomeWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(consumptionWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addComponent(incomeMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comsumptionMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel6)
                .addGap(62, 62, 62)
                .addComponent(jLabel7))
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addGap(48, 48, 48)
                .addComponent(jLabel10))
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sidePanelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sidePanelUsername)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sidePanelSex))
                .addGap(63, 63, 63)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addGap(6, 6, 6)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel7)))
                .addGap(11, 11, 11)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(incomeMonth)
                            .addComponent(comsumptionMonth))))
                .addGap(16, 16, 16)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(sidePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(incomeWeek)
                            .addComponent(consumptionWeek))))
                .addGap(26, 26, 26)
                .addComponent(grossAmountTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel33)
                .addGap(51, 51, 51)
                .addComponent(sidePanelUsername1)
                .addGap(80, 80, 80)
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logOutIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logOutText, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        backgroundPanel.add(sidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 760));
        sidePanel.getAccessibleContext().setAccessibleName("");

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
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 20, 40, 30));

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
        mainPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, -1, -1));

        alertText.setFont(new java.awt.Font("Roboto Light", 0, 10)); // NOI18N
        alertText.setForeground(new java.awt.Color(255, 51, 0));
        alertText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainPanel.add(alertText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 250, -1));

        timePeriodBox.setBackground(new java.awt.Color(255, 153, 0));
        timePeriodBox.setFont(new java.awt.Font("Roboto Medium", 0, 8)); // NOI18N
        timePeriodBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL TIME", "YEAR", "MONTH", "WEEK", " " }));
        timePeriodBox.setBorder(null);
        timePeriodBox.setFocusable(false);
        timePeriodBox.setLightWeightPopupEnabled(false);
        timePeriodBox.setOpaque(false);
        timePeriodBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timePeriodBoxActionPerformed(evt);
            }
        });
        mainPanel.add(timePeriodBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 70, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel2.setText("BUDGET");
        mainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel13.setText("INCOME");
        mainPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, -1, -1));

        jLabel18.setFont(new java.awt.Font("Roboto Medium", 0, 10)); // NOI18N
        jLabel18.setText("EXPENSE");
        mainPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, -1, -1));

        mainExpense.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mainExpense.setText("VALUE");
        mainPanel.add(mainExpense, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, -1, -1));

        mainIncome.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mainIncome.setText("VALUE");
        mainPanel.add(mainIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, -1, -1));

        mainBudget.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        mainBudget.setText("VALUE");
        mainPanel.add(mainBudget, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 80, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setOpaque(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-dashboardNormal.png"))); // NOI18N
        jLabel1.setText("  ");
        jLabel1.setToolTipText("");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setIconTextGap(6);
        jLabel1.setInheritsPopupMenu(false);

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
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("DASHBORD");

        reportLable.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        reportLable.setForeground(new java.awt.Color(100, 113, 116));
        reportLable.setText("REPORT");

        jLabel14.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(100, 113, 116));
        jLabel14.setText("BUDGETS");

        jLabel17.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(100, 113, 116));
        jLabel17.setText("GOALS");

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/incomeS.png"))); // NOI18N
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel29MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel29MouseExited(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(100, 113, 116));
        jLabel30.setText("INCOME");

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/outcomes.png"))); // NOI18N
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel31MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel31MouseExited(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Roboto Medium", 1, 8)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(100, 113, 116));
        jLabel32.setText("EXPENSE");

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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(reportJlable)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addComponent(jLabel29)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(reportLable)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(jLabel30)
                    .addComponent(jLabel32)))
        );

        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 690, 450, 50));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jPanel2.setOpaque(false);

        jLabel20.setText("GOALS");

        jLabel21.setText("BUDGET");

        jLabel26.setText("NUMBER OF BUGETS");

        jLabel34.setText("ACTIVE");

        jLabel36.setText("COMPLETED");

        completedBudget.setText("VALUE");

        activeBudget.setText("VALUE");

        numberOfBugets.setText("VALUE");

        jLabel27.setText("TARGET AMOUNT");

        targetamountvalue.setText("VALUE");

        jLabel28.setText("DURATION");

        durationvalue.setText("VALUE");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(numberOfBugets))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(36, 36, 36)
                                .addComponent(activeBudget))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(completedBudget)))))
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel21)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator2)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(targetamountvalue)
                        .addGap(34, 34, 34))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(durationvalue)
                        .addGap(34, 34, 34))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(targetamountvalue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(durationvalue))
                .addGap(39, 39, 39)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(activeBudget))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberOfBugets)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(completedBudget))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        mainPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 210, 240));

        jPanel3.setOpaque(false);

        jLabel19.setText("TOP EXPENSE");

        jLabel22.setText("TAG");

        jLabel23.setText("EXPENSE");

        jLabel24.setText("TOTAL");

        jLabel25.setText("VALUE");

        tag1.setText("Tag1");

        tag2.setText("Tag3");

        tag3.setText("Tag3");

        tagxEpense1.setText("tagxEpense1");

        tagxEpense2.setText("tagxEpense2");

        tagxEpense3.setText("tagxEpense3");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tag3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tag2)
                                .addGap(118, 118, 118)))
                        .addContainerGap(88, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tag1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tagxEpense1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(97, 97, 97)
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tagxEpense2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tagxEpense3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tag1)
                    .addComponent(tagxEpense1))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tag2)
                    .addComponent(tagxEpense2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tag3)
                    .addComponent(tagxEpense3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        mainPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 120, -1, -1));

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DATE", "", "AMOUNT", "CATEGORY", "LOCATION"
            }
        ));
        transactionTable.setOpaque(false);
        transactionTable.setShowHorizontalLines(false);
        transactionTable.setShowVerticalLines(false);
        jScrollPane1.setViewportView(transactionTable);
        if (transactionTable.getColumnModel().getColumnCount() > 0) {
            transactionTable.getColumnModel().getColumn(0).setMinWidth(110);
            transactionTable.getColumnModel().getColumn(1).setMaxWidth(20);
            transactionTable.getColumnModel().getColumn(2).setMaxWidth(70);
        }

        mainPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 590, 280));

        backgroundPanel.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 850, 760));
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
        new Account_LogIn().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonMouseClicked

    private void mainPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainPanelMouseClicked
        // TODO add your handling code here:
        if(sidePanel.isVisible()){
               uiDate.setForeground(Color.black);
         uiTime.setForeground(Color.black);
        }
    }//GEN-LAST:event_mainPanelMouseClicked

    private void userIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userIconMouseClicked
        // TODO add your handling code here:
        if(sidePanel.isVisible()){
         
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

    private void timePeriodBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timePeriodBoxActionPerformed
        // TODO add your handling code here:
        switch (timePeriodBox.getSelectedIndex()) {
            case 0:
                String sql="SELECT SUM(Amount) from income_pfms where Id=? and Username = ?";
                try{
                Connection con= Sql.getConnection();
                pst=con.prepareStatement(sql);
                pst.setInt(1, sessionId);
                pst.setString(2, sessionUsername);
                try(ResultSet result=pst.executeQuery()){
                    if(result.next()){
                        mainIncome.setText(result.getString("SUM(Amount)"));
                    }
                }
                }
                catch (SQLException ex) {
                System.out.println(ex);
                Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
               sql="SELECT SUM(Amount) from expense_pfms where Id= ? and Username = ?";
                try{
                Connection con= Sql.getConnection();
                pst=con.prepareStatement(sql);
                pst.setInt(1, sessionId);
                pst.setString(2, sessionUsername);
                try(ResultSet result=pst.executeQuery()){
                    if(result.next()){
                        mainExpense.setText(result.getString("SUM(Amount)"));
                        //mainExpense.
                    }
                }                
                }
                catch (SQLException ex) {
                    
                Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                break;

            case 1:
                System.out.println("year");
                
                
                
                /////////////////////////////////////
                
                
                
                break;
            case 2:
                System.out.println("month");
                
                sql="SELECT Amount, Date from expense_pfms WHERE Id = ? and Username = ? ";
                mainExpense.setText(totallPriceMonth(sql));
             
                
                sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
                mainIncome.setText(totallPriceMonth(sql));
                
                break;
            default:
                System.out.println("week");
                 
                sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
                 mainIncome.setText(totallPriceWeek(sql));
                 
                sql="SELECT Amount, Date from income_pfms WHERE Id = ? and Username = ? ";
                 mainExpense.setText(totallPriceWeek(sql));
                
                
                break;
        }
    }//GEN-LAST:event_timePeriodBoxActionPerformed

    private void reportJlableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseEntered
        // TODO add your handling code here:
       reportLable.setForeground(Color.white);
    }//GEN-LAST:event_reportJlableMouseEntered

    private void reportJlableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseExited
        // TODO add your handling code here:
        reportLable.setForeground(color);
    }//GEN-LAST:event_reportJlableMouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        System.out.println(sessionId+"u"+sessionUsername);
        new Main_Panel_Budgets(sessionId, sessionUsername).setVisible(true);
        
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Goal(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void reportJlableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportJlableMouseClicked
        // TODO add your handling code here:
        new Main_Panel_Report(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_reportJlableMouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Income(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Expense(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        jLabel14.setForeground(Color.white);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
        jLabel14.setForeground(color);
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        jLabel17.setForeground(Color.white);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
         jLabel17.setForeground(color);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel29MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseEntered
        // TODO add your handling code here:
        jLabel30.setForeground(Color.white);
    }//GEN-LAST:event_jLabel29MouseEntered

    private void jLabel31MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseEntered
        // TODO add your handling code here:
        jLabel32.setForeground(Color.white);
    }//GEN-LAST:event_jLabel31MouseEntered

    private void jLabel31MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseExited
        // TODO add your handling code here:
         jLabel32.setForeground(color);
    }//GEN-LAST:event_jLabel31MouseExited

    private void jLabel29MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseExited
        // TODO add your handling code here:
         jLabel30.setForeground(color);
    }//GEN-LAST:event_jLabel29MouseExited

    private void sidePanelUsername1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidePanelUsername1MouseClicked
        // TODO add your handling code here:
        new Account_Modify(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_sidePanelUsername1MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        new Main_Panel_Search(sessionId, sessionUsername).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        if (Desktop.isDesktopSupported()) {
    try {
        File myFile = new File("C:\\Users\\HOUSE OF PRAYER\\Documents\\WeChat Files\\oyenbrihight5\\Files\\help.pdf");
        Desktop.getDesktop().open(myFile);
    } catch (IOException ex) {
       
        System.out.println(ex);
    }
}
    }//GEN-LAST:event_jLabel33MouseClicked
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
            Logger.getLogger(Main_Panel.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(Main_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Panel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Main_Panel(0).setVisible(true);
                         
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeBudget;
    private javax.swing.JLabel addButton;
    private javax.swing.JLabel alertText;
    private javax.swing.JLabel backButton;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel completedBudget;
    private javax.swing.JLabel comsumptionMonth;
    private javax.swing.JLabel consumptionWeek;
    private javax.swing.JLabel durationvalue;
    private javax.swing.JLabel exitButton;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JLabel logOutText;
    private javax.swing.JLabel mainBudget;
    private javax.swing.JLabel mainExpense;
    private javax.swing.JLabel mainIncome;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel numberOfBugets;
    private javax.swing.JLabel reportJlable;
    private javax.swing.JLabel reportLable;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel sidePanelName;
    private javax.swing.JLabel sidePanelSex;
    private javax.swing.JLabel sidePanelUsername;
    private javax.swing.JLabel sidePanelUsername1;
    private javax.swing.JLabel tag1;
    private javax.swing.JLabel tag2;
    private javax.swing.JLabel tag3;
    private javax.swing.JLabel tagxEpense1;
    private javax.swing.JLabel tagxEpense2;
    private javax.swing.JLabel tagxEpense3;
    private javax.swing.JLabel targetamountvalue;
    private javax.swing.JComboBox<String> timePeriodBox;
    private javax.swing.JTable transactionTable;
    private javax.swing.JLabel uiDate;
    private javax.swing.JLabel uiTime;
    private javax.swing.JLabel userIcon;
    // End of variables declaration//GEN-END:variables
}
