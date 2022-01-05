/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import SQLConnect.SQLConnection;
import hdrestaurant.ClockThread;
//import java.sql.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import model.Staff;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Detail;
import model.Dish;
import model.Invoice;
import model.StaffPosition;
import model.Statistic;
import hdrestaurant.SharedData;
//import modelmethod.StaffPositionmethod;
//import java.sql.Date;
//import com.mysql.cj.jdbc.CallableStatement;
/**
 *
 * @author ASUS OS
 */
public class HomeAdminFr extends javax.swing.JFrame {

    /**
     * Creates new form HomeAdminFr
     */
    DefaultTableModel defaultTableModel;
    DefaultComboBoxModel defaultComboBoxModel;
    CallableStatement cStmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Connection conn = SQLConnection.getConnection();
    
    public HomeAdminFr() {
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        
        initClock();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        UpdatePosition1();
        UpdatePosition2();
        UpdateCategory();
        UpdateCategory2();
        RefreshDataStaff();
        RefreshDataPost();
        RefreshDataAcc();
        RefreshDataCate();
        RefreshDataDish();
        RefreshDataTable();
        
        SetTableDataInvoiceList();
    }
    
    private ClockThread clock = null;
    private void initClock(){
        clock = new ClockThread(jLabelClock);
        clock.start();
    }
    
    private void outClock(){
       if(clock != null){
           clock.interrupt();
       }
    }
    private void SetTableDataWork(){
        List<Staff> staffList = new ArrayList<Staff>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        
        jTableStaff.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Position");
        
        
        try{
            //String sql = "call staff_list_working();";
            String sql = "call staff_list(1);";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
          
            while(rs.next()){
                Staff staff = new Staff();
                staff.setstaff_id(rs.getInt("staff_id"));
                staff.setstaffName(rs.getString("staffName"));
                staff.setpostName(rs.getString("postName"));
                staff.setDoB(rs.getDate("DoB"));
                staff.setstaffPhone(rs.getString("staffPhone"));
                staff.setState(rs.getString("State"));
                staff.setBonus(rs.getInt("Bonus"));
                staff.settotalSal(rs.getLong("totalSal"));
                staffList.add(staff);
            }
            for (Staff staff : staffList) {
                defaultTableModel.addRow(new Object[]{
                    staff.getstaff_id(), 
                    staff.getstaffName(), 
                    staff.getpostName(),
                    staff.getDoB(), 
                    staff.getstaffPhone(),
                    staff.getState(),
                    staff.getBonus(),
                    staff.gettotalSal()
                });
            }
        
        } catch (Exception e){e.printStackTrace();}
        
    }
    
    private void SetTableDataRetired(){
        List<Staff> staffList = new ArrayList<Staff>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        
        jTableStaff.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Position");
        
        
        try{
            //String sql = "call staff_list_retired();";
            String sql = "call staff_list(0);";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
          
            while(rs.next()){
                Staff staff = new Staff();
                staff.setstaff_id(rs.getInt("staff_id"));
                staff.setstaffName(rs.getString("staffName"));
                staff.setpostName(rs.getString("postName"));
                staff.setDoB(rs.getDate("DoB"));
                staff.setstaffPhone(rs.getString("staffPhone"));
                staff.setState(rs.getString("State"));
                staff.setBonus(rs.getInt("Bonus"));
                staff.settotalSal(rs.getLong("totalSal"));
                
                staffList.add(staff);
            }
            for (Staff staff : staffList) {
                defaultTableModel.addRow(new Object[]{
                    staff.getstaff_id(), 
                    staff.getstaffName(), 
                    staff.getpostName(),
                    staff.getDoB(), 
                    staff.getstaffPhone(),
                    staff.getState(),
                    staff.getBonus(),
                    staff.gettotalSal()
                });
            }
        
        } catch (Exception e){e.printStackTrace();}
        
    }
    
    private void SetTableDataSearch(){
        List<Staff> staffList = new ArrayList<Staff>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        
        jTableStaff.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Position");
        
        
        try{
            cStmt = conn.prepareCall("call staff_search(?)");
            //cStmt = conn.prepareCall("call staff_search_by_name(?);");
            cStmt.setString(1, jTextFieldSearch.getText());
            rs = cStmt.executeQuery();
            
            while(rs.next()){
                Staff staff = new Staff();
                staff.setstaff_id(rs.getInt("staff_id"));
                staff.setstaffName(rs.getString("staffName"));
                staff.setpostName(rs.getString("postName"));
                staff.setDoB(rs.getDate("DoB"));
                staff.setstaffPhone(rs.getString("staffPhone"));
                staff.setState(rs.getString("State"));
                staff.setBonus(rs.getInt("Bonus"));
                staff.settotalSal(rs.getLong("totalSal"));
                
                staffList.add(staff);
            }
            for (Staff staff : staffList) {
                defaultTableModel.addRow(new Object[]{
                    staff.getstaff_id(), 
                    staff.getstaffName(), 
                    staff.getpostName(),
                    staff.getDoB(), 
                    staff.getstaffPhone(),
                    staff.getState(),
                    staff.getBonus(),
                    staff.gettotalSal()
                });
            }
        
        } catch (Exception e){e.printStackTrace();}
        
    }
    
