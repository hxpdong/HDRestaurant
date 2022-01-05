/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import SQLConnect.SQLConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Detail;
import model.Dish;
import model.Invoice;
import hdrestaurant.ClockThread;
import hdrestaurant.SharedData;
/**
 *
 * @author ASUS OS
 */
public class HomeStaffFr extends javax.swing.JFrame {

    /**
     * Creates new form HomeStaffFr
     */
    DefaultComboBoxModel defaultComboBoxModel;
    DefaultTableModel defaultTableModel;
    CallableStatement cStmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Connection conn = SQLConnection.getConnection();
    
    public HomeStaffFr() {
        initComponents();
        pack();
        setLocationRelativeTo(null);
        
        initClock();
        
        SetTableDataInvoice();
        UpdateTable();
        UpdateCategory();
        RefreshDataMenu();
        jLabelCate.setText("");
        RefreshDataInvoice();
        
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
    
    private void SetTableDataInvoice(){
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        defaultTableModel = new DefaultTableModel(){
            public boolean isCellEdittable(int row, int column) {
                return false;
            }
        };
        
        jTableInvoice.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Table");
        defaultTableModel.addColumn("State");
        
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
                    invoice.getTableName(), 
                    /*invoice.getStaff(),
                    invoice.getInvoiceDate(), 
                    invoice.getInvoiceDateUpt(),
                    invoice.getInvoiceTotal(),*/
                    invoice.getState()
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
        defaultTableModel.addColumn("Dish ID");
        defaultTableModel.addColumn("Unit Price");
        defaultTableModel.addColumn("Quantity");
        defaultTableModel.addColumn("Amount");
        
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
                detail.setDish_id(rs.getInt("dish_id"));
                detail.setUnitPrice(rs.getInt("unit_price"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setAmount(rs.getInt("amount"));
                detailList.add(detail);
            }
            
            for(Detail detail : detailList){
                defaultTableModel.addRow(new Object[]{
                    detail.getNo(),
                    detail.getDishName(),
                    detail.getDish_id(),
                    detail.getUnitPrice(),
                    detail.getQuantity(),
                    detail.getAmount()
                });
            }
        }catch (Exception e){e.printStackTrace();}
    }
    
    private void UpdateTable(){
        defaultComboBoxModel = new DefaultComboBoxModel();
        jComboBoxTable.setModel(defaultComboBoxModel);
        String sql = "call list_table_available";
        
        try{
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while(rs.next()){
                jComboBoxTable.addItem(rs.getString("tableName"));
            }
        }catch(Exception e){
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
    
    private void RefreshDataInvoice(){
        jLabelTableName.setText("");
    }
    
    private void RefreshDataMenu(){
        jLabelDishID.setText("");
        jLabelDishCate.setText("");
        jLabelDishName.setText("");
        jLabelDishPrice.setText("");
        jLabelDishUnit.setText("");
        jLabelQuantity.setText("0");
        
        
        jLabelMenu.setText("");
        
    }
    private void SetButtonUpdateHided(){
        jButtonUpdateInvoice.setEnabled(false); 
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
                jLabelDishName.setText("");
                jLabelDishCate.setText("");
                jLabelDishPrice.setText("");
                jLabelDishUnit.setText("");

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
                jLabelDishName.setText(tblDishName);
                jLabelDishCate.setText(tblDishCate);
                jLabelDishPrice.setText(tblDishPrice);
                jLabelDishUnit.setText(tblDishUnit);
                
                try{
                    pStmt = conn.prepareStatement("select menu_getMenuOf(?) as temp");
                    pStmt.setInt(1,Integer.parseInt(jLabelDishID.getText()));
                    rs = pStmt.executeQuery();
                    if (rs.next()){
                        jLabelMenu.setText(rs.getString("temp"));
                    }
                    
                }catch (Exception e){e.printStackTrace();}
                }
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date now = new Date();
            String st = sdf.format(now);
            
            if(jLabelInvoiceState.getText().equals("Paid")){    //Nếu mà hóa đơn đã "Paid" sẽ ẩn nút update,
                jButtonUpdateInvoice.setEnabled(false);
                
                
            }else {                                             //Nếu mà hóa đơn "Unpaid" sẽ hiện nút update
                jButtonUpdateInvoice.setEnabled(true);
                if(!jLabelMenu.getText().contains(st)){             //Nếu mà món ăn đó ko dc phục vụ ngày hôm nay
                    jButtonUpdateInvoice.setEnabled(false);         //thì sẽ ẩn nút update
                }else{
                    jButtonUpdateInvoice.setEnabled(true);          //Nếu mà món ăn được phục vụ ngày hôm nay sẽ hiện nút update
                                                            //SẼ KIỂM TRA ĐIỀU KIỆN HÓA ĐƠN ĐÃ TRẢ HAY CHƯA TRƯỚC
                }
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
        
        
        DefaultTableModel tblModel = (DefaultTableModel)jTableInvoice.getModel();
        int index = jTableInvoice.getSelectedRow();
        
        if ( index == -1 ) {
                jLabelInvoiceID.setText("");
                jLabelInvoiceTable.setText("");
                jLabelStaff.setText("");
                jLabelDate.setText("");
                jLabelDateUpt.setText("");
                jLabelInvoiceTotal.setText("");
                jLabelInvoiceState.setText("");
                

            }else {
                tblInvoiceID = tblModel.getValueAt(index, 0).toString();
                tblInvoiceTable = tblModel.getValueAt(index, 1).toString();
                tblInvoiceState = tblModel.getValueAt(index, 2).toString();
                
                try{
                    cStmt = conn.prepareCall("call invoice_get_info(?)");
                    cStmt.setInt(1,Integer.parseInt(tblInvoiceID));
                    rs=cStmt.executeQuery();
                    rs.next();
                    
                    try{
                       tblInvoiceDate = rs.getTimestamp("invoiceDate").toString();
                    }catch(Exception e){
                    }
                    try{
                       tblInvoiceDateUpt = rs.getTimestamp("invoiceDateUpt").toString();
                    }catch(Exception e){
                    }
                    
                    tblInvoiceStaff=rs.getString("staff");
                    tblInvoiceTotal=rs.getString("invoiceTotal");
                }catch (Exception e){e.printStackTrace();}

                jLabelInvoiceID.setText(tblInvoiceID);
                jLabelInvoiceTable.setText(tblInvoiceTable);
                jLabelStaff.setText(tblInvoiceStaff);
                jLabelDate.setText(tblInvoiceDate);
                jLabelDateUpt.setText(tblInvoiceDateUpt);
                jLabelInvoiceTotal.setText(tblInvoiceTotal);
                jLabelInvoiceState.setText(tblInvoiceState);
                if(jLabelInvoiceState.getText().equals("Paid")){
                    jButtonUpdateInvoice.setEnabled(false);
                }else jButtonUpdateInvoice.setEnabled(true);
                
        }
        SetTableDataDetail();
        UpdateTable();
        RefreshDataMenu();
        if(jLabelInvoiceState.getText().equals("Paid")){
            jButtonCloseInvoice.setEnabled(false);
        }else jButtonCloseInvoice.setEnabled(true);
    }
    
    private void SetInvoiceInfo(int invoice_id){    //Cap nhat hoa don hien tai
        String tblInvoiceID = null;
        String tblInvoiceTable = null;
        String tblInvoiceStaff = null;
        String tblInvoiceDate = null;
        String tblInvoiceDateUpt = null;
        String tblInvoiceTotal = null;
        String tblInvoiceState = null;
        
        tblInvoiceID = Integer.toString(invoice_id);
        DefaultTableModel tblModel = (DefaultTableModel)jTableInvoice.getModel();
        int index = jTableInvoice.getSelectedRow();
        
        if ( index == -1 ) {
                jLabelInvoiceID.setText("");
                jLabelInvoiceTable.setText("");
                jLabelStaff.setText("");
                jLabelDate.setText("");
                jLabelDateUpt.setText("");
                jLabelInvoiceTotal.setText("");
                jLabelInvoiceState.setText("");
                

            }else {
                /*
                tblInvoiceTable = tblModel.getValueAt(index, 1).toString();
                tblInvoiceState = tblModel.getValueAt(index, 2).toString();*/
                
                try{
                    cStmt = conn.prepareCall("call invoice_get_info(?)");
                    cStmt.setInt(1,Integer.parseInt(tblInvoiceID));
                    rs=cStmt.executeQuery();
                    rs.next();
                    
                    tblInvoiceTable =rs.getString("tableName");
                    tblInvoiceStaff = rs.getString("staff");
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
                    
                }catch (Exception e){e.printStackTrace();return;}

                jLabelInvoiceID.setText(tblInvoiceID);
                jLabelInvoiceTable.setText(tblInvoiceTable);
                jLabelStaff.setText(tblInvoiceStaff);
                jLabelDate.setText(tblInvoiceDate);
                jLabelDateUpt.setText(tblInvoiceDateUpt);
                jLabelInvoiceTotal.setText(tblInvoiceTotal);
                jLabelInvoiceState.setText(tblInvoiceState);
                
        }
        SetTableDataDetail();
        
    }
    
    private void SetDishDetailInfo(){
        String tblDishID = null;
        String tblDishName = null;
        String tblDishCate = null;
        String tblDishPrice = null;
        String tblDishUnit = null;
        String tblDishQuantity = null;
        DefaultTableModel tblModel = (DefaultTableModel)jTableDetail.getModel();
            int index = jTableDetail.getSelectedRow();
            if ( index == -1 ) {
                jLabelDishID.setText("");
                jLabelDishName.setText("");
                jLabelDishCate.setText("");
                jLabelDishPrice.setText("");
                jLabelDishUnit.setText("");
                jLabelQuantity.setText("");

            }else {
                tblDishID = tblModel.getValueAt(index, 2).toString();
                tblDishName = tblModel.getValueAt(index, 1).toString();
                tblDishPrice = tblModel.getValueAt(index, 3).toString();
                try{
                    cStmt = (CallableStatement) conn.prepareCall("call dish_get_info(?);");
                    cStmt.setInt(1,Integer.parseInt(tblDishID));
                    rs = cStmt.executeQuery();
                    rs.next();
                    
                    
                    tblDishCate = rs.getString("cateName");
                    
                    tblDishUnit = rs.getString("unit");
                    
                }catch(Exception e){e.printStackTrace();}
                tblDishQuantity = tblModel.getValueAt(index,4).toString();
                
                
                jLabelDishID.setText(tblDishID);
                jLabelDishName.setText(tblDishName);
                jLabelDishCate.setText(tblDishCate);
                jLabelDishPrice.setText(tblDishPrice);
                jLabelDishUnit.setText(tblDishUnit);
                jLabelQuantity.setText(tblDishQuantity);
                
                try{
                    pStmt = conn.prepareStatement("select menu_getMenuOf(?) as temp");
                    pStmt.setInt(1,Integer.parseInt(jLabelDishID.getText()));
                    rs = pStmt.executeQuery();
                    if (rs.next()){
                        jLabelMenu.setText(rs.getString("temp"));
                    }
                    
                }catch (Exception e){e.printStackTrace();}
                }
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date now = new Date();
            String st = sdf.format(now);
            
            if(!jLabelMenu.getText().contains(st)){
                jButtonUpdateInvoice.setEnabled(false);
            }else{
                jButtonUpdateInvoice.setEnabled(true);
            }
            
            
    }      
    
    private void NewInvoice(){
        while(true){
            if(jLabelTableName.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please choose the table");
                return;
            }else{
                try{
                    cStmt = conn.prepareCall("select table_getValid(?) as temp");
                    cStmt.setString(1,jLabelTableName.getText());
                    rs = cStmt.executeQuery();
                    if(rs.next()){
                        switch(rs.getInt("temp")){
                            case -1:
                                JOptionPane.showMessageDialog(this,"The table is not ready");
                                return;
                            default:
                                break;
                        }
                    }
                }catch (Exception e){e.printStackTrace();return;}
            }
            break;
        }
        try{
            cStmt = conn.prepareCall("call invoice_new(?,?);");
            cStmt.setString(1,jLabelTableName.getText());
            cStmt.setInt(2,SharedData.Account_log.getAccount_id());
            cStmt.executeQuery();
            JOptionPane.showMessageDialog(this, "Create new invoice successful");
            SetTableDataInvoice();
            UpdateTable();
            RefreshDataInvoice();
            return;
        }catch (Exception e){e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot create new invoice, try again");
            return;}
    }
    
    private void CloseInvoice(){
        while(true){
            if(jLabelInvoiceID.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please choose the invoice to Paid");
                return;
            }else
            break;
        }
        
        try{
            cStmt = conn.prepareCall("call invoice_close(?)");
            cStmt.setInt(1,Integer.parseInt(jLabelInvoiceID.getText()));
            cStmt.executeQuery();
            JOptionPane.showMessageDialog(this, "The invoice is paid successful");
            SetTableDataInvoice();
            SetInvoiceInfo();
            RefreshDataInvoice();
        }catch (Exception e){e.printStackTrace();}
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
        jLabelClock = new javax.swing.JLabel();
        jButtonChangePW = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        Invoice = new javax.swing.JPanel();
        Invoices = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableInvoice = new javax.swing.JTable();
        Table1 = new javax.swing.JLabel();
        jComboBoxTable = new javax.swing.JComboBox<>();
        jLabelTableName = new javax.swing.JLabel();
        jButtonNewInvoice = new javax.swing.JButton();
        DishMenu = new javax.swing.JPanel();
        Menu = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabelDishID = new javax.swing.JLabel();
        jLabelDishName = new javax.swing.JLabel();
        jLabelDishPrice = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabelDishUnit = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelDishCate = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableDish = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox<>();
        jLabelCate = new javax.swing.JLabel();
        jButtonDishSearch = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabelQuantity = new javax.swing.JLabel();
        jSpinnerQuantity = new javax.swing.JSpinner();
        jButtonUpdateInvoice = new javax.swing.JButton();
        jLabelMenu = new javax.swing.JLabel();
        InvoiceInformation = new javax.swing.JPanel();
        Detail = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelInvoiceID = new javax.swing.JLabel();
        jLabelInvoiceTotal = new javax.swing.JLabel();
        jLabelInvoiceState = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelDateUpt = new javax.swing.JLabel();
        jLabelStaff = new javax.swing.JLabel();
        jLabelDate = new javax.swing.JLabel();
        jLabelInvoiceTable = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDetail = new javax.swing.JTable();
        jButtonCloseInvoice = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Staff Homepage");
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

        LabelWelcome.setBackground(new java.awt.Color(255, 255, 255));
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

        jLabelClock.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jButtonChangePW.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonChangePW.setText("Change Password");
        jButtonChangePW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangePWActionPerformed(evt);
            }
        });

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
                .addComponent(jButtonChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtAccount_id, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelWelcomeLayout.setVerticalGroup(
            PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButtonLogout)
                        .addComponent(LabelWelcome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelClock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAccount_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonChangePW, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Invoice.setLayout(null);

        Invoices.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Invoices.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Invoices.setText("Invoices");
        Invoice.add(Invoices);
        Invoices.setBounds(0, 0, 210, 60);

        jTableInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Table", "State"
            }
        ));
        jTableInvoice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableInvoiceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableInvoice);

        Invoice.add(jScrollPane1);
        jScrollPane1.setBounds(10, 140, 190, 360);

        Table1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Table1.setText("Table:");
        Invoice.add(Table1);
        Table1.setBounds(10, 60, 40, 30);

        jComboBoxTable.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jComboBoxTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTableActionPerformed(evt);
            }
        });
        Invoice.add(jComboBoxTable);
        jComboBoxTable.setBounds(120, 60, 80, 30);

        jLabelTableName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        Invoice.add(jLabelTableName);
        jLabelTableName.setBounds(60, 60, 60, 30);

        jButtonNewInvoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonNewInvoice.setText("New");
        jButtonNewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewInvoiceActionPerformed(evt);
            }
        });
        Invoice.add(jButtonNewInvoice);
        jButtonNewInvoice.setBounds(60, 100, 80, 30);

        DishMenu.setLayout(null);

        Menu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Menu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Menu.setText("Menu");
        DishMenu.add(Menu);
        Menu.setBounds(0, 0, 350, 60);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Category:");
        DishMenu.add(jLabel8);
        jLabel8.setBounds(0, 300, 70, 30);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Name:");
        DishMenu.add(jLabel10);
        jLabel10.setBounds(10, 110, 50, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Quantity:");
        DishMenu.add(jLabel11);
        jLabel11.setBounds(80, 250, 70, 30);

        jLabelDishID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DishMenu.add(jLabelDishID);
        jLabelDishID.setBounds(70, 60, 50, 30);

        jLabelDishName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DishMenu.add(jLabelDishName);
        jLabelDishName.setBounds(70, 110, 180, 30);

        jLabelDishPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelDishPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DishMenu.add(jLabelDishPrice);
        jLabelDishPrice.setBounds(70, 160, 90, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("/");
        DishMenu.add(jLabel13);
        jLabel13.setBounds(160, 140, 10, 70);

        jLabelDishUnit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DishMenu.add(jLabelDishUnit);
        jLabelDishUnit.setBounds(170, 160, 70, 30);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("ID:");
        DishMenu.add(jLabel9);
        jLabel9.setBounds(10, 60, 30, 30);

        jLabelDishCate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DishMenu.add(jLabelDishCate);
        jLabelDishCate.setBounds(220, 60, 120, 30);

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
        jScrollPane3.setViewportView(jTableDish);

        DishMenu.add(jScrollPane3);
        jScrollPane3.setBounds(10, 340, 330, 160);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Category:");
        DishMenu.add(jLabel14);
        jLabel14.setBounds(140, 60, 70, 30);

        jComboBoxCategory.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBoxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoryActionPerformed(evt);
            }
        });
        DishMenu.add(jComboBoxCategory);
        jComboBoxCategory.setBounds(70, 300, 80, 30);

        jLabelCate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelCate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DishMenu.add(jLabelCate);
        jLabelCate.setBounds(150, 300, 110, 30);

        jButtonDishSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonDishSearch.setText("Search");
        jButtonDishSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDishSearchActionPerformed(evt);
            }
        });
        DishMenu.add(jButtonDishSearch);
        jButtonDishSearch.setBounds(260, 300, 80, 30);
        DishMenu.add(jSeparator1);
        jSeparator1.setBounds(10, 290, 330, 10);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Price:");
        DishMenu.add(jLabel12);
        jLabel12.setBounds(10, 160, 50, 30);

        jLabelQuantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DishMenu.add(jLabelQuantity);
        jLabelQuantity.setBounds(150, 250, 50, 30);

        jSpinnerQuantity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerQuantityStateChanged(evt);
            }
        });
        DishMenu.add(jSpinnerQuantity);
        jSpinnerQuantity.setBounds(200, 250, 50, 30);

        jButtonUpdateInvoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonUpdateInvoice.setText("Update");
        jButtonUpdateInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateInvoiceActionPerformed(evt);
            }
        });
        DishMenu.add(jButtonUpdateInvoice);
        jButtonUpdateInvoice.setBounds(260, 250, 80, 30);

        jLabelMenu.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        DishMenu.add(jLabelMenu);
        jLabelMenu.setBounds(10, 194, 330, 40);

        InvoiceInformation.setLayout(null);

        Detail.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        Detail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Detail.setText("Detail");
        InvoiceInformation.add(Detail);
        Detail.setBounds(0, 0, 530, 60);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("State:");
        InvoiceInformation.add(jLabel1);
        jLabel1.setBounds(330, 190, 50, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Total:");
        InvoiceInformation.add(jLabel2);
        jLabel2.setBounds(60, 190, 100, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Table:");
        InvoiceInformation.add(jLabel3);
        jLabel3.setBounds(330, 70, 50, 20);

        jLabelInvoiceID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InvoiceInformation.add(jLabelInvoiceID);
        jLabelInvoiceID.setBounds(170, 70, 80, 20);

        jLabelInvoiceTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InvoiceInformation.add(jLabelInvoiceTotal);
        jLabelInvoiceTotal.setBounds(170, 190, 130, 20);

        jLabelInvoiceState.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InvoiceInformation.add(jLabelInvoiceState);
        jLabelInvoiceState.setBounds(390, 190, 90, 20);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Invoice ID:");
        InvoiceInformation.add(jLabel4);
        jLabel4.setBounds(60, 70, 100, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Staff:");
        InvoiceInformation.add(jLabel5);
        jLabel5.setBounds(60, 100, 100, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Date:");
        InvoiceInformation.add(jLabel6);
        jLabel6.setBounds(60, 130, 100, 20);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Date update:");
        InvoiceInformation.add(jLabel7);
        jLabel7.setBounds(60, 160, 100, 20);

        jLabelDateUpt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        InvoiceInformation.add(jLabelDateUpt);
        jLabelDateUpt.setBounds(170, 160, 200, 20);

        jLabelStaff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        InvoiceInformation.add(jLabelStaff);
        jLabelStaff.setBounds(170, 100, 200, 20);

        jLabelDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        InvoiceInformation.add(jLabelDate);
        jLabelDate.setBounds(170, 130, 200, 20);

        jLabelInvoiceTable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        InvoiceInformation.add(jLabelInvoiceTable);
        jLabelInvoiceTable.setBounds(390, 70, 80, 20);

        jTableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Dish Name", "Dish ID", "Unit Price", "Quantity", "Amount"
            }
        ));
        jTableDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableDetail);

        InvoiceInformation.add(jScrollPane2);
        jScrollPane2.setBounds(10, 250, 510, 250);

        jButtonCloseInvoice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButtonCloseInvoice.setText("Paid");
        jButtonCloseInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseInvoiceActionPerformed(evt);
            }
        });
        InvoiceInformation.add(jButtonCloseInvoice);
        jButtonCloseInvoice.setBounds(423, 213, 90, 30);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Invoice, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DishMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(InvoiceInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DishMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                    .addComponent(InvoiceInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Invoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
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

    private void jComboBoxTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTableActionPerformed
        // TODO add your handling code here:
        String selectedValue = jComboBoxTable.getSelectedItem().toString();
        jLabelTableName.setText(selectedValue);
    }//GEN-LAST:event_jComboBoxTableActionPerformed

    private void jButtonNewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewInvoiceActionPerformed
        // TODO add your handling code here:
        NewInvoice();
    }//GEN-LAST:event_jButtonNewInvoiceActionPerformed

    private void jTableInvoiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInvoiceMouseClicked
        // TODO add your handling code here:
        SetInvoiceInfo();
        RefreshDataInvoice();
    }//GEN-LAST:event_jTableInvoiceMouseClicked

    private void jComboBoxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoryActionPerformed
        // TODO add your handling code here:
        String selectedValue = jComboBoxCategory.getSelectedItem().toString();
        jLabelCate.setText(selectedValue);
    }//GEN-LAST:event_jComboBoxCategoryActionPerformed

    private void jButtonDishSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDishSearchActionPerformed
        // TODO add your handling code here:
        SetTableDataDish();
    }//GEN-LAST:event_jButtonDishSearchActionPerformed

    private void jTableDishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDishMouseClicked
        // TODO add your handling code here:
        SetDishInfo();
    }//GEN-LAST:event_jTableDishMouseClicked

    private void jTableDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDetailMouseClicked
        // TODO add your handling code here:
        SetDishDetailInfo();
    }//GEN-LAST:event_jTableDetailMouseClicked

    private void jSpinnerQuantityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerQuantityStateChanged
        // TODO add your handling code here:
        String selectedValue = jSpinnerQuantity.getValue().toString();
        jLabelQuantity.setText(selectedValue);
    }//GEN-LAST:event_jSpinnerQuantityStateChanged

    private void jButtonUpdateInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateInvoiceActionPerformed
        // TODO add your handling code here:
        
        while(true){
            if(jLabelInvoiceID.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please choose the invoice");
                return;
            }
            break;
        }
        
        
        while(true){
            if(jLabelDishID.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please choose the dish");
                return;
            }
            break;
        }
        
        try{
            cStmt = conn.prepareCall("call detail_update(?,?,?)");
            cStmt.setInt(1,Integer.parseInt(jLabelInvoiceID.getText()));
            cStmt.setInt(2,Integer.parseInt(jLabelDishID.getText()));
            cStmt.setInt(3,Integer.parseInt(jLabelQuantity.getText()));
            cStmt.executeQuery();
            JOptionPane.showMessageDialog(this, "Update invoice successful");
            SetInvoiceInfo(Integer.parseInt(jLabelInvoiceID.getText()));    //cap nhat lai hoa don hien tai
            RefreshDataMenu();
            return;
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,"Cannot update invoice, try again");
            return;
        }
    }//GEN-LAST:event_jButtonUpdateInvoiceActionPerformed

    private void jButtonCloseInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseInvoiceActionPerformed
        // TODO add your handling code here:
        CloseInvoice();
    }//GEN-LAST:event_jButtonCloseInvoiceActionPerformed

    private void jButtonChangePWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangePWActionPerformed
        // TODO add your handling code here:
        ChangePasswordFr cpw = new ChangePasswordFr();
        cpw.show();
    }//GEN-LAST:event_jButtonChangePWActionPerformed

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
            java.util.logging.Logger.getLogger(HomeStaffFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeStaffFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeStaffFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeStaffFr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeStaffFr().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Detail;
    private javax.swing.JPanel DishMenu;
    private javax.swing.JPanel Invoice;
    private javax.swing.JPanel InvoiceInformation;
    private javax.swing.JLabel Invoices;
    private javax.swing.JLabel LabelWelcome;
    private javax.swing.JLabel Menu;
    private javax.swing.JPanel PanelWelcome;
    private javax.swing.JLabel Table1;
    private javax.swing.JPanel background;
    private javax.swing.JButton jButtonChangePW;
    private javax.swing.JButton jButtonCloseInvoice;
    private javax.swing.JButton jButtonDishSearch;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonNewInvoice;
    private javax.swing.JButton jButtonUpdateInvoice;
    private javax.swing.JComboBox<String> jComboBoxCategory;
    private javax.swing.JComboBox<String> jComboBoxTable;
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
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JLabel jLabelDateUpt;
    private javax.swing.JLabel jLabelDishCate;
    private javax.swing.JLabel jLabelDishID;
    private javax.swing.JLabel jLabelDishName;
    private javax.swing.JLabel jLabelDishPrice;
    private javax.swing.JLabel jLabelDishUnit;
    private javax.swing.JLabel jLabelInvoiceID;
    private javax.swing.JLabel jLabelInvoiceState;
    private javax.swing.JLabel jLabelInvoiceTable;
    private javax.swing.JLabel jLabelInvoiceTotal;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JLabel jLabelQuantity;
    private javax.swing.JLabel jLabelStaff;
    private javax.swing.JLabel jLabelTableName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSpinner jSpinnerQuantity;
    private javax.swing.JTable jTableDetail;
    private javax.swing.JTable jTableDish;
    private javax.swing.JTable jTableInvoice;
    private javax.swing.JLabel txtAccount_id;
    // End of variables declaration//GEN-END:variables
}
