/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;
import billingmanagementsystem.BillingsController;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import databaseSQL.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author User
 */
public class CustomerTableController implements Initializable {

    @FXML
    private TableView<CustomerData> customer_table;
    @FXML
    private TableColumn<CustomerData, Integer> customerID;
    @FXML
    private TableColumn<CustomerData, String> firstName;
    @FXML
    private TableColumn<CustomerData, String> lastName;
    @FXML
    private TableColumn<CustomerData, String> email;
    @FXML
    private TableColumn<CustomerData, String> address;
    @FXML
    private TableColumn<CustomerData, String> town;
    @FXML
    private TableColumn<CustomerData, String> country;
    @FXML
    private TableColumn<CustomerData, Integer> postal;
    @FXML
    private TextField searchbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       customerTable();
    }    
    private BillingsController billingsController;
    public void setBillingsController(BillingsController controller) {
    this.billingsController = controller;
 }


 
    @FXML
    private void tableclicked(MouseEvent event) {
        if (event.getClickCount() == 1) {
        CustomerData customer = customer_table.getSelectionModel().getSelectedItem();
        if (customer != null && billingsController != null) {
            billingsController.setCustomerData(customer);
          ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
    }

    @FXML
    private void searchcustomer(KeyEvent event) throws SQLException {
        String input = searchbox.getText();
    if (!input.isEmpty()) {
        ObservableList<CustomerData> originalList = customer_table.getItems();
        ObservableList<CustomerData> filteredList = FXCollections.observableArrayList();

        for (CustomerData customer : originalList) {
            if (String.valueOf(customer.getCustomerID()).contains(input)
                    || customer.getFirstName().toLowerCase().contains(input.toLowerCase())
                    || customer.getLastName().toLowerCase().contains(input.toLowerCase())) {
                filteredList.add(customer);
            }
        }

        customer_table.getItems().setAll(filteredList);
    } else {
        customer_table.getItems().clear();
        customerTable(); // Load all data again
    }
    }



    private void customerTable(){
        
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        town.setCellValueFactory(new PropertyValueFactory<>("town"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        postal.setCellValueFactory(new PropertyValueFactory<>("postal"));
        
        
        try
        {
            Connection con = DatabaseManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * from customers");
            
            while(rs.next()){
                customer_table.getItems().add(new CustomerData(
                rs.getInt("customerID"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("town"),
                rs.getString("country"),
                rs.getInt("postal")
                ));
            
        } 
    
    
    
}catch (SQLException ex)
        {
            Logger.getLogger(CustomerTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}