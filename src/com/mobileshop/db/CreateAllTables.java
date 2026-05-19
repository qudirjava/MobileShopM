/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobileshop.db;

import  java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Iphone
 */
public class CreateAllTables {
    
    
   
    public static void createTables()
    {
        
        
        try {
             Connection con=DbConnection.getConnection();
           //  System.out.println(con);
             
            if(con!=null)
            {
                String supplierMaster="CREATE TABLE IF NOT EXISTS supplierMaster (suppId INTEGER PRIMARY KEY AUTOINCREMENT ,suppName TEXT NOT NULL,suppShopName TEXT NOT NULL,suppAddress TEXT ,suppContact TEXT NOT NULL,suppGst TEXT) ";
                
                Statement st=con.createStatement();
                st.executeUpdate(supplierMaster);
                
                String UserTable="CREATE TABLE IF NOT EXISTS UserTable (UserId INTEGER PRIMARY KEY AUTOINCREMENT ,UserName TEXT NOT NULL,Password TEXT ,Roll TEXT )";
                
                st.executeUpdate(UserTable);
            } 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error"+ e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        
    }
    
}
