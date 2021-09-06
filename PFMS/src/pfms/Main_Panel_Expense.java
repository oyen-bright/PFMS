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
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author HOUSE OF PRAYER
 */


public class Main_Panel_Expense extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    String firstdate;
       int updateNumber=0;
    int data;
    String  data1;
    PreparedStatement pst;
    String name = null,userName = null,sex =null;
    String sessionUsername=null;
    int sessionId;
    int updateNo;
     Color color=new Color(100,113,116);
    
    public Main_Panel_Expense(int data, String data1) {
        this.data=data;
        this.data1=data1;
        this.getData_main();
        
        
        
        setUndecorated(true);
        
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
      
        
   
        
        grossAmountTextBox.setText(calGross());
        
 
        incomeCatPanel.setVisible(false);
        exCatPanel.setVisible(false);
        sidePanel.setVisible(false);
        datePanel.setVisible(false);
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
        getTableInfo();
                }    
    
    public Main_Panel_Expense(int data) {
        this.data=data;
        System.out.println(data+"incoming data");
        this.getData();
        
        
        
        setUndecorated(true);
        
        
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
        
        
        
        grossAmountTextBox.setText(calGross());


        incomeCatPanel.setVisible(false);
        exCatPanel.setVisible(false);
        sidePanel.setVisible(false);
        datePanel.setVisible(false);
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
                
         getTableInfo();       
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
            Logger.getLogger(Main_Panel_Expense.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel_Expense.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel_Expense.class.getName()).log(Level.SEVERE, null, ex);
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

public void getTableInfo(){
     SimpleDateFormat formatter = new SimpleDateFormat("EEE-yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss a");
        
        String date,time;
    
       DefaultTableModel model1 = (DefaultTableModel) incomeTable.getModel();
       System.out.println("here"); 
           int rowCount = model1.getRowCount();
           for (int i = rowCount - 1; i >= 0; i--) {
            model1.removeRow(i);
            }
                 
       try(Connection con=Sql.getConnection()){
           String sql="SELECT * FROM expense_pfms where Id=? and Username=?";
           pst=con.prepareStatement(sql);
           pst.setInt(1, sessionId);
           pst.setString(2, sessionUsername);
           try(ResultSet result=pst.executeQuery()){
                       int i=0;

               while(result.next()){
                   
                   
                   java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                              if (i==0){
                                  firstdate=result.getString("Date");
                              }
                   
                   date=formatter.format(getdate);
                               time=formatter1.format(getdate);
                             
                   i++;
                  model1.addRow(new Object[]{i,date, result.getString("Category"), "$"+ result.getString("Amount"),result.getString("Location"), result.getString("Note")
               , time  });
           
               }
              if(incomeTable.getValueAt(0, 2)==null){
              
              }
              else{
                  selectCategoryTextBox.setText((incomeTable.getValueAt(0, 2)).toString());
              amountTextBox.setText((incomeTable.getValueAt(0, 3)).toString());
              dateTextBox.setText(firstdate);
              locationTextBox.setText((incomeTable.getValueAt(0, 4)).toString());
              noteTextBox.setText((incomeTable.getValueAt(0, 5)).toString()); 
              
                
         String statement="SELECT Number from expense_pfms where Id=? and Username=? and Category=? and Amount=? and Location=? and Note =?  ";
            pst=con.prepareStatement(statement);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            pst.setString(3, incomeTable.getValueAt(0, 2).toString());
            pst.setString(4, (incomeTable.getValueAt(0, 3).toString()).substring(1));
            pst.setString(5, incomeTable.getValueAt(0, 4).toString());
            pst.setString(6, incomeTable.getValueAt(0, 5).toString());
           try(ResultSet rs=pst.executeQuery()){
               if(rs.next()){
                   updateNumber=rs.getInt("Number");
               }
           }
              }
      
               
           } catch (ParseException ex) {
                          System.out.println(ex);

                Logger.getLogger(Main_Panel_Report.class.getName()).log(Level.SEVERE, null, ex);
            }
    
       } catch (SQLException ex) { 
           System.out.println(ex);
            Logger.getLogger(Main_Panel_Report.class.getName()).log(Level.SEVERE, null, ex);
        }  
}
public void getDetails(){
    
 
    int row;
    row=incomeTable.getSelectedRow();

             selectCategoryTextBox.setText((incomeTable.getValueAt(row, 2)).toString());
              amountTextBox.setText((incomeTable.getValueAt(row, 3)).toString());
             
              locationTextBox.setText((incomeTable.getValueAt(row, 4)).toString());
              noteTextBox.setText((incomeTable.getValueAt(row, 5)).toString()); 
       try(Connection con=Sql.getConnection()){
         String statement="SELECT Number, Date from expense_pfms where Id=? and Username=? and Category=? and Amount=? and Location=? and Note =?  ";
            pst=con.prepareStatement(statement);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            pst.setString(3, incomeTable.getValueAt(row, 2).toString());
            pst.setString(4, (incomeTable.getValueAt(row, 3).toString()).substring(1));
            pst.setString(5, incomeTable.getValueAt(row, 4).toString());
            pst.setString(6, incomeTable.getValueAt(row, 5).toString());
           try(ResultSet rs=pst.executeQuery()){
               if(rs.next()){
                   updateNumber=rs.getInt("Number");
                    dateTextBox.setText(rs.getString("Date"));
               }
           }
        
    }      catch (SQLException ex) {
        System.out.println(ex);
               Logger.getLogger(Main_Panel_Expense.class.getName()).log(Level.SEVERE, null, ex);
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
        incomeCatPanel = new RoundedPanel(50);
        jLabel25 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        incomCatOkButton = new javax.swing.JButton();
        awardCheckBox = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        giftCheckBox = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        intrestCheckBox = new javax.swing.JCheckBox();
        jLabel30 = new javax.swing.JLabel();
        salaryCheckBox = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        loanCheckBox = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        sellingCheckBox = new javax.swing.JCheckBox();
        jLabel43 = new javax.swing.JLabel();
        incomeOtherCheckBox = new javax.swing.JCheckBox();
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        selectCateButtonGroup = new javax.swing.ButtonGroup();
        CateButtonGroup = new javax.swing.ButtonGroup();
        CateButtonGroup1 = new javax.swing.ButtonGroup();
        selectCateButtonGroup1 = new javax.swing.ButtonGroup();
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
        exitButton = new javax.swing.JLabel();
        backButton = new javax.swing.JLabel();
        datePanel = new RoundedPanel(50);
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        datePanelDate = new com.toedter.calendar.JCalendar();
        datePanelOkButton = new javax.swing.JButton();
        exCatPanel = new RoundedPanel(50);
        jLabel34 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        exOkButton = new javax.swing.JButton();
        billsCheckBox = new javax.swing.JCheckBox();
        jLabel27 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        businessCheckBox = new javax.swing.JCheckBox();
        jLabel37 = new javax.swing.JLabel();
        educationCheckBox = new javax.swing.JCheckBox();
        entertainmentCheckBox = new javax.swing.JCheckBox();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        familyCheckBox = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        fessCheckBox = new javax.swing.JCheckBox();
        jLabel32 = new javax.swing.JLabel();
        foodCheckBox = new javax.swing.JCheckBox();
        jLabel41 = new javax.swing.JLabel();
        friendsCheckBox = new javax.swing.JCheckBox();
        jLabel49 = new javax.swing.JLabel();
        exgiftsCheckBox = new javax.swing.JCheckBox();
        jLabel50 = new javax.swing.JLabel();
        healthCheckBox = new javax.swing.JCheckBox();
        jLabel51 = new javax.swing.JLabel();
        insurancesCheckBox = new javax.swing.JCheckBox();
        jLabel52 = new javax.swing.JLabel();
        investmentCheckBox = new javax.swing.JCheckBox();
        jLabel53 = new javax.swing.JLabel();
        exotherCheckBox = new javax.swing.JCheckBox();
        jLabel54 = new javax.swing.JLabel();
        shoppingCheckBox = new javax.swing.JCheckBox();
        jLabel55 = new javax.swing.JLabel();
        transportationCheckBox = new javax.swing.JCheckBox();
        jLabel42 = new javax.swing.JLabel();
        travelCheckBox = new javax.swing.JCheckBox();
        expenceDisplayPanel = new RoundedPanel(50);
        jLabel38 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        mainMainPanel = new RoundedPanel(50);
        jSeparator2 = new javax.swing.JSeparator();
        selectCategoryTextBox = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        amountTextBox = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel45 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        dateTextBox = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        locationTextBox = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        noteTextBox = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        deleteButton = new javax.swing.JLabel();
        updateButon = new javax.swing.JLabel();

        incomeCatPanel.setBackground(new java.awt.Color(0, 102, 102));
        incomeCatPanel.setOpaque(false);
        incomeCatPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Calender.png"))); // NOI18N
        incomeCatPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jLabel28.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("CATEGORY ");
        incomeCatPanel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, 20));

        incomCatOkButton.setText("OK");
        incomCatOkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomCatOkButtonActionPerformed(evt);
            }
        });
        incomeCatPanel.add(incomCatOkButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 100, 30));

        selectCateButtonGroup.add(awardCheckBox);
        awardCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        awardCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        awardCheckBox.setText("Award");
        awardCheckBox.setToolTipText("");
        awardCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        awardCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        awardCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(awardCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, 16));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/awardEs.png"))); // NOI18N
        incomeCatPanel.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/giftEs.png"))); // NOI18N
        incomeCatPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        selectCateButtonGroup.add(giftCheckBox);
        giftCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        giftCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        giftCheckBox.setText("Gifts");
        giftCheckBox.setToolTipText("");
        giftCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        giftCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        giftCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(giftCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, -1, 16));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/intrest moneyES.png"))); // NOI18N
        incomeCatPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, -1));

        selectCateButtonGroup.add(intrestCheckBox);
        intrestCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        intrestCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        intrestCheckBox.setText("Interest \nMoney");
        intrestCheckBox.setToolTipText("");
        intrestCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        intrestCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        intrestCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(intrestCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, 16));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/salaryES.png"))); // NOI18N
        incomeCatPanel.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        selectCateButtonGroup.add(salaryCheckBox);
        salaryCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        salaryCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        salaryCheckBox.setText("Salary");
        salaryCheckBox.setToolTipText("");
        salaryCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salaryCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salaryCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(salaryCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, 16));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/loanES.png"))); // NOI18N
        incomeCatPanel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, -1, -1));

        selectCateButtonGroup.add(loanCheckBox);
        loanCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        loanCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        loanCheckBox.setText("Loan");
        loanCheckBox.setToolTipText("");
        loanCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loanCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loanCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(loanCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, 16));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sellingES.png"))); // NOI18N
        incomeCatPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));

        selectCateButtonGroup.add(sellingCheckBox);
        sellingCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        sellingCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        sellingCheckBox.setText("Selling");
        sellingCheckBox.setToolTipText("");
        sellingCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sellingCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sellingCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(sellingCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, -1, 16));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/othersES.png"))); // NOI18N
        incomeCatPanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        selectCateButtonGroup.add(incomeOtherCheckBox);
        incomeOtherCheckBox.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        incomeOtherCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        incomeOtherCheckBox.setText("Others ");
        incomeOtherCheckBox.setToolTipText("");
        incomeOtherCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        incomeOtherCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        incomeOtherCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        incomeCatPanel.add(incomeOtherCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, 16));

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
        });

        reportJlable.setBackground(new java.awt.Color(255, 255, 102));
        reportJlable.setForeground(new java.awt.Color(255, 102, 255));
        reportJlable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Icon-reportsNormal2.png"))); // NOI18N
        reportJlable.addMouseListener(new java.awt.event.MouseAdapter() {
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

        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 610, 320, 50));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bach.png"))); // NOI18N
        mainPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel20.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel20.setText("EXPENSE");
        mainPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        exitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.png"))); // NOI18N
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 40, 30));

        backButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Back.png"))); // NOI18N
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });
        mainPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 50, -1, -1));

        datePanel.setBackground(new java.awt.Color(0, 102, 102));
        datePanel.setOpaque(false);
        datePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Calender.png"))); // NOI18N
        datePanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("DATE");
        datePanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, 20));

        datePanelDate.setDecorationBackgroundColor(new java.awt.Color(255, 145, 0));
        datePanelDate.setFocusCycleRoot(true);
        datePanelDate.setFont(new java.awt.Font("Roboto", 0, 9)); // NOI18N
        datePanelDate.setMaximumSize(new java.awt.Dimension(198, 147));
        datePanelDate.setMinimumSize(new java.awt.Dimension(188, 147));
        datePanelDate.setOpaque(false);
        datePanelDate.setPreferredSize(new java.awt.Dimension(198, 147));
        datePanel.add(datePanelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 360, 230));

        datePanelOkButton.setText("OK");
        datePanelOkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datePanelOkButtonActionPerformed(evt);
            }
        });
        datePanel.add(datePanelOkButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 117, 32));

        mainPanel.add(datePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 360, 450));

        exCatPanel.setBackground(new java.awt.Color(0, 102, 102));
        exCatPanel.setOpaque(false);
        exCatPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Calender.png"))); // NOI18N
        exCatPanel.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 20, -1, -1));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("CATEGORY ");
        exCatPanel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 21, -1, 20));

        exOkButton.setText("OK");
        exOkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exOkButtonActionPerformed(evt);
            }
        });
        exCatPanel.add(exOkButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 116, 32));

        CateButtonGroup1.add(billsCheckBox);
        billsCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        billsCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        billsCheckBox.setText("Bills ");
        billsCheckBox.setToolTipText("Bills & utilities");
        billsCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        billsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        billsCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(billsCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, 16));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/bills and utilityE.png"))); // NOI18N
        exCatPanel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/busnessE.png"))); // NOI18N
        exCatPanel.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        CateButtonGroup1.add(businessCheckBox);
        businessCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        businessCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        businessCheckBox.setText("Business");
        businessCheckBox.setToolTipText("");
        businessCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        businessCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        businessCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(businessCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, 16));

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/educationE.png"))); // NOI18N
        exCatPanel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        CateButtonGroup1.add(educationCheckBox);
        educationCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        educationCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        educationCheckBox.setText("Education");
        educationCheckBox.setToolTipText("");
        educationCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        educationCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        educationCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(educationCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, -1, 16));

        CateButtonGroup1.add(entertainmentCheckBox);
        entertainmentCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        entertainmentCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        entertainmentCheckBox.setText("Entertainment");
        entertainmentCheckBox.setToolTipText("");
        entertainmentCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        entertainmentCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        entertainmentCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(entertainmentCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, 16));

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/entertainmentE.png"))); // NOI18N
        exCatPanel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, -1, -1));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/familyE.png"))); // NOI18N
        exCatPanel.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        CateButtonGroup1.add(familyCheckBox);
        familyCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        familyCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        familyCheckBox.setText("Family");
        familyCheckBox.setToolTipText("");
        familyCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        familyCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        familyCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(familyCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, -1, 16));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/fess and chargesE.png"))); // NOI18N
        jLabel31.setToolTipText("Fess & Charges");
        exCatPanel.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, -1, -1));

        CateButtonGroup1.add(fessCheckBox);
        fessCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        fessCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        fessCheckBox.setText("Fess ");
        fessCheckBox.setToolTipText("Fess & Charges");
        fessCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fessCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fessCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(fessCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, -1, 16));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/food and drinkE.png"))); // NOI18N
        exCatPanel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, -1, -1));

        CateButtonGroup1.add(foodCheckBox);
        foodCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        foodCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        foodCheckBox.setText("Food ");
        foodCheckBox.setToolTipText("Food & Beverage");
        foodCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        foodCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        foodCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(foodCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, 16));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/friendnloverE.png"))); // NOI18N
        jLabel41.setToolTipText("Friends & Lover");
        exCatPanel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, -1, -1));

        CateButtonGroup1.add(friendsCheckBox);
        friendsCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        friendsCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        friendsCheckBox.setText("Friends ");
        friendsCheckBox.setToolTipText("Friends & Lover");
        friendsCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        friendsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        friendsCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(friendsCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, -1, 16));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/gift and donationsE.png"))); // NOI18N
        jLabel49.setToolTipText("Gifts & Donations");
        exCatPanel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        CateButtonGroup1.add(exgiftsCheckBox);
        exgiftsCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        exgiftsCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        exgiftsCheckBox.setText("Gifts ");
        exgiftsCheckBox.setToolTipText("Gifts & Donations");
        exgiftsCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exgiftsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exgiftsCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(exgiftsCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, 16));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Healt and fitnessE.png"))); // NOI18N
        exCatPanel.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, -1));

        CateButtonGroup1.add(healthCheckBox);
        healthCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        healthCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        healthCheckBox.setText("Health");
        healthCheckBox.setToolTipText("");
        healthCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        healthCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        healthCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(healthCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, -1, 16));

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/insurancesE.png"))); // NOI18N
        exCatPanel.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, -1, -1));

        CateButtonGroup1.add(insurancesCheckBox);
        insurancesCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        insurancesCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        insurancesCheckBox.setText("Insurances");
        insurancesCheckBox.setToolTipText("");
        insurancesCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        insurancesCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        insurancesCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(insurancesCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, -1, 16));

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/investmentE.png"))); // NOI18N
        exCatPanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, -1, -1));

        CateButtonGroup1.add(investmentCheckBox);
        investmentCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        investmentCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        investmentCheckBox.setText("Investment");
        investmentCheckBox.setToolTipText("");
        investmentCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        investmentCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        investmentCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(investmentCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, -1, 16));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/othersE.png"))); // NOI18N
        exCatPanel.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        CateButtonGroup1.add(exotherCheckBox);
        exotherCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        exotherCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        exotherCheckBox.setText("Others");
        exotherCheckBox.setToolTipText("");
        exotherCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exotherCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exotherCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(exotherCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, 16));

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/shoppingE.png"))); // NOI18N
        exCatPanel.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, -1, -1));

        CateButtonGroup1.add(shoppingCheckBox);
        shoppingCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        shoppingCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        shoppingCheckBox.setText("Shopping");
        shoppingCheckBox.setToolTipText("");
        shoppingCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        shoppingCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        shoppingCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(shoppingCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, 16));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/transportatonE.png"))); // NOI18N
        exCatPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        CateButtonGroup1.add(transportationCheckBox);
        transportationCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        transportationCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        transportationCheckBox.setText("Transportation");
        transportationCheckBox.setToolTipText("");
        transportationCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transportationCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        transportationCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(transportationCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, -1, 16));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/travelE.png"))); // NOI18N
        exCatPanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, -1, -1));

        CateButtonGroup1.add(travelCheckBox);
        travelCheckBox.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        travelCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        travelCheckBox.setText("Travel");
        travelCheckBox.setToolTipText("");
        travelCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        travelCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        travelCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        exCatPanel.add(travelCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, -1, 16));

        mainPanel.add(exCatPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, -1, -1));

        expenceDisplayPanel.setBackground(new java.awt.Color(255, 255, 255));
        expenceDisplayPanel.setForeground(new java.awt.Color(255, 255, 255));
        expenceDisplayPanel.setOpaque(false);
        expenceDisplayPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setText("EXPENSE");
        expenceDisplayPanel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 9, -1, -1));

        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Date", "Category", "Amount", "Location", "Note", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        incomeTable.setShowHorizontalLines(false);
        incomeTable.setShowVerticalLines(false);
        incomeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                incomeTableMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                incomeTableMouseReleased(evt);
            }
        });
        incomeTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                incomeTableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                incomeTableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(incomeTable);
        if (incomeTable.getColumnModel().getColumnCount() > 0) {
            incomeTable.getColumnModel().getColumn(0).setMaxWidth(20);
            incomeTable.getColumnModel().getColumn(3).setMaxWidth(60);
            incomeTable.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        expenceDisplayPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 610, 400));
        expenceDisplayPanel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 466, -1, -1));

        mainPanel.add(expenceDisplayPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 630, 440));

        mainMainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainMainPanel.setOpaque(false);
        mainMainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        mainMainPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 213, 20));

        selectCategoryTextBox.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        selectCategoryTextBox.setForeground(new java.awt.Color(92, 84, 107));
        selectCategoryTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        selectCategoryTextBox.setText("Select Category");
        selectCategoryTextBox.setAutoscrolls(false);
        selectCategoryTextBox.setBorder(null);
        selectCategoryTextBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectCategoryTextBox.setFocusable(false);
        selectCategoryTextBox.setOpaque(false);
        selectCategoryTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectCategoryTextBoxMouseClicked(evt);
            }
        });
        mainMainPanel.add(selectCategoryTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 220, 40));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/noteS.png"))); // NOI18N
        mainMainPanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, -1));

        amountTextBox.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        amountTextBox.setForeground(new java.awt.Color(92, 84, 107));
        amountTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        amountTextBox.setText("Amount");
        amountTextBox.setAutoscrolls(false);
        amountTextBox.setBorder(null);
        amountTextBox.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        amountTextBox.setFocusable(false);
        amountTextBox.setOpaque(false);
        amountTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                amountTextBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                amountTextBoxFocusLost(evt);
            }
        });
        amountTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                amountTextBoxMouseClicked(evt);
            }
        });
        amountTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountTextBoxActionPerformed(evt);
            }
        });
        mainMainPanel.add(amountTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 210, 40));
        mainMainPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 210, 10));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/CATS.png"))); // NOI18N
        mainMainPanel.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, 19));
        mainMainPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 210, 10));

        dateTextBox.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        dateTextBox.setForeground(new java.awt.Color(92, 84, 107));
        dateTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dateTextBox.setText("Date");
        dateTextBox.setAutoscrolls(false);
        dateTextBox.setBorder(null);
        dateTextBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        dateTextBox.setFocusable(false);
        dateTextBox.setOpaque(false);
        dateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateTextBoxMouseClicked(evt);
            }
        });
        mainMainPanel.add(dateTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 210, 40));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/amtS.png"))); // NOI18N
        mainMainPanel.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, 14));

        locationTextBox.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        locationTextBox.setForeground(new java.awt.Color(92, 84, 107));
        locationTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        locationTextBox.setText("Location");
        locationTextBox.setAutoscrolls(false);
        locationTextBox.setBorder(null);
        locationTextBox.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        locationTextBox.setFocusable(false);
        locationTextBox.setOpaque(false);
        locationTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                locationTextBoxFocusGained(evt);
            }
        });
        locationTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                locationTextBoxMouseClicked(evt);
            }
        });
        locationTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationTextBoxActionPerformed(evt);
            }
        });
        mainMainPanel.add(locationTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 289, 210, 40));
        mainMainPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 218, 10));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/DAS.png"))); // NOI18N
        mainMainPanel.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, -1, -1));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/LOCS.png"))); // NOI18N
        mainMainPanel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, -1, -1));

        noteTextBox.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        noteTextBox.setForeground(new java.awt.Color(92, 84, 107));
        noteTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        noteTextBox.setText("Note");
        noteTextBox.setAutoscrolls(false);
        noteTextBox.setBorder(null);
        noteTextBox.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        noteTextBox.setFocusable(false);
        noteTextBox.setOpaque(false);
        noteTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                noteTextBoxFocusGained(evt);
            }
        });
        noteTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noteTextBoxMouseClicked(evt);
            }
        });
        noteTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noteTextBoxActionPerformed(evt);
            }
        });
        mainMainPanel.add(noteTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 373, 210, 40));
        mainMainPanel.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 210, 10));

        deleteButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        deleteButton.setText("DELETE");
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteButtonMouseClicked(evt);
            }
        });
        mainMainPanel.add(deleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, -1, -1));

        updateButon.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        updateButon.setText("UPDATE");
        updateButon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButonMouseClicked(evt);
            }
        });
        mainMainPanel.add(updateButon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, -1, -1));

        mainPanel.add(mainMainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 100, 300, 520));

        backgroundPanel.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 910, 680));
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

    private void datePanelOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datePanelOkButtonActionPerformed
        // TODO add your handling code here:
        if (datePanelDate.getDate()==null){

        }
        else {
            dateTextBox.setText(datePanelDate.getDate().toString());
            System.out.println(datePanelDate.getDate().toString());
            datePanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
    }//GEN-LAST:event_datePanelOkButtonActionPerformed

    private void selectCategoryTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectCategoryTextBoxMouseClicked
        // TODO add your handling code here:
        ButtonModel billsModel = billsCheckBox.getModel();
        ButtonModel businessModel = businessCheckBox.getModel();
        ButtonModel educationModel = educationCheckBox.getModel();
        ButtonModel travelModel = travelCheckBox.getModel();
        ButtonModel familyModel = familyCheckBox.getModel();
        ButtonModel fessModel = fessCheckBox.getModel();
        ButtonModel transportationModel = transportationCheckBox.getModel();
        ButtonModel friendsModel = friendsCheckBox.getModel();
        ButtonModel exgiftsModel = exgiftsCheckBox.getModel();
        ButtonModel healthModel = healthCheckBox.getModel();
        ButtonModel insurancesModel = insurancesCheckBox.getModel();
        ButtonModel foodModel = foodCheckBox.getModel();
        ButtonModel exotherModel = exotherCheckBox.getModel();
        ButtonModel shoppingModel = shoppingCheckBox.getModel();
        ButtonModel investmentModel = investmentCheckBox.getModel();
        ButtonModel entertainmentModel = entertainmentCheckBox.getModel();
      
    
       if(selectCategoryTextBox.getText().equals("Bills & Utilities")){
           CateButtonGroup1.setSelected(billsModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Business")){
             CateButtonGroup1.setSelected(businessModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Education")){
           CateButtonGroup1.setSelected(educationModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Travel")){
           CateButtonGroup1.setSelected(travelModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Family")){
           CateButtonGroup1.setSelected(familyModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Fees & Charges")) {
            CateButtonGroup1.setSelected(fessModel, true);

        }
        else if (selectCategoryTextBox.getText().equals("Transportation")) {
           CateButtonGroup1.setSelected(transportationModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Friends & Lover")) {
           CateButtonGroup1.setSelected(friendsModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Gifts & Donations")) {
            CateButtonGroup1.setSelected(exgiftsModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Health & Fitness")) {
           CateButtonGroup1.setSelected(healthModel, true);

        }
        else if (selectCategoryTextBox.getText().equals("Insurances")) {
           CateButtonGroup1.setSelected(insurancesModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Food & Beverage")) {
            CateButtonGroup1.setSelected(foodModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Other Expenses")) {
           CateButtonGroup1.setSelected(exotherModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Shopping")) {
           CateButtonGroup1.setSelected(shoppingModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Investment")){
            CateButtonGroup1.setSelected(investmentModel, true);
        }
        else if (selectCategoryTextBox.getText().equals("Entertainment")){
           CateButtonGroup1.setSelected(entertainmentModel, true);
        }
        else{

        }
       
   mainMainPanel.setVisible(false);
        exCatPanel.setVisible(true);

    }//GEN-LAST:event_selectCategoryTextBoxMouseClicked

    private void amountTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amountTextBoxFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_amountTextBoxFocusGained

    private void amountTextBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amountTextBoxFocusLost
        // TODO add your handling code here:
        amountTextBox.setFocusable(false);
        if (amountTextBox.getText().equals("")){
            amountTextBox.setText("Amount");
        }
    }//GEN-LAST:event_amountTextBoxFocusLost

    private void amountTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amountTextBoxMouseClicked
        // TODO add your handling code here:
        amountTextBox.setFocusable(true);
        amountTextBox.setText("");
    }//GEN-LAST:event_amountTextBoxMouseClicked

    private void dateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateTextBoxMouseClicked
        
      //  try {
            // TODO add your handling code here:
         
        
            mainMainPanel.setVisible(false);
            datePanel.setVisible(true);
          //  String[] splited = dateTextBox.getText().split("\\s+"); 
           // java.util.Date getdate=new SimpleDateFormat("EEE-yyyy-MM-dd", Locale.US).parse(splited[0]);
          //  datePanelDate.setDate(getdate);
            
       
       // } //catch (ParseException ex) {
           // Logger.getLogger(Main_Panel_Income.class.getName()).log(Level.SEVERE, null, ex);
       // }
    }//GEN-LAST:event_dateTextBoxMouseClicked

    private void locationTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_locationTextBoxFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_locationTextBoxFocusGained

    private void locationTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_locationTextBoxMouseClicked
        // TODO add your handling code here:
        locationTextBox.setFocusable(true);
        locationTextBox.setText("");
    }//GEN-LAST:event_locationTextBoxMouseClicked

    private void locationTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationTextBoxActionPerformed
    }//GEN-LAST:event_locationTextBoxActionPerformed

    private void noteTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_noteTextBoxFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_noteTextBoxFocusGained

    private void noteTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noteTextBoxMouseClicked
        // TODO add your handling code here:

        noteTextBox.setText("");
        noteTextBox.setFocusable(true);
    }//GEN-LAST:event_noteTextBoxMouseClicked

    private void noteTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noteTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noteTextBoxActionPerformed

    private void incomCatOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomCatOkButtonActionPerformed
        // TODO add your handling code here:
        ButtonModel giftModel = giftCheckBox.getModel();
        ButtonModel incomeOtherModel = incomeOtherCheckBox.getModel();
        ButtonModel intrestModel = intrestCheckBox.getModel();
        ButtonModel loanModel = loanCheckBox.getModel();
        ButtonModel salaryModel = salaryCheckBox.getModel();
        ButtonModel sellingModel = sellingCheckBox.getModel();
        ButtonModel awardModel = awardCheckBox.getModel();

        if (selectCateButtonGroup.getSelection().equals(giftModel)){
            selectCategoryTextBox.setText("Gifts");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if (selectCateButtonGroup.getSelection().equals(incomeOtherModel)){
            selectCategoryTextBox.setText("Other Incomes");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if (selectCateButtonGroup.getSelection().equals(intrestModel)){
            selectCategoryTextBox.setText("Interest Money");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if (selectCateButtonGroup.getSelection().equals(loanModel)){
            selectCategoryTextBox.setText("Loan Income");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if (selectCateButtonGroup.getSelection().equals(salaryModel)){
            selectCategoryTextBox.setText("Salary");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if (selectCateButtonGroup.getSelection().equals(sellingModel)){
            selectCategoryTextBox.setText("Selling Income");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);

        }
        else if (selectCateButtonGroup.getSelection().equals(awardModel)){
            selectCategoryTextBox.setText("Award");
            incomeCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else{

        }

    }//GEN-LAST:event_incomCatOkButtonActionPerformed

    private void exOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exOkButtonActionPerformed
        // TODO add your handling code here:
        ButtonModel billsModel = billsCheckBox.getModel();
        ButtonModel businessModel = businessCheckBox.getModel();
        ButtonModel educationModel = educationCheckBox.getModel();
        ButtonModel travelModel = travelCheckBox.getModel();
        ButtonModel familyModel = familyCheckBox.getModel();
        ButtonModel fessModel = fessCheckBox.getModel();
        ButtonModel transportationModel = transportationCheckBox.getModel();
        ButtonModel friendsModel = friendsCheckBox.getModel();
        ButtonModel exgiftsModel = exgiftsCheckBox.getModel();
        ButtonModel healthModel = healthCheckBox.getModel();
        ButtonModel insurancesModel = insurancesCheckBox.getModel();
        ButtonModel foodModel = foodCheckBox.getModel();
        ButtonModel exotherModel = exotherCheckBox.getModel();
        ButtonModel shoppingModel = shoppingCheckBox.getModel();
        ButtonModel investmentModel = investmentCheckBox.getModel();
        ButtonModel entertainmentModel = entertainmentCheckBox.getModel();
        if(CateButtonGroup1.getSelection().equals(billsModel)){
            selectCategoryTextBox.setText("Bills & Utilities");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if (CateButtonGroup1.getSelection().equals(businessModel)){
            selectCategoryTextBox.setText("Business");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(educationModel))){
            selectCategoryTextBox.setText("Education");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(travelModel))){
            selectCategoryTextBox.setText("Travel");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(familyModel))){
            selectCategoryTextBox.setText("Family");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(fessModel))) {
            selectCategoryTextBox.setText("Fees & Charges");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);

        }
        else if ((CateButtonGroup1.getSelection().equals(transportationModel))) {
            selectCategoryTextBox.setText("Transportation");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(friendsModel))) {
            selectCategoryTextBox.setText("Friends & Lover");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(exgiftsModel))) {
            selectCategoryTextBox.setText("Gifts & Donations");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(healthModel))) {
            selectCategoryTextBox.setText("Health & Fitness");

        }
        else if ((CateButtonGroup1.getSelection().equals(insurancesModel))) {
            selectCategoryTextBox.setText("Insurances");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(foodModel))) {
            selectCategoryTextBox.setText("Food & Beverage");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(exotherModel))) {
            selectCategoryTextBox.setText("Other Expenses");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(shoppingModel))) {
            selectCategoryTextBox.setText("Shopping");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(investmentModel))){
            selectCategoryTextBox.setText("Investment");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else if ((CateButtonGroup1.getSelection().equals(entertainmentModel))){
            selectCategoryTextBox.setText("Entertainment");
            exCatPanel.setVisible(false);
            mainMainPanel.setVisible(true);
        }
        else{

        }

    }//GEN-LAST:event_exOkButtonActionPerformed

    private void amountTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountTextBoxActionPerformed

    private void incomeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeTableMouseClicked
        // TODO add your handling code here:
        getDetails();
    }//GEN-LAST:event_incomeTableMouseClicked

    private void incomeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_incomeTableKeyPressed
        // TODO add your handling code here:
        getDetails();
    }//GEN-LAST:event_incomeTableKeyPressed

    private void incomeTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_incomeTableKeyReleased
        // TODO add your handling code here:
        getDetails();
    }//GEN-LAST:event_incomeTableKeyReleased

    private void incomeTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeTableMouseReleased
        // TODO add your handling code here:
        getDetails();
    }//GEN-LAST:event_incomeTableMouseReleased

    private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteButtonMouseClicked
        // TODO add your handling code here
         int row;
         int delNumber = 0;
        row=incomeTable.getSelectedRow();
         
        if(row==-1){
        
        }
        else{
        try(Connection con=Sql.getConnection()){
            String statement="SELECT Number from expense_pfms where Id=? and Username=? and Category=? and Amount=? and Location=? and Note =?  ";
            pst=con.prepareStatement(statement);
            pst.setInt(1, sessionId);
            pst.setString(2, sessionUsername);
            pst.setString(3, incomeTable.getValueAt(row, 2).toString());
            pst.setString(4, (incomeTable.getValueAt(row, 3).toString()).substring(1));
            pst.setString(5, incomeTable.getValueAt(row, 4).toString());
            pst.setString(6, incomeTable.getValueAt(row, 5).toString());
            try(ResultSet rs=pst.executeQuery()){
                if(rs.next()){
                  delNumber=rs.getInt("Number");
                  System.out.println(delNumber+"me del");
                }
            }
            
            
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(Main_Panel_Expense.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        }
     
      

        
       if (delNumber==0){
        
        }
        else{
          System.out.println("was here");
            try(Connection con=Sql.getConnection()){
                String sql="Delete from expense_pfms where Number=? and Id=? and Username=? ";
                pst=con.prepareStatement(sql);
                pst.setInt(1, delNumber); 
                pst.setInt(2, sessionId);
                pst.setString(3, sessionUsername);
                pst.execute();
                getTableInfo();
            }   catch (SQLException ex) {
                System.out.println(ex);
                Logger.getLogger(Main_Panel_Budgets.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_deleteButtonMouseClicked

    private void updateButonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButonMouseClicked
        // TODO add your handling code here:
         String sql;
                     System.out.println("am sshappening");

        if(selectCategoryTextBox.getText().equals("") || selectCategoryTextBox.getText().equals("Select Category")
            || amountTextBox.getText().equals("") || amountTextBox.getText().equals("Amount") || dateTextBox.getText().equals("Date")
            || locationTextBox.getText().equals("")
            || noteTextBox.getText().equals("")|| updateNumber==0){

            System.out.println("am happening");
        }
        else{

            try(Connection con=Sql.getConnection()){
                
                    sql="UPDATE expense_pfms SET Category = ?, Amount=?, Date=? ,Location=?, Note=?  Where Id = ? and Username=? and Number =?";
                   
                
              

                pst=con.prepareStatement(sql);
               
                pst.setString(1, selectCategoryTextBox.getText());
                try{
                 int amount=Integer.parseInt(amountTextBox.getText());
             pst.setString(2, (amountTextBox.getText()));
            }
            catch(java.lang.NumberFormatException ex){
                pst.setString(2, (amountTextBox.getText()).substring(1));
            }
                pst.setString(3, dateTextBox.getText());
                pst.setString(4, locationTextBox.getText());
                pst.setString(5, noteTextBox.getText());
                pst.setInt(6, sessionId);
                pst.setString(7, sessionUsername);
                pst.setInt(8, updateNumber);
                pst.execute();
             getTableInfo();
             calGross();
             
              

            }
            catch (SQLException ex) {
                Logger.getLogger(Add_Panel.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex);
            }

        }
      

       
        
    }//GEN-LAST:event_updateButonMouseClicked

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
            Logger.getLogger(Main_Panel_Expense.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(Main_Panel_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Expense.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Main_Panel_Expense(0).setVisible(true);
                         
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup CateButtonGroup;
    private javax.swing.ButtonGroup CateButtonGroup1;
    private javax.swing.JLabel addButton;
    private javax.swing.JLabel alertText;
    private javax.swing.JTextField amountTextBox;
    private javax.swing.JCheckBox awardCheckBox;
    private javax.swing.JLabel backButton;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JCheckBox billsCheckBox;
    private javax.swing.JCheckBox businessCheckBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel comsumptionMonth;
    private javax.swing.JLabel consumptionWeek;
    private javax.swing.JPanel datePanel;
    private com.toedter.calendar.JCalendar datePanelDate;
    private javax.swing.JButton datePanelOkButton;
    private javax.swing.JTextField dateTextBox;
    private javax.swing.JLabel deleteButton;
    private javax.swing.JCheckBox educationCheckBox;
    private javax.swing.JCheckBox entertainmentCheckBox;
    private javax.swing.JPanel exCatPanel;
    private javax.swing.JButton exOkButton;
    private javax.swing.JCheckBox exgiftsCheckBox;
    private javax.swing.JLabel exitButton;
    private javax.swing.JCheckBox exotherCheckBox;
    private javax.swing.JPanel expenceDisplayPanel;
    private javax.swing.JCheckBox familyCheckBox;
    private javax.swing.JCheckBox fessCheckBox;
    private javax.swing.JCheckBox foodCheckBox;
    private javax.swing.JCheckBox friendsCheckBox;
    private javax.swing.JCheckBox giftCheckBox;
    private javax.swing.JLabel grossAmountTextBox;
    private javax.swing.JCheckBox healthCheckBox;
    private javax.swing.JButton incomCatOkButton;
    private javax.swing.JPanel incomeCatPanel;
    private javax.swing.JLabel incomeMonth;
    private javax.swing.JCheckBox incomeOtherCheckBox;
    private javax.swing.JTable incomeTable;
    private javax.swing.JLabel incomeWeek;
    private javax.swing.JCheckBox insurancesCheckBox;
    private javax.swing.JCheckBox intrestCheckBox;
    private javax.swing.JCheckBox investmentCheckBox;
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
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JCheckBox loanCheckBox;
    private javax.swing.JTextField locationTextBox;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JLabel logOutText;
    private javax.swing.JLabel mainExpense;
    private javax.swing.JLabel mainIncome;
    private javax.swing.JPanel mainMainPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField noteTextBox;
    private javax.swing.JLabel reportJlable;
    private javax.swing.JLabel reportLable;
    private javax.swing.JCheckBox salaryCheckBox;
    private javax.swing.ButtonGroup selectCateButtonGroup;
    private javax.swing.ButtonGroup selectCateButtonGroup1;
    private javax.swing.JTextField selectCategoryTextBox;
    private javax.swing.JCheckBox sellingCheckBox;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JCheckBox shoppingCheckBox;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel sidePanelName;
    private javax.swing.JLabel sidePanelSex;
    private javax.swing.JLabel sidePanelUsername;
    private javax.swing.JCheckBox transportationCheckBox;
    private javax.swing.JCheckBox travelCheckBox;
    private javax.swing.JLabel uiDate;
    private javax.swing.JLabel uiTime;
    private javax.swing.JLabel updateButon;
    private javax.swing.JLabel userIcon;
    // End of variables declaration//GEN-END:variables
}
