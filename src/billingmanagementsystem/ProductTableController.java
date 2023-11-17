/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import databaseSQL.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
/**
 * FXML Controller class
 *
 * @author User
 */
public class ProductTableController implements Initializable {

    @FXML
    private TableView<ProductData> product_table;
    @FXML
    private TableColumn<ProductData, Integer> productID;
    @FXML
    private TableColumn<ProductData, String> productName;
    @FXML
    private TableColumn<ProductData, Double> price;
    @FXML
    private TableColumn<ProductData, String> description;
    @FXML
    private TableColumn<ProductData, String> remarks;
    @FXML
    private TextField searchbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ProductTable();
    }    

    private void ProductTable(){
    
        productID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        remarks.setCellValueFactory(new PropertyValueFactory<>("Remarks"));
        
        try
        {
            Connection con = DatabaseManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");
            
            while(rs.next()){
             product_table.getItems().add(new ProductData(
                     rs.getInt("ProductID"),
                     rs.getString("ProductName"),
                     rs.getDouble("Price"),
                     rs.getString("Description"),
                     rs.getString("Remarks")
                    
             ));
            }
            
            
        } catch (SQLException ex)
        {
            Logger.getLogger(ProductTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    @FXML
    private void tableclicked(MouseEvent event) {
        
        if (event.getClickCount() == 1) {
        ProductData product = product_table.getSelectionModel().getSelectedItem();
        if (product != null && billingsController != null) {
            billingsController.setProductData(product);
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }
    }
}
    

//para to sa billing, para makuha yung instance ng controller
       private BillingsController billingsController;

    public void setBillingsController(BillingsController controller) {
    this.billingsController = controller;
    }   

    @FXML
    private void searchproduct(KeyEvent event) {
        String input = searchbox.getText();
    if (!input.isEmpty()) {
        ObservableList<ProductData> originalList = product_table.getItems();
        ObservableList<ProductData> filteredList = FXCollections.observableArrayList();

        for (ProductData product : originalList) {
            if (String.valueOf(product.getProductID()).contains(input)
                    || product.getProductName().toLowerCase().contains(input.toLowerCase())) {
                filteredList.add(product);
            }
        }

        product_table.getItems().setAll(filteredList);
    } else {
        product_table.getItems().clear();
        ProductTable(); // Load all data again
    }
    
    }
    
}

