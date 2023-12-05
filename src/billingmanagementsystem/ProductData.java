/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billingmanagementsystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import javafx.scene.image.Image;
/**
 *
 * @author User
 */
public class ProductData {
     private int ProductID, Stocks;
    private String ProductName, Description, parentType, type, Brand, otherAttributes;
    private double Price;
    private Image Image;
    
    public ProductData(int ProductID, String ProductName, double Price,int Stocks,String Description, Blob Image, String parentType, String type, String Brand, String otherAttributes){
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
        this.Stocks = Stocks;
        this.Description = Description;
        this.Image = convertBlobToImage(Image);
        this.parentType=parentType;
        this.type=type;
        this.Brand=Brand;
        this.otherAttributes=otherAttributes;
    }
    
    private Image convertBlobToImage(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            return new Image(new ByteArrayInputStream(data));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        }
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
    public int getStocks(){
    return Stocks;
    }
    
    public String getDescription(){
        return Description;
    }
   
    public Image getImage(){
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
    
    public void setStocks(int Stocks){
    this.Stocks=Stocks;
    }
    
    public void setDescription(String Description){
        this.Description = Description;
    }
   
    public void setImage(Image Image){
        this.Image = Image;
    }
    
    
    
    
    
    
    
    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        this.Brand = brand;
    }

    public String getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(String otherAttributes) {
        this.otherAttributes = otherAttributes;
    }
    
   
}

