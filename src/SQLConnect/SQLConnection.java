/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SQLConnect;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author ASUS OS
 */
public class SQLConnection {
    /*public static Connection getConnection() {
        Connection conn = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        conn =DriverManager.getConnection("jdbc:mysql://localhost/hdrestaurant?" +"user=root");
        
    } catch (Exception ex) {
        System.out.println("Noi ket khong thanh cong");
        ex.printStackTrace();
        }  
        return conn;
    }*/ 
    public static Connection getConnection(){
        Connection conn = null;
        String url ="jdbc:mysql://localhost/restaurant?";
        String user = "root";
        String password ="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println("Connect Successful");
        }catch (Exception ex){
            System.out.println("Noi ket khong thanh cong");
            ex.printStackTrace();
        }
        return conn;
    }
}

