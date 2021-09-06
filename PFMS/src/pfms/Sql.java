/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author HOUSE OF PRAYER
 */
public class Sql {
     
    public static Connection getConnection(){
       Connection con =null;
        int i=0;
         try {
            //personal_financial_ms
           // String url="jdbc:mysql://localhost:3308/personal_financial_management_system?useSSL=false";
             String url="jdbc:mysql://localhost:3309/personal_financial_ms?useSSL=false";
             String username="root";
             String password="brinnixs";
             
             con=DriverManager.getConnection(url, username, password);
             //System.out.println(i);
         } catch (SQLException ex) {
             System.out.println(ex);
             JOptionPane.showMessageDialog(null,ex); 
             Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
         }
       i++;
       return con;         
    }
    
    
}
