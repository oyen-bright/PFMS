/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pfms;

/**
 *
 * @author HOUSE OF PRAYER
 */
public class New_Account {
    String name, username, password, birthday,sex,email;
    String phoneNumber;
    public New_Account(String name, String username, String birthday, String sex, String email, String password,String phoneNumber)
    {
        this.name=name;
        this.username=username;
        this.birthday=birthday;
        this.sex=sex;
        this.email=email;
        this.password=password;
        this.phoneNumber=phoneNumber;
      
    }
    public String getName(){
        return name;
    }
    public String getUserName(){
        return username;
    }
    public String getbirthday(){
            return birthday;
    }
    public String getSex(){
           return sex;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getPhoneNuber(){
        return phoneNumber;
    }
 
    
}

