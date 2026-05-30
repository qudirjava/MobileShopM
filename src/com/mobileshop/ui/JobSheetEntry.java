/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mobileshop.ui;

import com.mobileshop.db.DbConnection;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import com.mobileshop.db.DbConnection;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 *
 * @author Iphone
 */
public class JobSheetEntry extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JobSheetEntry.class.getName());

    /**
     * Creates new form JobSheetEntry
     */
    public JobSheetEntry() {
        initComponents();
        genrateJobNo();
        status();
        loadBrandCombo();
        
        dcJobDate.setDate(new java.util.Date());
    }
    
   private void clearForm(){
       genrateJobNo();
   dcJobDate.setDate(new java.util.Date());
    
    txtMobileNo.setText("");
    txtCustomerName.setText("");
    txtAddress.setText("");
    txtCustomerName.setEditable(true);
    txtAddress.setEditable(true);
    
    // Yahi line change hai bhai
    cmbBrand.setSelectedIndex(0);  // Pehla item select
    cmbModel.setSelectedIndex(0);  // Pehla item select
    txtIMEI.setText("");
    txtProblems.setText("");
    txtAccessories.setText("");
    txtEstAmount.setText("");
    cmbStatus.setSelectedIndex(0);
    
   
    
    // 8. Cursor Mobile No pe le jao naye entry ke liye
    txtMobileNo.requestFocus();
}
    private void printJobSheet(int jobId) {
    PrinterJob pj = PrinterJob.getPrinterJob();
    pj.setPrintable(new Printable() {
        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) return NO_SUCH_PAGE;
            
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            
            int y = 20;
            
            // Logo - DbConnection ke path se
            try {
                String logoPath = System.getProperty("user.dir") + File.separator + "logo.png";
                BufferedImage logo = ImageIO.read(new File(logoPath));
                g2d.drawImage(logo, 200, y, 100, 50, null);
                y += 60;
            } catch(Exception e){ y += 20; }
            
            // DbConnection se shop detail
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.drawString(DbConnection.SHOP_NAME, 120, y); y += 20;
            
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString(DbConnection.SHOP_ADDRESS, 100, y); y += 15;
            g2d.drawString(DbConnection.SHOP_MOBILE, 160, y); y += 15;
            g2d.drawString( DbConnection.SHOP_GST, 150, y); y += 25;
            
            g2d.drawLine(20, y, 480, y); y += 15;
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("JOB SHEET", 210, y); y += 20;
            
            // Baaki job details tere form se
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            g2d.drawString("Job No: " + txtJobNo.getText(), 30, y);
            g2d.drawString("Date: " + new SimpleDateFormat("yyyy-MM-dd").format(dcJobDate.getDate()), 300, y); y += 15;
            g2d.drawString("Customer: " + txtCustomerName.getText(), 30, y);
            g2d.drawString("Mobile: " + txtMobileNo.getText(), 300, y); y += 15;
            g2d.drawString("Device: " + cmbBrand.getSelectedItem() + " - " + cmbModel.getSelectedItem(), 30, y); y += 15;
            g2d.drawString("Problem: " + txtProblems.getText(), 30, y); y += 15;
            g2d.drawString("IMEI NO:" + txtIMEI.getText(), 30, y); y += 25;
            g2d.drawString("Est Amount: " + txtEstAmount.getText(), 30, y); y += 25;
            
            g2d.drawLine(20, y, 480, y); y += 20;
            g2d.drawString("Customer Signature", 30, y);
            g2d.drawString("Authorized Sign", 320, y);
            
            return PAGE_EXISTS;
        }
    });
    
    if (pj.printDialog()) {
        try { pj.print(); } catch (PrinterException e) { JOptionPane.showMessageDialog(this, "Print Error: " + e.getMessage()); }
    }
}
    private void status()
    {
        cmbStatus.removeAllItems();
        cmbStatus.addItem("Peding");
        cmbStatus.addItem("Reparing");
        cmbStatus.addItem("Completed");
        
    }
    
    private void genrateJobNo()
    {
        String sql="SELECT MAX(JobId) FROM JOB_SHEET";
        try(Connection con=DbConnection.getConnection();
                Statement st=con.createStatement();
                ResultSet rs=st.executeQuery(sql)){
            int jobId=1;
            if(rs.next()){
                jobId=rs.getInt(1)+1;
            }
            txtJobNo.setText("JS-"+jobId);
            
        } catch (Exception e) {
            txtJobNo.setText("JS-1001");
        }
    }
    
    void loadBrandCombo() {
    cmbBrand.removeAllItems();
    cmbBrand.addItem("Select Brand");
    String sql = "SELECT DISTINCT brand FROM sparePurchaseItem ORDER BY brand";
    try(Connection con = DbConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql)){
        while(rs.next()){
            cmbBrand.addItem(rs.getString("brand"));
        }
    } catch(Exception e){ e.printStackTrace(); }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtJobNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dcJobDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtMobileNo = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIMEI = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtProblems = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        txtAccessories = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btnSavePrint = new javax.swing.JButton();
        cmbBrand = new javax.swing.JComboBox<>();
        cmbModel = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtEstAmount = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Job No");

        txtJobNo.setEditable(false);

        jLabel2.setText("Date");

        jLabel3.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Mobile No");

        txtMobileNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMobileNoKeyPressed(evt);
            }
        });

        btnSearch.setText("Search Customer");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        jLabel5.setText("Customer Name");

        jLabel6.setText("Address");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane1.setViewportView(txtAddress);

        jLabel7.setText("Brand");

        jLabel8.setText("Model");

        jLabel9.setText("IMEI No");

        jLabel10.setText("Problems");

        txtProblems.setColumns(20);
        txtProblems.setRows(5);
        jScrollPane2.setViewportView(txtProblems);

        jLabel11.setText("Accessories");

        btnSavePrint.setText("Save and Print");
        btnSavePrint.addActionListener(this::btnSavePrintActionPerformed);

        cmbBrand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBrand.addActionListener(this::cmbBrandActionPerformed);

        cmbModel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Est.Amount");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtJobNo)
                    .addComponent(cmbStatus, 0, 88, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(dcJobDate, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtMobileNo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch))
                    .addComponent(jScrollPane1)
                    .addComponent(txtCustomerName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnSavePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(cmbModel, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtIMEI, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtEstAmount, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtAccessories, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)))))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcJobDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtJobNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMobileNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(txtIMEI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtAccessories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtEstAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSavePrint))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2273, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMobileNoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMobileNoKeyPressed
        // TODO add your handling code here:
        
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
        searchCustomer();
    }
}
    
    private void searchCustomer()
    {
        
    
    if(txtMobileNo.getText().isEmpty()){
        return;
    }
        try(Connection con=DbConnection.getConnection()) {
            
        PreparedStatement ps = con.prepareStatement("SELECT * FROM CUSTOMER_MASTER WHERE MobileNo = ?");
        ps.setString(1, txtMobileNo.getText());
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            txtCustomerName.setText(rs.getString("CustomerName"));
            txtAddress.setText(rs.getString("Address"));
            txtCustomerName.setEditable(false); // Purana customer hai to naam change na ho
            txtAddress.setEditable(false);
        } else {
            txtCustomerName.setText("");
            txtAddress.setText("");
            txtCustomerName.setEditable(true);
            txtAddress.setEditable(true);
            txtCustomerName.requestFocus();
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }

            
        
    
    }//GEN-LAST:event_txtMobileNoKeyPressed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        
        searchCustomer();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void cmbBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBrandActionPerformed
        // TODO add your handling code here:
        if(cmbBrand.getSelectedItem()==null)
        {
            return;
        }
        String brand = cmbBrand.getSelectedItem().toString();
        if(brand.equals("Select Brand"))
        {
            return;
        }
    
         cmbModel.removeAllItems();
         cmbModel.addItem("Select Model");
         String sql = "SELECT DISTINCT modelName FROM sparePurchaseItem WHERE brand=? ORDER BY modelName";
        try(Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql)){
        pst.setString(1, brand);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            cmbModel.addItem(rs.getString("modelName"));
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(this,"Error" +e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_cmbBrandActionPerformed

    private void btnSavePrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePrintActionPerformed
        // TODO add your handling code here:
        
        try(Connection con = DbConnection.getConnection())
        {
        con.setAutoCommit(false);
         // 1. Customer save/update
         int customerId;
         String checkSql = "SELECT CustomerId FROM CUSTOMER_MASTER WHERE MobileNo=?";
         PreparedStatement psCheck = con.prepareStatement(checkSql);
          psCheck.setString(1, txtMobileNo.getText().trim());
          ResultSet rs = psCheck.executeQuery();
          if(rs.next())
          {
               customerId = rs.getInt("CustomerId");
          }
          else{
              String insCust = "INSERT INTO CUSTOMER_MASTER(CustomerName, MobileNo, Address) VALUES(?,?,?)";
              
              PreparedStatement psCust=con.prepareStatement(insCust,Statement.RETURN_GENERATED_KEYS);
              
              
            psCust.setString(1, txtCustomerName.getText().trim());
            psCust.setString(2, txtMobileNo.getText().trim());
            psCust.setString(3, txtAddress.getText().trim());
            psCust.executeUpdate();
            ResultSet gk = psCust.getGeneratedKeys();
            gk.next();
            customerId = gk.getInt(1);
          }
       
        
          String insJob = "INSERT INTO JOB_SHEET(JobNo,JobDate,CustomerId,DeviceBrand,DeviceModel,IMEI,ProblemDesc,Accessories,Status,EstimatedAmount) VALUES(?,?,?,?,?,?,?,?,?,?)";
          
          PreparedStatement psJob = con.prepareStatement(insJob, Statement.RETURN_GENERATED_KEYS);
            psJob.setString(1, txtJobNo.getText());
            psJob.setString(2,  new SimpleDateFormat("yyyy-MM-dd").format(dcJobDate.getDate())); // JobDate TEXT hai tere me
            psJob.setInt(3, customerId);
            psJob.setString(4, cmbBrand.getSelectedItem().toString());
            psJob.setString(5, cmbModel.getSelectedItem().toString());
            psJob.setString(6, txtIMEI.getText().trim());
            psJob.setString(7, txtProblems.getText().trim());
            psJob.setString(8, txtAccessories.getText().trim());
            psJob.setString(9, cmbStatus.getSelectedItem().toString());
            psJob.setString(10, txtEstAmount.getText());
            psJob.executeUpdate();
            
            
            ResultSet jobRs=psJob.getGeneratedKeys();
            int jobId=0;
            if(jobRs.next()){
                jobId=jobRs.getInt(1);
            }

        con.commit();
        JOptionPane.showMessageDialog(this, "Job Saved: " + txtJobNo.getText());
        
            printJobSheet(jobId);
            
            clearForm();
            
        
        
         }
        catch(Exception e)
        {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            
        }
        
        
    }//GEN-LAST:event_btnSavePrintActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JobSheetEntry().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSavePrint;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbBrand;
    private javax.swing.JComboBox<String> cmbModel;
    private javax.swing.JComboBox<String> cmbStatus;
    private com.toedter.calendar.JDateChooser dcJobDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtAccessories;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtEstAmount;
    private javax.swing.JTextField txtIMEI;
    private javax.swing.JTextField txtJobNo;
    private javax.swing.JTextField txtMobileNo;
    private javax.swing.JTextArea txtProblems;
    // End of variables declaration//GEN-END:variables
}
