/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hdrestaurant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;

/**
 *
 * @author ASUS OS
 */
public class ClockThread extends Thread{
    private JLabel lbl;
    private boolean exit = false;
    public ClockThread(JLabel lbl){
        this.lbl=lbl;
    }
    
    public void run(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MM-yyyy hh:mm:ss aa", Locale.ENGLISH);
        while(!exit){
            Date now = new Date();
            String st = sdf.format(now);
            
            lbl.setText(st);
            
            try{
                Thread.sleep(1000);
            }catch(InterruptedException ex){}
            
        }
    }
    public void interrupt(){
        exit = true;
    }
}
