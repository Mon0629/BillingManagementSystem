/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML private TableColumn<Customer,Integer> editCol;
    @FXML private final TableCell<Customer, String> cell = new TableCell<Customer, String>();
     
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
    	
    	setupCustomerTableView();
    	
    }    
    
    private void refreshCustomerTableview() {
    	CustomerDAO.fetchAllCustomers();
    	customerTableView.setItems(CustomerDAO.getCustomersData());
    	
    }
    
    private void setupCustomerTableView() {
    	refreshCustomerTableview();
    	
//    	Callback<TableColumn<Customer, String>, TableCell<Customer, String>> cellFactory = (TableColumn<Customer, String> param) -> {
//             // make cell containing buttons
//             final TableCell<Customer, String> cell = new TableCell<Customer, String>() {
//                 
//                 public void updateItem(String item, boolean empty) {
//                     super.updateItem(item, empty);
//                     //that cell created only on non-empty rows
//                     if (empty) {
//                         setGraphic(null);
//                         setText(null);
//
//                     } else {
//
//                         private final Button deleteButton = new Button("Delete");
//                         private final Button editButton = new Button("Edit");
//
//                         deleteButton.setOnAction((MouseEvent event) -> {
//                             
//                             try {
//                            	 Connection connection = DatabaseManager.getConnection();
//                                 Customer customer = customerTableView.getSelectionModel().getSelectedItem();
//                                 String query = "DELETE FROM bms.customers WHERE id  ="+customer.getCustomerId();
//                                 
//                                 preparedStatement = connection.prepareStatement(query);
//                                 preparedStatement.execute();
//                                 refreshCustomerTableview();
//                                 
//                             } catch (SQLException ex) {
//                                 Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
//                             }
//                                                      
//                         });
////                         editIcon.setOnMouseClicked((MouseEvent event) -> {
////                             
////                             Customer = studentsTable.getSelectionModel().getSelectedItem();
//                             FXMLLoader loader = new FXMLLoader ();
//                             loader.setLocation(getClass().getResource("/tableView/addStudent.fxml"));
//                             try {
//                                 loader.load();
//                             } catch (IOException ex) {
//                                 Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
//                             }
//                             
//                             AddStudentController addStudentController = loader.getController();
//                             addStudentController.setUpdate(true);
//                             addStudentController.setTextField(Customer.getId(), Customer.getName(), 
//                                     Customer.getBirth().toLocalDate(),Customer.getAdress(), Customer.getEmail());
//                             Parent parent = loader.getRoot();
//                             Stage stage = new Stage();
//                             stage.setScene(new Scene(parent));
//                             stage.initStyle(StageStyle.UTILITY);
//                             stage.show();
////
//                         });

//                         HBox managebtn = new HBox(editIcon, deleteIcon);
//                         managebtn.setStyle("-fx-alignment:center");
//                         HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
//                         HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
//
//                         setGraphic(managebtn);
//
//                         setText(null);
//                     }
//                 }
//             }
//             return cell;
//         }
//          editCol.setCellFactory(cellFactory);
//          refreshCustomerTableview();
//          
//    	}
    }
    
}
