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

    

