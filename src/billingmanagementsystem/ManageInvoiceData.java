/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billingmanagementsystem;

import databaseSQL.DatabaseManager;
import java.sql.Timestamp;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author User
 */
public class ManageInvoiceData {
   
   private int customerID;
    private Timestamp creationDate;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;
    private String address;
    private String town;
    private int postal;

    private int billID;
    private int shipCustomerID;
    private Date issueDate;
    private Date dueDate;
    private String docType;
    private Timestamp transactionAdded;

    private int lineItemID;
    private int productID;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double lineItemTotal;
    
    public ManageInvoiceData(int customerID, Timestamp creationDate, String firstName, String lastName,
                                String contactNumber, String email, String address, String town, int postal,
                                int billID, int shipCustomerID, Date issueDate, Date dueDate, String docType,
                                Timestamp transactionAdded, int lineItemID, int productID, String productName,
                                int quantity, double unitPrice, double lineItemTotal) {
        this.customerID = customerID;
        this.creationDate = creationDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.town = town;
        this.postal = postal;

        this.billID = billID;
        this.shipCustomerID = shipCustomerID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.docType = docType;
        this.transactionAdded = transactionAdded;

        this.lineItemID = lineItemID;
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineItemTotal = lineItemTotal;
    }

    
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public int getPostal() {
        return postal;
    }

    public void setPostal(int postal) {
        this.postal = postal;
    }

    // Bill Table
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getShipCustomerID() {
        return shipCustomerID;
    }

    public void setShipCustomerID(int shipCustomerID) {
        this.shipCustomerID = shipCustomerID;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Timestamp getTransactionAdded() {
        return transactionAdded;
    }

    public void setTransactionAdded(Timestamp transactionAdded) {
        this.transactionAdded = transactionAdded;
    }

    // LineItems Table
    public int getLineItemID() {
        return lineItemID;
    }

    public void setLineItemID(int lineItemID) {
        this.lineItemID = lineItemID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public double getLineItemTotal(){
        return lineItemTotal;
    }
    
    public void setLineItemTotal(double lineItemTotal){
    this.lineItemTotal = lineItemTotal;
    }
    
    
    
     
}
