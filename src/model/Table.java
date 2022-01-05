/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS OS
 */
public class Table {
    private int table_id;
    private String tableName;
    private int num_of_seat;
    
    public void setTable_id(int table_id){
        this.table_id=table_id;
    }
    public void setTableName(String tableName){
        this.tableName=tableName;
    }
    public void setNum_of_seat(int num_of_seat){
        this.num_of_seat=num_of_seat;
    }
    
    public int getTable_id(){
        return table_id;
    }
    public String getTableName(){
        return tableName;
    }
    public int getNum_of_seat(){
        return num_of_seat;
    }
}
