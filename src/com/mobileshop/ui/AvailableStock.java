/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mobileshop.ui;
import com.mobileshop.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Delta
 */
public class AvailableStock extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AvailableStock.class.getName());

    /**
     * Creates new form AvailableStock
     */
    public AvailableStock() {
        initComponents();
        loadBrandFilter();
    }
    private void loadStockData() {
    DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
    dtm.setRowCount(0);
    
    String brand = cmbBrandFilter.getSelectedItem().toString();
    String model = cmbModelFilter.getSelectedItem().toString();
    String partName = txtPartName.getText().trim();
    
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT brand, modelName, partName, partQuality, partCode, ");
    sql.append("SUM(qty) as purchasedQty ");
    sql.append("FROM sparePurchaseItem WHERE 1=1 ");
    
    if (!brand.equals("All")) {
        sql.append(" AND brand = '").append(brand).append("' ");
    }
    if (!model.equals("All")) {
        sql.append(" AND modelName = '").append(model).append("' ");
    }
    if (!partName.isEmpty()) {
        sql.append(" AND partName LIKE '%").append(partName).append("%' ");
    }
    
    sql.append(" GROUP BY brand, modelName, partName, partQuality, partCode ");
    sql.append(" ORDER BY brand, modelName, partName");
    
    try (Connection con = DbConnection.getConnection();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql.toString())) {
        
        int sr = 1;
        int totalRows = 0;
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            int purchased = rs.getInt("purchasedQty");
            int sold = 0; // Baad me Sales table se lenge
            int available = purchased - sold;
            
            row.add(sr++);                      
            row.add(rs.getString("brand"));     
            row.add(rs.getString("modelName")); 
            row.add(rs.getString("partName"));  
            row.add(rs.getString("partQuality"));
            row.add(rs.getString("partCode"));  
            row.add(purchased);                 
            row.add(sold);                      
            row.add(available);                 
            
            dtm.addRow(row);
            totalRows++;
        }
        lblTotal.setText("Total Items: " + totalRows);
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Stock Load Error: " + e.getMessage());
    }
}
    private void loadBrandFilter() {
    // Table ka naam check kar: Brand hai ya BrandEntry
    String sql = "SELECT brandId, brandName FROM brandEntry ORDER BY brandName"; 
    try(Connection con = DbConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()) {
        
        cmbBrandFilter.removeAllItems();
        cmbBrandFilter.addItem("All");
        while (rs.next()) {
            String brandName = rs.getString("brandName");
            cmbBrandFilter.addItem(brandName); // Yahi sahi hai
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Brand Load Error: " + e.getMessage());
    }
}
    private void cmbBrandFilterActionPerformed(java.awt.event.ActionEvent evt) {                                               
    // Null check lagana zaruri hai
    if(cmbBrandFilter.getSelectedItem() == null) {
        return;
    }
    
    cmbModelFilter.removeAllItems();
    cmbModelFilter.addItem("All");
    
    String selectedBrand = cmbBrandFilter.getSelectedItem().toString();
    
    if(selectedBrand.equals("All")){
        loadStockData();
        return;
    }
    
    try (Connection con = DbConnection.getConnection()) {
        String getBrandIdSql = "SELECT brandId FROM brandEntry WHERE brandName = ?";
        PreparedStatement pst1 = con.prepareStatement(getBrandIdSql);
        pst1.setString(1, selectedBrand);
        ResultSet rs1 = pst1.executeQuery();
        
        if(rs1.next()){
            int brandId = rs1.getInt("brandId");
            String sql = "SELECT modelName FROM sparePurchaseItem WHERE brandId = ? ORDER BY modelName";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, brandId);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                cmbModelFilter.addItem(rs.getString("modelName"));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Model Load Error: " + e.getMessage());
    }
    loadStockData();
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
        cmbBrandFilter = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbModelFilter = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtPartName = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Brand");

        jLabel2.setText("Model");

        jLabel3.setText("Part Name");

        btnSearch.setText("Search");

        btnReset.setText("Reset");

        btnPrint.setText("Print");

        btnExport.setText("Export");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Sr No", "Brand", "Model", "Part Name", "Quality", "Part Code", "Pur.Qty", "Sold.Qty", "Avail.Qty"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lblTotal.setText("Total Items: 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbBrandFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbModelFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPartName, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(50, 50, 50)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnSearch)
                                    .addGap(41, 41, 41)
                                    .addComponent(btnExport))
                                .addComponent(btnReset)
                                .addComponent(btnPrint))))
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cmbBrandFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmbModelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSearch)
                            .addComponent(btnExport))
                        .addGap(18, 18, 18)
                        .addComponent(btnReset)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPartName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnPrint)))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(lblTotal)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.awt.EventQueue.invokeLater(() -> new AvailableStock().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbBrandFilter;
    private javax.swing.JComboBox<String> cmbModelFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtPartName;
    // End of variables declaration//GEN-END:variables
}
