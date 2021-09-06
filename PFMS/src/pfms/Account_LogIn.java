/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author HOUSE OF PRAYER
 */

 
public class Account_LogIn extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    Connection con= null;
    PreparedStatement pst= null;
    int sessionId;
    
    public Account_LogIn() {
        setUndecorated(true);
        this.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        initComponents();
        
        uiDate.setText((java.time.LocalDate.now()).toString());
        uiTime.setText(format.format(java.time.LocalTime.now()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new RoundedPanel(50);
        mainPanel = new RoundedPanel(50);
        uiDate = new javax.swing.JLabel();
        uiTime = new javax.swing.JLabel();
        exitButton = new javax.swing.JLabel();
        avaterHeader = new javax.swing.JLabel();
        signupLink = new javax.swing.JLabel();
        usernameTextBox = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        pswIcon = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        passwordLabel = new javax.swing.JLabel();
        passwordTextBox = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        usignupLink = new javax.swing.JLabel();
        loginHeaderText = new javax.swing.JLabel();
        alertText = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(900, 50));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        backgroundPanel.setBackground(new java.awt.Color(255, 153, 0));
        backgroundPanel.setOpaque(false);
        backgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uiDate.setText("Date");
        mainPanel.add(uiDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        uiTime.setText("Time");
        mainPanel.add(uiTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        exitButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exitButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\HOUSE OF PRAYER\\Desktop\\52945-20.png")); // NOI18N
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });
        mainPanel.add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 40, 30));

        avaterHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avaterHeader.setIcon(new javax.swing.ImageIcon("C:\\Users\\HOUSE OF PRAYER\\Desktop\\Task\\New folder\\scalable-vector-graphics-avatar-learning-icon-customer-login-avatar.png")); // NOI18N
        mainPanel.add(avaterHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 300, 160));

        signupLink.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        signupLink.setText("SIGN UP");
        signupLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signupLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signupLinkMouseClicked(evt);
            }
        });
        mainPanel.add(signupLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        usernameTextBox.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        usernameTextBox.setForeground(new java.awt.Color(92, 84, 107));
        usernameTextBox.setText("Username");
        usernameTextBox.setAutoscrolls(false);
        usernameTextBox.setBorder(null);
        usernameTextBox.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        usernameTextBox.setOpaque(false);
        usernameTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                usernameTextBoxFocusGained(evt);
            }
        });
        mainPanel.add(usernameTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, 250, 40));

        loginButton.setBackground(new java.awt.Color(255, 255, 255));
        loginButton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        loginButton.setText("LOGIN");
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        mainPanel.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, 250, 40));

        pswIcon.setIcon(new javax.swing.ImageIcon("C:\\Users\\HOUSE OF PRAYER\\Desktop\\Task\\New folder\\d-login.png")); // NOI18N
        mainPanel.add(pswIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 393, -1, -1));

        userIcon.setIcon(new javax.swing.ImageIcon("C:\\Users\\HOUSE OF PRAYER\\Desktop\\Task\\New folder\\891388-login.png")); // NOI18N
        mainPanel.add(userIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 343, -1, -1));
        mainPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 250, -1));
        mainPanel.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 250, -1));

        passwordLabel.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(92, 84, 107));
        passwordLabel.setText("Password");
        mainPanel.add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 70, -1));

        passwordTextBox.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        passwordTextBox.setForeground(new java.awt.Color(92, 84, 107));
        passwordTextBox.setBorder(null);
        passwordTextBox.setOpaque(false);
        passwordTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordTextBoxFocusGained(evt);
            }
        });
        passwordTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextBoxActionPerformed(evt);
            }
        });
        mainPanel.add(passwordTextBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 250, 40));

        jLabel8.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        jLabel8.setText("Don't have an account ?");
        mainPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 550, 160, -1));

        usignupLink.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        usignupLink.setForeground(new java.awt.Color(92, 84, 107));
        usignupLink.setText("<html>\n<body>\n\n<u>Sign Up</u>\n\n</body>\n</html>\n");
        usignupLink.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usignupLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usignupLinkMouseClicked(evt);
            }
        });
        mainPanel.add(usignupLink, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, -1, -1));

        loginHeaderText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        loginHeaderText.setForeground(new java.awt.Color(92, 84, 107));
        loginHeaderText.setText("Log In");
        mainPanel.add(loginHeaderText, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 110, -1, -1));

        alertText.setFont(new java.awt.Font("Roboto Light", 0, 10)); // NOI18N
        alertText.setForeground(new java.awt.Color(255, 51, 0));
        alertText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainPanel.add(alertText, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 443, 250, -1));
        alertText.getAccessibleContext().setAccessibleDescription("");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Calender.png"))); // NOI18N
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, -1, -1));

        backgroundPanel.add(mainPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 0, 344, 645));
        mainPanel.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_exitButtonMouseClicked

    private void passwordTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextBoxActionPerformed

    private void signupLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signupLinkMouseClicked
        // TODO add your handling code here:
        new Account_Create().setVisible(true);
        dispose();
    }//GEN-LAST:event_signupLinkMouseClicked

    private void usignupLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usignupLinkMouseClicked
        // TODO add your handling code here:
        new Account_Create().setVisible(true);
        dispose();
    }//GEN-LAST:event_usignupLinkMouseClicked

    private void usernameTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_usernameTextBoxFocusGained
        // TODO add your handling code here:
        usernameTextBox.setText("");
    }//GEN-LAST:event_usernameTextBoxFocusGained

    private void passwordTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordTextBoxFocusGained
        // TODO add your handling code here:
        passwordLabel.setText("");
    }//GEN-LAST:event_passwordTextBoxFocusGained

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        
          alertText.setText("");
          
         if(passwordTextBox.getText().equals("")|| passwordTextBox.getText().equals("Password")||
                 usernameTextBox.getText().equals("Username")||
                 usernameTextBox.getText().equals("")){
                 alertText.setText("*Input error please check inputs");
        }
         else{
             String sql="SELECT * FROM user_account WHERE Username=? and Password=? ";
             try(Connection con=Sql.getConnection();){
                pst=con.prepareStatement(sql);
                pst.setString(1, usernameTextBox.getText());
                pst.setString(2, passwordTextBox.getText());
               try (ResultSet results = pst.executeQuery()) {
                   if (results.next()) {
                      
                      System.out.println("Sucess:username and password found");
                      
                      String name =results.getString("Name");
                      sessionId=results.getInt("Id");
                      System.out.println("Login Username:"+name);
                        
                      sql="INSERT into user_account_log_info(Username, logIn_Info)" + "values(?,?)";
                         LocalDateTime now=LocalDateTime.now();
                         try(Connection con2 = Sql.getConnection();){
                             
                         pst=con2.prepareStatement(sql);
                         pst.setString(1, results.getString("username"));
                         pst.setString(2, now.atZone(ZoneId.of("GMT")).format(DateTimeFormatter.RFC_1123_DATE_TIME));
                         pst.execute();
                         
                         
                         String sql2="SELECT LAST_INSERT_ID()";
            pst=con2.prepareStatement(sql2);
           try(ResultSet result=pst.executeQuery()){
               if (result.next()) {
              System.out.println("loginWTF:"+result.getInt(1));
              int logInId=(result.getInt(1));
                
              new Main_Panel(logInId).setVisible(true);
              
              this.dispose();
              
               }
               }
                         }
                           catch(SQLException ex){
                        System.out.println(ex);
            }
                         
                         
                   }else{
                       alertText.setText("*Username or password wrong");
                       System.out.println("username and password not found");
                   }
               }
            }
            catch(SQLException ex){
                
            }
            
        }
             
              
        
    }//GEN-LAST:event_loginButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Account_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Account_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Account_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Account_LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Account_LogIn().setVisible(true);
                 
            }
        });
        
         
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alertText;
    private javax.swing.JLabel avaterHeader;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginHeaderText;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JPasswordField passwordTextBox;
    private javax.swing.JLabel pswIcon;
    private javax.swing.JLabel signupLink;
    private javax.swing.JLabel uiDate;
    private javax.swing.JLabel uiTime;
    private javax.swing.JLabel userIcon;
    private javax.swing.JTextField usernameTextBox;
    private javax.swing.JLabel usignupLink;
    // End of variables declaration//GEN-END:variables
}
