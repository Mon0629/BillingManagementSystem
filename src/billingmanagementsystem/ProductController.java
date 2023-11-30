/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package billingmanagementsystem;

import databaseSQL.DatabaseManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ProductController implements Initializable {

    @FXML
    private TableView<ProductData> product_table;
    @FXML
    private TableColumn<ProductData, Integer> productID;
    @FXML
    private TableColumn<ProductData, String> productName;
    @FXML
    private TableColumn<ProductData, Double> price;
    @FXML
    private TableColumn<ProductData, String> Description;
    @FXML
    private TableColumn<ProductData, String> Remarks;
    @FXML
    private TableColumn<ProductData, Boolean> editcolumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProductTable();
    }    

    
     //loading prod into the table
     private void ProductTable(){
         product_table.getItems().clear();
        productID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Remarks.setCellValueFactory(new PropertyValueFactory<>("Remarks"));
        editcolumn.setCellFactory(new ButtonCellFactory());
        editcolumn.setCellFactory(new ButtonCellFactory());
        
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
                     rs.getString("Remarks"),
                     (com.mysql.cj.jdbc.Blob) (Blob) rs.getBlob("Image")));
            }
            
            
        } catch (SQLException ex)
        {
            Logger.getLogger(ProductTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
     
     void refresh(){
     ProductTable();
     }
     

    @FXML
    private void SearchProduct(KeyEvent event) {
    }

    @FXML
private void OpenProductDetails(MouseEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductDetails.fxml"));

        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

    } catch (IOException ex) {
        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
    }
}



    @FXML
    private void RefreshTable(MouseEvent event) {
        ProductTable();
    }

    
    
}
