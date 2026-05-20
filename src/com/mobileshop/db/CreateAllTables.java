/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobileshop.db;

import  java.sql.*;
import javax.swing.JOptionPane;
import  java.sql.*;


/**
 *
 * @author Iphone
 */
public class CreateAllTables {
    
    
   
    public static void createTables()
    {
        
        
        try (Connection con=DbConnection.getConnection()){
             
           //  System.out.println(con);
             
            if(con!=null)
            {
                Statement st=con.createStatement();
                st.execute("PRAGMA foreign_key = NO");
                //Table 1 SupplierMaster Table
                String supplierMaster="CREATE TABLE IF NOT EXISTS supplierMaster (suppId INTEGER PRIMARY KEY AUTOINCREMENT ,suppName TEXT NOT NULL,suppShopName TEXT NOT NULL,suppAddress TEXT ,suppContact TEXT NOT NULL,suppGst TEXT) ";
                st.executeUpdate(supplierMaster);
                // Table 2 UserTable 
                String UserTable="CREATE TABLE IF NOT EXISTS UserTable (UserId INTEGER PRIMARY KEY AUTOINCREMENT ,UserName TEXT NOT NULL,Password TEXT ,Roll TEXT )";
                st.executeUpdate(UserTable);
                // Table 3 saprePurchaseEntry
                String sparePurchaseEntry="CREATE TABLE IF NOT EXISTS sparePurchaseEntry("
                        +"purchaseId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"billNo TEXT NOT NULL UNIQUE,"
                        +"purchaseDate TEXT NOT NULL,"
                        +"suppId INTEGER NOT NULL,"
                        +"suppName TEXT NOT NULL,"
                        +"brandId INTEGER NOT NULL,"
                        +"brandName TEXT NOT NULL,"
                        +"totalQty INTEGER NOT NULL,"
                        +"netAmount REAL NOT NULL,"
                        +"pymentType TEXT NOT NULL CHECK(pymentType IN ('Cash','Credit','UPI','Card')),"
                        +"createAt TEXT DEFAULT CURRENT_TIMESTAMP,"
                        +"FOREIGN KEY (suppId) REFERENCES supplierMaster(suppId),"
                        +"FOREIGN KEY (brandId) REFERENCES brandEntry(brandId)"
                        +")";
                st.executeUpdate(sparePurchaseEntry);
                // Table 4 saprePurchaseItem
                String sparePurchaseItem="CREATE TABLE IF NOT EXISTS sparePurchaseItem("
                        +"itemId INTEGER PRIMARY KEY AUTOINCREMENT,"
                        +"purchaseId INTEGER NOT NULL,"
                        +"brand TEXT NOT NULL,"
                        +"modelName TEXT NOT NULL,"
                        +"partName TEXT NOT NULL,"
                        +"partQuality TEXT ,"
                        +"partCode TEXT,"
                        +"qty INTEGER NOT NULL DEFAULT 1,"
                        +"purchaseRate REAL NOT NULL,"
                        +"amount REAL NOT NULL,"
                        +"FOREIGN KEY(purchaseId)REFERENCES sparePurchaseEntry(purchaseId) ON DELETE CASCADE"
                        +")";
                 st.executeUpdate(sparePurchaseItem);
                 
                 
                 //Table 5 Brand
                 
                 String brandEntry="CREATE TABLE IF NOT EXISTS brandEntry("
                         +"brandId INTEGER PRIMARY KEY AUTOINCREMENT,"
                         +"brandName TEXT NOT NULL"
                         +")";
                 st.executeUpdate(brandEntry);
                 System.out.println("Brand table created succesfully!!");
                         
                
                JOptionPane.showMessageDialog(null,"All table Created Successfully");
                
                
            } 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error"+ e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        createTables();
        
    }
    
}
