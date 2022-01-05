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
public class Statistic {
    private Date iDay;
    private int NumInvoice;
    private long iTotal; 
    
    public void setiDay(Date iDay){
        this.iDay=iDay;
    }
    public void setNumInvoice(int NumInvoice){
        this.NumInvoice=NumInvoice;
    }
    public void setiTotal(long iTotal){
        this.iTotal=iTotal;
    }
    
    public Date getiDay(){
        return iDay;
    }
    public int getNumInvoice(){
        return NumInvoice;
    }
    public Long getiTotal(){
        return iTotal;
    }
}
