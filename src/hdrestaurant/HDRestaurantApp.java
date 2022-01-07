/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hdrestaurant;

import SQLConnect.SQLConnection;
import frame.LoginFr;
import java.sql.Connection;

/**
 *
 * @author ASUS OS
 */
public class HDRestaurantApp {

    /**
     * @param args the command line arguments
     */
  /*  public static void main(String[] args) {
        // TODO code application logic here
    java.awt.EventQueue.invokeLater(new Runnable() {
            //Connection conn = SQLConnection.getConnection();
            public void run() {
                new LoginFr().setVisible(true);
            }
        });
    }*/
    
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Connection conn = SQLConnection.getConnection();
            if (conn != null){
                System.out.println("Connect Successful");
                java.awt.EventQueue.invokeLater(new Runnable() {
            
                public void run() {
                new LoginFr().setVisible(true);
            }
        });
            }
        } catch (Exception e) {
            System.out.println("Lỗi: "+e.getMessage());
        }
    }
}
