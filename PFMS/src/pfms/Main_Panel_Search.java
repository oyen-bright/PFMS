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


public class Main_Panel_Search extends javax.swing.JFrame {

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
     
     
     
    
    
    public Main_Panel_Search(int data, String data1) {
        this.data=data;
        this.data1=data1;
        this.getData_main();
        
        
        
        setUndecorated(true);
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
   
        
        grossAmountTextBox.setText(calGross());
        
        
        montlyData();
       incomeTable.setVisible(false);
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
    
    public Main_Panel_Search(int data) {
        this.data=data;
        System.out.println(data+"incoming data");
        this.getData();
        
        
        
        setUndecorated(true);
        
        
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        
        initComponents();
        
        
                grossAmountTextBox.setText(calGross());

        
        montlyData();

        incomeTable.setVisible(false);
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
                      //  System.out.println("usernameExist"+"meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        
                        
                       
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
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
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
public void montlyData(){
    int janINc = 0, febINc= 0, marINc= 0, aprINc= 0, mayINc= 0, junINc = 0, julINc = 0, augINc = 0,sepINc = 0, octINc = 0, novINc = 0, decINc = 0;
    int janEXc= 0, febEXc= 0, marEXc= 0, aprEXc= 0, mayEXc= 0, junEXc= 0, julEXc= 0, augEXc = 0,sepEXc= 0, octEXc= 0, novEXc= 0, decEXc= 0;
     
     
     try(Connection con=Sql.getConnection()){
         String statement="SELECT * from income_pfms where Id=? and Username=?";
         pst=con.prepareStatement(statement);
         pst.setInt(1, sessionId);
         pst.setString(2, sessionUsername);
         try(ResultSet result=pst.executeQuery()){
             while(result.next()){
                 
                 java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                 System.out.println(getdate.getMonth()+"AMT:"+result.getInt("Amount"));
                
                     switch(getdate.getMonth()+1){
                         case 1:
                         janINc+=result.getInt("Amount");
                         System.out.println("1");
                         break;
                         
                         case 2:
                         febINc+=result.getInt("Amount");
                         System.out.println("2");
                         break;
                         
                         case 3:
                         marINc+=result.getInt("Amount");
                         System.out.println("3");
                         break;
                        
                         case 4:
                         aprINc+=result.getInt("Amount");
                         System.out.println("4");
                         break;
                         
                         case 5:
                         mayINc+=result.getInt("Amount");
                         System.out.println("5");
                         break;
                       
                         case 6:
                         junINc+=result.getInt("Amount");
                         System.out.println("6");
                         break;
                        
                         case 7:
                         julINc+=result.getInt("Amount");
                         System.out.println("7");
                         break;
                        
                         case 8:
                         augINc+=result.getInt("Amount");
                         System.out.println("8");
                         break;
                        
                         case 9:
                         sepINc+=result.getInt("Amount");
                         System.out.println("9");
                         break;
                         
                         case 10:
                         octINc+=result.getInt("Amount");
                         System.out.println("10");
                         break;
                        
                         case 11:
                         novINc+=result.getInt("Amount");
                         System.out.println("11");
                         break;
                        
                         case 12:
                         decINc+=result.getInt("Amount");
                         System.out.println("12");
                         break;
                         
                         
                         
                         
                     }
                     
                     
                     
                     
                 
                 
                 
                 
             }
             
             janIN.setText("$ "+String.valueOf(janINc));
             febIN.setText("$ "+String.valueOf(febINc));
             marIN.setText("$ "+String.valueOf(marINc));
             aprIN.setText("$ "+String.valueOf(aprINc));
             mayIN.setText("$ "+String.valueOf(mayINc));
             junIN.setText("$ "+String.valueOf(junINc));
             julIN.setText("$ "+String.valueOf(julINc));
             augIN.setText("$ "+String.valueOf(augINc));
             novIN.setText("$ "+String.valueOf(novINc));
             sepIN.setText("$ "+String.valueOf(sepINc));
             octIN.setText("$ "+String.valueOf(octINc));
             decIN.setText("$ "+String.valueOf(decINc));
             
             
             
             
         } catch (ParseException ex) {
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
        }
   statement="SELECT * from expense_pfms where Id=? and Username=?";
         pst=con.prepareStatement(statement);
         pst.setInt(1, sessionId);
         pst.setString(2, sessionUsername);
         try(ResultSet result=pst.executeQuery()){
             while(result.next()){
                 
                 java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                 System.out.println(getdate.getMonth()+"AMT:"+result.getInt("Amount"));
                
                     switch(getdate.getMonth()+1){
                         case 1:
                         janEXc+=result.getInt("Amount");
                         System.out.println("1");
                         break;
                         
                         case 2:
                         febEXc+=result.getInt("Amount");
                         System.out.println("2");
                         break;
                         
                         case 3:
                         marEXc+=result.getInt("Amount");
                         System.out.println("3");
                         break;
                        
                         case 4:
                         aprEXc+=result.getInt("Amount");
                         System.out.println("4");
                         break;
                         
                         case 5:
                         mayEXc+=result.getInt("Amount");
                         System.out.println("5");
                         break;
                       
                         case 6:
                         junEXc+=result.getInt("Amount");
                         System.out.println("6");
                         break;
                        
                         case 7:
                         julEXc+=result.getInt("Amount");
                         System.out.println("7");
                         break;
                        
                         case 8:
                         augEXc+=result.getInt("Amount");
                         System.out.println("8");
                         break;
                        
                         case 9:
                         sepEXc+=result.getInt("Amount");
                         System.out.println("9");
                         break;
                         
                         case 10:
                         octEXc+=result.getInt("Amount");
                         System.out.println("10");
                         break;
                        
                         case 11:
                         novEXc+=result.getInt("Amount");
                         System.out.println("11");
                         break;
                        
                         case 12:
                         decEXc+=result.getInt("Amount");
                         System.out.println("12");
                         break;
                         
                         
                         
                         
                     }
                     
                     
                     
                     
                 
                 
                 
                 
             }
             
             janEX.setText("$ "+String.valueOf(janEXc));
             febEX.setText("$ "+String.valueOf(febEXc));
             marEX.setText("$ "+String.valueOf(marEXc));
             aprEX.setText("$ "+String.valueOf(aprEXc));
             mayEX.setText("$ "+String.valueOf(mayEXc));
             junEX.setText("$ "+String.valueOf(junEXc));
             julEX.setText("$ "+String.valueOf(julEXc));
             augEX.setText("$ "+String.valueOf(augEXc));
             novEX.setText("$ "+String.valueOf(novEXc));
             sepEX.setText("$ "+String.valueOf(sepEXc));
             octEX.setText("$ "+String.valueOf(octEXc));
             decEX.setText("$ "+String.valueOf(decEXc));
             
             
             
             
         } catch (ParseException ex) {
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
        }

         
         
     }  catch (SQLException ex) {
         System.out.println(ex);
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
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

        SELECT = new javax.swing.ButtonGroup();
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
        exitButton = new javax.swing.JLabel();
        backButton = new javax.swing.JLabel();
        searchBar = new javax.swing.JTextField();
        jPanel2 = new RoundedPanel(30);
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        janIN = new javax.swing.JLabel();
        janEX = new javax.swing.JLabel();
        febIN = new javax.swing.JLabel();
        febEX = new javax.swing.JLabel();
        marIN = new javax.swing.JLabel();
        aprIN = new javax.swing.JLabel();
        mayIN = new javax.swing.JLabel();
        junIN = new javax.swing.JLabel();
        julIN = new javax.swing.JLabel();
        augIN = new javax.swing.JLabel();
        sepIN = new javax.swing.JLabel();
        octIN = new javax.swing.JLabel();
        novIN = new javax.swing.JLabel();
        decIN = new javax.swing.JLabel();
        marEX = new javax.swing.JLabel();
        aprEX = new javax.swing.JLabel();
        mayEX = new javax.swing.JLabel();
        junEX = new javax.swing.JLabel();
        julEX = new javax.swing.JLabel();
        augEX = new javax.swing.JLabel();
        sepEX = new javax.swing.JLabel();
        octEX = new javax.swing.JLabel();
        novEX = new javax.swing.JLabel();
        decEX = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        incomeTable = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();

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

        mainExpense.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        mainExpense.setText("VALUE");
        mainPanel.add(mainExpense, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, -1, -1));

        mainIncome.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
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

        mainPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 580, 320, 50));
        jPanel1.getAccessibleContext().setAccessibleDescription("");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icon-budgetsTitle.png"))); // NOI18N
        mainPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        jLabel20.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel20.setText("SEARCH");
        mainPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        exitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Exit.png"))); // NOI18N
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 40, 30));

        backButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Back.png"))); // NOI18N
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backButtonMouseClicked(evt);
            }
        });
        mainPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 50, -1, -1));

        searchBar.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        searchBar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchBar.setText("Search with  Amount, Category, Location or Month ");
        searchBar.setFocusable(false);
        searchBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchBarMouseClicked(evt);
            }
        });
        searchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBarActionPerformed(evt);
            }
        });
        searchBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBarKeyPressed(evt);
            }
        });
        mainPanel.add(searchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 500, 30));

        jPanel2.setBackground(new java.awt.Color(235, 239, 229));
        jPanel2.setOpaque(false);

        jLabel25.setText("JAN");

        jLabel26.setText("FEB");

        jLabel27.setText("MAR");

        jLabel28.setText("APR");

        jLabel29.setText("MAY");

        jLabel30.setText("JUN");

        jLabel31.setText("JUL");

        jLabel32.setText("AUG");

        jLabel33.setText("SEP");

        jLabel34.setText("OCT");

        jLabel35.setText("NOV");

        jLabel36.setText("DEC");

        jLabel37.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel37.setText("INCOME");

        jLabel38.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel38.setText("EXPENSE");

        janIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        janIN.setForeground(new java.awt.Color(51, 255, 0));
        janIN.setText("NULL");

        janEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        janEX.setForeground(new java.awt.Color(255, 0, 0));
        janEX.setText("NULL");

        febIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        febIN.setForeground(new java.awt.Color(51, 255, 0));
        febIN.setText("NULL");

        febEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        febEX.setForeground(new java.awt.Color(255, 0, 0));
        febEX.setText("NULL");

        marIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        marIN.setForeground(new java.awt.Color(51, 255, 0));
        marIN.setText("NULL");

        aprIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        aprIN.setForeground(new java.awt.Color(51, 255, 0));
        aprIN.setText("NULL");

        mayIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mayIN.setForeground(new java.awt.Color(51, 255, 0));
        mayIN.setText("NULL");

        junIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        junIN.setForeground(new java.awt.Color(51, 255, 0));
        junIN.setText("NULL");

        julIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        julIN.setForeground(new java.awt.Color(51, 255, 0));
        julIN.setText("NULL");

        augIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        augIN.setForeground(new java.awt.Color(51, 255, 0));
        augIN.setText("NULL");

        sepIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        sepIN.setForeground(new java.awt.Color(51, 255, 0));
        sepIN.setText("NULL");

        octIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        octIN.setForeground(new java.awt.Color(51, 255, 0));
        octIN.setText("NULL");

        novIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        novIN.setForeground(new java.awt.Color(51, 255, 0));
        novIN.setText("NULL");

        decIN.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        decIN.setForeground(new java.awt.Color(51, 255, 0));
        decIN.setText("NULL");

        marEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        marEX.setForeground(new java.awt.Color(255, 0, 0));
        marEX.setText("NULL");

        aprEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        aprEX.setForeground(new java.awt.Color(255, 0, 0));
        aprEX.setText("NULL");

        mayEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        mayEX.setForeground(new java.awt.Color(255, 0, 0));
        mayEX.setText("NULL");

        junEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        junEX.setForeground(new java.awt.Color(255, 0, 0));
        junEX.setText("NULL");

        julEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        julEX.setForeground(new java.awt.Color(255, 0, 0));
        julEX.setText("NULL");

        augEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        augEX.setForeground(new java.awt.Color(255, 0, 0));
        augEX.setText("NULL");

        sepEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        sepEX.setForeground(new java.awt.Color(255, 0, 0));
        sepEX.setText("NULL");

        octEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        octEX.setForeground(new java.awt.Color(255, 0, 0));
        octEX.setText("NULL");

        novEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        novEX.setForeground(new java.awt.Color(255, 0, 0));
        novEX.setText("NULL");

        decEX.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        decEX.setForeground(new java.awt.Color(255, 0, 0));
        decEX.setText("NULL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(janIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(febIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(marIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aprIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mayIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(junIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(julIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(augIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sepIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(octIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(novIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(decIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(janEX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(febEX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(marEX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aprEX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mayEX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(junEX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(julEX)
                    .addComponent(augEX)
                    .addComponent(sepEX)
                    .addComponent(octEX)
                    .addComponent(novEX)
                    .addComponent(decEX))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(janIN)
                    .addComponent(janEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(febIN)
                    .addComponent(febEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(marIN)
                    .addComponent(marEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(aprIN)
                    .addComponent(aprEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(mayIN)
                    .addComponent(mayEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(junIN)
                    .addComponent(junEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(julIN)
                    .addComponent(julEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(augIN)
                    .addComponent(augEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(sepIN)
                    .addComponent(sepEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(octIN)
                    .addComponent(octEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(novIN)
                    .addComponent(novEX))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(decIN)
                    .addComponent(decEX))
                .addGap(28, 28, 28))
        );

        mainPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 210, 230, 370));

        incomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Type", "Date", "Category", "Amount", "Location", "Note", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
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
        }

        mainPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 590, 380));

        jLabel23.setText("SEARCH");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        mainPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, -1, -1));

        jLabel24.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel24.setText("MONTLY DATA");
        mainPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 180, -1, -1));

        backgroundPanel.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 860, 645));
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
        // TODO add your handling code here
      
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

    private void searchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchBarActionPerformed

    private void searchBarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchBarMouseClicked
        // TODO add your handling code here:
        searchBar.setFocusable(true);
        searchBar.setText("");
    }//GEN-LAST:event_searchBarMouseClicked

    private void incomeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_incomeTableMouseClicked

    private void incomeTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_incomeTableMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_incomeTableMouseReleased

    private void incomeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_incomeTableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_incomeTableKeyPressed

    private void incomeTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_incomeTableKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_incomeTableKeyReleased

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        System.out.println(searchBar.getText());
        if(searchBar.getText().equals("Search with  Amount, Category, Location or Month ")||searchBar.getText().equals("")){
        
        }
        else{
            incomeTable.setVisible(true);
         String date,time;
          int i=0;
          SimpleDateFormat formatter = new SimpleDateFormat("EEE-yyyy-MM-dd");
        SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm:ss a");
    
       DefaultTableModel model1 = (DefaultTableModel) incomeTable.getModel();
       System.out.println("here"); 
           int rowCount = model1.getRowCount();
           for (int j = rowCount - 1; j >= 0; j--) {
            model1.removeRow(j);
            }
       String str = searchBar.getText();
       try(Connection con=Sql.getConnection()){
           String sql="SELECT * from expense_pfms where Amount=? or Category = ? or Location= ? and Id=? and Username=? ";
           pst=con.prepareStatement(sql);
             pst.setString(1, str);
             pst.setString(2, str);
             pst.setString(3, str);
             pst.setInt(4, sessionId);
             pst.setString(5, sessionUsername);
             try(ResultSet result=pst.executeQuery()){
                 
                    
                     while(result.next()){
                              java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                            
                   
                   date=formatter.format(getdate);
                               time=formatter1.format(getdate);
                             
                   i++;
                  model1.addRow(new Object[]{i,"Expense",date, result.getString("Category"), "$ "+result.getString("Amount"),result.getString("Location"), result.getString("Note")
               , time  });
                     }
                 
             
                 } catch (ParseException ex) {
                 Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
             }
                     sql="SELECT * from income_pfms where Amount=? or Category = ? or Location= ? and Id=? and Username=? ";
                 pst=con.prepareStatement(sql);
             pst.setString(1, str);
             pst.setString(2, str);
             pst.setString(3, str);
             pst.setInt(4, sessionId);
             pst.setString(5, sessionUsername);
             try(ResultSet result=pst.executeQuery()){
                 
                     
                     while(result.next()){
                                   java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                            
                   
                   date=formatter.format(getdate);
                               time=formatter1.format(getdate);
                             
                   i++;
                  model1.addRow(new Object[]{i,"Income",date, result.getString("Category"), result.getString("Amount"),result.getString("Location"), result.getString("Note")
               , time  });
                     }     
                     
                 
             } catch (ParseException ex) {
                 Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
             }
       } catch (SQLException ex) {
           System.out.println(ex);
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
        }
       if(incomeTable.getRowCount()==0||incomeTable.getColumnCount()==0){
           int month = 0;
           if(searchBar.getText().equals("jan")||searchBar.getText().equals("january")){
               month=0;
               
           }
           else if(searchBar.getText().equals("feb")||searchBar.getText().equals("febuary")){
               month=1;
           }
           else if(searchBar.getText().equals("mar")||searchBar.getText().equals("march")){
               month=2;
           }
           else if(searchBar.getText().equals("apr")||searchBar.getText().equals("april")){
               month=3;
           }
           else if(searchBar.getText().equals("may")||searchBar.getText().equals("may")){
               month=4;
           }else if(searchBar.getText().equals("jun")||searchBar.getText().equals("june")){
               month=5;
           }
           else if(searchBar.getText().equals("jul")||searchBar.getText().equals("july")){
               month=6;
           }
           else if(searchBar.getText().equals("aug")||searchBar.getText().equals("august")){
               month=7;
           }
           else if(searchBar.getText().equals("sep")||searchBar.getText().equals("september")){
               month=8;
           }
           else if(searchBar.getText().equals("oct")||searchBar.getText().equals("october")){
               month=9;
           }
           else if(searchBar.getText().equals("nov")||searchBar.getText().equals("november")){
               month=10;
           }
           else if(searchBar.getText().equals("dec")||searchBar.getText().equals("december")){
               month=11;
           }
           try(Connection con=Sql.getConnection()){
               String statement="SELECT * from income_pfms where Id=? and Username=?";
               pst= con.prepareStatement(statement);
               pst.setInt(1, sessionId);
               pst.setString(2, sessionUsername);
               int l=1;
               try(ResultSet result=pst.executeQuery()){
                   while(result.next()){
                         java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                             if(getdate.getMonth()==month){
                                 date=formatter.format(getdate);
                               time=formatter1.format(getdate);
                                 model1.addRow(new Object[]{l,"Income",date, result.getString("Category"), "$ "+result.getString("Amount"),result.getString("Location"), result.getString("Note")
               , time  });
                                 l++;
                             }
                   }
               } catch (ParseException ex) {
                   Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
               }
               statement="SELECT * from expense_pfms where Id=? and Username=?";
               pst= con.prepareStatement(statement);
               pst.setInt(1, sessionId);
               pst.setString(2, sessionUsername);
               try(ResultSet result=pst.executeQuery()){
                   while(result.next()){
                         java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                             if(getdate.getMonth()==month){
                                 date=formatter.format(getdate);
                               time=formatter1.format(getdate);
                                 model1.addRow(new Object[]{l,"Expense",date, result.getString("Category"), "$ "+result.getString("Amount"),result.getString("Location"), result.getString("Note")
               , time  });
                                 l++;
                             }
                   }
               } catch (ParseException ex) {
                   Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
               }
               
               
               
               
           }    catch (SQLException ex) {
               System.out.println(ex);
                    Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
                }
           
           
           
           
           
           
           
       }
        }
    }//GEN-LAST:event_jLabel23MouseClicked

    private void searchBarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBarKeyPressed
      
    }//GEN-LAST:event_searchBarKeyPressed
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
            Logger.getLogger(Main_Panel_Search.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(Main_Panel_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Panel_Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Main_Panel_Search(0).setVisible(true);
                         
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup SELECT;
    private javax.swing.JLabel addButton;
    private javax.swing.JLabel alertText;
    private javax.swing.JLabel aprEX;
    private javax.swing.JLabel aprIN;
    private javax.swing.JLabel augEX;
    private javax.swing.JLabel augIN;
    private javax.swing.JLabel backButton;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel comsumptionMonth;
    private javax.swing.JLabel consumptionWeek;
    private javax.swing.JLabel decEX;
    private javax.swing.JLabel decIN;
    private javax.swing.JLabel exitButton;
    private javax.swing.JLabel febEX;
    private javax.swing.JLabel febIN;
    private javax.swing.JLabel grossAmountTextBox;
    private javax.swing.JLabel incomeMonth;
    private javax.swing.JTable incomeTable;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel janEX;
    private javax.swing.JLabel janIN;
    private javax.swing.JLabel julEX;
    private javax.swing.JLabel julIN;
    private javax.swing.JLabel junEX;
    private javax.swing.JLabel junIN;
    private javax.swing.JLabel logOutIcon;
    private javax.swing.JLabel logOutText;
    private javax.swing.JLabel mainExpense;
    private javax.swing.JLabel mainIncome;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel marEX;
    private javax.swing.JLabel marIN;
    private javax.swing.JLabel mayEX;
    private javax.swing.JLabel mayIN;
    private javax.swing.JLabel novEX;
    private javax.swing.JLabel novIN;
    private javax.swing.JLabel octEX;
    private javax.swing.JLabel octIN;
    private javax.swing.JLabel reportJlable;
    private javax.swing.JLabel reportLable;
    private javax.swing.JTextField searchBar;
    private javax.swing.JLabel sepEX;
    private javax.swing.JLabel sepIN;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JLabel sidePanelName;
    private javax.swing.JLabel sidePanelSex;
    private javax.swing.JLabel sidePanelUsername;
    private javax.swing.JLabel uiDate;
    private javax.swing.JLabel uiTime;
    private javax.swing.JLabel userIcon;
    // End of variables declaration//GEN-END:variables
}
