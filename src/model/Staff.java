/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


import java.util.Date;

/**
 *
 * @author ASUS OS
 */
public class Staff {
    private int staff_id;
    private String staffName;
    private String postName;
    private Date DoB;
    private String staffPhone;
    private String State;
    private int Bonus;
    private long totalSal;

  /*  private String staffState;
    private int Bonus;
    private int totalSal;*/
    
    public int getstaff_id(){
        return staff_id;
    }
    public void setstaff_id(int staff_id){
        this.staff_id=staff_id;
    }
    
    public String getstaffName(){
        return staffName;
    }
    public void setstaffName(String staffName){
        this.staffName=staffName;
    }
    
    public String getpostName(){
        return postName;
    }
    public void setpostName(String postName){
        this.postName=postName;
    }
    
    public Date getDoB(){
        return DoB;
    }
    public void setDoB(Date DoB){
        this.DoB=DoB;
    }
    
    public String getstaffPhone(){
        return staffPhone;
    }
    public void setstaffPhone(String staffPhone){
        this.staffPhone=staffPhone;
    }
    
    public String getState(){
        return State;
    }
    public void setState(String State){
        this.State=State;
    }
    
    public int getBonus(){
        return Bonus;
    }
    public void setBonus(int Bonus){
        this.Bonus=Bonus;
    }
    
    public long gettotalSal(){
        return totalSal;
    }
    public void settotalSal(long totalSal){
        this.totalSal=totalSal;
    }
}
