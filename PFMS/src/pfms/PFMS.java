/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import javax.swing.ButtonModel;
/**
 *
 * @author HOUSE OF PRAYER
 */
public class PFMS {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       //  public void totallPriceWeek(String sql)
    
       
   /*   PreparedStatement pst;
      int thismonth,firstmonth;
        int avgMonth;
        int[] noMonth=new int[12];
        int j=0;
        String sqlDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
 
            String sql="SELECT * from income_pfms where Id=? and Username=?";
            try(Connection con=Sql.getConnection()){
                pst=con.prepareStatement(sql);
                pst.setInt(1, 1);
                pst.setString(2, "aha");
                try(ResultSet result=pst.executeQuery()){
                    if(result.next()){
                        try{
                    
                    java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(result.getString("Date")); 
                    thismonth=getdate.getMonth();
                    noMonth[0]=thismonth;
                            sql="SELECT * from income_pfms where Id=? and Username=?";    
                            pst=con.prepareStatement(sql);
                            pst.setInt(1, 1);
                            pst.setString(2, "aha");
                            try(ResultSet result1=pst.executeQuery()){
                                while(result1.next()){
                     for(int i = 0; i< noMonth.length; i++){ 
                        System.out.println("herer");
                        if(thismonth==noMonth[i]){
                            
                        }
                     
                        else{
                         noMonth[j]=thismonth;
                         j++;
                    }    
                    }
                                }
                            }

                        }
                        catch (ParseException ex) {
                              System.out.println(ex);
                          }

                      
                    
                    
                }
                  System.out.println(noMonth.length);
                }
                
            } catch (SQLException ex) {
                System.out.println(ex);
                Logger.getLogger(Main_Panel_Report.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
            
            
            
        }
       */
       
    /*         LocalDate thisdate= LocalDate.now();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        int nextMonth=thisdate.getMonthValue();
        nextMonth++;  
        LocalDate date=thisdate;
        //date=date.minusDays(1);
         while(date.getMonthValue()!=nextMonth){
        date=date.plusDays(1);
             if(date.getMonthValue()==nextMonth){;
             break;
               }
         }
         System.out.println(date.toString());
       
       
 /*
    System.out.print(2-5);
    
    //public void totallPriceMonth(String sql){
    LocalDate thisdate= LocalDate.now();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String sqlDate;
    int monthPrice = 0;
    
    System.out.println(thisdate);
    int nextMonth=thisdate.getMonthValue();
    nextMonth++;
    thisdate=thisdate.plusDays(1);
    LocalDate date=thisdate.with(TemporalAdjusters.firstDayOfMonth());
    date=date.minusDays(1);
    while(date.getMonthValue()!=nextMonth){
    date=date.plusDays(1);
    if(date.getMonthValue()==nextMonth){;
        break;
    }
    else{
        System.out.println("hi"+date);
   /* try(Connection con=Sql.getConnection()){
        PreparedStatement pst=con.prepareStatement(sql);
        try(ResultSet results=pst.executeQuery()){
            while (results.next()){
                 try { 
                              
                              java.util.Date getdate=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US).parse(results.getString("expenditure_date")); 
                               sqlDate=formatter.format(getdate);
                              
                               if (sqlDate.equals(date.toString())){
                                   monthPrice+=results.getInt("expenditure_price");
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
        
    }
    catch(SQLException ex){
        System.out.println(ex);
    }
   // System.out.println(date);
    }
}
    //thisMonthConsumption.setText(String.valueOf(monthPrice));
       */
   }  
}



