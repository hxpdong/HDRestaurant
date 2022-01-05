/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import SQLConnect.SQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ASUS OS
 */
public class Account {
    private int account_id;
    private String username;
    private String passwd;
    private int type_id;
    
    public Account(){
        
    }
    
    public Account(int account_id, String username, String passwd, int type_id){
        this.account_id = account_id;
        this.username = username;
        this.passwd = passwd;
        this.type_id = type_id;
    }
    
    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String passwd) {
        this.passwd = passwd;
    }
    
    public void setType_id (int type_id){
        this.type_id = type_id;
    }
    
    public int getAccount_id(){
        return account_id;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return passwd;
    }
    
    public Account checkLogin(String username, String passwd){
        String sql = "select account_id, username, passwd from accounts"
                + " where username=? and passwd=?";
        Account acc = new Account();
        
        try{
            Connection conn = SQLConnection.getConnection(); 
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            pStmt.setString(2, passwd);
            
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()){
                acc.setUsername(username);
                acc.setPassword(passwd);
                acc.setAccount_id(rs.getInt("account_id"));
            }
        }catch (Exception e){}
        return acc;
    }
}

