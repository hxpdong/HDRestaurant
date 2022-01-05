/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS OS
 */
public class Detail {
    private int number;
    private String dishName;
    private int dish_id;
    private int unit_price;
    private int quantity;
    private int amount;
    
    public void setNo(int number){
        this.number=number;
    }
    public void setDishName(String dishName){
        this.dishName=dishName;
    }
    public void setDish_id(int dish_id){
        this.dish_id=dish_id;
    }
    public void setUnitPrice(int unit_price){
        this.unit_price=unit_price;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public void setAmount(int amount){
        this.amount=amount;
    }
    
    public int getNo(){
        return number;
    }
    public String getDishName(){
        return dishName;
    }
    public int getDish_id(){
        return dish_id;
    }
    public int getUnitPrice(){
        return unit_price;
    }
    public int getQuantity(){
        return quantity;
    }
    public int getAmount(){
        return amount;
    }
}