    private void SetTableDataDish(){
        List<Dish> dishList = new ArrayList<Dish>();
        defaultTableModel = new DefaultTableModel();
        
        jTableDish.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Name");
        defaultTableModel.addColumn("Price");
        try{
            cStmt = conn.prepareCall("call dish_list_by_cate(?);");
            cStmt.setString(1, jLabelCate.getText());
            rs = cStmt.executeQuery();
            
            while(rs.next()){
                Dish dish = new Dish();
                dish.setDish_id(rs.getInt("dish_id"));
                dish.setDishName(rs.getString("dishName"));
                dish.setCateName(rs.getString("cateName"));
                dish.setPrice(rs.getInt("price"));
                dish.setUnit(rs.getString("unit"));
                dish.setState(rs.getString("state"));
                dishList.add(dish);
            }
            for (Dish dish : dishList) {
                defaultTableModel.addRow(new Object[]{
                    dish.getDish_id(), 
                    dish.getDishName(),
                    dish.getPrice()
                    //dish.getUnit()
                    
                });
            }
        }catch(Exception e){System.out.println("lỗi");e.printStackTrace();}
    }
    private void SetTableDataInvoiceList(){
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        
        jTableInvoiceList.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Date");
        
        
        try{
            String sql = "call invoice_list()";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            
            while(rs.next()){
                Invoice invoice = new Invoice();
                invoice.setInvoice_id(rs.getInt("invoice_id"));
                invoice.setTableName(rs.getString("tableName"));
                invoice.setStaff(rs.getString("staff"));
                invoice.setInvoiceDate(rs.getDate("invoiceDate"));
                invoice.setInvoiceDateUpt(rs.getDate("invoiceDateUpt"));
                invoice.setInvoiceTotal(rs.getLong("invoiceTotal"));
                invoice.setState(rs.getString("state"));
                
                invoiceList.add(invoice);
            }
            for (Invoice invoice : invoiceList) {
                defaultTableModel.addRow(new Object[]{
                    invoice.getInvoice_id(), 
                    invoice.getInvoiceDateUpt()
                    
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void SetTableDataDetail(){
        List<Detail> detailList = new ArrayList<Detail>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        jTableDetail.setModel(defaultTableModel);
        defaultTableModel.addColumn("No.");
        
        defaultTableModel.addColumn("Dish Name");
        
        defaultTableModel.addColumn("Quantity");
        
        try{
            if(jLabelInvoiceID.getText().equals("")){
                return;
            }

            String sql = "call invoice_list_detail(?);";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1,Integer.parseInt(jLabelInvoiceID.getText()));
            rs = pStmt.executeQuery();
            while(rs.next()){
                Detail detail = new Detail();
                detail.setNo(rs.getInt("No."));
                
                detail.setDishName(rs.getString("dishName"));
                
                detail.setQuantity(rs.getInt("quantity"));
                detailList.add(detail);
            }
            
            for(Detail detail : detailList){
                defaultTableModel.addRow(new Object[]{
                    detail.getNo(),
                    
                    detail.getDishName(),
                    
                    detail.getQuantity()
                });
            }
        }catch (Exception e){e.printStackTrace();}
    }
    
    private void SetTableDataStatisticByMonth(){
        List<Statistic> statisticList = new ArrayList<Statistic>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        jTableStatisticByMonth.setModel(defaultTableModel);
        defaultTableModel.addColumn("Date");
        
        defaultTableModel.addColumn("Num of Invoice");
        
        defaultTableModel.addColumn("Total");
        
        try{
            String sql = "call statisticByMonth(?,?);";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,jTextFieldMonth.getText());
            pStmt.setString(2,jTextFieldYear.getText());
            rs = pStmt.executeQuery();
            
            while (rs.next()){
                Statistic statistic = new Statistic();
                statistic.setiDay(rs.getDate("iDay"));
                statistic.setNumInvoice(rs.getInt("NumInvoice"));
                statistic.setiTotal(rs.getLong("iTotal"));
                statisticList.add(statistic);
            }
            
            for(Statistic statistic : statisticList){
                defaultTableModel.addRow(new Object[]{
                    statistic.getiDay(),
                    
                    statistic.getNumInvoice(),
                    
                    statistic.getiTotal()
                });
            }
            
            PreparedStatement pStmt1 = conn.prepareStatement("select invoice_statisticNumByMonth(?,?) as totalNum");
            PreparedStatement pStmt2 = conn.prepareStatement("select invoice_statisticMonth(?,?) as totalIncome");
            
            pStmt1.setString(1,jTextFieldMonth.getText());
            pStmt1.setString(2,jTextFieldYear.getText());
            pStmt2.setString(1,jTextFieldMonth.getText());
            pStmt2.setString(2,jTextFieldYear.getText());
            
            ResultSet rs1 = pStmt1.executeQuery();
            ResultSet rs2 = pStmt2.executeQuery();
            rs1.next(); rs2.next();
            
            jLabelTtInvoice.setText(rs1.getString("totalNum"));
            jLabelTtIncome.setText(rs2.getString("totalIncome"));
            
        }catch (Exception e){e.printStackTrace();}
    }
    
    private void RefreshDataStaff(){
        /*defaultTableModel.setRowCount(0);*/
        SetTableDataWork();
        jLabelStaffID.setText("");
        jTextFieldStaffName.setText("");
        jLabeStafflPost.setText("");
        //jTextFieldDOB.setText("");
        
        jTextFieldStaffPhone.setText("");
        jLabelStaffState.setText("");
        jLabelStaffBonus.setText("0");
        jLabelTotalSal.setText("");
        jTextFieldSearch.setText("");
        
    }
    
    private void RefreshDataPost(){
        jLabelPost.setText("");
        jTextFieldNewPost.setText("");
        jTextFieldBasicSal.setText("");
        
    }
    private void RefreshDataAcc(){
        jTextFieldID.setText("");
        jTextFieldUsername.setText("");
        jTextFieldPassword.setText("");
    }
    private void RefreshDataCate(){
        jLabelCate.setText("");
    }
    private void RefreshDataDish(){
        SetTableDataDish();
        jLabelDishID.setText("");
        jLabelDishCate.setText("");
        jTextFieldDishName.setText("");
        jTextFieldDishPrice.setText("");
        jTextFieldDishUnit.setText("");
        jCheckBoxDMonday.setSelected(false);
        jCheckBoxDTuesday.setSelected(false);
        jCheckBoxDWednesday.setSelected(false);
        jCheckBoxDThursday.setSelected(false);
        jCheckBoxDFriday.setSelected(false);
        jCheckBoxDSaturday.setSelected(false);
        jCheckBoxDSunday.setSelected(false);
    }
    
    private void RefreshDataTable(){
        jTextFieldTableName.setText("");
        jLabelTableNumOfSeat.setText("");
    }
    private void UpdatePosition1(){
        defaultComboBoxModel = new DefaultComboBoxModel();
        jComboBoxStaffPost.setModel(defaultComboBoxModel);
        
        String sql = "call list_position";
        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                jComboBoxStaffPost.addItem(rs.getString("postName"));
                
                
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void UpdatePosition2(){
        defaultComboBoxModel = new DefaultComboBoxModel();
        jComboBoxPost.setModel(defaultComboBoxModel);
        
        String sql = "call list_position";
        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                jComboBoxPost.addItem(rs.getString("postName"));
                
                
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void UpdateCategory(){
        defaultComboBoxModel = new DefaultComboBoxModel();
        jComboBoxCategory.setModel(defaultComboBoxModel);
        String sql = "call list_category";
        
        try{
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                jComboBoxCategory.addItem(rs.getString("CateName"));
            }
        }catch (Exception e){e.printStackTrace();}
    }
    private void UpdateCategory2(){
        defaultComboBoxModel = new DefaultComboBoxModel();
        jComboBoxCategory2.setModel(defaultComboBoxModel);
        String sql = "call list_category";
        
        try{
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()){
                jComboBoxCategory2.addItem(rs.getString("CateName"));
            }
        }catch (Exception e){e.printStackTrace();}
    }
    
    private void NewStaff(){
        if (jLabelStaffID.getText().trim().equals("")){
            //Check name
            while(true){
                if(jTextFieldStaffName.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(HomeAdminFr.this, "Name is blank");
                    return;
                } else if (jTextFieldStaffName.getText().trim().length() > 50){
                    JOptionPane.showMessageDialog(HomeAdminFr.this, "Name is over 50 characters");
                    return;
                }else
                break;
            }
            //Check Position
            while(true){
                if(jLabeStafflPost.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(HomeAdminFr.this, "Position is blank");
                    return;
                } else
                break;
            }
            //Check Phone
            while(true){
                if(jTextFieldStaffPhone.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(HomeAdminFr.this, "Phone is blank");
                    return;
                } else if (!jTextFieldStaffPhone.getText().trim().matches("^0[0-9]{9}$")){
                    JOptionPane.showMessageDialog(HomeAdminFr.this, "Phone is wrong format.\n Correct is 10 numbers, with 0 at first\n Ex: 0xxxxxxxxx");
                    return;
                }else
                break;
            }
            try{
                cStmt = conn.prepareCall("call staff_new(?,?,?,?,?)");
                cStmt.setString(1, jTextFieldStaffName.getText());
                cStmt.setString(2, jLabeStafflPost.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(jDateChooserStaffDoB.getDate());
                cStmt.setString(3, date);
                cStmt.setString(4, jTextFieldStaffPhone.getText());
                cStmt.setInt(5,Integer.parseInt(jLabelStaffBonus.getText()));
                cStmt.executeUpdate();
                
                defaultTableModel.setRowCount(0);
                SetTableDataWork();
                RefreshDataStaff();
                JOptionPane.showMessageDialog(HomeAdminFr.this, "Add new Staff successful");
                
            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(HomeAdminFr.this, "Cannot add new staff, try again");}
        }else{
            JOptionPane.showMessageDialog(this, "Plese press Refresh before add new Staff");
        }
    }

    private void UpdateStaff(){
        if (!jLabelStaffID.getText().equals("")){
            if (jLabelStaffState.getText().equals("Work")){
                //Check name
                while(true){
                    if(jTextFieldStaffName.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(HomeAdminFr.this, "Name is blank");
                        return;
                    } else if (jTextFieldStaffName.getText().trim().length() > 50){
                        JOptionPane.showMessageDialog(HomeAdminFr.this, "Name is over 50 characters");
                        return;
                    }else
                    break;
                }
                //Check Position
                while(true){
                    if(jLabeStafflPost.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(HomeAdminFr.this, "Position is blank");
                        return;
                    } else
                    break;
                }
                //Check Phone
                while(true){
                    if(jTextFieldStaffPhone.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(HomeAdminFr.this, "Phone is blank");
                        return;
                    } else if (!jTextFieldStaffPhone.getText().trim().matches("^0[0-9]{9}$")){
                        JOptionPane.showMessageDialog(HomeAdminFr.this, "Phone is wrong format.\n Correct is 10 numbers, with 0 at first\n Ex: 0xxxxxxxxx");
                        return;
                    }else
                    break;
                }
                try{
                    cStmt = conn.prepareCall("call staff_update(?,?,?,?,?,?)");
                    cStmt.setInt(1,Integer.parseInt(jLabelStaffID.getText()));
                    cStmt.setString(2,jTextFieldStaffName.getText());
                    cStmt.setString(3,jLabeStafflPost.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(jDateChooserStaffDoB.getDate());
                    cStmt.setString(4,date);
                    cStmt.setString(5,jTextFieldStaffPhone.getText());
                    cStmt.setInt(6,Integer.parseInt(jLabelStaffBonus.getText()));
                    cStmt.executeQuery();
                    defaultTableModel.setRowCount(0);
                    SetTableDataWork();
                    RefreshDataStaff();
                    JOptionPane.showMessageDialog(this, "Update staff successful");
                    
                }catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(HomeAdminFr.this, "Cannnot update staff, try again");
                }
            } else {
                JOptionPane.showMessageDialog(this, "The staff is retied");
                SetTableDataWork();
                RefreshDataStaff();
            }    
        }else{
            JOptionPane.showMessageDialog(this, "Please choose the staff to update!");
        }
    }
    
    private void DeleteStaff(){
        if(!jLabelStaffID.getText().equals("")){
            if(jLabelStaffState.getText().equals("Work")){
                try{
                    cStmt = conn.prepareCall("call staff_delete(?)");
                    cStmt.setInt(1, Integer.parseInt(jLabelStaffID.getText()));
                    cStmt.executeQuery();
                    
                    defaultTableModel.setRowCount(0);
                    SetTableDataWork();
                    RefreshDataStaff();
                    JOptionPane.showMessageDialog(this, "Delete staff successful");
                }catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Cannot delete staff, try again");
                }
                
            }else {
                JOptionPane.showMessageDialog(this, "This staff has retired");
                SetTableDataWork();
                RefreshDataStaff();
            }
        }else {
            JOptionPane.showMessageDialog(this, "Please choose the staff to delete");
        }
    }
    
    private void SearchStaff(){
        
        if (!jTextFieldSearch.getText().equals("")){
            defaultTableModel.setRowCount(0);
            SetTableDataSearch();
        } else
            JOptionPane.showMessageDialog(this, "Name to search is blank");
    }
    
    private void SetStaffInfo(){
        String tblStaffID = null;
        String tblName = null;
        String tblPosition = null;
        String tblBirthday = null;
        String tblPhone = null;
        String tblState = null;
        String tblBonus = null;
        String tbltotalSal= null;
        
            DefaultTableModel tblModel = (DefaultTableModel)jTableStaff.getModel();
            int index = jTableStaff.getSelectedRow();
            if ( index == -1 ) {
                jLabelStaffID.setText("");
                jTextFieldStaffName.setText("");
                jLabeStafflPost.setText("");
                //jTextFieldDOB.setText("");
                
                jTextFieldStaffPhone.setText("");
                jLabelStaffState.setText("");
                jLabelStaffBonus.setText("");
                jLabelTotalSal.setText("");


            }else {
                tblStaffID = tblModel.getValueAt(index, 0).toString();
                tblName = tblModel.getValueAt(index, 1).toString();
                tblPosition = tblModel.getValueAt(index, 2).toString();
                try{
                    cStmt = (CallableStatement) conn.prepareCall("call staff_get_info(?);");
                    cStmt.setInt(1,Integer.parseInt(tblStaffID));
                    rs = cStmt.executeQuery();
                    rs.next();
                    //tblBirthday = rs.getString("DoB");
                    try {
                        tblBirthday = rs.getDate("DoB").toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = sdf.parse(tblBirthday);
                        jDateChooserStaffDoB.setDate(date);                   
                    } catch (ParseException ex) {
                        
                       Logger.getLogger(HomeAdminFr.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
   
                    tblPhone = rs.getString("staffPhone");
                    tblState = rs.getString("State");
                    tblBonus = rs.getString("Bonus");
                    tbltotalSal= rs.getString("totalSal");
                }catch(Exception e){e.printStackTrace();}
            jLabelStaffID.setText(tblStaffID);
            jTextFieldStaffName.setText(tblName);
            jLabeStafflPost.setText(tblPosition);
            //jTextFieldDOB.setText(tblBirthday);
            jTextFieldStaffPhone.setText(tblPhone);
            jLabelStaffState.setText(tblState);
            jLabelStaffBonus.setText(tblBonus);
            jLabelTotalSal.setText(tbltotalSal);
            }
    }
    
    private void SetDishInfo(){
        String tblDishID = null;
        String tblDishName = null;
        String tblDishCate = null;
        String tblDishPrice = null;
        String tblDishUnit = null;
        
        DefaultTableModel tblModel = (DefaultTableModel)jTableDish.getModel();
            int index = jTableDish.getSelectedRow();
            if ( index == -1 ) {
                jLabelDishID.setText("");
                jTextFieldDishName.setText("");
                jLabelDishCate.setText("");
                jTextFieldDishPrice.setText("");
                jTextFieldDishUnit.setText("");

            }else {
                tblDishID = tblModel.getValueAt(index, 0).toString();
                tblDishName = tblModel.getValueAt(index, 1).toString();
                tblDishPrice = tblModel.getValueAt(index, 2).toString();
                try{
                    cStmt = (CallableStatement) conn.prepareCall("call dish_get_info(?);");
                    cStmt.setInt(1,Integer.parseInt(tblDishID));
                    rs = cStmt.executeQuery();
                    rs.next();
                    
                    
                    tblDishCate = rs.getString("cateName");
                    
                    tblDishUnit = rs.getString("unit");
                    
                }catch(Exception e){e.printStackTrace();}
                
                
                
            jLabelDishID.setText(tblDishID);
            jTextFieldDishName.setText(tblDishName);
            jLabelDishCate.setText(tblDishCate);
            jTextFieldDishPrice.setText(tblDishPrice);
            jTextFieldDishUnit.setText(tblDishUnit);
            
            try{
                CallableStatement cStmt1 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt1.setString(1, jCheckBoxDMonday.getText());
                cStmt1.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs1 = cStmt1.executeQuery();
                if(rs1.next()){
                    switch(rs1.getInt("temp")){
                        case 1:
                            jCheckBoxDMonday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDMonday.setSelected(false);
                            break;
                    }
                }
                
                CallableStatement cStmt2 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt2.setString(1, jCheckBoxDTuesday.getText());
                cStmt2.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs2 = cStmt2.executeQuery();
                if(rs2.next()){
                    switch(rs2.getInt("temp")){
                        case 1:
                            jCheckBoxDTuesday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDTuesday.setSelected(false);
                            break;
                    }
                }
                
                CallableStatement cStmt3 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt3.setString(1, jCheckBoxDWednesday.getText());
                cStmt3.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs3 = cStmt3.executeQuery();
                if(rs3.next()){
                    switch(rs3.getInt("temp")){
                        case 1:
                            jCheckBoxDWednesday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDWednesday.setSelected(false);
                            break;
                    }
                }
                
                CallableStatement cStmt4 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt4.setString(1, jCheckBoxDThursday.getText());
                cStmt4.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs4 = cStmt4.executeQuery();
                if(rs4.next()){
                    switch(rs4.getInt("temp")){
                        case 1:
                            jCheckBoxDThursday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDThursday.setSelected(false);
                            break;
                    }
                }
                
                CallableStatement cStmt5 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt5.setString(1, jCheckBoxDFriday.getText());
                cStmt5.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs5 = cStmt5.executeQuery();
                if(rs5.next()){
                    switch(rs5.getInt("temp")){
                        case 1:
                            jCheckBoxDFriday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDFriday.setSelected(false);
                            break;
                    }
                }
                
                CallableStatement cStmt6 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt6.setString(1, jCheckBoxDSaturday.getText());
                cStmt6.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs6 = cStmt6.executeQuery();
                if(rs6.next()){
                    switch(rs6.getInt("temp")){
                        case 1:
                            jCheckBoxDSaturday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDSaturday.setSelected(false);
                            break;
                    }
                }
                
                CallableStatement cStmt7 = conn.prepareCall("select menu_getExists(?,?) as temp");
                cStmt7.setString(1, jCheckBoxDSunday.getText());
                cStmt7.setInt(2, Integer.parseInt(jLabelDishID.getText()));
                ResultSet rs7 = cStmt7.executeQuery();
                if(rs7.next()){
                    switch(rs7.getInt("temp")){
                        case 1:
                            jCheckBoxDSunday.setSelected(true);
                            break;
                        default:
                            jCheckBoxDSunday.setSelected(false);
                            break;
                    }
                }
            }catch (Exception e){e.printStackTrace();}
            }
    }
    
    private void SetInvoiceInfo(){
        String tblInvoiceID = null;
        String tblInvoiceTable = null;
        String tblInvoiceStaff = null;
        String tblInvoiceDate = null;
        String tblInvoiceDateUpt = null;
        String tblInvoiceTotal = null;
        String tblInvoiceState = null;
        
        DefaultTableModel tblModel = (DefaultTableModel)jTableInvoiceList.getModel();
        int index = jTableInvoiceList.getSelectedRow();
        
        if ( index == -1 ) {
                jLabelInvoiceID.setText("");
                jLabelInvoiceTable.setText("");
                jLabelInvoiceStaff.setText("");
                jLabelInvoiceDate.setText("");
                jLabelInvoiceDateUpt.setText("");
                jLabelInvoiceTotal.setText("");
                jLabelInvoiceState.setText("");
                

            }else {
                tblInvoiceID = tblModel.getValueAt(index, 0).toString();
                
                
                try{
                    cStmt = conn.prepareCall("call invoice_get_info(?)");
                    cStmt.setInt(1,Integer.parseInt(tblInvoiceID));
                    rs=cStmt.executeQuery();
                    rs.next();
                    
                    tblInvoiceTable=rs.getString("tableName");
                    tblInvoiceStaff=rs.getString("staff");
                    try{
                       tblInvoiceDate = rs.getTimestamp("invoiceDate").toString();
                    }catch(Exception e){
                    }
                    try{
                       tblInvoiceDateUpt = rs.getTimestamp("invoiceDateUpt").toString();
                    }catch(Exception e){
                    }
                    tblInvoiceTotal=rs.getString("invoiceTotal");
                    tblInvoiceState=rs.getString("state");
                }catch (Exception e){e.printStackTrace();}

                jLabelInvoiceID.setText(tblInvoiceID);
                jLabelInvoiceTable.setText(tblInvoiceTable);
                jLabelInvoiceStaff.setText(tblInvoiceStaff);
                jLabelInvoiceDate.setText(tblInvoiceDate);
                jLabelInvoiceDateUpt.setText(tblInvoiceDateUpt);
                jLabelInvoiceTotal.setText(tblInvoiceTotal);
                jLabelInvoiceState.setText(tblInvoiceState);
                
                SetTableDataDetail();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        PanelWelcome = new javax.swing.JPanel();
        jButtonLogout = new javax.swing.JButton();
        LabelWelcome = new javax.swing.JLabel();
        txtAccount_id = new javax.swing.JLabel();
        jButtonChangePassword = new javax.swing.JButton();
        jLabelClock = new javax.swing.JLabel();
        TabbedPaneAdmin = new javax.swing.JTabbedPane();
        TabStaff = new javax.swing.JPanel();
        StaffInfo = new javax.swing.JPanel();
        TitleStaff = new javax.swing.JLabel();
        StaffID = new javax.swing.JLabel();
        jLabelStaffID = new javax.swing.JLabel();
        StaffName = new javax.swing.JLabel();
        jTextFieldStaffName = new javax.swing.JTextField();
        StaffPosition = new javax.swing.JLabel();
        jLabeStafflPost = new javax.swing.JLabel();
        jComboBoxStaffPost = new javax.swing.JComboBox<>();
        StaffBirthday = new javax.swing.JLabel();
        jDateChooserStaffDoB = new com.toedter.calendar.JDateChooser();
        StaffState = new javax.swing.JLabel();
        jLabelStaffState = new javax.swing.JLabel();
        StaffPhone = new javax.swing.JLabel();
        jTextFieldStaffPhone = new javax.swing.JTextField();
        StaffBonus = new javax.swing.JLabel();
        jLabelStaffBonus = new javax.swing.JLabel();
        jSpinnerStaffBonus = new javax.swing.JSpinner();
        percent = new javax.swing.JLabel();
        totalsal = new javax.swing.JLabel();
        jLabelTotalSal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStaff = new javax.swing.JTable();
        jButtonWork = new javax.swing.JButton();
        jButtonRetired = new javax.swing.JButton();
        jButtonDeleteStaff = new javax.swing.JButton();
        jButtonNewStaff = new javax.swing.JButton();
        jButtonUpdateStaff = new javax.swing.JButton();
        jButtonRefreshStaff = new javax.swing.JButton();
        jButtonSearchStaff = new javax.swing.JButton();
        jTextFieldSearch = new javax.swing.JTextField();
        Entertosearch = new javax.swing.JLabel();
        Cateinfo = new javax.swing.JPanel();
        TitlePost = new javax.swing.JLabel();
        jComboBoxPost = new javax.swing.JComboBox<>();
        jLabelPost = new javax.swing.JLabel();
        NewPost = new javax.swing.JLabel();
        jTextFieldNewPost = new javax.swing.JTextField();
        jTextFieldBasicSal = new javax.swing.JTextField();
        BasicSal = new javax.swing.JLabel();
        jButtonUpdatePost = new javax.swing.JButton();
        jButtonNewPost = new javax.swing.JButton();
        AccountInfo = new javax.swing.JPanel();
        TitleAccount = new javax.swing.JLabel();
        AccountID = new javax.swing.JLabel();
        Username = new javax.swing.JLabel();
        Password = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jTextFieldUsername = new javax.swing.JTextField();
        jTextFieldPassword = new javax.swing.JTextField();
        jButtonCreateAcc = new javax.swing.JButton();
        jButtonAccList = new javax.swing.JButton();
        TabMenu = new javax.swing.JPanel();
        CategoryInfo = new javax.swing.JPanel();
        NewCate = new javax.swing.JLabel();
        jTextFieldNewCate = new javax.swing.JTextField();
        jButtonNewCate = new javax.swing.JButton();
        MenuByCate = new javax.swing.JPanel();
        Category = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDish = new javax.swing.JTable();
        jLabelCate = new javax.swing.JLabel();
        jButtonDishSearch = new javax.swing.JButton();
        jButtonDishDelete = new javax.swing.JButton();
        jButtonEditHomeCustomer = new javax.swing.JButton();
        DishInfo = new javax.swing.JPanel();
        Information = new javax.swing.JLabel();
        DishID = new javax.swing.JLabel();
        DishName = new javax.swing.JLabel();
        DishCategory = new javax.swing.JLabel();
        DishPrice = new javax.swing.JLabel();
        jLabelDishID = new javax.swing.JLabel();
        jTextFieldDishName = new javax.swing.JTextField();
        jComboBoxCategory2 = new javax.swing.JComboBox<>();
        jLabelDishCate = new javax.swing.JLabel();
        jTextFieldDishPrice = new javax.swing.JTextField();
        jTextFieldDishUnit = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButtonNewDish = new javax.swing.JButton();
        jButtonUpdateDish = new javax.swing.JButton();
        jButtonDeleteDish = new javax.swing.JButton();
        jButtonRefeshDish = new javax.swing.JButton();
        jCheckBoxDMonday = new javax.swing.JCheckBox();
        jCheckBoxDWednesday = new javax.swing.JCheckBox();
        jCheckBoxDThursday = new javax.swing.JCheckBox();
        jCheckBoxDFriday = new javax.swing.JCheckBox();
        jCheckBoxDSaturday = new javax.swing.JCheckBox();
        jCheckBoxDSunday = new javax.swing.JCheckBox();
        jCheckBoxDTuesday = new javax.swing.JCheckBox();
        TableInfo = new javax.swing.JPanel();
        Tableinformation = new javax.swing.JLabel();
        TableName = new javax.swing.JLabel();
        NumOfSeat = new javax.swing.JLabel();
        jTextFieldTableName = new javax.swing.JTextField();
        jSpinnerTableNumOfSeat = new javax.swing.JSpinner();
        jLabelTableNumOfSeat = new javax.swing.JLabel();
        jButtonTableNew = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        TabInvoice = new javax.swing.JPanel();
        InvoiceList = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableInvoiceList = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        InvoiceDetail = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelInvoiceID = new javax.swing.JLabel();
        jLabelInvoiceTable = new javax.swing.JLabel();
        jLabelInvoiceStaff = new javax.swing.JLabel();
        jLabelInvoiceDate = new javax.swing.JLabel();
        jLabelInvoiceDateUpt = new javax.swing.JLabel();
        jLabelInvoiceTotal = new javax.swing.JLabel();
        jLabelInvoiceState = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableDetail = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        TabStatistic = new javax.swing.JPanel();
        StatisticIncome = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldYear = new javax.swing.JTextField();
        jTextFieldMonth = new javax.swing.JTextField();
        jButtonStatisticSearch = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableStatisticByMonth = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelTtIncome = new javax.swing.JLabel();
        jLabelTtInvoice = new javax.swing.JLabel();
        TabInventory = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Homepage");
        setResizable(false);

        background.setBackground(new java.awt.Color(0, 153, 153));
        background.setPreferredSize(new java.awt.Dimension(1000, 600));

        PanelWelcome.setBackground(new java.awt.Color(255, 255, 255));

        jButtonLogout.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        jButtonLogout.setToolTipText("");
        jButtonLogout.setBorder(null);
        jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogoutActionPerformed(evt);
            }
        });

        LabelWelcome.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelWelcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelWelcome.setText("Restaurant Management");

        txtAccount_id.setBackground(new java.awt.Color(255, 255, 255));
        txtAccount_id.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtAccount_id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAccount_id.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtAccount_idPropertyChange(evt);
            }
        });

        jButtonChangePassword.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonChangePassword.setText("Change Password");
        jButtonChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangePasswordActionPerformed(evt);
            }
        });

        jLabelClock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout PanelWelcomeLayout = new javax.swing.GroupLayout(PanelWelcome);
        PanelWelcome.setLayout(PanelWelcomeLayout);
        PanelWelcomeLayout.setHorizontalGroup(
            PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelClock, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonChangePassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAccount_id, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelWelcomeLayout.setVerticalGroup(
            PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonLogout)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LabelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonChangePassword))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(PanelWelcomeLayout.createSequentialGroup()
                            .addComponent(jLabelClock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(6, 6, 6))
                        .addComponent(txtAccount_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabbedPaneAdmin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TabbedPaneAdmin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TabbedPaneAdminStateChanged(evt);
            }
        });

        TabStaff.setBackground(new java.awt.Color(255, 255, 255));

        StaffInfo.setLayout(null);

        TitleStaff.setBackground(new java.awt.Color(255, 255, 255));
        TitleStaff.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TitleStaff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleStaff.setText("Staff Information");
        StaffInfo.add(TitleStaff);
        TitleStaff.setBounds(10, 11, 560, 29);

        StaffID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffID.setText("ID:");
        StaffInfo.add(StaffID);
        StaffID.setBounds(10, 51, 64, 24);

        jLabelStaffID.setBackground(new java.awt.Color(255, 255, 255));
        jLabelStaffID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelStaffID.setText(" ");
        StaffInfo.add(jLabelStaffID);
        jLabelStaffID.setBounds(81, 51, 59, 24);

        StaffName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffName.setText("Name:");
        StaffInfo.add(StaffName);
        StaffName.setBounds(10, 80, 64, 33);

        jTextFieldStaffName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        StaffInfo.add(jTextFieldStaffName);
        jTextFieldStaffName.setBounds(80, 80, 190, 30);

        StaffPosition.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffPosition.setText("Position:");
        StaffInfo.add(StaffPosition);
        StaffPosition.setBounds(300, 80, 61, 30);

        jLabeStafflPost.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabeStafflPost.setText(" ");
        StaffInfo.add(jLabeStafflPost);
        jLabeStafflPost.setBounds(369, 80, 70, 30);

        jComboBoxStaffPost.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxStaffPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxStaffPostActionPerformed(evt);
            }
        });
        StaffInfo.add(jComboBoxStaffPost);
        jComboBoxStaffPost.setBounds(440, 80, 88, 31);

        StaffBirthday.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffBirthday.setText("Birthday:");
        StaffInfo.add(StaffBirthday);
        StaffBirthday.setBounds(10, 120, 65, 30);

        jDateChooserStaffDoB.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        StaffInfo.add(jDateChooserStaffDoB);
        jDateChooserStaffDoB.setBounds(80, 120, 190, 30);

        StaffState.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffState.setText("State:");
        StaffInfo.add(StaffState);
        StaffState.setBounds(300, 120, 61, 30);

        jLabelStaffState.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelStaffState.setText(" ");
        StaffInfo.add(jLabelStaffState);
        jLabelStaffState.setBounds(369, 120, 70, 32);

        StaffPhone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffPhone.setText("Phone:");
        StaffInfo.add(StaffPhone);
        StaffPhone.setBounds(10, 160, 64, 30);

        jTextFieldStaffPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        StaffInfo.add(jTextFieldStaffPhone);
        jTextFieldStaffPhone.setBounds(80, 160, 190, 30);

        StaffBonus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        StaffBonus.setText("Bonus:");
        StaffInfo.add(StaffBonus);
        StaffBonus.setBounds(300, 160, 61, 30);

        jLabelStaffBonus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelStaffBonus.setText(" ");
        StaffInfo.add(jLabelStaffBonus);
        jLabelStaffBonus.setBounds(370, 160, 70, 32);

        jSpinnerStaffBonus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSpinnerStaffBonus.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerStaffBonusStateChanged(evt);
            }
        });
        StaffInfo.add(jSpinnerStaffBonus);
        jSpinnerStaffBonus.setBounds(460, 160, 70, 32);

        percent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        percent.setText("%");
        StaffInfo.add(percent);
        percent.setBounds(440, 160, 17, 30);

        totalsal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalsal.setText("Total Salary:");
        StaffInfo.add(totalsal);
        totalsal.setBounds(300, 200, 86, 30);

        jLabelTotalSal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotalSal.setText("  ");
        StaffInfo.add(jLabelTotalSal);
        jLabelTotalSal.setBounds(398, 200, 130, 32);

        jTableStaff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTableStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Position"
            }
        ));
        jTableStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableStaffMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableStaff);

        StaffInfo.add(jScrollPane1);
        jScrollPane1.setBounds(10, 290, 450, 148);

        jButtonWork.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonWork.setForeground(new java.awt.Color(51, 255, 0));
        jButtonWork.setText("Work");
        jButtonWork.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonWork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWorkActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonWork);
        jButtonWork.setBounds(470, 310, 60, 40);

        jButtonRetired.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonRetired.setForeground(new java.awt.Color(255, 51, 0));
        jButtonRetired.setText("Retired");
        jButtonRetired.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonRetired.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRetiredActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonRetired);
        jButtonRetired.setBounds(470, 380, 60, 40);

        jButtonDeleteStaff.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonDeleteStaff.setText("Delete");
        jButtonDeleteStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteStaffActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonDeleteStaff);
        jButtonDeleteStaff.setBounds(190, 250, 80, 30);

        jButtonNewStaff.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonNewStaff.setText("New");
        jButtonNewStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewStaffActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonNewStaff);
        jButtonNewStaff.setBounds(10, 250, 80, 30);

        jButtonUpdateStaff.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonUpdateStaff.setText("Update");
        jButtonUpdateStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateStaffActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonUpdateStaff);
        jButtonUpdateStaff.setBounds(100, 250, 80, 30);

        jButtonRefreshStaff.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonRefreshStaff.setText("Refresh");
        jButtonRefreshStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshStaffActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonRefreshStaff);
        jButtonRefreshStaff.setBounds(10, 210, 80, 30);

        jButtonSearchStaff.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonSearchStaff.setText("Search");
        jButtonSearchStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchStaffActionPerformed(evt);
            }
        });
        StaffInfo.add(jButtonSearchStaff);
        jButtonSearchStaff.setBounds(430, 250, 100, 30);

        jTextFieldSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        StaffInfo.add(jTextFieldSearch);
        jTextFieldSearch.setBounds(280, 250, 150, 30);

        Entertosearch.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        Entertosearch.setText("Enter to search:");
        StaffInfo.add(Entertosearch);
        Entertosearch.setBounds(290, 230, 120, 20);

        Cateinfo.setLayout(null);

        TitlePost.setBackground(new java.awt.Color(255, 255, 255));
        TitlePost.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TitlePost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitlePost.setText("Position");
        Cateinfo.add(TitlePost);
        TitlePost.setBounds(10, 10, 380, 29);

        jComboBoxPost.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPostActionPerformed(evt);
            }
        });
        Cateinfo.add(jComboBoxPost);
        jComboBoxPost.setBounds(10, 60, 90, 30);

        jLabelPost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Cateinfo.add(jLabelPost);
        jLabelPost.setBounds(110, 60, 80, 30);

        NewPost.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        NewPost.setText("New position:");
        Cateinfo.add(NewPost);
        NewPost.setBounds(200, 60, 100, 30);

        jTextFieldNewPost.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldNewPost.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Cateinfo.add(jTextFieldNewPost);
        jTextFieldNewPost.setBounds(300, 60, 80, 30);

        jTextFieldBasicSal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextFieldBasicSal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Cateinfo.add(jTextFieldBasicSal);
        jTextFieldBasicSal.setBounds(80, 110, 210, 30);

        BasicSal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        BasicSal.setText("Basic:");
        Cateinfo.add(BasicSal);
        BasicSal.setBounds(20, 110, 50, 30);

        jButtonUpdatePost.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonUpdatePost.setText("Update");
        jButtonUpdatePost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdatePostActionPerformed(evt);
            }
        });
        Cateinfo.add(jButtonUpdatePost);
        jButtonUpdatePost.setBounds(300, 110, 80, 30);

        jButtonNewPost.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonNewPost.setText("New");
        jButtonNewPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewPostActionPerformed(evt);
            }
        });
        Cateinfo.add(jButtonNewPost);
        jButtonNewPost.setBounds(160, 150, 80, 30);

        AccountInfo.setLayout(null);

        TitleAccount.setBackground(new java.awt.Color(255, 255, 255));
        TitleAccount.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        TitleAccount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleAccount.setText("Account");
        AccountInfo.add(TitleAccount);
        TitleAccount.setBounds(10, 10, 380, 29);

        AccountID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AccountID.setText("Staff ID:");
        AccountInfo.add(AccountID);
        AccountID.setBounds(20, 70, 80, 30);

        Username.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Username.setText("Username:");
        AccountInfo.add(Username);
        Username.setBounds(20, 110, 80, 30);

        Password.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Password.setText("Password:");
        AccountInfo.add(Password);
        Password.setBounds(20, 150, 80, 30);

        jTextFieldID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AccountInfo.add(jTextFieldID);
        jTextFieldID.setBounds(110, 70, 150, 30);

        jTextFieldUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AccountInfo.add(jTextFieldUsername);
        jTextFieldUsername.setBounds(110, 110, 150, 30);

        jTextFieldPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        AccountInfo.add(jTextFieldPassword);
        jTextFieldPassword.setBounds(110, 150, 150, 30);

        jButtonCreateAcc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonCreateAcc.setText("Create");
        jButtonCreateAcc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCreateAccActionPerformed(evt);
            }
        });
        AccountInfo.add(jButtonCreateAcc);
        jButtonCreateAcc.setBounds(290, 110, 80, 30);

        jButtonAccList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonAccList.setText("List");
        jButtonAccList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAccListActionPerformed(evt);
            }
        });
        AccountInfo.add(jButtonAccList);
        jButtonAccList.setBounds(290, 190, 80, 30);

        javax.swing.GroupLayout TabStaffLayout = new javax.swing.GroupLayout(TabStaff);
        TabStaff.setLayout(TabStaffLayout);
        TabStaffLayout.setHorizontalGroup(
            TabStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StaffInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(TabStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Cateinfo, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(AccountInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TabStaffLayout.setVerticalGroup(
            TabStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TabStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(StaffInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(TabStaffLayout.createSequentialGroup()
                        .addComponent(Cateinfo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AccountInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)))
                .addContainerGap())
        );

        TabbedPaneAdmin.addTab("Staff", TabStaff);

        TabMenu.setBackground(new java.awt.Color(255, 255, 255));

        CategoryInfo.setLayout(null);

        NewCate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        NewCate.setText("New Category:");
        CategoryInfo.add(NewCate);
        NewCate.setBounds(10, 30, 110, 30);

        jTextFieldNewCate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CategoryInfo.add(jTextFieldNewCate);
        jTextFieldNewCate.setBounds(120, 30, 190, 30);

        jButtonNewCate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonNewCate.setText("New");
        jButtonNewCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewCateActionPerformed(evt);
            }
        });
        CategoryInfo.add(jButtonNewCate);
        jButtonNewCate.setBounds(320, 30, 70, 30);

        MenuByCate.setLayout(null);

        Category.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Category.setText("Category:");
        MenuByCate.add(Category);
        Category.setBounds(10, 10, 80, 30);

        jComboBoxCategory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBoxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoryActionPerformed(evt);
            }
        });
        MenuByCate.add(jComboBoxCategory);
        jComboBoxCategory.setBounds(90, 10, 150, 30);

        jTableDish.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price"
            }
        ));
        jTableDish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDishMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableDish);

        MenuByCate.add(jScrollPane2);
        jScrollPane2.setBounds(10, 90, 410, 190);

        jLabelCate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelCate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MenuByCate.add(jLabelCate);
        jLabelCate.setBounds(10, 50, 180, 30);

        jButtonDishSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDishSearch.setText("Search");
        jButtonDishSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDishSearchActionPerformed(evt);
            }
        });
        MenuByCate.add(jButtonDishSearch);
        jButtonDishSearch.setBounds(210, 50, 80, 30);

        jButtonDishDelete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonDishDelete.setText("List Delete");
        jButtonDishDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDishDeleteActionPerformed(evt);
            }
        });
        MenuByCate.add(jButtonDishDelete);
        jButtonDishDelete.setBounds(10, 300, 110, 30);

        jButtonEditHomeCustomer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonEditHomeCustomer.setText("Edit");
        jButtonEditHomeCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditHomeCustomerActionPerformed(evt);
            }
        });
        MenuByCate.add(jButtonEditHomeCustomer);
        jButtonEditHomeCustomer.setBounds(310, 300, 110, 30);

        DishInfo.setLayout(null);

        Information.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Information.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Information.setText("Dish Information");
        DishInfo.add(Information);
        Information.setBounds(0, 0, 500, 60);

        DishID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DishID.setText("ID:");
        DishInfo.add(DishID);
        DishID.setBounds(20, 60, 30, 30);

        DishName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DishName.setText("Name:");
        DishInfo.add(DishName);
        DishName.setBounds(20, 110, 70, 30);

        DishCategory.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DishCategory.setText("Caterogy:");
        DishInfo.add(DishCategory);
        DishCategory.setBounds(130, 60, 70, 30);

        DishPrice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DishPrice.setText("Price:");
        DishInfo.add(DishPrice);
        DishPrice.setBounds(260, 110, 50, 30);

        jLabelDishID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDishID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DishInfo.add(jLabelDishID);
        jLabelDishID.setBounds(50, 60, 60, 30);

        jTextFieldDishName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DishInfo.add(jTextFieldDishName);
        jTextFieldDishName.setBounds(90, 110, 160, 30);

        jComboBoxCategory2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jComboBoxCategory2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategory2ActionPerformed(evt);
            }
        });
        DishInfo.add(jComboBoxCategory2);
        jComboBoxCategory2.setBounds(370, 60, 120, 30);

        jLabelDishCate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelDishCate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DishInfo.add(jLabelDishCate);
        jLabelDishCate.setBounds(210, 60, 160, 30);

        jTextFieldDishPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextFieldDishPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        DishInfo.add(jTextFieldDishPrice);
        jTextFieldDishPrice.setBounds(310, 110, 120, 30);

        jTextFieldDishUnit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        DishInfo.add(jTextFieldDishUnit);
        jTextFieldDishUnit.setBounds(440, 110, 50, 30);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("/");
        DishInfo.add(jLabel1);
        jLabel1.setBounds(430, 100, 10, 50);

        jButtonNewDish.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonNewDish.setText("New");
        jButtonNewDish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewDishActionPerformed(evt);
            }
        });
        DishInfo.add(jButtonNewDish);
        jButtonNewDish.setBounds(410, 150, 80, 30);

        jButtonUpdateDish.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonUpdateDish.setText("Update");
        jButtonUpdateDish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateDishActionPerformed(evt);
            }
        });
        DishInfo.add(jButtonUpdateDish);
        jButtonUpdateDish.setBounds(410, 190, 80, 30);

        jButtonDeleteDish.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonDeleteDish.setText("Delete");
        jButtonDeleteDish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteDishActionPerformed(evt);
            }
        });
        DishInfo.add(jButtonDeleteDish);
        jButtonDeleteDish.setBounds(410, 230, 80, 30);

        jButtonRefeshDish.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonRefeshDish.setText("Refresh");
        jButtonRefeshDish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefeshDishActionPerformed(evt);
            }
        });
        DishInfo.add(jButtonRefeshDish);
        jButtonRefeshDish.setBounds(320, 150, 80, 30);

        jCheckBoxDMonday.setText("Monday");
        DishInfo.add(jCheckBoxDMonday);
        jCheckBoxDMonday.setBounds(20, 150, 100, 23);

        jCheckBoxDWednesday.setText("Wednesday");
        DishInfo.add(jCheckBoxDWednesday);
        jCheckBoxDWednesday.setBounds(20, 180, 100, 23);

        jCheckBoxDThursday.setText("Thursday");
        DishInfo.add(jCheckBoxDThursday);
        jCheckBoxDThursday.setBounds(150, 180, 100, 23);

        jCheckBoxDFriday.setText("Friday");
        DishInfo.add(jCheckBoxDFriday);
        jCheckBoxDFriday.setBounds(20, 210, 100, 23);

        jCheckBoxDSaturday.setText("Saturday");
        DishInfo.add(jCheckBoxDSaturday);
        jCheckBoxDSaturday.setBounds(150, 210, 100, 23);

        jCheckBoxDSunday.setText("Sunday");
        DishInfo.add(jCheckBoxDSunday);
        jCheckBoxDSunday.setBounds(20, 240, 100, 23);

        jCheckBoxDTuesday.setText("Tuesday");
        DishInfo.add(jCheckBoxDTuesday);
        jCheckBoxDTuesday.setBounds(150, 150, 100, 23);

        TableInfo.setLayout(null);

        Tableinformation.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Tableinformation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Tableinformation.setText("Table");
        TableInfo.add(Tableinformation);
        Tableinformation.setBounds(0, 0, 500, 40);

        TableName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        TableName.setText("Table Name:");
        TableInfo.add(TableName);
        TableName.setBounds(80, 40, 90, 30);

        NumOfSeat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        NumOfSeat.setText("Seats:");
        TableInfo.add(NumOfSeat);
        NumOfSeat.setBounds(80, 80, 90, 30);

        jTextFieldTableName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TableInfo.add(jTextFieldTableName);
        jTextFieldTableName.setBounds(180, 40, 180, 30);

        jSpinnerTableNumOfSeat.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerTableNumOfSeatStateChanged(evt);
            }
        });
        TableInfo.add(jSpinnerTableNumOfSeat);
        jSpinnerTableNumOfSeat.setBounds(300, 80, 60, 30);

        jLabelTableNumOfSeat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTableNumOfSeat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TableInfo.add(jLabelTableNumOfSeat);
        jLabelTableNumOfSeat.setBounds(180, 80, 80, 30);

        jButtonTableNew.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonTableNew.setText("New");
        jButtonTableNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTableNewActionPerformed(evt);
            }
        });
        TableInfo.add(jButtonTableNew);
        jButtonTableNew.setBounds(370, 80, 80, 30);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("List Table");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        TableInfo.add(jButton1);
        jButton1.setBounds(350, 120, 100, 30);

        javax.swing.GroupLayout TabMenuLayout = new javax.swing.GroupLayout(TabMenu);
        TabMenu.setLayout(TabMenuLayout);
        TabMenuLayout.setHorizontalGroup(
            TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CategoryInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                    .addComponent(MenuByCate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DishInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                    .addComponent(TableInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TabMenuLayout.setVerticalGroup(
            TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TabMenuLayout.createSequentialGroup()
                        .addComponent(DishInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(TableInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TabMenuLayout.createSequentialGroup()
                        .addComponent(CategoryInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MenuByCate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        TabbedPaneAdmin.addTab("Menu/Table", TabMenu);

        TabInvoice.setBackground(new java.awt.Color(255, 255, 255));

        InvoiceList.setLayout(null);

        jTableInvoiceList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date"
            }
        ));
        jTableInvoiceList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableInvoiceListMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableInvoiceList);

        InvoiceList.add(jScrollPane3);
        jScrollPane3.setBounds(10, 140, 320, 300);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Invoices");
        InvoiceList.add(jLabel13);
        jLabel13.setBounds(0, 0, 340, 40);

        InvoiceDetail.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("State:");
        InvoiceDetail.add(jLabel2);
        jLabel2.setBounds(320, 170, 50, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Invoice ID:");
        InvoiceDetail.add(jLabel3);
        jLabel3.setBounds(20, 50, 80, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Table:");
        InvoiceDetail.add(jLabel4);
        jLabel4.setBounds(320, 50, 50, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Staff:");
        InvoiceDetail.add(jLabel5);
        jLabel5.setBounds(20, 80, 90, 30);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Date:");
        InvoiceDetail.add(jLabel6);
        jLabel6.setBounds(20, 110, 90, 30);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Date Update:");
        InvoiceDetail.add(jLabel7);
        jLabel7.setBounds(20, 140, 90, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Total:");
        InvoiceDetail.add(jLabel8);
        jLabel8.setBounds(20, 170, 50, 30);

        jLabelInvoiceID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceID);
        jLabelInvoiceID.setBounds(120, 50, 70, 30);

        jLabelInvoiceTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceTable);
        jLabelInvoiceTable.setBounds(390, 50, 110, 30);

        jLabelInvoiceStaff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceStaff);
        jLabelInvoiceStaff.setBounds(120, 80, 200, 30);

        jLabelInvoiceDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceDate);
        jLabelInvoiceDate.setBounds(120, 110, 200, 30);

        jLabelInvoiceDateUpt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceDateUpt);
        jLabelInvoiceDateUpt.setBounds(120, 140, 200, 30);

        jLabelInvoiceTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceTotal);
        jLabelInvoiceTotal.setBounds(80, 170, 100, 30);

        jLabelInvoiceState.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        InvoiceDetail.add(jLabelInvoiceState);
        jLabelInvoiceState.setBounds(390, 170, 110, 30);

        jTableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Dish Name", "Quantity"
            }
        ));
        jScrollPane4.setViewportView(jTableDetail);

        InvoiceDetail.add(jScrollPane4);
        jScrollPane4.setBounds(10, 220, 580, 220);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Detail");
        InvoiceDetail.add(jLabel14);
        jLabel14.setBounds(0, 0, 600, 40);

        javax.swing.GroupLayout TabInvoiceLayout = new javax.swing.GroupLayout(TabInvoice);
        TabInvoice.setLayout(TabInvoiceLayout);
        TabInvoiceLayout.setHorizontalGroup(
            TabInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(InvoiceDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        TabInvoiceLayout.setVerticalGroup(
            TabInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TabInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InvoiceList, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(InvoiceDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        TabbedPaneAdmin.addTab("Invoice", TabInvoice);

        TabStatistic.setBackground(new java.awt.Color(255, 255, 255));

        StatisticIncome.setLayout(null);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Year:");
        StatisticIncome.add(jLabel9);
        jLabel9.setBounds(150, 60, 60, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Total Income:");
        StatisticIncome.add(jLabel10);
        jLabel10.setBounds(10, 170, 110, 30);
        StatisticIncome.add(jTextFieldYear);
        jTextFieldYear.setBounds(210, 60, 80, 30);
        StatisticIncome.add(jTextFieldMonth);
        jTextFieldMonth.setBounds(80, 60, 60, 30);

        jButtonStatisticSearch.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonStatisticSearch.setText("Search");
        jButtonStatisticSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStatisticSearchActionPerformed(evt);
            }
        });
        StatisticIncome.add(jButtonStatisticSearch);
        jButtonStatisticSearch.setBounds(360, 60, 90, 30);

        jTableStatisticByMonth.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Num of Invoice", "Total"
            }
        ));
        jScrollPane5.setViewportView(jTableStatisticByMonth);

        StatisticIncome.add(jScrollPane5);
        jScrollPane5.setBounds(10, 210, 452, 230);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Month:");
        StatisticIncome.add(jLabel11);
        jLabel11.setBounds(10, 60, 60, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Total Invoice:");
        StatisticIncome.add(jLabel12);
        jLabel12.setBounds(10, 120, 110, 30);

        jLabelTtIncome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        StatisticIncome.add(jLabelTtIncome);
        jLabelTtIncome.setBounds(140, 170, 150, 30);

        jLabelTtInvoice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        StatisticIncome.add(jLabelTtInvoice);
        jLabelTtInvoice.setBounds(140, 120, 150, 30);

        javax.swing.GroupLayout TabStatisticLayout = new javax.swing.GroupLayout(TabStatistic);
        TabStatistic.setLayout(TabStatisticLayout);
        TabStatisticLayout.setHorizontalGroup(
            TabStatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabStatisticLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StatisticIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(493, Short.MAX_VALUE))
        );
        TabStatisticLayout.setVerticalGroup(
            TabStatisticLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TabStatisticLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StatisticIncome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabbedPaneAdmin.addTab("Statistic", TabStatistic);

        TabInventory.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout TabInventoryLayout = new javax.swing.GroupLayout(TabInventory);
        TabInventory.setLayout(TabInventoryLayout);
        TabInventoryLayout.setHorizontalGroup(
            TabInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 975, Short.MAX_VALUE)
        );
        TabInventoryLayout.setVerticalGroup(
            TabInventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );

        TabbedPaneAdmin.addTab("Inventory", TabInventory);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TabbedPaneAdmin))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(PanelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabbedPaneAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        // TODO add your handling code here:
        outClock();
        dispose();
        LoginFr log = new LoginFr();
        log.show();
        SharedData.Account_log = null;
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void txtAccount_idPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtAccount_idPropertyChange
        // TODO add your handling code here:
        txtAccount_id.setText(SharedData.Account_log.getUsername());
    }//GEN-LAST:event_txtAccount_idPropertyChange
    
    private void jButtonChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangePasswordActionPerformed
        // TODO add your handling code here:
        ChangePasswordFr chpw = new ChangePasswordFr();
        chpw.show();
    }//GEN-LAST:event_jButtonChangePasswordActionPerformed

    private void TabbedPaneAdminStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TabbedPaneAdminStateChanged
        // TODO add your handling code here:
        RefreshDataStaff();
    }//GEN-LAST:event_TabbedPaneAdminStateChanged

    private void jButtonEditHomeCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditHomeCustomerActionPerformed
        // TODO add your handling code here:
        HomeCustomerFr chome = new HomeCustomerFr();
        chome.show();
    }//GEN-LAST:event_jButtonEditHomeCustomerActionPerformed

    private void jButtonDishDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDishDeleteActionPerformed
        // TODO add your handling code here:
        ListDishDeletedFr dl = new ListDishDeletedFr();
        dl.show();
    }//GEN-LAST:event_jButtonDishDeleteActionPerformed

    private void jButtonRefeshDishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefeshDishActionPerformed
        // TODO add your handling code here:
        RefreshDataDish();
    }//GEN-LAST:event_jButtonRefeshDishActionPerformed

    private void jButtonDeleteDishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteDishActionPerformed
        // TODO add your handling code here:
        if(!jLabelDishID.getText().equals("")){
            try{
                cStmt = conn.prepareCall("call dish_delete(?);");
                cStmt.setInt(1, Integer.parseInt(jLabelDishID.getText()));
                cStmt.executeQuery();

                JOptionPane.showMessageDialog(this, "Delete dish Successful");
                RefreshDataDish();
                return;
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Cannot delete dish, try again");
                return;
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please choose the dish need to delete");
            return;
        }
    }//GEN-LAST:event_jButtonDeleteDishActionPerformed

    private void jButtonUpdateDishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateDishActionPerformed
        // TODO add your handling code here:
        if(!jLabelDishID.getText().equals("")){
            while(true){
                if(jTextFieldDishName.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Dish name is blank");
                    return;
                }
                break;
            }

            while(true){
                if(jTextFieldDishPrice.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Price is blank");
                    return;
                }else if(!jTextFieldDishPrice.getText().matches("[0-9]{4,}")){
                    JOptionPane.showMessageDialog(this, "Price is wrong format\nPrice is number");
                    return;
                }
                break;
            }
            while(true){
                if(jTextFieldDishUnit.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Unit is blank");
                    return;
                }
                break;
            }
            try{
                cStmt = conn.prepareCall("call dish_update(?,?,?,?,?);");
                cStmt.setInt(1,Integer.parseInt(jLabelDishID.getText()));
                cStmt.setString(2,jTextFieldDishName.getText());
                cStmt.setString(3, jLabelDishCate.getText());
                cStmt.setInt(4,Integer.parseInt(jTextFieldDishPrice.getText()));
                cStmt.setString(5,jTextFieldDishUnit.getText());
                
                if(jCheckBoxDMonday.isSelected()){
                    CallableStatement cStmt1 = conn.prepareCall("call menu_add(?,?);");
                    cStmt1.setString(1,jCheckBoxDMonday.getText());
                    cStmt1.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt1.executeQuery();
                }else{
                    CallableStatement cStmt1 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt1.setString(1,jCheckBoxDMonday.getText());
                    cStmt1.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt1.executeQuery();
                }
                if(jCheckBoxDTuesday.isSelected()){
                    CallableStatement cStmt2 = conn.prepareCall("call menu_add(?,?);");
                    cStmt2.setString(1,jCheckBoxDTuesday.getText());
                    cStmt2.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt2.executeQuery();
                }else{
                    CallableStatement cStmt2 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt2.setString(1,jCheckBoxDTuesday.getText());
                    cStmt2.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt2.executeQuery();
                }
                if(jCheckBoxDWednesday.isSelected()){
                    CallableStatement cStmt3 = conn.prepareCall("call menu_add(?,?);");
                    cStmt3.setString(1,jCheckBoxDWednesday.getText());
                    cStmt3.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt3.executeQuery();
                }else{
                    CallableStatement cStmt3 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt3.setString(1,jCheckBoxDWednesday.getText());
                    cStmt3.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt3.executeQuery();
                }
                if(jCheckBoxDThursday.isSelected()){
                    CallableStatement cStmt4 = conn.prepareCall("call menu_add(?,?);");
                    cStmt4.setString(1,jCheckBoxDThursday.getText());
                    cStmt4.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt4.executeQuery();
                }else{
                    CallableStatement cStmt4 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt4.setString(1,jCheckBoxDThursday.getText());
                    cStmt4.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt4.executeQuery();
                }
                if(jCheckBoxDFriday.isSelected()){
                    CallableStatement cStmt5 = conn.prepareCall("call menu_add(?,?);");
                    cStmt5.setString(1,jCheckBoxDFriday.getText());
                    cStmt5.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt5.executeQuery();
                }else{
                    CallableStatement cStmt5 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt5.setString(1,jCheckBoxDFriday.getText());
                    cStmt5.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt5.executeQuery();
                }
                if(jCheckBoxDSaturday.isSelected()){
                    CallableStatement cStmt6 = conn.prepareCall("call menu_add(?,?);");
                    cStmt6.setString(1,jCheckBoxDSaturday.getText());
                    cStmt6.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt6.executeQuery();
                }else{
                    CallableStatement cStmt6 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt6.setString(1,jCheckBoxDSaturday.getText());
                    cStmt6.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt6.executeQuery();
                }
                if(jCheckBoxDSunday.isSelected()){
                    CallableStatement cStmt7 = conn.prepareCall("call menu_add(?,?);");
                    cStmt7.setString(1,jCheckBoxDSunday.getText());
                    cStmt7.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt7.executeQuery();
                }else{
                    CallableStatement cStmt7 = conn.prepareCall("call menu_delete(?,?)");
                    cStmt7.setString(1,jCheckBoxDSunday.getText());
                    cStmt7.setInt(2,Integer.parseInt(jLabelDishID.getText()));
                    cStmt7.executeQuery();
                }
                
                cStmt.executeQuery();

                JOptionPane.showMessageDialog(this, "Update dish successful");
                RefreshDataDish();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,"Cannot update dish, try again");
                return;
            }
        }else {
            JOptionPane.showMessageDialog(this,"Please choose the dish need to update");
            return;
        }
    }//GEN-LAST:event_jButtonUpdateDishActionPerformed

    private void jButtonNewDishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewDishActionPerformed
        // TODO add your handling code here:
        if(jLabelDishID.getText().equals("")){
            while(true){
                if(jLabelDishCate.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Category is blank");
                    return;
                }
                break;
            }

            while(true){
                if(jTextFieldDishName.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Dish name is blank");
                    return;
                }
                break;
            }

            while(true){
                if(jTextFieldDishPrice.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Price is blank");
                    return;
                }else if(!jTextFieldDishPrice.getText().matches("[0-9]{4,}")){
                    JOptionPane.showMessageDialog(this, "Price is wrong format\nPrice is number");
                    return;
                }
                break;
            }
            while(true){
                if(jTextFieldDishUnit.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Unit is blank");
                    return;
                }
                break;
            }
            try{
                cStmt = conn.prepareCall("call dish_new(?,?,?,?);");
                cStmt.setString(1,jTextFieldDishName.getText());
                cStmt.setString(2, jLabelDishCate.getText());
                cStmt.setInt(3, Integer.parseInt(jTextFieldDishPrice.getText()));
                cStmt.setString(4, jTextFieldDishUnit.getText());
                cStmt.executeQuery();
                
                pStmt = conn.prepareStatement("select dish_get_max() as id;");
                rs = pStmt.executeQuery();
                rs.next();
                
                if(jCheckBoxDMonday.isSelected()){
                    CallableStatement cStmt1 = conn.prepareCall("call menu_add(?,?);");
                    cStmt1.setString(1,jCheckBoxDMonday.getText());
                    cStmt1.setInt(2,rs.getInt("id"));
                    cStmt1.executeQuery();
                }
                if(jCheckBoxDTuesday.isSelected()){
                    CallableStatement cStmt2 = conn.prepareCall("call menu_add(?,?);");
                    cStmt2.setString(1,jCheckBoxDTuesday.getText());
                    cStmt2.setInt(2,rs.getInt("id"));
                    cStmt2.executeQuery();
                }
                if(jCheckBoxDWednesday.isSelected()){
                    CallableStatement cStmt3 = conn.prepareCall("call menu_add(?,?);");
                    cStmt3.setString(1,jCheckBoxDWednesday.getText());
                    cStmt3.setInt(2,rs.getInt("id"));
                    cStmt3.executeQuery();
                }
                if(jCheckBoxDThursday.isSelected()){
                    CallableStatement cStmt4 = conn.prepareCall("call menu_add(?,?);");
                    cStmt4.setString(1,jCheckBoxDThursday.getText());
                    cStmt4.setInt(2,rs.getInt("id"));
                    cStmt4.executeQuery();
                }
                if(jCheckBoxDFriday.isSelected()){
                    CallableStatement cStmt5 = conn.prepareCall("call menu_add(?,?);");
                    cStmt5.setString(1,jCheckBoxDFriday.getText());
                    cStmt5.setInt(2,rs.getInt("id"));
                    cStmt5.executeQuery();
                }
                if(jCheckBoxDSaturday.isSelected()){
                    CallableStatement cStmt6 = conn.prepareCall("call menu_add(?,?);");
                    cStmt6.setString(1,jCheckBoxDSaturday.getText());
                    cStmt6.setInt(2,rs.getInt("id"));
                    cStmt6.executeQuery();
                }
                if(jCheckBoxDSunday.isSelected()){
                    CallableStatement cStmt7 = conn.prepareCall("call menu_add(?,?);");
                    cStmt7.setString(1,jCheckBoxDSunday.getText());
                    cStmt7.setInt(2,rs.getInt("id"));
                    cStmt7.executeQuery();
                }
                
                
                
                JOptionPane.showMessageDialog(this, "Add new dish successful");
                RefreshDataDish();
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Cannot add new dish, try again");
                return;}
        }else{
            JOptionPane.showMessageDialog(this, "ID already exists\nPress Refresh before add new dish");
            return;
        }

    }//GEN-LAST:event_jButtonNewDishActionPerformed

    private void jComboBoxCategory2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategory2ActionPerformed
        // TODO add your handling code here:
        String selectedValue = jComboBoxCategory2.getSelectedItem().toString();
        jLabelDishCate.setText(selectedValue);
    }//GEN-LAST:event_jComboBoxCategory2ActionPerformed

    private void jButtonDishSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDishSearchActionPerformed
        // TODO add your handling code here:
        SetTableDataDish();
    }//GEN-LAST:event_jButtonDishSearchActionPerformed

    private void jTableDishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDishMouseClicked
        // TODO add your handling code here:
        SetDishInfo();
    }//GEN-LAST:event_jTableDishMouseClicked

    private void jComboBoxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoryActionPerformed
        // TODO add your handling code here:
        String selectedValue = jComboBoxCategory.getSelectedItem().toString();
        jLabelCate.setText(selectedValue);

    }//GEN-LAST:event_jComboBoxCategoryActionPerformed

    private void jButtonNewCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewCateActionPerformed
        // TODO add your handling code here:
        while(true){
            if(jTextFieldNewCate.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Category is blank");
                return;
            }
            break;
        }
        try{
            cStmt = conn.prepareCall("call dish_cate_new(?);");
            cStmt.setString(1, jTextFieldNewCate.getText());
            cStmt.executeQuery();
            JOptionPane.showMessageDialog(this, "Add new Category successful");
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot add new category, try again");}
        UpdateCategory();
        UpdateCategory2();
        RefreshDataCate();
        RefreshDataDish();
    }//GEN-LAST:event_jButtonNewCateActionPerformed

    private void jButtonAccListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAccListActionPerformed
        // TODO add your handling code here:
        ListAccountFr la = new ListAccountFr();
        la.show();
    }//GEN-LAST:event_jButtonAccListActionPerformed

    private void jButtonCreateAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCreateAccActionPerformed
        // TODO add your handling code here:
        while(true){
            if(jTextFieldID.getText().equals("")){
                JOptionPane.showMessageDialog(this, "The staff ID is blank");
                return;
            }else {
                try{
                    cStmt = conn.prepareCall("select staff_getExists(?) as temp");
                    cStmt.setInt(1, Integer.parseInt(jTextFieldID.getText()));
                    rs = cStmt.executeQuery();
                    if (rs.next()){
                        switch (rs.getInt("temp")){
                            case -1:
                            JOptionPane.showMessageDialog(this,"The ID does not match any staff");
                            return;
                            default:
                            break;
                        }
                    }
                }catch (Exception e){e.printStackTrace();return;}
                try{
                    CallableStatement cStmt2 = conn.prepareCall("select account_getExists(?) as temp");
                    cStmt2.setInt(1,Integer.parseInt(jTextFieldID.getText()));
                    ResultSet rs2 = cStmt2.executeQuery();
                    if(rs2.next()){
                        switch(rs2.getInt("temp")){
                            case -1:
                            break;
                            default:
                            JOptionPane.showMessageDialog(this,"The ID already have account");
                            return;
                        }
                    }
                }catch (Exception e){e.printStackTrace();return;}

            }
            break;
        }

        while(true){
            if(jTextFieldUsername.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Username is blank");
                return;
            }else{
                try{
                    CallableStatement cStmt3 = conn.prepareCall("select account_getExistsUsername(?) as temp;");
                    cStmt3.setString(1,jTextFieldUsername.getText());
                    ResultSet rs3 = cStmt3.executeQuery();
                    if(rs3.next()){
                        switch(rs3.getInt("temp")){
                            case -1:
                            break;
                            default:
                            JOptionPane.showMessageDialog(this,"The username already exists");
                            return;
                        }
                    }
                }catch (Exception e){e.printStackTrace();return;}
            }
            break;
        }

        while(true){
            if(jTextFieldPassword.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Password is blank");
                return;
            }else
            break;
        }

        try{
            CallableStatement cStmt4 = conn.prepareCall("call account_new(?,?,?);");
            cStmt4.setInt(1,Integer.parseInt(jTextFieldID.getText()));
            cStmt4.setString(2, jTextFieldUsername.getText());
            cStmt4.setString(3, jTextFieldPassword.getText());
            cStmt4.executeQuery();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Cannot create account, try again");
            e.printStackTrace();
            return;}
        RefreshDataAcc();
        JOptionPane.showMessageDialog(this, "Create account successful");
    }//GEN-LAST:event_jButtonCreateAccActionPerformed

    private void jButtonNewPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewPostActionPerformed
        // TODO add your handling code here:
        while(true){
            if(jTextFieldBasicSal.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please Enter the basic salary");
                return;
            }else if(!jTextFieldBasicSal.getText().matches("[0-9]{7,}")){
                JOptionPane.showMessageDialog(this, "The salary must >= 1000000");
                return;
            }break;
        }

        while(true){
            if(jTextFieldNewPost.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter the new Position");
                return;
            }else{
                try{
                    cStmt = conn.prepareCall("select position_getExists(?) as temp;");
                    cStmt.setString(1, jTextFieldNewPost.getText());
                    rs = cStmt.executeQuery();

                    if(rs.next()){
                        switch (rs.getInt("temp")){
                            case 1:
                            JOptionPane.showMessageDialog(this, "The position already exists");
                            return;
                            case -1:
                            try{
                                CallableStatement cStmt2 = conn.prepareCall("call position_new(?,?);");
                                cStmt2.setString(1,jTextFieldNewPost.getText());
                                cStmt2.setInt(2,Integer.parseInt(jTextFieldBasicSal.getText()));
                                cStmt2.executeQuery();
                            }catch (Exception e){
                                JOptionPane.showMessageDialog(this, "Cannot add new position, try again");
                                e.printStackTrace();
                                return;}

                        }
                    }

                }catch (Exception e){e.printStackTrace();}
            }
            break;
        }
        UpdatePosition1();
        UpdatePosition2();
        RefreshDataPost();
        JOptionPane.showMessageDialog(this, "Add new position successful");

    }//GEN-LAST:event_jButtonNewPostActionPerformed

    private void jButtonUpdatePostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdatePostActionPerformed
        // TODO add your handling code here:
        while(true){
            if(jLabelPost.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please choose the Position to update");
                return;
            }else{
                break;
            }
        }

        while(true){
            if(jTextFieldBasicSal.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please Enter the basic salary");
                return;
            }else if(!jTextFieldBasicSal.getText().matches("[0-9]{7,}")){
                JOptionPane.showMessageDialog(this, "The salary must >= 1000000");
                return;
            }else {
                try{
                    cStmt = conn.prepareCall("call position_update(?,?);");
                    cStmt.setString(1, jLabelPost.getText());
                    cStmt.setInt(2, Integer.parseInt(jTextFieldBasicSal.getText()));
                    cStmt.executeQuery();
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this, "Cannot update salary, try again");
                    e.printStackTrace();
                    return;}

                RefreshDataPost();
                SetTableDataWork();
                SetStaffInfo();
                JOptionPane.showMessageDialog(this, "The salary update successful");
                return;
            }
        }
    }//GEN-LAST:event_jButtonUpdatePostActionPerformed

    private void jComboBoxPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPostActionPerformed
        // TODO add your handling code here:
        String selectedValue = jComboBoxPost.getSelectedItem().toString();
        jLabelPost.setText(selectedValue);
        try{
            /*StaffPositionmethod stpmt = new StaffPositionmethod();
            
            StaffPosition stp = stpmt.getSal(jLabelPost.getText());
            
            
            jTextFieldBasicSal.setText(Integer.toString(stp.getBasicSal()));*/
            StaffPosition stp = new StaffPosition().getSal(jLabelPost.getText());
            //StaffPosition stp2 = stp.getSal(jLabelPost.getText(););
            jTextFieldBasicSal.setText(Integer.toString(stp.getBasicSal()));
        }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jComboBoxPostActionPerformed

    private void jButtonSearchStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchStaffActionPerformed
        // TODO add your handling code here:

        SearchStaff();
    }//GEN-LAST:event_jButtonSearchStaffActionPerformed

    private void jButtonRefreshStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshStaffActionPerformed
        // TODO add your handling code here:
        RefreshDataStaff();
    }//GEN-LAST:event_jButtonRefreshStaffActionPerformed

    private void jButtonUpdateStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateStaffActionPerformed
        // TODO add your handling code here:
        UpdateStaff();
    }//GEN-LAST:event_jButtonUpdateStaffActionPerformed

    private void jButtonNewStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewStaffActionPerformed
        // TODO add your handling code here:
        NewStaff();
    }//GEN-LAST:event_jButtonNewStaffActionPerformed

    private void jButtonDeleteStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteStaffActionPerformed
        // TODO add your handling code here:
        DeleteStaff();
    }//GEN-LAST:event_jButtonDeleteStaffActionPerformed

    private void jButtonRetiredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRetiredActionPerformed
        // TODO add your handling code here:
        SetTableDataRetired();
    }//GEN-LAST:event_jButtonRetiredActionPerformed

    private void jButtonWorkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWorkActionPerformed
        // TODO add your handling code here:
        SetTableDataWork();
    }//GEN-LAST:event_jButtonWorkActionPerformed

    private void jTableStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStaffMouseClicked
        // TODO add your handling code here:

        SetStaffInfo();
    }//GEN-LAST:event_jTableStaffMouseClicked

    private void jSpinnerStaffBonusStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerStaffBonusStateChanged
        // TODO add your handling code here:
        String selectedValue = jSpinnerStaffBonus.getValue().toString();
        jLabelStaffBonus.setText(selectedValue);
    }//GEN-LAST:event_jSpinnerStaffBonusStateChanged

    private void jComboBoxStaffPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxStaffPostActionPerformed
        // TODO add your handling code here:
        String selectedValue = jComboBoxStaffPost.getSelectedItem().toString();
        jLabeStafflPost.setText(selectedValue);
    }//GEN-LAST:event_jComboBoxStaffPostActionPerformed

    private void jSpinnerTableNumOfSeatStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerTableNumOfSeatStateChanged
        // TODO add your handling code here:
        String selectedValue = jSpinnerTableNumOfSeat.getValue().toString();
        jLabelTableNumOfSeat.setText(selectedValue);
    }//GEN-LAST:event_jSpinnerTableNumOfSeatStateChanged

    private void jButtonTableNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTableNewActionPerformed
        // TODO add your handling code here:
        while(true){
            if(jTextFieldTableName.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Table Name is blank");
                return;
            }
            break;
        }
        
        while(true){
            if(!jLabelTableNumOfSeat.getText().equals("")){
                if(Integer.parseInt(jLabelTableNumOfSeat.getText())<4){
                    JOptionPane.showMessageDialog(this,"Seats need to be >= 4");
                    return;
                }
            }else{
                JOptionPane.showMessageDialog(this,"Seats is blank");
                return;
            }
            break;
        }
        
        try{
            cStmt = conn.prepareCall("call table_new(?,?);");
            cStmt.setString(1, jTextFieldTableName.getText());
            cStmt.setInt(2,Integer.parseInt(jLabelTableNumOfSeat.getText()));
            cStmt.executeQuery();
            JOptionPane.showMessageDialog(this, "Add new table successful");
            RefreshDataTable();
            return;
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,"Cannot add new table, try again");
            e.printStackTrace();
            return;
        }
    }//GEN-LAST:event_jButtonTableNewActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ListTableFr lt = new ListTableFr();
        lt.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTableInvoiceListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInvoiceListMouseClicked
        // TODO add your handling code here:
        SetInvoiceInfo();
    }//GEN-LAST:event_jTableInvoiceListMouseClicked

    private void jButtonStatisticSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStatisticSearchActionPerformed
        // TODO add your handling code here:
        SetTableDataStatisticByMonth();
    }//GEN-LAST:event_jButtonStatisticSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeAdminFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeAdminFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeAdminFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeAdminFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeAdminFr().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AccountID;
    private javax.swing.JPanel AccountInfo;
    private javax.swing.JLabel BasicSal;
    private javax.swing.JLabel Category;
    private javax.swing.JPanel CategoryInfo;
    private javax.swing.JPanel Cateinfo;
    private javax.swing.JLabel DishCategory;
    private javax.swing.JLabel DishID;
    private javax.swing.JPanel DishInfo;
    private javax.swing.JLabel DishName;
    private javax.swing.JLabel DishPrice;
    private javax.swing.JLabel Entertosearch;
    private javax.swing.JLabel Information;
    private javax.swing.JPanel InvoiceDetail;
    private javax.swing.JPanel InvoiceList;
    private javax.swing.JLabel LabelWelcome;
    private javax.swing.JPanel MenuByCate;
    private javax.swing.JLabel NewCate;
    private javax.swing.JLabel NewPost;
    private javax.swing.JLabel NumOfSeat;
    private javax.swing.JPanel PanelWelcome;
    private javax.swing.JLabel Password;
    private javax.swing.JLabel StaffBirthday;
    private javax.swing.JLabel StaffBonus;
    private javax.swing.JLabel StaffID;
    private javax.swing.JPanel StaffInfo;
    private javax.swing.JLabel StaffName;
    private javax.swing.JLabel StaffPhone;
    private javax.swing.JLabel StaffPosition;
    private javax.swing.JLabel StaffState;
    private javax.swing.JPanel StatisticIncome;
    private javax.swing.JPanel TabInventory;
    private javax.swing.JPanel TabInvoice;
    private javax.swing.JPanel TabMenu;
    private javax.swing.JPanel TabStaff;
    private javax.swing.JPanel TabStatistic;
    private javax.swing.JTabbedPane TabbedPaneAdmin;
    private javax.swing.JPanel TableInfo;
    private javax.swing.JLabel TableName;
    private javax.swing.JLabel Tableinformation;
    private javax.swing.JLabel TitleAccount;
    private javax.swing.JLabel TitlePost;
    private javax.swing.JLabel TitleStaff;
    private javax.swing.JLabel Username;
    private javax.swing.JPanel background;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAccList;
    private javax.swing.JButton jButtonChangePassword;
    private javax.swing.JButton jButtonCreateAcc;
    private javax.swing.JButton jButtonDeleteDish;
    private javax.swing.JButton jButtonDeleteStaff;
    private javax.swing.JButton jButtonDishDelete;
    private javax.swing.JButton jButtonDishSearch;
    private javax.swing.JButton jButtonEditHomeCustomer;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonNewCate;
    private javax.swing.JButton jButtonNewDish;
    private javax.swing.JButton jButtonNewPost;
    private javax.swing.JButton jButtonNewStaff;
    private javax.swing.JButton jButtonRefeshDish;
    private javax.swing.JButton jButtonRefreshStaff;
    private javax.swing.JButton jButtonRetired;
    private javax.swing.JButton jButtonSearchStaff;
    private javax.swing.JButton jButtonStatisticSearch;
    private javax.swing.JButton jButtonTableNew;
    private javax.swing.JButton jButtonUpdateDish;
    private javax.swing.JButton jButtonUpdatePost;
    private javax.swing.JButton jButtonUpdateStaff;
    private javax.swing.JButton jButtonWork;
    private javax.swing.JCheckBox jCheckBoxDFriday;
    private javax.swing.JCheckBox jCheckBoxDMonday;
    private javax.swing.JCheckBox jCheckBoxDSaturday;
    private javax.swing.JCheckBox jCheckBoxDSunday;
    private javax.swing.JCheckBox jCheckBoxDThursday;
    private javax.swing.JCheckBox jCheckBoxDTuesday;
    private javax.swing.JCheckBox jCheckBoxDWednesday;
    private javax.swing.JComboBox<String> jComboBoxCategory;
    private javax.swing.JComboBox<String> jComboBoxCategory2;
    private javax.swing.JComboBox<String> jComboBoxPost;
    private javax.swing.JComboBox<String> jComboBoxStaffPost;
    private com.toedter.calendar.JDateChooser jDateChooserStaffDoB;
    private javax.swing.JLabel jLabeStafflPost;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCate;
    private javax.swing.JLabel jLabelClock;
    private javax.swing.JLabel jLabelDishCate;
    private javax.swing.JLabel jLabelDishID;
    private javax.swing.JLabel jLabelInvoiceDate;
    private javax.swing.JLabel jLabelInvoiceDateUpt;
    private javax.swing.JLabel jLabelInvoiceID;
    private javax.swing.JLabel jLabelInvoiceStaff;
    private javax.swing.JLabel jLabelInvoiceState;
    private javax.swing.JLabel jLabelInvoiceTable;
    private javax.swing.JLabel jLabelInvoiceTotal;
    private javax.swing.JLabel jLabelPost;
    private javax.swing.JLabel jLabelStaffBonus;
    private javax.swing.JLabel jLabelStaffID;
    private javax.swing.JLabel jLabelStaffState;
    private javax.swing.JLabel jLabelTableNumOfSeat;
    private javax.swing.JLabel jLabelTotalSal;
    private javax.swing.JLabel jLabelTtIncome;
    private javax.swing.JLabel jLabelTtInvoice;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinnerStaffBonus;
    private javax.swing.JSpinner jSpinnerTableNumOfSeat;
    private javax.swing.JTable jTableDetail;
    private javax.swing.JTable jTableDish;
    private javax.swing.JTable jTableInvoiceList;
    private javax.swing.JTable jTableStaff;
    private javax.swing.JTable jTableStatisticByMonth;
    private javax.swing.JTextField jTextFieldBasicSal;
    private javax.swing.JTextField jTextFieldDishName;
    private javax.swing.JTextField jTextFieldDishPrice;
    private javax.swing.JTextField jTextFieldDishUnit;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldMonth;
    private javax.swing.JTextField jTextFieldNewCate;
    private javax.swing.JTextField jTextFieldNewPost;
    private javax.swing.JTextField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldStaffName;
    private javax.swing.JTextField jTextFieldStaffPhone;
    private javax.swing.JTextField jTextFieldTableName;
    private javax.swing.JTextField jTextFieldUsername;
    private javax.swing.JTextField jTextFieldYear;
    private javax.swing.JLabel percent;
    private javax.swing.JLabel totalsal;
    private javax.swing.JLabel txtAccount_id;
    // End of variables declaration//GEN-END:variables
}
