/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billingmanagementsystem;

/**
 *
 * @author User
 */
public class ProductData {
     private int ProductID;
    private String ProductName, Description, Remarks;
    private double Price;
    
    public ProductData(int ProductID, String ProductName, double Price, String Description, String Remarks){
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.Description = Description;
        this.Remarks = Remarks;
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
}
