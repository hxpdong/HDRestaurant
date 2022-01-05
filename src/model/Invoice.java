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
public class Invoice {
    private int invoice_id;
    private String tableName;
    private String staff;
    private Date invoiceDate;
    private Date invoiceDateUpt;
    private long invoiceTotal;
    private String state;
    
    public void setInvoice_id(int invoice_id){
        this.invoice_id=invoice_id;
    }
    public void setTableName(String tableName){
        this.tableName=tableName;
    }
    public void setStaff(String staff){
        this.staff=staff;
    }
    public void setInvoiceDate(Date invoiceDate){
        this.invoiceDate=invoiceDate;
    }
    public void setInvoiceDateUpt(Date invoiceDateUpt){
        this.invoiceDateUpt=invoiceDateUpt;
    }
    public void setInvoiceTotal(long invoiceTotal){
        this.invoiceTotal=invoiceTotal;
    }
    public void setState(String state){
        this.state=state;
    }
    
    public int getInvoice_id(){
        return invoice_id;
    }
    public String getTableName(){
        return tableName;
    }
    public String getStaff(){
        return staff;
    }
    public Date getInvoiceDate(){
        return invoiceDate;
    }
    public Date getInvoiceDateUpt(){
        return invoiceDateUpt;
    }
    public long getInvoiceTotal(){
        return invoiceTotal;
    }
    public String getState(){
        return state;
    }
}
