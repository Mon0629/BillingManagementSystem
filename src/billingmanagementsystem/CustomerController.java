/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.sql.Connection;
import java.sql.Timestamp;

import customer.Customer;
import customer.CustomerDAOImpl;
import databaseSQL.DatabaseManager;
import billingmanagementsystem.ButtonTableCellFactory;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerController implements Initializable {

    @FXML private TableView<Customer> customerTableView;
    @FXML private TableColumn<Customer, Integer> customerIdCol; 
    @FXML private TableColumn<Customer,Timestamp> creationDateCol;
    @FXML private TableColumn<Customer,String> customerFirstNameCol;
    @FXML private TableColumn<Customer,String> customerLastNameCol;
    @FXML private TableColumn<Customer,String> emailCol;
    @FXML private TableColumn<Customer,String> townCol;
    @FXML private TableColumn<Customer,String> addressCol;
    @FXML private TableColumn<Customer,String> countryCol;
    @FXML private TableColumn<Customer,Integer> postalCol;
    @FXML private TableColumn<Customer,Boolean> editCol;
     
    CustomerDAOImpl CustomerDAO = new CustomerDAOImpl(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     	customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    	creationDateCol.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
    	customerFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    	customerLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    	emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    	townCol.setCellValueFactory(new PropertyValueFactory<>("town"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    	countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
    	postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        editCol.setCellFactory(new ButtonTableCellFactory());
    	
    	refreshCustomerTableview();
    	
    }    
    
    private void refreshCustomerTableview() {
    	CustomerDAO.fetchAllCustomers();
    	customerTableView.setItems(CustomerDAO.getCustomersData());
    	
    }
    
}
