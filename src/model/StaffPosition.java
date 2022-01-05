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
public class StaffPosition {
    private int post_id;
    private String postName;
    private int basic_sal;
    
    public void setPost_id(int post_id){
        this.post_id = post_id;
    }
    
    public int getPost_id(){
        return post_id;
    }
    
    public void setPostName(String postName){
        this.postName=postName;
    }
    
    public String getPostName(){
        return postName;
    }
    
    public void setBasicSal(int basic_sal){
        this.basic_sal=basic_sal;
    }
    public int getBasicSal(){
        return basic_sal;
    }
    
    public StaffPosition getSal(String postName){
        String sql = "select position_get_sal(?) as basic";
        StaffPosition stp = new StaffPosition();
        try{
            Connection conn = SQLConnection.getConnection(); 
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, postName);
            
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()){
                        
                stp.setPostName(postName);
                stp.setBasicSal(rs.getInt("basic"));
                
            }
        }catch (Exception e){}
        return stp;
    }
}
