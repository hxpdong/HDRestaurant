/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS OS
 */
public class Dish {
    private int dish_id;
    private String dishName;
    private String cateName;
    private int price;
    private String unit;
    private String state;
    
    public void setDish_id(int dish_id){
        this.dish_id=dish_id;
    }
    public int getDish_id(){
        return dish_id;
    }
    public void setDishName(String dishName){
        this.dishName=dishName;
    }
    public String getDishName(){
        return dishName;
    }
    public void setCateName (String cateName){
        this.cateName=cateName;
    }
    public String getCateName(){
        return cateName;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public int getPrice(){
        return price;
    }
    public void setUnit(String unit){
        this.unit=unit;
    }
    public String getUnit(){
        return unit;
    }
    public void setState(String state){
        this.state=state;
    }
    public String getState(){
        return state;
    }
}