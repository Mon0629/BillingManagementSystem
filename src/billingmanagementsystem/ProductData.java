/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billingmanagementsystem;

import java.sql.*;
/**
 *
 * @author User
 */
public class ProductData {
     private int ProductID;
    private String ProductName, Description, Remarks;
    private double Price;
    private Blob Image;
    
    public ProductData(int ProductID, String ProductName, double Price,  String Remarks, String Description, Blob Image){
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.Remarks = Remarks;
        this.Description = Description;
        
        this.Image = Image;
    }
    
    public int getProductID(){
        return ProductID;
    }
    public String getProductName(){
        return ProductName;
    }
    public double getPrice(){
        return Price;
    }
    public String getDescription(){
        return Description;
    }
    public String getRemarks(){
        return Remarks;
    }
    public Blob getImage(){
        return Image;
    }
    
    
    public void setProductID(int ProductID){
        this.ProductID = ProductID;
    }
    public void setProductName(String ProductName){
       this.ProductName = ProductName;
    }
    public void setPrice(double Price){
        this.Price = Price;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }
    public void setRemarks(String Remarks){
        this.Remarks = Remarks;
    }
    public void setImage(Blob Image){
        this.Image = Image;
    }
    
    
   
}

