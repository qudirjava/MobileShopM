/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mobileshop.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Iphone
 */
public class DbConnection {
    
    public static final String SHOP_NAME = "I-Care Mobile Sale and Serveice";
    public static final String SHOP_ADDRESS = "Shop No. 12, ITI Corrner Nanded, MH - 458001";
    public static final String SHOP_MOBILE = "Mob: 8087494984";
    public static final String SHOP_GST = "GSTIN: 23ABCDE1234F1Z5";
    public static void main(String[] args) {
        DbConnection.getConnection();
    }
  
   public static Connection getConnection(){
       
        
        Connection con=null;
        
        try {
            
            String appDataPath=System.getenv("APPDATA")+File.separator+"MobileShop";
            String appDbPath=appDataPath+File.separator+"mobileShop.db";
            
            File folder=new File(appDataPath);
            if (!folder.exists()) {
                folder.mkdirs();
                
                
            }
            
            String url="jdbc:sqlite:"+appDbPath;
            con=DriverManager.getConnection(url);
            
           // System.out.println(con);
            
        } catch (Exception e) {
        }
        
        
        
        
        return con;
        
        
    }
    
   
    
}

    

